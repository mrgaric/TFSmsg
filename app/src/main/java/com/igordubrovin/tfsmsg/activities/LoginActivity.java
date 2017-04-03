package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.widgets.ProgressButton;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private ProgressButton button;

    private LoginTask task = new LoginTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (ProgressButton) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginTask().execute();
            }
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            button.showProgress();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            button.hideProgress();
            startNextScreen();
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

        /*if (password.getText().toString().equals("admin")) {
            SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_LOGGED);
            editor.putString(ProjectConstants.USERS_LOGIN, login.getText().toString());
            editor.apply();
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.root_login_view), "Login Error", BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark));
            snackbar.show();
        }*/
    }
}

