package com.igordubrovin.tfsmsg.utils;

import android.support.annotation.NonNull;

import com.igordubrovin.tfsmsg.db.ChatDatabase;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.db.DialogItem_Table;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.db.MessageItem_Table;
import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Игорь on 15.05.2017.
 */

public class DBFlowHelper {

    @Inject
    public DBFlowHelper(){
    }

    public void getMessageItems (DialogItem dialogItem, final ChatDbItemsListener chatDbItemsListener){
        SQLite.select()
                .from(MessageItem.class)
                .where(MessageItem_Table.dialogItem_id.eq(dialogItem.getId()))
                .orderBy(MessageItem_Table.id, false)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<MessageItem>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<MessageItem> tResult) {
                        chatDbItemsListener.itemsReceived(tResult);
                    }
                })
                .execute();
    }

    public void getDialogItemsDb(final ChatDbItemsListener chatDbItemsListener) {
        SQLite.select()
                .from(DialogItem.class)
                .orderBy(DialogItem_Table.id, false)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<DialogItem>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<DialogItem> tResult) {
                        chatDbItemsListener.itemsReceived(tResult);
                    }
                })
                .execute();
    }

    public void saveItem(@NonNull final BaseModel item, final ChatDbItemsListener chatDbItemsListener) {
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.save();
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        chatDbItemsListener.itemAdded(item);
                    }
                })
                .build()
                .execute();
    }

    public void saveItem(@NonNull final BaseModel item) {
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.save();
                    }
                })
                .build()
                .execute();
    }

    public void deleteItem(@NonNull final BaseModel item, final ChatDbItemsListener chatDbItemsListener){
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.delete();
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        chatDbItemsListener.itemDeleted(item);
                    }
                })
                .build()
                .execute();
    }
}
