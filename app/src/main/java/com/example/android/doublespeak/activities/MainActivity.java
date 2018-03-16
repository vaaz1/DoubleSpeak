package com.example.android.doublespeak.activities;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.doublespeak.R;
import com.example.android.doublespeak.carddata.Animal;
import com.example.android.doublespeak.carddata.CardLanguage;
import com.example.android.doublespeak.models.Cell;
import com.example.android.doublespeak.recycler.RecyclerViewAdapter;
import com.example.android.doublespeak.utils.MyBounceInterpolator;
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

    private TextView tvTime, tvPoints;


    static {
        cellList.add(new Cell("Löwe", R.drawable.lowe));
        cellList.add(new Cell("Igel", R.drawable.igel));
        cellList.add(new Cell("Adler", R.drawable.adler));
        cellList.add(new Cell("Fuchs", R.drawable.fuchs));
        cellList.add(new Cell("Eule", R.drawable.eule));
        cellList.add(new Cell("Affe", R.drawable.affe));
        cellList.add(new Cell("Kuh", R.drawable.kuh));
        cellList.add(new Cell("Esel", R.drawable.esel));
        cellList.add(new Cell("Taube", R.drawable.taube));
        cellList.add(new Cell("Pfau", R.drawable.pfau));
        cellList.add(new Cell("Hai", R.drawable.hai));
        cellList.add(new Cell("Reh", R.drawable.reh));
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
    private Handler handler;

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
        try {


        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < cellList.size(); i += 2) {
            cells.add(i, ((Cell) cellList.get(i / 2).clone()));
            cells.add(i + 1, ((Cell) cellList.get(i / 2).clone()));
        }
        cellList.clear();
        cellList.addAll(cells);
        }catch (Exception e){

        }
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
        tvTime = findViewById(R.id.tvTime);
        tvPoints = findViewById(R.id.tvPoints);
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
        handler = new Handler();
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
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);

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
                    tvPoints.setText("points : " + rightGuesses * 10);
                    mExplosionField.explode(firstCard);
                    mExplosionField.explode(secondCard);
                    if (rightGuesses == cellList.size() / 2) {
                        firstCard.setOnClickListener(null);
                        secondCard.setOnClickListener(null);
                        soundPlayer.makeSoundGameCompleted();
                        final long endTime = System.currentTimeMillis()-startTime;
                        final int countTry = counter / 2;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String winMessage = "Level completed!";
                                Intent lastScreenIntent = new Intent (MainActivity.this, GameFinishActivity.class);
                                lastScreenIntent.putExtra("time", String.valueOf(endTime/1000));
                                lastScreenIntent.putExtra("points",String.valueOf(rightGuesses * 10));
                                lastScreenIntent.putExtra("tries", String.valueOf(countTry));
                                lastScreenIntent.putExtra("win_lose", winMessage);
                                startActivity(lastScreenIntent);
                                finish();
                            }
                        },1500);

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

private long second;
    @Override
    public void onTimeUpdate(final long seconds) {
        this.second = seconds;
        handler.post(new Runnable() {
            @Override
            public void run() {
                tvTime.setText("time: " + Long.toString(second) + " sec");

            }
        });
    }

    @Override
    public void onTimerEnded() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() - startTime;
                int countTry = counter / 2;
                String winMessage = "Game over!";
                Intent lastScreenIntent = new Intent (MainActivity.this, GameFinishActivity.class);
                lastScreenIntent.putExtra("time", "time over");
                lastScreenIntent.putExtra("points",String.valueOf(rightGuesses * 10));
                lastScreenIntent.putExtra("tries", String.valueOf(countTry));
                lastScreenIntent.putExtra("win_lose", winMessage);
                startActivity(lastScreenIntent);
                finish();
            }
        });
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
