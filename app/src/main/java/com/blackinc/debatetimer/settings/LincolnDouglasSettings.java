package com.blackinc.debatetimer.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.blackinc.debatetimer.R;

public class LincolnDouglasSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.lincoln_douglas_preference, rootKey);
    }
}