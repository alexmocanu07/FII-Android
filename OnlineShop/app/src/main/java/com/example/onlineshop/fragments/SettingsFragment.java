package com.example.onlineshop.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.onlineshop.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
