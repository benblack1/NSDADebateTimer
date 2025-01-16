package com.blackinc.debatetimer;

import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.blackinc.debatetimer.bigquestions.MainActivityBQ;
import com.blackinc.debatetimer.congress.MainActivityCongress;
import com.blackinc.debatetimer.lincolndouglas.MainActivityLD;
import com.blackinc.debatetimer.policy.MainActivityCX;
import com.blackinc.debatetimer.publicforum.MainActivityPF;
import com.blackinc.debatetimer.settings.Settings;
import com.blackinc.debatetimer.worldschools.MainActivityWS;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LaunchScreen extends Activity {
    public static int cx;
    public static int cy;
    private int[] array = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cx = 0;
        cy = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStop() {
        super.onStop();
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("Debate Timer", icon, Color.parseColor("#424242"));
        ((Activity)this).setTaskDescription(taskDescription);
        cx = 0;
        cy = 0;
    }

    @Override
    public void onResume (){
        super.onResume();
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("Debate Timer", icon, Color.parseColor("#424242"));
        ((Activity)this).setTaskDescription(taskDescription);
        setButtonColors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        getMenuInflater().inflate(R.menu.help, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.settings:
                //Toast toast = Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT);
                //toast.show();
                startActivity(new Intent(LaunchScreen.this, Settings.class));
                return true;
            case R.id.help:
                Toast toast = Toast.makeText(getApplicationContext(), "Please send an email to blackend.inc@gmail.com", Toast.LENGTH_LONG);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setButtonColors(){
        Drawable ldDrawable = getDrawable(R.drawable.launchscreenld);
        Drawable pfDrawable = getDrawable(R.drawable.launchscreenpf);
        Drawable cxDrawable = getDrawable(R.drawable.launchscreencx);
        Drawable bqDrawable = getDrawable(R.drawable.launchscreenbq);
        Drawable wsDrawable = getDrawable(R.drawable.launchscreenws);
        Drawable speechDrawable = getDrawable(R.drawable.launchscreenspeech);
        List<Drawable> buttonColors = new ArrayList<>();
        buttonColors.add(ldDrawable);
        buttonColors.add(pfDrawable);
        buttonColors.add(cxDrawable);
        buttonColors.add(bqDrawable);
        buttonColors.add(wsDrawable);
        buttonColors.add(speechDrawable);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        List<String> spColors = Arrays.asList("ld_color", "pf_color", "cx_color", "bq_color", "ws_color", "cong_color");
        List<String> spHexes = Arrays.asList("#1565C0", "#EF6C00", "#C62828", "#2E7D32", "#6A1B9A", "#0277BD");
        SharedPreferences.Editor edit = sp.edit();
        for (int i = 0; i < spColors.size(); i++){
            if (Objects.equals(sp.getString(spColors.get(i), "-1"), "-1")){
                edit.putString(spColors.get(i), spHexes.get(i));
            }
        }
        edit.apply();

        switch (sp.getString("ld_color","-1")){
            case "#1565C0":
                ldDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                ldDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                ldDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                ldDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                ldDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                ldDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        switch (sp.getString("pf_color","-1")){
            case "#1565C0":
                pfDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                pfDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                pfDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                pfDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                pfDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                pfDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        switch (sp.getString("cx_color","-1")){
            case "#1565C0":
                cxDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                cxDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                cxDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                cxDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                cxDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                cxDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        switch (sp.getString("bq_color","-1")){
            case "#1565C0":
                bqDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                bqDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                bqDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                bqDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                bqDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                bqDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        switch (sp.getString("ws_color","-1")){
            case "#1565C0":
                wsDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                wsDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                wsDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                wsDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                wsDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                wsDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        switch (sp.getString("cong_color","-1")){
            case "#1565C0":
                speechDrawable = getDrawable(R.drawable.launchscreenld);
                break;
            case "#EF6C00":
                speechDrawable = getDrawable(R.drawable.launchscreenpf);
                break;
            case "#C62828":
                speechDrawable = getDrawable(R.drawable.launchscreencx);
                break;
            case "#2E7D32":
                speechDrawable = getDrawable(R.drawable.launchscreenbq);
                break;
            case "#6A1B9A":
                speechDrawable = getDrawable(R.drawable.launchscreenws);
                break;
            case "#0277BD":
                speechDrawable = getDrawable(R.drawable.launchscreenspeech);
                break;
        }

        findViewById(R.id.launchLD).setBackground(ldDrawable);
        findViewById(R.id.launchPF).setBackground(pfDrawable);
        findViewById(R.id.launchCX).setBackground(cxDrawable);
        findViewById(R.id.launchBQ).setBackground(bqDrawable);
        findViewById(R.id.launchWS).setBackground(wsDrawable);
        findViewById(R.id.launchCong).setBackground(speechDrawable);
    }
    public void launchLD(View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchLD);
        //cxLD = Math.round(viewer.getX() + (viewer.getWidth()/2));
        //cyLD = Math.round(viewer.getY() + (viewer.getHeight()/2));
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;
        //cxLD = Math.round(viewer.getPivotX());
        //cyLD = Math.round(viewer.getPivotY());
        //viewer.getLocationOnScreen(array);

        MainActivityLD.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityLD.class));
    }

    public void launchPF(View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchPF);
        //cxLD = Math.round(viewer.getX() + (viewer.getWidth()/2));
        //cyLD = Math.round(viewer.getY() + (viewer.getHeight()/2));
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;

        MainActivityPF.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityPF.class));
    }

    public void launchCX(View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchCX);
        //cxLD = Math.round(viewer.getX() + (viewer.getWidth()/2));
        //cyLD = Math.round(viewer.getY() + (viewer.getHeight()/2));
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;

        MainActivityCX.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityCX.class));
    }

    public void launchBQ (View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchBQ);
        //cxLD = Math.round(viewer.getX() + (viewer.getWidth()/2));
        //cyLD = Math.round(viewer.getY() + (viewer.getHeight()/2));
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;

        MainActivityBQ.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityBQ.class));
    }

    public void launchWS (View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchWS);
        //cxLD = Math.round(viewer.getX() + (viewer.getWidth()/2));
        //cyLD = Math.round(viewer.getY() + (viewer.getHeight()/2));
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;

        MainActivityWS.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityWS.class));
    }

    public void launchCong (View view){
        array = new int[2];
        View viewer = findViewById(R.id.launchCong);
        viewer.getLocationOnScreen(array);
        cx = array[0] + viewer.getWidth()/2;
        cy = array[1] + viewer.getHeight()/2;

        MainActivityCongress.position = 0;
        startActivity(new Intent(LaunchScreen.this, MainActivityCongress.class));
    }

    //ToDo: Have several different options and ask people
    //ToDo: Change Colors?
    //ToDo: Tutorial?
}
