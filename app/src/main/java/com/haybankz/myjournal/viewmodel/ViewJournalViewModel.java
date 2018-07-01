package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.haybankz.myjournal.data.FirebaseDbHelper;
import com.haybankz.myjournal.model.JournalModel;

public class ViewJournalViewModel extends AndroidViewModel {
    private DatabaseReference database;
    private JournalModel journal;

    public ViewJournalViewModel(@NonNull Application application) {
        super(application);
        database = FirebaseDbHelper.getDatabaseRef();
    }


    public JournalModel getJournal(String key){

        database.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                journal = new JournalModel();

                    journal = dataSnapshot.getValue(JournalModel.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return journal;
    }

    public void setJournal(JournalModel journal){
        this.journal = journal;
    }

    public void deleteJournal(JournalModel journal){

        database.child(journal.getKey()).removeValue();

    }
}
