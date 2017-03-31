package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.igordubrovin.tfsmsg.utils.ProjectConstants;

/**
 * Created by Игорь on 28.03.2017.
 */

public class StartTransparentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
        if (sPref.getBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_NOT_LOGGED))
            intent = new Intent(getApplicationContext(), NavigationActivity.class);
        else
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
