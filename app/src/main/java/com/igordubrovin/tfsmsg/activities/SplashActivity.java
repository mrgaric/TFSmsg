package com.igordubrovin.tfsmsg.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.ImageAdapter;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*final ImageView ivSend = (ImageView) findViewById(R.id.anim_iv_send);
        final ImageView ivKeyb = (ImageView) findViewById(R.id.anim_iv_keyboard);
        final ImageView ivEmoj = (ImageView) findViewById(R.id.anim_iv_emoj);
        final ImageView ivCancel = (ImageView) findViewById(R.id.anim_iv_cancel);
        final Drawable drawableSend = ivSend.getDrawable();
        final Drawable drawableKeyb = ivKeyb.getDrawable();
        final Drawable drawableEmoj = ivEmoj.getDrawable();
        final Drawable drawableCancel = ivCancel.getDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Animatable) drawableSend).start();
            ((Animatable) drawableKeyb).start();
            ((Animatable) drawableEmoj).start();
            ((Animatable) drawableCancel).start();
        }*/

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        new Loading().execute();
    }

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*super.onPostExecute(aVoid);
            Intent intent;
            SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
            if (sPref.getBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_NOT_LOGGED))
                intent = new Intent(getApplicationContext(), NavigationActivity.class);
            else
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);*/
        }
    }
}
