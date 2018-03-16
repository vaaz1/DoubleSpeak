package com.example.android.doublespeak.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by roman on 15/03/2018.
 */

public final class Cell {
    @NonNull
    private final String animal;
    private final  @DrawableRes int imageRes;
    private ModeCell modeCell = ModeCell.IsImage;


    public enum ModeCell{
        IsImage,IsText;
    }




    public Cell(@NonNull String animal, int imageRes) {
        this.animal = animal;
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public ModeCell getModeCell() {
        return modeCell;
    }

    public void setModeCell(ModeCell modeCell) {
        this.modeCell = modeCell;
    }

    @NonNull
    public String getAnimal() {
        return animal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }else if (obj instanceof Cell){
            return this.getImageRes() == ((Cell) obj).getImageRes();
        }
        return false;
    }
}
