package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.haybankz.myjournal.data.FirebaseDbHelper;
import com.haybankz.myjournal.model.JournalModel;

public class AddJournalViewModel extends AndroidViewModel {

//   private AppDatabase appDatabase;
//   private final LiveData<List<Journal>> journalListLiveData;


//    FirebaseDatabase mDatabase;
private DatabaseReference mReference;

    public AddJournalViewModel(@NonNull Application application) {
        super(application);
//        appDatabase = AppDatabase.getDatabase(this.getApplication());
//        journalListLiveData = appDatabase.journalRepo().getAllJournals();
//        Journal mJournal = appDatabase.journalRepo().getJournalById("");

//        mDatabase = FirebaseDatabase.getInstance();

//        mReference = mDatabase.getReference().child("journals");

        mReference = FirebaseDbHelper.getDatabaseRef();

    }

    public void addJournal(JournalModel journal){

        String key = mReference.push().getKey();
        journal.setKey(key);
        assert key != null;
        mReference.child(key).setValue(journal);

//        new AddEditJournalAsyncTask(appDatabase).execute(mJournal);
    }

    public void deleteJournal(JournalModel journal){

        mReference.child(journal.getKey()).removeValue();
//        new DeleteJournalAsyncTask(appDatabase).execute(mJournal);
    }

//    public LiveData<List<Journal>> getJournalListLiveData() {
//        return journalListLiveData;
//    }

    //Async task class to handle database insert and update
//    private static class AddEditJournalAsyncTask extends AsyncTask<Journal, Void, Void>{
//
//
//        private AppDatabase db;
//
//
//        public AddEditJournalAsyncTask(AppDatabase appDatabase){
//            db = appDatabase;
//        }
//
//        @Override
//        protected Void doInBackground(final Journal... journals) {
//            db.journalRepo().addJournal(journals[0]);
//            return null;
//        }
//    }
//
//
//    //Async task class to handle database delete
//    private static class DeleteJournalAsyncTask extends AsyncTask<Journal, Void, Void>{
//
//
//        private AppDatabase db;
//
//        public DeleteJournalAsyncTask(AppDatabase appDatabase){
//            db = appDatabase;
//        }
//
//        @Override
//        protected Void doInBackground(final Journal... journals) {
//            db.journalRepo().deleteJournal(journals[0]);
//            return null;
//        }
//
//    }


//    public enum DatabaseOps{ INSERT,
//        EDIT,
//        SELECT_ONE,
//        SELECT_ALL,
//        DELETE }
}
