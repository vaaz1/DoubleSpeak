package com.example.android.doublespeak.utils;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;




public final class TimeKeeper {
    private long timeLimit;
    private final WeakReference<TimerCallback> callback;
    private final Timer timer;
    private long initialTime;
    private long elapsedTime;



    public TimeKeeper(@NonNull TimerCallback callback,long timeLimit) {
        this.timeLimit = timeLimit;
        this.timer = new Timer();
        this.callback = new WeakReference<>(callback);


    }


    public void start(){
        // Update the elapsed time every second.
        this.initialTime = SystemClock.elapsedRealtime();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = (SystemClock.elapsedRealtime() - initialTime) / 1000;
                long remainingTime = timeLimit - elapsedTime;
                if (remainingTime < 0){
                    remainingTime *= -1;
                }
                if (callback.get() == null) {
                    timer.cancel();
                    timer.purge();
                } else if (remainingTime > 0) {
                    callback.get().onTimeUpdate(remainingTime);
                } else {
                    callback.get().onTimerEnded();
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 1000, 1000);
    }

    public interface TimerCallback {

        void onTimeUpdate(long seconds);

        void onTimerEnded();
    }


}
