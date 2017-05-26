package com.igordubrovin.tfsmsg.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Игорь on 26.05.2017.
 */

public abstract class DefaultValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //empty
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        //empty
    }
}
