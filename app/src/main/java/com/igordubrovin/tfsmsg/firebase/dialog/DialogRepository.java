package com.igordubrovin.tfsmsg.firebase.dialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.igordubrovin.tfsmsg.firebase.DefaultValueEventListener;
import com.igordubrovin.tfsmsg.firebase.DialogItemValueListener;
import com.igordubrovin.tfsmsg.firebase.OnTransactionComplete;
import com.igordubrovin.tfsmsg.utils.DialogIdManager;
import com.igordubrovin.tfsmsg.utils.DialogItem;

import java.util.LinkedList;

import javax.inject.Inject;

/**
 * Created by Игорь on 26.05.2017.
 */

public class DialogRepository {
    //private final static DialogRepository instance = new DialogRepository();
    private final DatabaseReference db;
    private final DialogIdManager dialogIdManager;
    @Inject
    public DialogRepository(DialogIdManager dialogIdManager) {
        this.dialogIdManager = dialogIdManager;
        this.db = FirebaseDatabase.getInstance().getReference("dialogs");
    }

    /*public static synchronized DialogRepository getInstance() {
        return instance;
    }*/

    public void addDialog(final DialogItem dialogItem, final OnTransactionComplete<Void> onTransactionComplete) {
        UserInfo userInfo = FirebaseAuth.getInstance().getCurrentUser();
        db.child(userInfo.getUid()).push().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(dialogItem);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean commited, DataSnapshot dataSnapshot) {
                if (commited) {
                    dialogIdManager.addDialogId(dataSnapshot.getKey());
                    onTransactionComplete.onCommit(null);
                } else {
                    onTransactionComplete.onAbort(databaseError.toException());
                }
            }
        });
    }

    public void getDialogs(final DialogItemValueListener<DialogItem> eventListener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.child(currentUser.getUid()).addListenerForSingleValueEvent(new DefaultValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<DialogItem> items = new LinkedList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dialogIdManager.addDialogId(snapshot.getKey());
                    items.addFirst(snapshot.getValue(DialogItem.class));
                }
                eventListener.onValue(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
