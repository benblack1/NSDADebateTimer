package com.blackinc.debatetimer.bigquestions;

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

import com.blackinc.debatetimer.CircleIndicator;
import com.blackinc.debatetimer.LaunchScreen;
import com.blackinc.debatetimer.R;

import static com.blackinc.debatetimer.R.id.container;
import static com.blackinc.debatetimer.R.id.ld_root_layout;
import static com.blackinc.debatetimer.R.id.root_layout;

public class MainActivityBQ extends AppCompatActivity {

    public static long seconds;
    public static String timerTitle;
    TabLayout tabLayout;
    public static long affPrepSeconds = 180200;
    public static long negPrepSeconds = 180200;
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

        setContentView(R.layout.bq_activity_main);

        if (savedInstanceState == null) {
            final View rootLayout = findViewById(R.id.root_layout);
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
        mViewPager.setOffscreenPageLimit(9);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        reset(view);

        //tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        //tabLayout.setupWithViewPager(mViewPager);

    }

    private void circularRevealActivity() {

        final View rootLayout = findViewById(R.id.root_layout);

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
        TaskDescription taskDescription = new TaskDescription("Big Questions Timer", icon, Color.parseColor(sp.getString("bq_color", "-1")));
        ((Activity)this).setTaskDescription(taskDescription);
    }

    @Override
    public void onResume (){
        super.onResume();
        mViewPager.setCurrentItem(position);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("Big Questions Timer", icon, Color.parseColor(sp.getString("bq_color","-1")));
        ((Activity)this).setTaskDescription(taskDescription);
        findViewById(root_layout).setBackgroundColor(Color.parseColor(sp.getString("bq_color","-1")));
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


        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast toast = Toast.makeText(getApplicationContext(), "Hey", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }*/

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
                    return new BQ_1AC();
                case 1:
                    return new BQ_1NC();
                case 2:
                    return new BQ_1QS();
                case 3:
                    return new BQ_1AR();
                case 4:
                    return new BQ_1NR();
                case 5:
                    return new BQ_2QS();
                case 6:
                    return new BQ_2AC();
                case 7:
                    return new BQ_2NC();
                case 8:
                    return new BQ_2AR();
                case 9:
                    return new BQ_2NR();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            //NUmber of total pages
            return 10; //fragments.length;
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
        final View rootLayout = findViewById(R.id.root_layout);

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
        seconds = (Integer.parseInt(sp.getString("bq_cn", "5")) * 60000) + 200;
        timerTitle = "AC";
        position = 1;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer1NC (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_cn", "5")) * 60000) + 200;
        timerTitle = "NC";
        position = 2;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer1QS (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_qs", "3")) * 60000) + 200;
        timerTitle = "QS";
        position = 3;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer1AR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_rb", "4")) * 60000) + 200;
        timerTitle = "AR";
        position = 4;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer1NR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_rb", "4")) * 60000) + 200;
        timerTitle = "NR";
        position = 5;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer2QS (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_qs", "3")) * 60000) + 200;
        timerTitle = "QS";
        position = 6;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer2AC (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_cd", "3")) * 60000) + 200;
        timerTitle = "AC";
        position = 7;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer2NC (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_cd", "3")) * 60000) + 200;
        timerTitle = "NC";
        position = 8;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }
    public void timer2AR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_rt", "3")) * 60000) + 200;
        timerTitle = "AR";
        position = 9;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void timer2NR (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = (Integer.parseInt(sp.getString("bq_rt", "3")) * 60000) + 200;
        timerTitle = "NR";
        position = 0;
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void affPrep (View view){
        timerTitle = "Aff Prep";
        prep = true;
        aff = true;
        position = mViewPager.getCurrentItem();
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void negPrep (View view){
        timerTitle = "Neg Prep";
        prep = true;
        aff = false;
        position = mViewPager.getCurrentItem();
        startActivity(new Intent (MainActivityBQ.this, TimerActivityBQ.class));
    }

    public void reset (View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        affPrepSeconds = (Integer.parseInt(sp.getString("bq_prep", "3")) * 60000) + 200;
        negPrepSeconds = (Integer.parseInt(sp.getString("bq_prep", "3")) * 60000) + 200;
    }



}
