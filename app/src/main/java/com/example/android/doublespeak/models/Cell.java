package com.example.android.doublespeak.models;

import android.support.annotation.NonNull;

/**
 * Created by roman on 15/03/2018.
 */

public final class Cell {
    @NonNull
    private final String animal;

    public Cell(@NonNull String animal) {
        this.animal = animal;
    }

    @NonNull
    public String getAnimal() {
        return animal;
    }

}
