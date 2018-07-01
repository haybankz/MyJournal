package com.haybankz.myjournal.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.haybankz.myjournal.Constant;
import com.haybankz.myjournal.R;
import com.haybankz.myjournal.data.DateConverter;
import com.haybankz.myjournal.model.JournalModel;
import com.haybankz.myjournal.viewmodel.EditJournalViewModel;
import com.haybankz.myjournal.viewmodel.JournalListItemViewModel;
import com.haybankz.myjournal.viewmodel.ViewJournalViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewJournalActivity extends AppCompatActivity {

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.content_text_view)
    TextView contentTextView;

    @BindView(R.id.date_text_view) TextView dateTextView;

    ViewJournalViewModel viewModel;
     JournalModel journal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal);

        ButterKnife.bind(this);

        JournalModel j = null;

        if(getIntent() != null) {
            Bundle b = getIntent().getExtras();
            assert b != null;
            j = (JournalModel) b.get(Constant.JOURNAL_KEY);


        }



        viewModel = ViewModelProviders.of(this).get(ViewJournalViewModel.class);

        assert j != null;
        journal = viewModel.getJournal(j.getKey());


        if(journal == null){
            journal = j;
            setupUi(j);
        }else{
            setupUi(journal);
        }







    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUi(journal);

    }


    public void setupUi(JournalModel journal){
        assert journal != null;
//        viewModel.setJournal(journal);
        titleTextView.setText(journal.getTitle());
        dateTextView.setText(DateConverter.getDateString(journal.getCreatedOn()));
        contentTextView.setText(journal.getContent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_journal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_edit:

                Intent editJournalIntent = new Intent(ViewJournalActivity.this, EditJournalActivity.class);
                editJournalIntent.putExtra(Constant.JOURNAL_KEY, journal);

                startActivity(editJournalIntent);
                finish();

                break;

            case R.id.action_delete:

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
