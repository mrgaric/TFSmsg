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
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends MvpActivity<ISplashView, ISplashPresenter>
        implements ISplashView{

    private SplashScreenComponent splashScreenComponent;

    @Inject LoginManager loginManager;
    private ImageAdapter imageAdapter;

    @BindView(R.id.grid_view_splash) GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);
        imageAdapter = splashScreenComponent.getImageAdapter();
        gridview.setAdapter(imageAdapter);
        getPresenter().checkLoginState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageAdapter.stopAnimation();
        App.clearSplashScreenComponent();
        App.clearSingleComponent();
    }

    @NonNull
    @Override
    public ISplashPresenter createPresenter() {
        splashScreenComponent = App.plusSplashScreenComponent();
        return splashScreenComponent.getSplashPresenter();
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNavigationActivity() {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }
}
