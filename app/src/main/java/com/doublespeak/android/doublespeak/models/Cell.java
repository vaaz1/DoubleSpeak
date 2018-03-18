package com.doublespeak.android.doublespeak.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public final class Cell implements Cloneable {
    @NonNull
    private final String animal;
    private final @DrawableRes
    int imageRes;
    private ModeCell modeCell;

    public Cell(@NonNull String animal, int imageRes) {
        this(animal, imageRes, null);
    }

    private Cell(@NonNull String animal, int imageRes, ModeCell modeCell) {
        this.animal = animal;
        this.imageRes = imageRes;
        this.modeCell = modeCell;
    }

    public int getImageRes() {
        return imageRes;
    }

    public ModeCell getModeCell() {
        return modeCell;
    }

    public Cell setModeCell(ModeCell modeCell) {
        this.modeCell = modeCell;
        return this;
    }

    @NonNull
    public String getAnimal() {
        return animal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof Cell) {
            return this.getImageRes() == ((Cell) obj).getImageRes();
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Cell(getAnimal(), getImageRes(), ModeCell.IsImage);
    }

    public enum ModeCell {
        IsImage, IsText
    }
}
