package com.doublespeak.android.doublespeak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.doublespeak.android.doublespeak.models.Cell;
import com.doublespeak.android.doublespeak.recycler.RecyclerViewAdapter;
import com.doublespeak.android.doublespeak.utils.MyBounceInterpolator;
import com.doublespeak.android.doublespeak.utils.SoundPlayer;
import com.doublespeak.android.doublespeak.utils.TextSay;
import com.doublespeak.android.doublespeak.utils.TimeKeeper;
import com.example.android.doublespeak.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements TimeKeeper.TimerCallback, View.OnClickListener {

    public static final int TIME_LIMIT = 30;
    private static final int NUM_OF_COLUMNS = 4;
    //    create empty list of cells
    private static final List<Cell> cellList = new ArrayList<>(0);
    public static final String TIME = "time";
    public static final String POINTS = "points";
    public static final String TRIES = "tries";
    public static final String WIN_LOSE = "win_lose";

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

    private SoundPlayer soundPlayer;
    private TextSay textSay;
    private TextSay.LocaleLanguage currentLocalLanguage;
    private int firstPosition;
    private int rightGuesses;
    private int counter;
    private long startTime, endTime, second;
    private View firstCard;
    private ExplosionField mExplosionField;
    private RecyclerView recycler;
    private TimeKeeper timeKeeper;
    private Handler handler;
    private Animation myAnim;
    private MyBounceInterpolator interpolator;
    private Runnable updateSecondTextRunnable = new Runnable() {
        @Override
        public void run() {
            tvTime.setText(String.format("time: %s sec", Long.toString(second)));
        }
    };

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
                cells.add(i, ((Cell) cellList.get(i / 2).clone()).setModeCell(Cell.ModeCell.IsText));
                cells.add(i + 1, ((Cell) cellList.get(i / 2).clone()).setModeCell(Cell.ModeCell.IsImage));
            }
            cellList.clear();
            cellList.addAll(cells);
        } catch (Exception ignore) {

        }
    }

    private void initRecyclerView() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(this, NUM_OF_COLUMNS));
        recycler.setAdapter(new RecyclerViewAdapter(this, cellList));
    }

    private void initViews() {
        recycler = findViewById(R.id.game_grid);
        //appBar = findViewById(R.id.game_bar);
        tvTime = findViewById(R.id.tvTime);
        tvPoints = findViewById(R.id.tvPoints);
    }

    private void initObjects() {
        textSay = new TextSay(this);
        soundPlayer = new SoundPlayer(this);
        //currentTypeLanguage = CardLanguage.TypeLanguages.TYPE_GERMAN;
        currentLocalLanguage = TextSay.LocaleLanguage.GERMAN;
        //cardLanguage = new Animal(CardLanguage.Level.EASY_LEVEL);
        //textSayEndListener = new TextSayEndListener();
        timeKeeper = new TimeKeeper(this, TIME_LIMIT);
        mExplosionField = ExplosionField.attach2Window(this);
        handler = new Handler();
        interpolator = new MyBounceInterpolator(0.2, 10);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
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
    public void onClick(View currentCardClicked) {
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        myAnim.setInterpolator(interpolator);
        currentCardClicked.startAnimation(myAnim);
        try {
            if (++counter == 1) {
                startTime = System.currentTimeMillis();
                timeKeeper.start();
            }
            if (firstCard == null) {
                firstCard = currentCardClicked;
                firstCard.setOnClickListener(null);
                firstPosition = ((int) firstCard.getTag());
                textSay.say(currentLocalLanguage, cellList.get(firstPosition).getAnimal());
            } else {
                int otherPosition = ((int) currentCardClicked.getTag());
                boolean isSame = cellList.get(firstPosition).equals(cellList.get(otherPosition));
                if (isSame) {
                    timeKeeper.cancel();
                    currentCardClicked.setOnClickListener(null);
                    rightGuesses++;
                    tvPoints.setText(String.format("points : %s", String.valueOf(rightGuesses * 10)));
                    mExplosionField.explode(firstCard);
                    mExplosionField.explode(currentCardClicked);
                    if (rightGuesses == cellList.size() / 2) {
                        firstCard.setOnClickListener(null);
                        currentCardClicked.setOnClickListener(null);
                        soundPlayer.makeSoundGameCompleted();
                        endTime = System.currentTimeMillis() - startTime;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goToGameFinishActivity(String.valueOf(endTime / 1000) + " sec", "Level completed ! ");
                            }
                        }, 1000);

                    } else {
                        soundPlayer.makeSoundSuccess();

                    }
                } else {
                    soundPlayer.makeSoundFail();
                    firstCard.setOnClickListener(this);
                }
                firstCard = null;

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void goToGameFinishActivity(String time, String winLoseMessage) {
        Intent lastScreenIntent = new Intent(MainActivity.this, GameFinishActivity.class);
        lastScreenIntent.putExtra(TIME, time);
        lastScreenIntent.putExtra(POINTS, String.valueOf(rightGuesses * 10));
        lastScreenIntent.putExtra(TRIES, String.valueOf(counter / 2));
        lastScreenIntent.putExtra(WIN_LOSE, winLoseMessage);
        startActivity(lastScreenIntent);
        finish();
    }

    @Override
    public void onTimeUpdate(final long seconds) {
        this.second = seconds;
        handler.post(updateSecondTextRunnable);
    }

    @Override
    public void onTimerEnded() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                goToGameFinishActivity("time over", "Game over ! ");
            }
        });
    }


}
