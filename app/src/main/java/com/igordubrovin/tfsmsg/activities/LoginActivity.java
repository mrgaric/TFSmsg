package com.igordubrovin.tfsmsg.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.di.components.LoginScreenComponent;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ILoginPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ILoginView;
import com.igordubrovin.tfsmsg.mvp.presenters.LoginPresenter;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.ProgressButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MvpActivity<ILoginView, ILoginPresenter>
        implements ILoginView, GoogleApiClient.OnConnectionFailedListener{

    @Inject
    Context context;
    @Inject
    LoginPresenter loginPresenter;
    @BindView(R.id.edit_text_login)
    EditText login;
    @BindView(R.id.edit_text_password)
    EditText password;
    @BindView(R.id.btn_enter)
    ProgressButton button;
    @BindView(R.id.sign_in_button)
    SignInButton googleSignInBtn;

    private GoogleSignInOptions signInOptions;
    private GoogleApiClient client;
    private LoginScreenComponent loginScreenComponent = App.plusLoginScreenComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginScreenComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    @NonNull
    @Override
    public ILoginPresenter createPresenter() {
        return loginPresenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ProjectConstants.PROGRESS_BUTTON_STATE, !button.isClickable());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setStateProgressButton(savedInstanceState.getBoolean(ProjectConstants.PROGRESS_BUTTON_STATE));
    }

    @OnClick (R.id.btn_enter)
    public void onClickCheckLogin(View v) {
        getPresenter().onClickBtnCheckLogin(login.getText().toString(), password.getText().toString());
        setStateProgressButton(true);
    }

    @OnClick (R.id.sign_in_button)
    public void onClickGoogleSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
        startActivityForResult(signInIntent, ProjectConstants.GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProjectConstants.GOOGLE_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getPresenter().handleSignInResult(result);
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

    @Override
    public void loginSuccessful() {
        startNextScreen();
    }

    @Override
    public void showError() {
        showErrorLogin();
        setStateProgressButton(false);
    }

    private void startNextScreen() {
        App.plusUserComponent();
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showErrorLogin(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.root_login_view), "Login Error", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        snackbar.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //empty
    }
}
