package com.blackinc.debatetimer.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.blackinc.debatetimer.R;

public class PolicySettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.policy_preference, rootKey);
    }
}