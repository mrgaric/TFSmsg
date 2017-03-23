package com.igordubrovin.tfsmsg;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessageItem implements Parcelable {

    private String messageText;

    MessageItem (String messageText){
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
        dest.writeString(messageText);
    }
}
