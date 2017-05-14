package com.igordubrovin.tfsmsg.mvp.presenters;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ISplashPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;
import com.igordubrovin.tfsmsg.utils.LoginManager;

/**
 * Created by Ксения on 14.05.2017.
 */

public class SplashPresenter extends MvpBasePresenter<ISplashView>
        implements ISplashPresenter {

    private LoginManager loginManager;

    public SplashPresenter(LoginManager loginManager){
        this.loginManager = loginManager;
    }

    @Override
    public void checkLoginState() {
        new Loading().execute();
    }

    private void returnResultView(boolean success){
        if (success)
            getView().showNavigationActivity();
        else
            getView().showLoginActivity();
    }

    private class Loading extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return loginManager.isLogin();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            returnResultView(result);
        }
    }
}
