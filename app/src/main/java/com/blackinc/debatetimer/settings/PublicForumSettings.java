package com.blackinc.debatetimer.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.blackinc.debatetimer.R;

public class PublicForumSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.public_forum_preference, rootKey);
    }
}