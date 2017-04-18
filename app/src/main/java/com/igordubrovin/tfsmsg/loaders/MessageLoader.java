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

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    public void deliverResult(MessageItem data) {
        super.deliverResult(data);
    }

    @Override
    public void deliverCancellation() {
        super.deliverCancellation();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void unregisterListener(OnLoadCompleteListener<MessageItem> listener) {
        super.unregisterListener(listener);
    }

    @Override
    public void unregisterOnLoadCanceledListener(OnLoadCanceledListener<MessageItem> listener) {
        super.unregisterOnLoadCanceledListener(listener);
    }

    private IncomingMessageItem createIncomingMessageItem(String sender, String message){
        return new IncomingMessageItem(sender, message);
    }

    @Override
    public void registerOnLoadCanceledListener(OnLoadCanceledListener<MessageItem> listener) {
        super.registerOnLoadCanceledListener(listener);
    }

    @Override
    public void registerListener(int id, OnLoadCompleteListener<MessageItem> listener) {
        super.registerListener(id, listener);
    }

    @Override
    public boolean takeContentChanged() {
        return super.takeContentChanged();
    }

    @Override
    public void rollbackContentChanged() {
        super.rollbackContentChanged();
    }
}
