package com.example.android.doublespeak.utils;

import android.os.SystemClock;

/**
 * Created by roman on 16/03/2018.
 */

public class TimeKeeper {
    private static volatile long mOffset = 0;
    private static volatile long mServerTime = 0;

    private static TimeKeeper mOurInstance = new TimeKeeper();

    private TimeKeeper() {
    }

    public static TimeKeeper getInstance() {
        return mOurInstance;
    }

    //TODO: these are not thread safe, might not be a problem - but fix later anyway
    public void setNewOffset(long serverTime) {
        mOffset = serverTime - SystemClock.elapsedRealtime();
        mServerTime = serverTime;
    }

    //TODO: these are not thread safe, might not be a problem - but fix later anyway
    public long getTime() {
        return SystemClock.elapsedRealtime() + mOffset;
    }

    public long getServerTime() {
        return mServerTime;
    }

    public long getTimeOffset() {
        return mOffset;
    }
}
