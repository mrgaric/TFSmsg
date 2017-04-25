package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;

@Table(database = DialogsDatabase.class)
public class DialogItem {

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

    public DialogItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void save(){
        FlowManager.getModelAdapter(DialogItem.class).save(this);
    }
}
