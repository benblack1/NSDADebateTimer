package com.blackinc.debatetimer.worldschools;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackinc.debatetimer.R;

import java.util.concurrent.TimeUnit;

import static com.blackinc.debatetimer.R.id.root_layout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TimerActivityWS extends AppCompatActivity {

    public long seconds;
    private static final String FORMAT = "%01d:%02d";
    private CountDownTimer countDownTimer;
    private boolean started = false;
    private boolean paused = false;
    private int i = 0;
    private final Handler handler = new Handler();
    private MediaPlayer player;
    public Animation anim;
    public View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);

        setContentView(R.layout.ws_activity_timer);


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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        player = MediaPlayer.create(getApplicationContext(), R.raw.beep);

    }

    private void circularRevealActivity() {

        final View rootLayout = findViewById(R.id.root_layout);
        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, MainActivityWS.cxWS,
                MainActivityWS.cyWS,0, finalRadius);
        circularReveal.setDuration(400);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private void endCircularRevealActivity() {

        final View rootLayout = findViewById(R.id.root_layout);
        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, MainActivityWS.cxWS,
                MainActivityWS.cyWS, finalRadius, 0);
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

    @Override
    public void onStop() {
        super.onStop();
        if(!paused){
            paused = true;
        }
        else
            paused = false;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        TaskDescription taskDescription = new TaskDescription("World Schools Timer", icon, Color.parseColor("#000000"));
        ((Activity)this).setTaskDescription(taskDescription);
    }

    @Override
    public void onResume(){
        super.onResume();
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        findViewById(root_layout).setBackgroundColor(Color.parseColor(sp.getString("ws_color","-1")));
        TaskDescription taskDescription = new TaskDescription("World Schools Timer", icon, Color.parseColor("#000000"));
        ((Activity)this).setTaskDescription(taskDescription);
        TextView timerTitle = (TextView) findViewById((R.id.timerTitle));
        timerTitle.setText(MainActivityWS.timerTitle);
        view1 = findViewById(R.id.reveal);
        anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_PARENT, .5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(MainActivityWS.seconds);
        anim.setFillAfter(true);
        if(!paused){
            view1.startAnimation(anim);
        }
        start(null);
    }

    public void launchMain(View view) {
        endCircularRevealActivity();
        countDownTimer.cancel();
        if(player.getCurrentPosition() > 0){
            player.stop();
        }

        //finish();
        //startActivity(new Intent(TimerActivityLD.this, MainActivityWS.class));
    }

    public void pause (View view){
        start(null);
        Button button = (Button)findViewById(R.id.playbutton);
        button.setVisibility(view.GONE);
        ImageView image = (ImageView) findViewById(R.id.playimage);
        image.setVisibility(view.GONE);
        Button button1 = (Button)findViewById(R.id.pausebutton);
        button1.setVisibility(view.VISIBLE);
        ImageView image1 = (ImageView)findViewById(R.id.pauseimage);
        image1.setVisibility(view.VISIBLE);
        view1 = findViewById(R.id.reveal);
        anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_PARENT, .5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(MainActivityWS.seconds);
        anim.setFillAfter(true);
        view1.startAnimation(anim);
    }

    public void play (View view){
        start(null);
        Button button = (Button)findViewById(R.id.playbutton);
        button.setVisibility(view.VISIBLE);
        ImageView image = (ImageView) findViewById(R.id.playimage);
        image.setVisibility(view.VISIBLE);
        Button button1 = (Button)findViewById(R.id.pausebutton);
        button1.setVisibility(view.GONE);
        ImageView image1 = (ImageView)findViewById(R.id.pauseimage);
        image1.setVisibility(view.GONE);
        anim.cancel();
    }

    @Override
    public void onBackPressed(){
        endCircularRevealActivity();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        countDownTimer.cancel();
    }

    public void start(View view) {
        seconds = MainActivityWS.seconds;
        started = true;
        if (!paused) {
            paused = true;

            countDownTimer = new CountDownTimer(seconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    TextView timerText = (TextView) findViewById(R.id.timerText);

                    timerText.setText("" + String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                    MainActivityWS.seconds = MainActivityWS.seconds - 1000;
                }

                public void onFinish() {

                    TextView timerText = (TextView) findViewById(R.id.timerText);
                    timerText.setText("0:00");
                    started = false;
                    flashScreen();
                }

            }.start();

        } else {
            countDownTimer.cancel();
            paused = false;
        }

    }

    public void flashScreen() {
        final ImageView image = (ImageView) findViewById(R.id.flashscreen);
        int a = 400;

        for(int b = 0; b<1000; b++) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    image.setVisibility(View.GONE);

                }
            }, i+=a);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    image.setVisibility(View.VISIBLE);

                }
            }, i+=a);
        }

    }

}
