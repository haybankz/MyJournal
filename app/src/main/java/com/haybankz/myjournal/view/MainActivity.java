package com.haybankz.myjournal.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.haybankz.myjournal.Constant;
import com.haybankz.myjournal.R;
import com.haybankz.myjournal.adapter.JournalRecyclerViewAdapter;
import com.haybankz.myjournal.listeners.ClickListener;
import com.haybankz.myjournal.listeners.OnFirebaseDataChanged;
import com.haybankz.myjournal.listeners.RecyclerTouchListener;
import com.haybankz.myjournal.model.JournalModel;
import com.haybankz.myjournal.viewmodel.JournalListItemViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnFirebaseDataChanged {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.journal_recycler_view) RecyclerView journalRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.no_journal_layout) LinearLayout emptyLayout;
    @BindView(R.id.no_network_layout) LinearLayout noNetworkLayout;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefresh;

    private JournalListItemViewModel viewModel;
    private JournalRecyclerViewAdapter adapter;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    private GoogleSignInAccount account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if(getIntent() != null){
            Bundle b = getIntent().getExtras();
            assert b != null;
            GoogleSignInAccount acct = (GoogleSignInAccount) b.get("account");
            assert acct != null;
            account = acct;
        }

        journalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JournalRecyclerViewAdapter(this, new ArrayList<JournalModel>());
        journalRecyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(JournalListItemViewModel.class);

        viewModel.setupAdapter(adapter, this);

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isConnectedToInternet()) {

                    viewModel.setupAdapter(adapter, new OnFirebaseDataChanged() {
                        @Override
                        public void dataChanged() {

                            adapter.notifyDataSetChanged();

                            if (adapter.getItemCount() == 0) {
                                emptyLayout.setVisibility(View.VISIBLE);
                                loadingLayout.setVisibility(View.GONE);
                                journalRecyclerView.setVisibility(View.GONE);
                                noNetworkLayout.setVisibility(View.GONE);
                            } else {
                                journalRecyclerView.setVisibility(View.VISIBLE);
                                loadingLayout.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.GONE);
                                noNetworkLayout.setVisibility(View.GONE);
                            }


                        }
                    });

                }else if(!isConnectedToInternet() && adapter.getItemCount() > 0){

                    journalRecyclerView.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.GONE);
                    noNetworkLayout.setVisibility(View.GONE);

                }else{

                    // show no network layout
                    noNetworkLayout.setVisibility(View.VISIBLE);
                    journalRecyclerView.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                }
                swipeRefresh.setRefreshing(false);
            }
        });


        if(isConnectedToInternet()) {
            loadingLayout.setVisibility(View.VISIBLE);
        }else{
            // show no network layout
            noNetworkLayout.setVisibility(View.VISIBLE);
            journalRecyclerView.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddJournalActivity.class);
                intent.putExtra(Constant.ACCOUNT_KEY, account);
                startActivity(intent);

            }
        });


        journalRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                journalRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                JournalModel journal = adapter.getItem(position);

                Intent viewJournalIntent = new Intent(MainActivity.this, ViewJournalActivity.class);

                viewJournalIntent.putExtra(Constant.JOURNAL_KEY, journal);
                viewJournalIntent.putExtra(Constant.ACCOUNT_KEY, account);

                startActivity(viewJournalIntent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        Toast.makeText(this, "Welcome, " + account.getDisplayName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            viewModel.signOut();
            Intent signInIntent = new Intent(this, LoginActivity.class);
            startActivity(signInIntent);
            finish();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

//       viewModel.setupAdapter(adapter, this);

    }



    @Override
    public void dataChanged() {

        adapter.notifyDataSetChanged();

        if(adapter.getItemCount() == 0){
            emptyLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            journalRecyclerView.setVisibility(View.GONE);
            noNetworkLayout.setVisibility(View.GONE);
        }else{
            journalRecyclerView.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
            noNetworkLayout.setVisibility(View.GONE);
        }
        swipeRefresh.setRefreshing(false);
    }


    public boolean isConnectedToInternet(){


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        networkInfo = connectivityManager.getActiveNetworkInfo();


        // true if there is network and its connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
