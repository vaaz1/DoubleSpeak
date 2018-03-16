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
import android.widget.TableLayout;
import android.widget.TableRow;

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

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimeKeeper.TimerCallback {

    public static final int TIME_LIMIT = 30;
    public static final int TIME_LIMIT1 = 30;
    private List<CardLanguage.TranslateImage> arrayListEasyLevel;
    private List<CardLanguage.TranslateImage> currentLevelData;
    private CardLanguage cardLanguage;
    private TextSay textSay;
    private SoundPlayer soundPlayer;
    private CardLanguage.TypeLanguages currentTypeLanguage;
    private TextSay.LocaleLanguage currentLocalLanguage;
    private RelativeLayout appBar;
    private TableLayout mainGameTableLayout;
    private TextSayEndListener textSayEndListener;
    private int rightGuesses;
    private boolean animIsRunning;
    private int firstPosition;
    private int otherPosition;
    private int counter;
    private long startTime;
    private CardView firstCard;
    private CardView secondCard;
    private TimeKeeper timeKeeper;
    private ExplosionField mExplosionField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObjects();
        initArray(arrayListEasyLevel, currentLevelData);
        shuffle(currentLevelData);
        buildScreen();
    }

    private void buildScreen() {
        int position = 0;
        TableLayout.LayoutParams rowParam = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(rowParam);
            for (int j = 0; j < 4; j++) {
                CardView cardView = (CardView) View.inflate(this, R.layout.item_putin, null);
                cardView.setOnClickListener(this);
                cardView.setTag(position);
                ImageView imageInside = cardView.findViewById(R.id.card_image);
                tableRow.addView(cardView);
                //imageInside.setImageResource(R.drawable.material_mountain);
                Glide.with(this).load(currentLevelData.get(position).getImageRes()).into(imageInside);
                position++;

            }
            mainGameTableLayout.addView(tableRow);
        }
        mainGameTableLayout.invalidate();
    }

    private void initViews() {
        mainGameTableLayout = findViewById(R.id.mainGameTableLayout);
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
        mExplosionField = ExplosionField.attach2Window(this);
        timeKeeper = new TimeKeeper(this, TIME_LIMIT1);

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
                timeKeeper.start();
            }

            if (firstCard == null) {
                firstCard = (CardView) view;
                firstCard.setOnClickListener(null);
                firstPosition = ((int) firstCard.getTag());
                textSay.setTextSayEndListener(null);
                textSay.say(currentLocalLanguage, cardLanguage.getLanguage(currentTypeLanguage, firstPosition));
            } else {
                secondCard = ((CardView) view);
                otherPosition = ((int) secondCard.getTag());
                //textSay.say(currentLocalLanguage, cardLanguage.getLanguage(currentTypeLanguage, otherPosition));
                //textSay.setTextSayEndListener(textSayEndListener);
                boolean isSame = CardLanguage.isSameData(arrayListEasyLevel.get(firstPosition), arrayListEasyLevel.get(otherPosition));
                //textSayEndListener.setSame(isSame);
                if (isSame) {
                    rightGuesses++;
                    mExplosionField.explode(firstCard);
                    mExplosionField.explode(secondCard);
                    if (rightGuesses == currentLevelData.size() / 2) {
                        firstCard.setOnClickListener(null);
                        secondCard.setOnClickListener(null);
                        soundPlayer.makeSoundGameCompleted();
                    } else {
                        soundPlayer.makeSoundSuccess();

                    }
                } else {
                    soundPlayer.makeSoundFail();

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
