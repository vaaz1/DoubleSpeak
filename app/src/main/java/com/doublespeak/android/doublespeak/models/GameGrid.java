package com.doublespeak.android.doublespeak.models;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.Map;


public final class GameGrid {

    @NonNull
    private final Map<Pair, Cell> positionAnimalMap;

    public GameGrid(@NonNull Map<Pair, Cell> positionAnimalMap) {
        this.positionAnimalMap = positionAnimalMap;
    }

    private Cell getCellAtPos(Pair pos) {
        return positionAnimalMap.get(pos);
    }
}
