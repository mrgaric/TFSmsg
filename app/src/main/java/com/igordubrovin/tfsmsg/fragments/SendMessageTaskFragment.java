package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Игорь on 11.04.2017.
 */

public class SendMessageTaskFragment extends Fragment {

    public static final String TAG = "SendMessageTaskFragment";

    private MessageSentListener messageSentListener;
    private Boolean success;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageSentListener) {
            messageSentListener = (MessageSentListener) context;
            if (success != null){
                messageSentListener.messageSent(success);
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

    public void startSend(String message){
        new SendMessageTask().execute(message);
    }

    private void setSuccess(Boolean success) {
        if (messageSentListener != null) {
            messageSentListener.messageSent(success);
        } else {
            this.success = success;
        }
    }

    public interface MessageSentListener{
        void messageSent(Boolean success);
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
