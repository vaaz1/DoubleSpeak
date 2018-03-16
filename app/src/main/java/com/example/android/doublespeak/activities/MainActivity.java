package com.example.android.doublespeak.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

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

public class MainActivity extends AppCompatActivity implements TimeKeeper.TimerCallback, View.OnClickListener {

    public static final int TIME_LIMIT = 30;
    private static final int NUM_OF_COLUMNS = 4;
    //    create empty list of cells
    private static final List<Cell> cellList = new ArrayList<>(0);

    static {
        cellList.add(new Cell("Löwe", R.drawable.eagle));
        cellList.add(new Cell("Igel", R.drawable.eagle));
        cellList.add(new Cell("Adler", R.drawable.eagle));
        cellList.add(new Cell("Fuchs", R.drawable.eagle));
        cellList.add(new Cell("Eule", R.drawable.eagle));
        cellList.add(new Cell("Affe", R.drawable.putin));
        cellList.add(new Cell("Kuh", R.drawable.putin));
        cellList.add(new Cell("Esel", R.drawable.putin));
        cellList.add(new Cell("Taube", R.drawable.putin));
        cellList.add(new Cell("Pfau", R.drawable.putin));
        cellList.add(new Cell("Hai", R.drawable.putin));
        cellList.add(new Cell("Reh", R.drawable.putin));
    }


    private CardLanguage cardLanguage;
    private SoundPlayer soundPlayer;
    private RelativeLayout appBar;
    private TextSay textSay;
    private CardLanguage.TypeLanguages currentTypeLanguage;
    private TextSay.LocaleLanguage currentLocalLanguage;
    private TextSayEndListener textSayEndListener;
    private int rightGuesses;
    private int firstPosition;
    private int otherPosition;
    private int counter;
    private long startTime;
    private View firstCard;
    private View secondCard;
    private ExplosionField mExplosionField;
    private RecyclerView recycler;
    private TimeKeeper timeKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObjects();
        shuffle(cellList);
        initArray();
        shuffle(cellList);
        initRecyclerView();
    }

    private void initArray() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < cellList.size(); i += 2) {
            cells.add(i, cellList.get(i / 2));
            cells.add(i + 1, cellList.get(i / 2));
        }
        cellList.clear();
        cellList.addAll(cells);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(gridLayoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, cellList);
        recycler.setAdapter(recyclerViewAdapter);
    }

    private void initViews() {
        recycler = findViewById(R.id.game_grid);
        appBar = findViewById(R.id.game_bar);
    }

    private void initObjects() {
        textSay = new TextSay(this);
        soundPlayer = new SoundPlayer(this);
        currentTypeLanguage = CardLanguage.TypeLanguages.TYPE_GERMAN;
        currentLocalLanguage = TextSay.LocaleLanguage.GERMAN;
        cardLanguage = new Animal(CardLanguage.Level.EASY_LEVEL);
        textSayEndListener = new TextSayEndListener();
        timeKeeper = new TimeKeeper(this, TIME_LIMIT);
        mExplosionField = ExplosionField.attach2Window(this);
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
            if (++counter == 1) {
                startTime = System.currentTimeMillis();
                timeKeeper.start();
            }
            if (firstCard == null) {
                firstCard = view;
                firstCard.setOnClickListener(null);
                firstPosition = ((int) firstCard.getTag());
                textSay.setTextSayEndListener(null);
                textSay.say(currentLocalLanguage, cellList.get(firstPosition).getAnimal());
            } else {
                secondCard = view;
                otherPosition = ((int) secondCard.getTag());
                //textSay.say(currentLocalLanguage, cardLanguage.getLanguage(currentTypeLanguage, otherPosition));
                //textSay.setTextSayEndListener(textSayEndListener);
                boolean isSame = cellList.get(firstPosition).equals(cellList.get(otherPosition));
                //textSayEndListener.setSame(isSame);
                if (isSame) {
                    rightGuesses++;
                    mExplosionField.explode(firstCard);
                    mExplosionField.explode(secondCard);
                    if (rightGuesses == cellList.size() / 2) {
                        firstCard.setOnClickListener(null);
                        secondCard.setOnClickListener(null);
                        soundPlayer.makeSoundGameCompleted();
                        long endTime = startTime - System.currentTimeMillis();
                        String second = String.valueOf(endTime / 1000);
                        int countTry = counter / 2;

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
