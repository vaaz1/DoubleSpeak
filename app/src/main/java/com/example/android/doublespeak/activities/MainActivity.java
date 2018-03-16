package com.example.android.doublespeak.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.android.doublespeak.R;
import com.example.android.doublespeak.carddata.Animal;
import com.example.android.doublespeak.carddata.CardLanguage;
import com.example.android.doublespeak.models.Cell;
import com.example.android.doublespeak.recycler.RecyclerViewAdapter;
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
    private static final int NUM_OF_COLUMNS = 4;
    //    create empty list of cells
    private final static List<Cell> cellList = new ArrayList<>(0);

    static {
        cellList.add(new Cell("Löwe"));
        cellList.add(new Cell("Igel"));
        cellList.add(new Cell("Adler"));
        cellList.add(new Cell("Fuchs"));
        cellList.add(new Cell("Eule"));
        cellList.add(new Cell("Affe"));
        cellList.add(new Cell("Kuh"));
        cellList.add(new Cell("Esel"));
        cellList.add(new Cell("Taube"));
        cellList.add(new Cell("Pfau"));
        cellList.add(new Cell("Hai"));
        cellList.add(new Cell("Reh"));
    }

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
    private ExplosionField mExplosionField;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObjects();
        initArray(arrayListEasyLevel, currentLevelData);
        shuffle(currentLevelData);
        int position = 0;
        TimeKeeper.setTimeLimit(TIME_LIMIT);
        mExplosionField = ExplosionField.attach2Window(this);

                /* init recycler view */
        recycler = findViewById(R.id.game_grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(gridLayoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, cellList);
        recycler.setAdapter(recyclerViewAdapter);

    }

    private void initViews() {
//        mainGameTableLayout = findViewById(R.id.mainGameTableLayout);
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
                    if (rightGuesses == currentLevelData.size() / 2) {
                        firstCard.setOnClickListener(null);
                        secondCard.setOnClickListener(null);
                        soundPlayer.makeSoundGameCompleted();
                        long endTime = startTime - System.currentTimeMillis();
                        String second = String.valueOf(endTime / 1000);
                        int countTry = counter / 2;

                    } else {
                        soundPlayer.makeSoundSuccess();
                        mExplosionField.explode(firstCard);
                        mExplosionField.explode(secondCard);
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
