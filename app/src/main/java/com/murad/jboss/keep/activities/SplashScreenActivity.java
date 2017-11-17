package com.murad.jboss.keep.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.murad.jboss.keep.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String TAG = SplashScreenActivity.class.getSimpleName();

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate");

        final Intent intent = new Intent(this, MainActivity.class);

        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        int SPLASH_DISPLAY_LENGTH = 500;
        handler = new Handler();
        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        if(handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
