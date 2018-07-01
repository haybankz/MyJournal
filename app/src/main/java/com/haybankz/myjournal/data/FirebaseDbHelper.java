package com.haybankz.myjournal.data;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haybankz.myjournal.Constant;

public class FirebaseDbHelper {

    private static FirebaseDatabase mDatabase;
    private static DatabaseReference mReference;

    public static DatabaseReference getDatabaseRef(){
        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference().child(Constant.JOURNAL_KEY);

        return mReference;
    }

}
