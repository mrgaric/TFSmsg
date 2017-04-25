package com.igordubrovin.tfsmsg.db;

import android.os.Parcel;
import android.os.Parcelable;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by Игорь on 18.03.2017.
 */

@Table(database = MessagesDatabase.class)
public class MessageItem implements Parcelable {

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

    public MessageItem(){};

    public MessageItem (String messageText){
        this.messageText = messageText;
    }

    protected MessageItem(Parcel in) {
        messageText = in.readString();
    }

    public static final Creator<MessageItem> CREATOR = new Creator<MessageItem>() {
        @Override
        public MessageItem createFromParcel(Parcel in) {
            return new MessageItem(in);
        }

        @Override
        public MessageItem[] newArray(int size) {
            return new MessageItem[size];
        }
    };

    public String getMessageText() {
        return messageText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
