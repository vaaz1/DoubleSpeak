package com.doublespeak.android.doublespeak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.doublespeak.android.doublespeak.utils.SoundPlayer;
import com.example.android.doublespeak.R;


public class GameFinishActivity extends AppCompatActivity {


    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        soundPlayer = new SoundPlayer(this);
        initTextViewWithText();

    }

    private void initTextViewWithText() {
        Intent intent = getIntent();

        TextView timeTextView = findViewById(R.id.time_score);
        TextView pointsTextView = findViewById(R.id.word_number_score);
        TextView finishTextView = findViewById(R.id.win_screen_title);
        TextView triesTextView = findViewById(R.id.tries_number_score);

        timeTextView.setText(intent.getStringExtra(MainActivity.TIME));
        pointsTextView.setText(intent.getStringExtra(MainActivity.POINTS));
        triesTextView.setText(intent.getStringExtra(MainActivity.TRIES));
        finishTextView.setText(intent.getStringExtra(MainActivity.WIN_LOSE));
    }

    public void tryAgain(View view) {
        soundPlayer.stop();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
