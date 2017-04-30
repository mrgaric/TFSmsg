package com.igordubrovin.tfsmsg.db;

import android.support.annotation.NonNull;

import com.igordubrovin.tfsmsg.fragments.ChatDbFragment;
import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

/**
 * Created by Игорь on 28.04.2017.
 */

public class ChatDbHelper {

    private ChatDbFragment chatDbFragment;
    private ChatDbItemsListener chatDbItemsListener;

    public ChatDbHelper(){}

    public ChatDbHelper(ChatDbFragment chatDbFragment){
        this.chatDbFragment = chatDbFragment;
    }

    public ChatDbHelper(ChatDbItemsListener chatDbItemsListener){
        this.chatDbItemsListener = chatDbItemsListener;
    }

    public void getMessageItems (DialogItem dialogItem){
        SQLite.select()
                .from(MessageItem.class)
                .where(MessageItem_Table.dialogItem_id.eq(dialogItem.getId()))
                .orderBy(MessageItem_Table.id, false)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<MessageItem>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<MessageItem> tResult) {
                        if (chatDbFragment != null)
                            chatDbFragment.returnListItemReceived(tResult);
                        else if (chatDbItemsListener != null)
                                chatDbItemsListener.itemsReceived(tResult);
                    }
                })
                .execute();
    }

    public void getDialogItemsDb() {
        SQLite.select()
                .from(DialogItem.class)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<DialogItem>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<DialogItem> tResult) {
                        if (chatDbFragment != null)
                            chatDbFragment.returnListItemReceived(tResult);
                        else if (chatDbItemsListener != null)
                                chatDbItemsListener.itemsReceived(tResult);
                    }
                })
                .execute();
    }

    public void saveItem(@NonNull final BaseModel item) {
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.save();
                        /*try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        if (chatDbFragment != null)
                            chatDbFragment.returnItemAdded(item);
                        else if (chatDbItemsListener != null)
                            chatDbItemsListener.itemAdded(item);
                    }
                })
                .build()
                .execute();
    }

    public void deleteItem(@NonNull final BaseModel item){
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.delete();
                        /*try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        if (chatDbFragment != null)
                            chatDbFragment.returnItemAdded(item);
                        else
                            chatDbItemsListener.itemDeleted(item);
                    }
                })
                .build()
                .execute();
    }
}
