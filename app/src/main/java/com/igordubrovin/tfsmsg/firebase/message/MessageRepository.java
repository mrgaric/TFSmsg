package com.igordubrovin.tfsmsg.firebase.message;

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
import com.igordubrovin.tfsmsg.utils.MessageItem;

import java.util.LinkedList;

import javax.inject.Inject;

/**
 * Created by Игорь on 26.05.2017.
 */

public class MessageRepository {
    private final DatabaseReference db;
    private final DialogIdManager dialogIdManager;

    @Inject
    public MessageRepository(DialogIdManager dialogIdManager){
        this.dialogIdManager = dialogIdManager;
        db = FirebaseDatabase.getInstance().getReference("message");
    }

    public void addMessage(String dialogId, MessageItem messageItem, OnTransactionComplete<MessageItem> onTransactionComplete){
        UserInfo userInfo = FirebaseAuth.getInstance().getCurrentUser();
        db.child(userInfo.getUid()).child(dialogId).push().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(messageItem);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if (b)
                    onTransactionComplete.onCommit(dataSnapshot.getValue(MessageItem.class));
                else
                    onTransactionComplete.onAbort(databaseError.toException());
            }
        });
    }

    public void getMessages(String dialogId, final DialogItemValueListener<MessageItem> eventListener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.child(currentUser.getUid()).child(dialogId).addListenerForSingleValueEvent(new DefaultValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<MessageItem> items = new LinkedList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items.addFirst(snapshot.getValue(MessageItem.class));
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
