package com.blackinc.debatetimer.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.blackinc.debatetimer.R;

public class BigQuestionsSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.big_questions_preference, rootKey);
    }
}