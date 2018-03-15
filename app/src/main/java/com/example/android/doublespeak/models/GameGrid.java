package com.example.android.doublespeak.models;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.Map;

/**
 * Created by roman on 15/03/2018.
 */

public final class GameGrid {

    @NonNull
    private final Map<Pair, Cell> positionAnimalMap;

    public GameGrid(@NonNull Map<Pair, Cell> positionAnimalMap) {
        this.positionAnimalMap = positionAnimalMap;
    }

    private final Cell getCellAtPos(Pair pos) {
        return positionAnimalMap.get(pos);
    }
}
