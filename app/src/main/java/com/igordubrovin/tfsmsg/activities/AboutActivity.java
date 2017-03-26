package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;

import com.igordubrovin.tfsmsg.R;
import com.lb.material_preferences_library.PreferenceActivity;

public class AboutActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_About);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getPreferencesXmlId() {
        return R.xml.pref_about;
    }
}