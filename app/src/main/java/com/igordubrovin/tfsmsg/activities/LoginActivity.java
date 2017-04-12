package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.fragments.LoginTaskFragment;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.ProgressButton;

public class LoginActivity extends AppCompatActivity
        implements LoginTaskFragment.LoginListener{

    private EditText login;
    private EditText password;
    private ProgressButton button;
    private LoginTaskFragment loginTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (ProgressButton) findViewById(R.id.btn_enter);
        button.setOnClickListener(clickProgressButton);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        loginTaskFragment = (LoginTaskFragment) supportFragmentManager.findFragmentByTag(LoginTaskFragment.TAG);
        if (loginTaskFragment == null) {
            loginTaskFragment = new LoginTaskFragment();
            supportFragmentManager.beginTransaction().add(loginTaskFragment, LoginTaskFragment.TAG).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ProjectConstants.PROGRESS_BUTTON_STATE, ! button.isClickable());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setStateProgressButton(savedInstanceState.getBoolean(ProjectConstants.PROGRESS_BUTTON_STATE));
    }

    View.OnClickListener clickProgressButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginTaskFragment.checkLogin(login.getText().toString(), password.getText().toString());
            setStateProgressButton(true);
        }
    };

    @Override
    public void onResult(Boolean success) {
        if (success)
            startNextScreen();
        else {
            showErrorLogin();
            setStateProgressButton(false);
        }
    }

    private void setStateProgressButton(Boolean buttonPressed){
        if (buttonPressed) {
            button.showProgress();
            button.setClickable(false);
        } else {
            button.hideProgress();
            button.setClickable(true);
        }
    }

    private void startNextScreen() {

        SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_LOGGED);
        editor.putString(ProjectConstants.USERS_LOGIN, login.getText().toString());
        editor.apply();
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    private void showErrorLogin(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.root_login_view), "Login Error", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark));
        snackbar.show();
    }
}

