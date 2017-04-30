package com.igordubrovin.tfsmsg.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.utils.PrefManager;

public class SplashActivity extends AppCompatActivity {

    GridView gridview;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gridview = (GridView) findViewById(R.id.grid_view_splash);
        Point size = new Point();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        int width = size.x/2;
        int height = size.y/3;
        imageAdapter = new ImageAdapter(this, width, height);
        gridview.setAdapter(imageAdapter);
        new Loading().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageAdapter.stopAnimation();
    }

    private class Loading extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return PrefManager.getInstance().isLogin();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Intent intent;
            if (result)
                intent = new Intent(getApplicationContext(), NavigationActivity.class);
            else
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
