package com.igordubrovin.tfsmsg.mvp.presenters;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ILoginPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ILoginView;
import com.igordubrovin.tfsmsg.mvp.models.LoginModel;

import javax.inject.Inject;

public class LoginPresenter extends MvpBasePresenter<ILoginView>
        implements ILoginPresenter {

    private Boolean success;
    private final LoginModel loginModel;

    @Inject
    public LoginPresenter(LoginModel loginModel){
        this.loginModel = loginModel;
    }

    @Override
    public void attachView(ILoginView view) {
        super.attachView(view);
        if (success != null) {
            returnResultView(success);
            success = null;
        }
    }

    @Override
    public void onClickBtnCheckLogin(String login, String password) {
        new LoginTask().execute(login, password);
    }

    private void returnResultView(Boolean success){
        if (success)
            getView().loginSuccessful();
        else
            getView().showError();
    }

    private void setSuccess(Boolean success) {
        if (isViewAttached())
            returnResultView(success);
        else
            this.success = success;
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return loginModel.checkLogin(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            setSuccess(success);
        }
    }

}
