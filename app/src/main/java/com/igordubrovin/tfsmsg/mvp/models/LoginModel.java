package com.igordubrovin.tfsmsg.mvp.models;

import com.igordubrovin.tfsmsg.utils.LoginManager;

public class LoginModel {

    private final LoginManager loginManager;

    public LoginModel(LoginManager loginManager){
        this.loginManager = loginManager;
    }

    public Boolean checkLogin(String login, String password){
        if (!login.equals("")) {
            loginManager.saveLogin(login);
            loginManager.setFlagLogin(true);
            return true;
        } else {
            loginManager.setFlagLogin(false);
            return false;
        }
    }
}
