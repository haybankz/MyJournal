package com.haybankz.myjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.haybankz.myjournal.adapter.JournalRecyclerViewAdapter;
import com.haybankz.myjournal.data.FirebaseDbHelper;
import com.haybankz.myjournal.data.GoogleAuthHelper;
import com.haybankz.myjournal.listeners.OnFirebaseDataChanged;
import com.haybankz.myjournal.model.JournalModel;

import java.util.ArrayList;
import java.util.List;

public class JournalListItemViewModel extends AndroidViewModel {


    public JournalListItemViewModel(@NonNull Application application) {
        super(application);


    }





    private static final DatabaseReference database = FirebaseDbHelper.getDatabaseRef();
    private OnFirebaseDataChanged dataChangeListener;


    public void setupAdapter(final JournalRecyclerViewAdapter adapter,OnFirebaseDataChanged onFirebaseDataChanged){

        dataChangeListener = onFirebaseDataChanged;


         database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<JournalModel> journalModelList = new ArrayList<>();
                for (DataSnapshot journalSnapshot : dataSnapshot.getChildren()) {
                    Log.d("JournaListItemViewModel", "onDataChange: journal snapshot = " + journalSnapshot.toString());
                    JournalModel journal = journalSnapshot.getValue(JournalModel.class);
                    assert journal != null;
                    Log.d("JournaListItemViewModel", "onDataChange: "+ journal.getKey() + "\n\n\n");
                    journalModelList.add(journal);
                }
                adapter.addAllItems(journalModelList);

                dataChangeListener.dataChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void signOut(){
        GoogleAuthHelper.getClient(getApplication()).signOut();

    }

    public void deleteJournal(JournalModel journal){

        database.child(journal.getKey()).removeValue();

    }



}
