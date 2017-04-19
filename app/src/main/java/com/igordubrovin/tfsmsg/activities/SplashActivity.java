package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

public class SplashActivity extends AppCompatActivity {

    GridView gridview;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageAdapter = new ImageAdapter(this);
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(imageAdapter);
        new Loading().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageAdapter.interruptThreads();
    }

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent;
            SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
            if (sPref.getBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_NOT_LOGGED))
                intent = new Intent(getApplicationContext(), NavigationActivity.class);
            else
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
