package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.igordubrovin.tfsmsg.utils.MessageItem;

/**
 * Created by Игорь on 11.04.2017.
 */

public class SendMessageTaskFragment extends Fragment {

    public static final String TAG = "SendMessageTaskFragment";

    private MessageSentListener messageSentListener;
    private Boolean success;
    private MessageItem message;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageSentListener) {
            messageSentListener = (MessageSentListener) context;
            if (success != null){
                messageSentListener.messageSent(success, message);
                success = null;
            }
        } else
            throw new ClassCastException(context.toString()
                    + " must implement MessageSentListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        messageSentListener = null;
    }

    public void startSend(MessageItem message){
        this.message = message;
        new SendMessageTask().execute(message.getMessageText());
    }

    private void setSuccess(Boolean success) {
        if (messageSentListener != null) {
            messageSentListener.messageSent(success, message);
        } else {
            this.success = success;
        }
    }

    public interface MessageSentListener{
        void messageSent(Boolean success, MessageItem message);
    }

    private class SendMessageTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            setSuccess(success);
        }
    }

}
