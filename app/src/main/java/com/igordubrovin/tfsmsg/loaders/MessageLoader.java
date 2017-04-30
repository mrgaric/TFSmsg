package com.igordubrovin.tfsmsg.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.utils.DateHelper;

/**
 * Created by Игорь on 12.04.2017.
 */

public class MessageLoader extends AsyncTaskLoader<MessageItem> {

    public MessageLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public MessageItem loadInBackground() {
        MessageItem messageItem = null;
        try {
            Thread.sleep(3000);
            String sender = "test test test test test test test test test";
            String message = "Test Test Test Test Test Test Test Test Test Test";
            messageItem = createMessageItem(sender, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return messageItem;
    }

    private synchronized MessageItem createMessageItem(String sender, String message){
        MessageItem messageItem = new MessageItem();
        DateHelper dateHelper = new DateHelper();
        messageItem.setTime(dateHelper.getCurrentTime());
        messageItem.setDate(dateHelper.getCurrentDate());
        messageItem.setMessageText(message);
        messageItem.setIdAuthor(sender);
        return messageItem;
    }
}
