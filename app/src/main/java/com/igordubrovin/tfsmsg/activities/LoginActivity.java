package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (Button) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextScreen();
            }
        });
    }

    private void startNextScreen() {
        if (password.getText().toString().equals("admin")) {
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
            SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_LOGGED);
            editor.putString(ProjectConstants.USERS_LOGIN, login.getText().toString());
            editor.apply();
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.root_login_view), "Login Error", BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark));
            snackbar.show();
        }
    }
}

