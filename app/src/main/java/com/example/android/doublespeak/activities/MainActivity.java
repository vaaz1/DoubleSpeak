package com.example.android.doublespeak.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.android.doublespeak.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gameGrid = findViewById(R.id.game_grid);

        RelativeLayout appBar = findViewById(R.id.game_bar);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {

//                create the grid cell card
                View putinView = View.inflate(this, R.layout.item_putin, null);

//                add the card to the grid
                gameGrid.addView(putinView);
//                get the card imageview
                ImageView putinImg = putinView.findViewById(R.id.card_image);

                putinImg.setOnClickListener(this);

//                set the card image
                Glide.with(this).load(R.drawable.putin).into(putinImg);

            }
        }

        gameGrid.invalidate();


    }


    private int counter;
    private long startTime;
    private ImageView firstCard;
    private ImageView secondCard;
    @Override
    public void onClick(View view) {
        counter++;
        if (counter == 1){
            startTime = System.currentTimeMillis();
        }

        if (firstCard == null){
            firstCard = (ImageView) view;
        }else{
            secondCard = ((ImageView) view);

        }
    }
}
