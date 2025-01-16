package com.blackinc.debatetimer.lincolndouglas;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackinc.debatetimer.CircleIndicator;
import com.blackinc.debatetimer.LaunchScreen;
import com.blackinc.debatetimer.R;

import java.util.Arrays;
import java.util.List;

import static com.blackinc.debatetimer.R.id.actext;
import static com.blackinc.debatetimer.R.id.container;
import static com.blackinc.debatetimer.R.id.ld_root_layout;

public class MainActivityLD extends AppCompatActivity {

    public static long seconds;
    public static String timerTitle;
    TabLayout tabLayout;
    public static long affPrepSeconds = 240200;
    public static long negPrepSeconds = 240200;
    public static boolean aff;
    public static int position = -1;
    public static boolean negCheck = false;
    public static float startRad;
    public static boolean prep = false;

    protected View view;
    private ImageButton btnNext, btnFinish;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private PagerAdapter mAdapter;
    private int[] array = new int[2];

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);

        setContentView(R.layout.ld_activity_main);


        if(LaunchScreen.cx == 0 && LaunchScreen.cy == 0){
            LaunchScreen.cx = this.getResources().getDisplayMetrics().widthPixels/2;
            LaunchScreen.cy = this.getResources().getDisplayMetrics().heightPixels;
        }

        if (savedInstanceState == null) {
            final View rootLayout = findViewById(R.id.ld_root_layout);
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(6);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        reset(view);

        //tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        //tabLayout.setupWithViewPager(mViewPager);

    }

    private void circularRevealActivity() {

        final View rootLayout = findViewById(R.id.ld_root_layout);

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, LaunchScreen.cx,
                LaunchScreen.cy, 0, finalRadius);
        circularReveal.setDuration(400);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("Lincoln Douglas Timer", icon, Color.parseColor(sp.getString("ld_color", "-1")));
        ((Activity)this).setTaskDescription(taskDescription);
    }

    @Override
    public void onResume (){
        super.onResume();
        mViewPager.setCurrentItem(position);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("Lincoln Douglas Timer", icon, Color.parseColor(sp.getString("ld_color","-1")));
        ((Activity)this).setTaskDescription(taskDescription);
        findViewById(ld_root_layout).setBackgroundColor(Color.parseColor(sp.getString("ld_color","-1")));
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_colors, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    /*
     * A placeholder fragment containing a simple view.

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        //private String fragments [] = {"1AC", "CX", "1NC", "CX", "1AR", "1NR", "2AR"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public SectionsPagerAdapter(FragmentManager supportFragmentManager, Context applicationContext){
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            //Case is the position, and each position returns different fragments or pages
            switch (position){
                case 0:
                    return new LD_1AC();
                case 1:
                    return new LD_1CX();
                case 2:
                    return new LD_1NC();
                case 3:
                    return new LD_2CX();
                case 4:
                    return new LD_1AR();
                case 5:
                    return new LD_1NR();
                case 6:
                    return new LD_2AR();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            //NUmber of total pages
            return 7; //fragments.length;
        }

        /*@Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1AC";
                case 1:
                    return "NCX";
                case 2:
                    return "1NC";
                case 3:
                    return "ACX";
                case 4:
                    return "1AR";
                case 5:
                    return "2NR";
                case 6:
                    return "2AR";
            }
            return fragments[position];
        }*/

    }

    @Override
    public void onBackPressed() {
        final View rootLayout = findViewById(R.id.ld_root_layout);

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, LaunchScreen.cx,
                LaunchScreen.cy, finalRadius, 0);

        circularReveal.setDuration(400);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rootLayout.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void timer1AC (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_ac", "6")) * 60000) + 200;
        timerTitle = "AC";
        position = 1;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer1AR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_1ar", "4")) * 60000) + 200;
        timerTitle = "1AR";
        position = 5;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer1CX (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_cx", "3")) * 60000) + 200;
        timerTitle = "CX";
        position = 2;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer1NC (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_nc", "7")) * 60000) + 200;
        timerTitle = "NC";
        position = 3;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer1NR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_nr", "6")) * 60000) + 200;
        position = 6;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer2AR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_2ar", "3")) * 60000) + 200;
        timerTitle = "2AR";
        position = 0;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void timer2CX (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("ld_cx", "3")) * 60000) + 200;
        timerTitle = "CX";
        position = 4;
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void negPrep (View view){
        timerTitle = "Neg Prep";
        prep = true;
        aff = false;
        position = mViewPager.getCurrentItem();
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void affPrep (View view){
        timerTitle = "Aff Prep";
        prep = true;
        aff = true;
        position = mViewPager.getCurrentItem();
        startActivity(new Intent (MainActivityLD.this, TimerActivityLD.class));
    }

    public void reset (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        affPrepSeconds = (Integer.parseInt(sp.getString("ld_prep", "4")) * 60000) + 200;
        negPrepSeconds = (Integer.parseInt(sp.getString("ld_prep", "4")) * 60000) + 200;
    }



}
