package com.example.android.doublespeak.utils;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by roman on 16/03/2018.
 */

public final class TimeKeeper {
    private static long timeLimit;
    private final WeakReference<TimerCallback> callback;
    private final Timer timer;
    private long initialTime;
    private long elapsedtime;


    public static void setTimeLimit(long timeLimit) {
        TimeKeeper.timeLimit = timeLimit;
    }

    public TimeKeeper(@NonNull final TimerCallback callback) {
        this.initialTime = SystemClock.elapsedRealtime();
        this.timer = new Timer();
        this.callback = new WeakReference<>(callback);

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedtime = (SystemClock.elapsedRealtime() - initialTime) / 1000;
                long remainingTime = TimeKeeper.timeLimit - elapsedtime;
                if (TimeKeeper.this.callback.get() == null) {
                    timer.cancel();
                    timer.purge();
                } else if (remainingTime != 0) {
                    TimeKeeper.this.callback.get().onTimeUpdate(remainingTime);
                } else {
                    TimeKeeper.this.callback.get().onTimerEnded();
                }
            }
        }, 1000, 1000);
    }

    public interface TimerCallback {

        void onTimeUpdate(long seconds);

        void onTimerEnded();
    }


}
