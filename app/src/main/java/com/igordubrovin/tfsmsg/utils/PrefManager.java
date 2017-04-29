package com.igordubrovin.tfsmsg.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Игорь on 28.04.2017.
 */

public class PrefManager {
    private static final String PREF_FILE = "custom_name";
    private static final String PREF_LOGIN = "pref_login";
    private static final String PREF_FLAG_LOGIN = "pref_flag_login";

    private static PrefManager instance;

    private final Context context;

    private PrefManager(Context context) {
        this.context = context;
    }

    public static void newInstance(Context context) {
        instance = new PrefManager(context);
    }

    public static synchronized PrefManager getInstance() {
        return instance;
    }

    public String login() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREF_LOGIN, null);
    }

    public void saveLogin(String login) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_LOGIN, login);
        edit.apply();
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREF_FLAG_LOGIN, false);
    }

    public void setFlagLogin (boolean flagLogin){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(PREF_FLAG_LOGIN, flagLogin);
        edit.apply();
    }
}
