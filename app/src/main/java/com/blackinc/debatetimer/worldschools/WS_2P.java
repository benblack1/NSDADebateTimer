package com.blackinc.debatetimer.worldschools;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackinc.debatetimer.R;

import java.util.Arrays;
import java.util.List;

public class WS_2P extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.ws_secondp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        TextView text = view.findViewById(R.id.timertext);
        List<String> times = Arrays.asList("0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00");
        text.setText(times.get(Integer.parseInt(sp.getString("ws_ts", "8"))));
    }
}