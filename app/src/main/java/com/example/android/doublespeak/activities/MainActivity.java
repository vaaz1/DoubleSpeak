package com.example.android.doublespeak.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.android.doublespeak.R;
import com.example.android.doublespeak.carddata.Animal;
import com.example.android.doublespeak.carddata.CardLanguage;
import com.example.android.doublespeak.utils.SoundPlayer;
import com.example.android.doublespeak.utils.TextSay;
import com.example.android.doublespeak.utils.TimeKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimeKeeper.TimerCallback {

    private List<CardLanguage.TranslateImage> arrayListEasyLevel;
    private List<CardLanguage.TranslateImage> currentLevelData;
    private CardLanguage cardLanguage;
    private TextSay textSay;
    private SoundPlayer soundPlayer;
    private CardLanguage.TypeLanguages currentTypeLanguage;
    private TextSay.LocaleLanguage currentLocalLanguage;
    private RelativeLayout appBar;
    private LinearLayout mainLinearLayoutGame;
    private TextSayEndListener textSayEndListener;
    private int rightGuesses;
    private boolean animIsRunning;
    private int firstPosition;
    private int otherPosition;
    private int counter;




    /*    @Override
        public void onClick(View view) {
            if (flip != null) {
                if (isRun || flip.isRunning()) {
                    Log.d("isRun", "true");
                    return;
                }
            }
            counter++;
            if (counter == 1) {
                startTime = System.currentTimeMillis();
            }
            //flipIt(view);
            //Toast.makeText(this, "Tag is: " + view.getTag(), Toast.LENGTH_SHORT).show();
            final ImageView clickedCard = (ImageView) view;
            final int tag = Integer.parseInt(clickedCard.getTag().toString());
            //int tag = Integer.valueOf(clickedCard.getTag().toString());
            clickedCard.setImageResource(heroes[tag]);
            if (counter % 2 == 0) {
                // Even card - need to check this card and the previous card
                int tag2 = Integer.parseInt(firstCard.getTag().toString());
                flipIt(firstCard);
                flipIt(clickedCard);
                if (heroes[tag].equals(heroes[tag2])) {
                    // Cards are the same
                    clickedCard.setOnClickListener(null);
                    //firstCard.setOnClickListener(null);
                    rightGuesses++;
                    if (heroes.length == (2 * rightGuesses)) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                removeToEndActivity();
                            }
                        }, flip.isRunning() ? 900 : 0);
                    }
                } else {
                    // Cards are different
                    isRun = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firstCard.setOnClickListener(GameActivity.this);
                            firstCard.setImageResource(R.drawable.card);
                            clickedCard.setImageResource(R.drawable.card);
                            isRun = false;
                        }
                    }, 900);
                    *//*flipIt(firstCard);
                flipIt(clickedCard);*//*

            }
            Log.d("isRun", "false");
        } else {
            // Odd Card
            firstCard = clickedCard;
            clickedCard.setOnClickListener(null);
        }
    }*/
    private long startTime;
    private CardView firstCard;
    private CardView secondCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObjects();
        initArray(arrayListEasyLevel, currentLevelData);
        shuffle(currentLevelData);
        int position = 0;

        CardView.LayoutParams imageParam = new FrameLayout.LayoutParams(100, 100, Gravity.CENTER);
        GridLayout.LayoutParams cardParam = new GridLayout.LayoutParams(GridLayout.spec(0, 0.0F), GridLayout.spec(0, 0.0F));
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);


        for (int i = 0; i < 3; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.VERTICAL);
            row.setLayoutParams(rowParams);
            for (int j = 0; j < 4; j++) {
//                create the grid cell card
                CardView cardView = (CardView) View.inflate(this, R.layout.item_putin, null);
//                add the card to the grid
                cardView.setOnClickListener(this);
                cardView.setTag(position);
//                get the card imageView
                ImageView imageInside = cardView.findViewById(R.id.card_image);
                //imageInside.setLayoutParams(imageParam);

//                set the card image
                imageInside.setImageResource(R.drawable.material_mountain);
                //Glide.with(this).load(currentLevelData.get(position).getImageRes()).into(imageInside);
                position++;
                row.addView(cardView);

            }
            mainLinearLayoutGame.addView(row);
        }
    }

    private void initViews() {
        mainLinearLayoutGame = findViewById(R.id.mainLinearLayoutGame);
        appBar = findViewById(R.id.game_bar);


    }

    private void initObjects() {
        textSay = new TextSay(this);
        soundPlayer = new SoundPlayer(this);
        currentTypeLanguage = CardLanguage.TypeLanguages.TYPE_GERMAN;
        currentLocalLanguage = TextSay.LocaleLanguage.GERMAN;
        cardLanguage = new Animal(CardLanguage.Level.EASY_LEVEL);
        arrayListEasyLevel = cardLanguage.getArrayListEasyLevel();
        currentLevelData = new ArrayList<>(arrayListEasyLevel.size() * 2);
        textSayEndListener = new TextSayEndListener();
    }

    private void initArray(List<CardLanguage.TranslateImage> arrayListLevel, List<CardLanguage.TranslateImage> currentLevelData) {
        for (int i = 0; i < arrayListLevel.size(); i += 2) {
            currentLevelData.add(i, arrayListLevel.get(i / 2));
            currentLevelData.add(i + 1, arrayListLevel.get(i / 2));
        }
    }

    private <T> void shuffle(List<T> arrayList) {
        Random random = new Random(System.currentTimeMillis());
        int randomNumber;
        T temp;
        for (int i = 0; i < arrayList.size(); i++) {
            randomNumber = random.nextInt(arrayList.size());
            temp = arrayList.get(i);
            arrayList.set(i, arrayList.get(randomNumber));
            arrayList.set(randomNumber, temp);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            if (animIsRunning) {
                return;
            }
            counter++;
            if (counter == 1) {
                // First click app
                startTime = System.currentTimeMillis();
            }

            if (firstCard == null) {
                firstCard = (CardView) view;
                firstCard.setOnClickListener(null);
                firstPosition = ((int) firstCard.getTag());
                textSay.say(currentLocalLanguage, cardLanguage.getLanguage(currentTypeLanguage, firstPosition));

            } else {
                secondCard = ((CardView) view);
                otherPosition = ((int) secondCard.getTag());
                textSay.say(currentLocalLanguage, cardLanguage.getLanguage(currentTypeLanguage, otherPosition));
                boolean isSame = CardLanguage.isSameData(arrayListEasyLevel.get(firstPosition), arrayListEasyLevel.get(otherPosition));
                textSayEndListener.setSame(isSame);
                textSay.setTextSayEndListener(textSayEndListener);
                if (isSame) {
                    rightGuesses++;
                    if (rightGuesses == currentLevelData.size() / 2) {
                        soundPlayer.makeSoundGameCompleted();
                    }
                }
                firstCard.setOnClickListener(this);
                firstCard = null;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void onTimeUpdate(long seconds) {

    }

    @Override
    public void onTimerEnded() {

    }

    private class TextSayEndListener implements TextSay.TextSayEndListener {
        private boolean isSame;


        public void setSame(boolean same) {
            isSame = same;
        }

        @Override
        public void onFinish() {
            try {
                if (isSame) {
                    soundPlayer.makeSoundSuccess();
                } else {
                    soundPlayer.makeSoundFail();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
