package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Игорь on 18.03.2017.
 */

@Table(database = ChatDatabase.class)
@org.parceler.Parcel(analyze = {MessageItem.class})
public class MessageItem extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String messageText;
    @Column
    String idAuthor;
    @Column
    String time;
    @Column
    String date;
    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "dialogItem_id",
            foreignKeyColumnName = "id")},
            saveForeignKeyModel = false,
            stubbedRelationship = true)
    DialogItem dialogItem;

    public MessageItem(){};

    public synchronized String getMessageText() {
        return messageText;
    }

    public synchronized void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public synchronized String getIdAuthor() {
        return idAuthor;
    }

    public synchronized void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public synchronized String getTime() {
        return time;
    }

    public synchronized void setTime(String time) {
        this.time = time;
    }

    public synchronized String getDate() {
        return date;
    }

    public synchronized void setDate(String date) {
        this.date = date;
    }

    public synchronized DialogItem getDialogItem() {
        return dialogItem;
    }

    public synchronized void setDialogItem(DialogItem dialogItem) {
        this.dialogItem = dialogItem;
    }

    public synchronized long getId() {
        return id;
    }

}
