package com.igordubrovin.tfsmsg.utils;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessageIncomingItem extends MessageItem {

    private String sender;

    public MessageIncomingItem(String messageText, String sender) {
        super(messageText);
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
