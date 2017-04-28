package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

/**
 * Created by Игорь on 28.04.2017.
 */

public class ChatDbHelper {

    public void getMessageItems (DialogItem dialogItem, QueryTransaction.QueryResultListCallback<MessageItem> callback){
        SQLite.select()
                .from(MessageItem.class)
                .where(MessageItem_Table.dialogItem_id.eq(dialogItem.getId()))
                .orderBy(MessageItem_Table.id, false)
                .async()
                .queryListResultCallback(callback)
                .execute();
    }

    public void getDialogItemsDb(QueryTransaction.QueryResultListCallback<DialogItem> callback) {
        SQLite.select()
                .from(DialogItem.class)
                .async()
                .queryListResultCallback(callback).execute();
    }

    public void addItem(final BaseModel item, Transaction.Success success) {
        FlowManager.getDatabase(ChatDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        item.save();
                    }
                })
                .success(success)
                .build()
                .execute();
    }
}
