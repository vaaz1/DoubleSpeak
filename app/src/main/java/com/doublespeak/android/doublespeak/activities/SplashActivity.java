package com.doublespeak.android.doublespeak.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.doublespeak.android.doublespeak.R;
import com.doublespeak.android.doublespeak.utils.Global;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }


    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                proceed();
            }
        }, 3000);
    }


    private void proceed() {
        Global.launchActivity(this, MainActivity.class);
        finish();
    }

}