package com.haybankz.myjournal.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.haybankz.myjournal.Constant;
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

//    @BindView(R.id.textView_date) TextView dateTv;

//    @BindView(R.id.btn_save) FloatingActionButton saveFab;

//    @BindView(R.id.button_save) Button saveBtn;

    private AddJournalViewModel viewModel;

    Calendar cal;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_journal);

        ButterKnife.bind(this);





//        cal = Calendar.getInstance();
//        date = cal.getTime();

//        dateTv.setText(String.valueOf(date));
        viewModel = ViewModelProviders.of(this).get(AddJournalViewModel.class);



//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = titleEt.getText().toString().trim();
//                String content = contentEt.getText().toString().trim();
//
//
//                cal = Calendar.getInstance();
//                date = cal.getTime();
//
////                dateTv.setText(String.valueOf(date));
//
//                viewModel.addJournal(new JournalModel("", title, content,  date.getTime()));
//
//                finish();
//
//
//            }
//        });

//        saveFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String title = titleEt.getText().toString().trim();
//                String content = contentEt.getText().toString().trim();
//
//
//                cal = Calendar.getInstance();
//                date = cal.getTime();
//
////                dateTv.setText(String.valueOf(date));
//
//                viewModel.addJournal(new JournalModel("", title, content,  date.getTime()));
//
//                finish();
//
//            }
//        });
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
