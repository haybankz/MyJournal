package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.haybankz.myjournal.data.FirebaseDbHelper;
import com.haybankz.myjournal.model.JournalModel;

public class EditJournalViewModel extends AndroidViewModel {


    private DatabaseReference mReference;
    private JournalModel mJournal;

    public EditJournalViewModel(@NonNull Application application) {
        super(application);
        mReference = FirebaseDbHelper.getDatabaseRef();
    }

    public void editJournal (JournalModel journal){
        setJournal(journal);
        mReference.child(journal.getKey()).setValue(journal);
    }

    public void setJournal(JournalModel mJournal){
        this.mJournal = mJournal;
    }

    public JournalModel getJournal(){
        return mJournal;
    }
}

