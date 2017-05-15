package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.di.components.SplashScreenComponent;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ISplashPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;
import com.igordubrovin.tfsmsg.mvp.presenters.SplashPresenter;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends MvpActivity<ISplashView, ISplashPresenter>
        implements ISplashView{

    @Inject
    LoginManager loginManager;
    @Inject
    ImageAdapter imageAdapter;
    @Inject
    SplashPresenter splashPresenter;
    private SplashScreenComponent splashScreenComponent = App.plusSplashScreenComponent();
    @BindView(R.id.grid_view_splash) GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        splashScreenComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        gridview.setAdapter(imageAdapter);
        getPresenter().checkLoginState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageAdapter.stopAnimation();
    }

    @NonNull
    @Override
    public ISplashPresenter createPresenter() {
        return splashPresenter;
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNavigationActivity() {
        App.plusUserComponent();
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }
}
