package com.example.android.doublespeak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.doublespeak.R;

/**
 * Created by Vera on 16.03.2018.
 */

public class GameFinishActivity extends AppCompatActivity {

    String text_time;
    String text_points;
    String text_win_lose;
    String text_tries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent intent = getIntent();
        text_time = intent.getStringExtra("time");
        try {
            //win
            Integer.valueOf(text_time);
            text_time += " sec ";
        } catch (NumberFormatException e) {
           //lose
        }
        text_points = intent.getStringExtra("points");
        text_tries = intent.getStringExtra("tries");
        text_win_lose = intent.getStringExtra("win_lose");



        TextView timeTextView = findViewById(R.id.time_score);
        TextView pointsTextView = findViewById(R.id.word_number_score);
        TextView finishTextView = findViewById(R.id.win_screen_title);
        TextView triesTextView = findViewById(R.id.tries_number_score);

        timeTextView.setText(text_time);
        pointsTextView.setText(text_points);
        triesTextView.setText(text_tries);
        finishTextView.setText(text_win_lose);
    }
}
