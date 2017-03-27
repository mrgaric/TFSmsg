package com.igordubrovin.tfsmsg.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.igordubrovin.tfsmsg.R;

/**
 * Created by Игорь on 26.03.2017.
 */

public class AboutFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_about);
    }
}
