package com.igordubrovin.tfsmsg;

import android.os.Parcel;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessageIncomingItem extends MessageItem {

    private String sender;

    MessageIncomingItem(String messageText, String sender) {
        super(messageText);
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    protected MessageIncomingItem(Parcel in) {
        super(in);
        sender = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(sender);
    }
}
