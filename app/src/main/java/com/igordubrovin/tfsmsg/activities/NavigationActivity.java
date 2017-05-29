package com.igordubrovin.tfsmsg.activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.di.components.NavigationScreenComponent;
import com.igordubrovin.tfsmsg.fragments.AboutFragment;
import com.igordubrovin.tfsmsg.fragments.DialogsFragment;
import com.igordubrovin.tfsmsg.fragments.SettingsFragment;
import com.igordubrovin.tfsmsg.interfaces.InjectFragment;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity
        implements InjectFragment{

    TextView tvLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.fab_add_dialog)
    FloatingActionButton fabAddDialog;
    @Inject
    String userLogin;
    @Inject
    FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;
    private View navigationViewHeader;
    private NavigationScreenComponent navigationScreenComponent = App.plusNavigationScreenComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        navigationScreenComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        int visibility;
        if (savedInstanceState == null){
            visibility = View.VISIBLE;
        } else {
            visibility = savedInstanceState.getInt(ProjectConstants.STATE_VISIBILITY_FAB);
        }
        ButterKnife.bind(this);
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
        outState.putInt(ProjectConstants.STATE_VISIBILITY_FAB, fabAddDialog.getVisibility());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ProjectConstants.REQUEST_CODE_DIALOG_ADD){
                Fragment dialogsFragment = getSupportFragmentManager().findFragmentByTag(ProjectConstants.FRAGMENT_DIALOGS);
                if (dialogsFragment != null) {
                    ((DialogsFragment)dialogsFragment).addItem(Parcels.unwrap(data.getParcelableExtra(ProjectConstants.DIALOG_ITEM_INTENT)));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    private void initFab(int visibility){
        fabAddDialog.setVisibility(visibility);
    }

    @OnClick(R.id.fab_add_dialog)
    public void onClickAddDialog(View v) {
        Intent intent = new Intent(this, AddDialogActivity.class);
        startActivityForResult(intent, ProjectConstants.REQUEST_CODE_DIALOG_ADD);
    }

    private void initNavigationView(Bundle savedInstanceState){
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        navigationViewHeader = navigationView.getHeaderView(0);
        tvLogin = ButterKnife.findById(navigationViewHeader, R.id.tv_login_navigation_view_header);
        tvLogin.setText(userLogin);
        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(ProjectConstants.MENU_DIALOGS).setChecked(true);
            navigationItemSelectedListener.onNavigationItemSelected(navigationView.getMenu().getItem(ProjectConstants.MENU_DIALOGS));
        }
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
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
                App.clearUserComponent();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                firebaseAuth.signOut();
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public void injectDialogsFragment(DialogsFragment dialogsFragment) {
        navigationScreenComponent.inject(dialogsFragment);
    }
}