package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.fragments.AboutFragment;
import com.igordubrovin.tfsmsg.fragments.DialogsFragment;
import com.igordubrovin.tfsmsg.fragments.SettingsFragment;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

public class NavigationActivity extends AppCompatActivity {

    private final static int MENU_DIALOGS = 0;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tvLogin;
    private FloatingActionButton fabAddDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initFab();
        initToolbar();
        initNavigationView(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initFab(){
        fabAddDialog = (FloatingActionButton) findViewById(R.id.fab_add_dialog);
        fabAddDialog.setOnClickListener(clickFab);
    }

    View.OnClickListener clickFab = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment dialogsFragment = getSupportFragmentManager().findFragmentByTag(ProjectConstants.FRAGMENT_DIALOGS);
            if (dialogsFragment != null){
                ((DialogsFragment) dialogsFragment).clickFAB();
            }
        }
    };

    private void showFab(){
        fabAddDialog.setVisibility(View.VISIBLE);
    }

    private void hideFab(){
        fabAddDialog.setVisibility(View.GONE);
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationView(Bundle savedInstanceState){
        View navigationViewHeader;
        SharedPreferences sPref;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        navigationViewHeader = navigationView.getHeaderView(0);
        tvLogin = (TextView) navigationViewHeader.findViewById(R.id.tv_login_navigation_view_header);
        sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
        tvLogin.setText(sPref.getString(ProjectConstants.USERS_LOGIN, "admin"));

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(MENU_DIALOGS).setChecked(true);
            navigationItemSelectedListener.onNavigationItemSelected(navigationView.getMenu().getItem(MENU_DIALOGS));
        }
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_dialogs:
                    DialogsFragment dialogsFragment = new DialogsFragment();
                    replaceFragment(dialogsFragment, ProjectConstants.FRAGMENT_DIALOGS);
                    showFab();
                    break;
                case R.id.nav_settings:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    replaceFragment(settingsFragment, ProjectConstants.FRAGMENT_SETTINGS);
                    hideFab();
                    break;
                case R.id.nav_about:
                    AboutFragment aboutFragment = new AboutFragment();
                    replaceFragment(aboutFragment, ProjectConstants.FRAGMENT_ABOUT);
                    hideFab();
                    break;
                case R.id.nav_exit:
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    SharedPreferences sPref = getSharedPreferences(ProjectConstants.PREFERENCES_LOGIN_FILE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putBoolean(ProjectConstants.PREFERENCES_STATE_LOGIN, ProjectConstants.USER_NOT_LOGGED);
                    editor.putString(ProjectConstants.USERS_LOGIN, "");
                    editor.apply();
                    finish();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.replace_fragment_enter, R.anim.replace_fragment_exite);
        fragmentTransaction.replace(R.id.content_navigation, fragment, tag);
        fragmentTransaction.commit();
    }
}
