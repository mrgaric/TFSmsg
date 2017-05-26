package com.igordubrovin.tfsmsg.firebase.message;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.igordubrovin.tfsmsg.firebase.OnTransactionComplete;
import com.igordubrovin.tfsmsg.utils.MessageItem;

/**
 * Created by Игорь on 26.05.2017.
 */

public class MessageRepository {
    private final static MessageRepository instance = new MessageRepository();
    private final DatabaseReference db;

    private MessageRepository(){
        db = FirebaseDatabase.getInstance().getReference("message");
    }

    public static synchronized MessageRepository getInstance(){
        return instance;
    }

    public void addMessage(MessageItem messageItem, OnTransactionComplete onTransactionComplete){
        UserInfo userInfo = FirebaseAuth.getInstance().getCurrentUser();
        db.child(userInfo.getUid()).push().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(messageItem);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if (b)
                    onTransactionComplete.onCommit(dataSnapshot.getValue());
                else
                    onTransactionComplete.onAbort(databaseError.toException());
            }
        });

    }
}
