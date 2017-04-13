package com.igordubrovin.tfsmsg.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.igordubrovin.tfsmsg.utils.IncomingMessageItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;

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
        IncomingMessageItem incomingMessageItem = null;
        try {
            Thread.sleep(3000);
            String sender = "test test test test test test test test test";
            String message = "Test Test Test Test Test Test Test Test Test Test";
            incomingMessageItem = createIncomingMessageItem(sender, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return incomingMessageItem;
    }

    private IncomingMessageItem createIncomingMessageItem(String sender, String message){
        return new IncomingMessageItem(sender, message);
    }
}
