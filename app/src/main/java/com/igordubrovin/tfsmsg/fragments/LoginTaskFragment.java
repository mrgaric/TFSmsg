package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.igordubrovin.tfsmsg.utils.PrefManager;

/**
 * Created by Игорь on 10.04.2017.
 */

public class LoginTaskFragment extends Fragment {

    public static final String TAG = "LoginTaskFragment";

    private LoginListener loginListener;
    private Boolean success;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener){
            loginListener = (LoginListener) context;
            if (success != null){
                loginListener.onResult(success);
                success = null;
            }
        } else
            throw new ClassCastException(context.toString()
                + " must implement LoginListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginListener = null;
    }

    public void checkLogin(String login, String password){
        new LoginTask().execute(login, password);
    }

    private void setSuccess(Boolean success) {
        if (loginListener != null) {
            loginListener.onResult(success);
        } else {
            this.success = success;
        }
    }

    public interface LoginListener {
        void onResult(Boolean success);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!params[0].equals("")) {
                PrefManager.getInstance().saveLogin(params[0]);
                PrefManager.getInstance().setFlagLogin(true);
                return true;
            } else {
                PrefManager.getInstance().setFlagLogin(false);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            setSuccess(success);
        }
    }
}
