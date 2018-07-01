package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.haybankz.myjournal.data.FirebaseDbHelper;
import com.haybankz.myjournal.model.JournalModel;

public class AddJournalViewModel extends AndroidViewModel {





    private DatabaseReference mReference;

    public AddJournalViewModel(@NonNull Application application) {
        super(application);

        mReference = FirebaseDbHelper.getDatabaseRef();

    }

    public void addJournal(JournalModel journal){

        String key = mReference.push().getKey();
        journal.setKey(key);
        assert key != null;
        mReference.child(key).setValue(journal);

    }




}
