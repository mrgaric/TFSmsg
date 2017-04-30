package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
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
import com.igordubrovin.tfsmsg.utils.PrefManager;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

public class NavigationActivity extends AppCompatActivity {

    private static final int MENU_DIALOGS = 0;
    private static final String STATE_VISIBILITY_FAB = "state_visiblity_fab";
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View navigationViewHeader;
    private TextView tvLogin;
    private FloatingActionButton fabAddDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        int visibility;
        if (savedInstanceState == null){
            visibility = View.VISIBLE;
        } else {
            visibility = savedInstanceState.getInt(STATE_VISIBILITY_FAB);
        }
        initToolbar();
        initFab(visibility);
        initNavigationView(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VISIBILITY_FAB, fabAddDialog.getVisibility());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Fragment dialogsFragment = getSupportFragmentManager().findFragmentByTag(ProjectConstants.FRAGMENT_DIALOGS);
        if (dialogsFragment != null) {
            ((DialogsFragment)dialogsFragment).getDialogItemsDb();
        }
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initFab(int visibility){
        fabAddDialog = (FloatingActionButton) findViewById(R.id.fab_add_dialog);
        fabAddDialog.setVisibility(visibility);
        fabAddDialog.setOnClickListener(clickFab);
    }

    private View.OnClickListener clickFab = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment dialogsFragment = getSupportFragmentManager().findFragmentByTag(ProjectConstants.FRAGMENT_DIALOGS);
            if (dialogsFragment != null){
                ((DialogsFragment) dialogsFragment).clickFAB();
            }
        }
    };

    private void initNavigationView(Bundle savedInstanceState){
        String userLogin;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        navigationViewHeader = navigationView.getHeaderView(0);
        tvLogin = (TextView) navigationViewHeader.findViewById(R.id.tv_login_navigation_view_header);
        userLogin = PrefManager.getInstance().login();
        tvLogin.setText(userLogin);

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
                    DialogsFragment dialogsFragment = DialogsFragment.newInstance(tvLogin.getText().toString());
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
                    PrefManager.getInstance().saveLogin("");
                    PrefManager.getInstance().setFlagLogin(false);
                    finish();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private void showFab(){
        fabAddDialog.setVisibility(View.VISIBLE);
    }

    private void hideFab(){
        fabAddDialog.setVisibility(View.GONE);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.replace_fragment_enter, R.anim.replace_fragment_exite);
        fragmentTransaction.replace(R.id.content_navigation, fragment, tag);
        fragmentTransaction.commit();
    }
}