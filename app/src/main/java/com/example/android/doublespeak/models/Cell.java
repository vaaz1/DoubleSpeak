package com.example.android.doublespeak.models;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

/**
 * Created by roman on 15/03/2018.
 */

public final class Cell {
    @NonNull
    private final String animal;
    @NonNull
    private final Pair position;

    public Cell(@NonNull String animal, @NonNull Pair position) {
        this.animal = animal;
        this.position = position;
    }

    @NonNull
    public String getAnimal() {
        return animal;
    }

    @NonNull
    public Pair getPosition() {
        return position;
    }
}
