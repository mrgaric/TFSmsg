package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

@Table(database = ChatDatabase.class)
@Parcel(analyze = {DialogItem.class})
public class DialogItem extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String title;
    @Column
    String desc;
    @Column
    String time;
    @Column
    String date;

    public DialogItem(){};

    public synchronized long getId() {
        return id;
    }

    public synchronized String getTitle() {
        return title;
    }

    public synchronized void setTitle(String title) {
        this.title = title;
    }

    public synchronized String getDesc() {
        return desc;
    }

    public synchronized void setDesc(String desc) {
        this.desc = desc;
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

    //public List<MessageItem> messageItems;

    /*@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "messageItems")
    public List<MessageItem> getMessageItems (){
            messageItems = SQLite.select()
                    .from(MessageItem.class)
                    .where(MessageItem_Table.dialogItem_id.eq(id))
                    .orderBy(MessageItem_Table.id, false)
                    .queryList();
        return messageItems;
    }*/
}