package com.igordubrovin.tfsmsg.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.igordubrovin.tfsmsg.utils.IncomingMessageItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Игорь on 12.04.2017.
 */

public class MessageLoader extends AsyncTaskLoader<List<MessageItem>> {

    private List<MessageItem> messageItems = new LinkedList<>();;

    public MessageLoader(Context context) {
        super(context);
    }

    @Override
    public List<MessageItem> loadInBackground() {
        try {
            Thread.sleep(3000);
            String sender = "test test test test test test test test test";
            String message = "Test Test Test Test Test Test Test Test Test Test";
            IncomingMessageItem incomingMessageItem = createIncomingMessageItem(sender, message);
            messageItems.add(incomingMessageItem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return messageItems;
    }

    public void addSentMessage(MessageItem messageItem){
        ((LinkedList)messageItems).addFirst(messageItem);
    }

    private IncomingMessageItem createIncomingMessageItem(String sender, String message){
        return new IncomingMessageItem(sender, message);
    }
}
