package com.doublespeak.android.doublespeak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.doublespeak.android.doublespeak.utils.Global;
import com.example.android.doublespeak.R;


public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private TextView tvGoodLuck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lottieAnimationView = findViewById(R.id.animation_view);
        tvGoodLuck = findViewById(R.id.tvGoodLuck);

    }


    @Override
    protected void onResume() {
        super.onResume();
        lottieAnimationView.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    //Global.fadeView(1,0,lottieAnimationView,60);
                    tvGoodLuck.setVisibility(View.VISIBLE);
                    //Global.fadeView(0,1,tvGoodLuck,100);
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lottieAnimationView.pauseAnimation();
    }


}