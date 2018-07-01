package com.haybankz.myjournal.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.haybankz.myjournal.R;
import com.haybankz.myjournal.model.JournalModel;
import com.haybankz.myjournal.viewmodel.AddJournalViewModel;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddJournalActivity extends AppCompatActivity {

    @BindView(R.id.editText_title) EditText titleEt;

    @BindView(R.id.editText_content) EditText contentEt;

    private AddJournalViewModel viewModel;

    Calendar cal;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_journal);

        ButterKnife.bind(this);






        viewModel = ViewModelProviders.of(this).get(AddJournalViewModel.class);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);

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
            case R.id.action_save:

                String title = titleEt.getText().toString().trim();
                String content = contentEt.getText().toString().trim();

                cal = Calendar.getInstance();
                date = cal.getTime();

                viewModel.addJournal(new JournalModel("", title, content,  date.getTime()));

                finish();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
