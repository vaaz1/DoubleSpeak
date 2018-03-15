package com.example.android.doublespeak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gameGrid = findViewById(R.id.game_grid);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {

//                create the grid cell card
                View putinView = View.inflate(this, R.layout.item_putin, null);

//                add the card to the grid
                gameGrid.addView(putinView);

//                get the card imageview
                ImageView putinImg = putinView.findViewById(R.id.card_image);

//                set the card image
                Glide.with(this).load(R.drawable.putin).into(putinImg);

            }
        }

        gameGrid.invalidate();


    }
}
