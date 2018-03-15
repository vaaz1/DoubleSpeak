package com.example.android.doublespeak.carddata;


import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

public abstract class CardLanguage {


    public static final int INITIAL_CAPACITY = 200;
    private static int MAX_TRANSLATE = -1;

    private ArrayList<TranslateImage> arrayListLanguages;
    private ArrayList<TranslateImage> arrayListEasyLevel;
    private ArrayList<TranslateImage> arrayListMediumLevel;
    private ArrayList<TranslateImage> arrayListHardLevel;

    public int EASY_LEVEL;
    public int MEDIUM_LEVEL;
    public int HARD_LEVEL;


    public enum Level {
        EASY_LEVEL, MEDIUM_LEVEL, HARD_LEVEL, ALL_LEVEL;
    }

    protected class TranslateImage {
        private @DrawableRes
        int imageRes;
        protected String[] languages;

        protected TranslateImage(@DrawableRes int imageRes, String[] languages) {
            if (MAX_TRANSLATE == -1) {
                MAX_TRANSLATE = languages.length;
            } else if (MAX_TRANSLATE < languages.length) {
                throw new RuntimeException("You muse to translate least " + (languages.length - 1) + " Translates");
            }
            this.imageRes = imageRes;
            this.languages = languages;
        }

        public @DrawableRes
        int getImageRes() {
            return imageRes;
        }

    }


    public enum TypeLanguages {
        // image and name;
        // TODO: 27/02/2018
        TYPE_ENGLISH(0, "English"), TYPE_HEBREW(1, "עברית");

        private int typeValue;
        private String name;

        TypeLanguages(int typeValue, String name) {
            this.typeValue = typeValue;
            this.name = name;
        }

        public int getTypeValue() {
            return typeValue;
        }

        public static List<TypeLanguages> getAllTypeLanguages() {
            List<TypeLanguages> languagesList = new ArrayList<>();
            languagesList.add(TYPE_ENGLISH);
            languagesList.add(TYPE_HEBREW);
            return languagesList;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    public CardLanguage(Level level) {
        if (level == null) {
            throw new RuntimeException("Level cannot be null");
        }
        arrayListEasyLevel = new ArrayList<>();
        arrayListMediumLevel = new ArrayList<>();
        arrayListHardLevel = new ArrayList<>();
        arrayListLanguages = new ArrayList<>(INITIAL_CAPACITY);
        switch (level) {
            case EASY_LEVEL:
                addEasyLevel();
                break;
            case MEDIUM_LEVEL:
                addMediumLevel();
                break;
            case HARD_LEVEL:
                addHardLevel();
                break;
            case ALL_LEVEL:
                addEasyLevel();
                addMediumLevel();
                addHardLevel();
                break;
        }
        arrayListLanguages.addAll(arrayListEasyLevel);
        EASY_LEVEL = arrayListLanguages.size();
        arrayListLanguages.addAll(arrayListMediumLevel);
        MEDIUM_LEVEL = arrayListLanguages.size();
        arrayListLanguages.addAll(arrayListHardLevel);
        HARD_LEVEL = arrayListLanguages.size();
    }


    protected abstract void addEasyLevel();

    protected abstract void addMediumLevel();

    protected abstract void addHardLevel();


    protected void addEasyLanguages(@DrawableRes int imageRes, String... languages) {
        arrayListEasyLevel.add(new TranslateImage(imageRes, languages));
    }

    protected void addMediumLanguages(@DrawableRes int imageRes, String... languages) {
        arrayListEasyLevel.add(new TranslateImage(imageRes, languages));
    }

    protected void addHardLanguages(@DrawableRes int imageRes, String... languages) {
        arrayListEasyLevel.add(new TranslateImage(imageRes, languages));
    }


    public ArrayList<TranslateImage> getArrayListLanguages() {
        return arrayListLanguages;
    }

    public ArrayList<TranslateImage> getArrayListEasyLevel() {
        return arrayListEasyLevel;
    }

    public ArrayList<TranslateImage> getArrayListMediumLevel() {
        return arrayListMediumLevel;
    }

    public ArrayList<TranslateImage> getArrayListHardLevel() {
        return arrayListHardLevel;
    }

    public @DrawableRes
    int getImageByteByPosition(int position) {
        if (position > arrayListLanguages.size() - 1 || position < 0) {
            return -1;
        }
        return arrayListLanguages.get(position).getImageRes();
    }

    public String[] getLanguages(ArrayList<? extends TranslateImage> translateImages, TypeLanguages typeLanguages) {
        if (typeLanguages == null) return null;
        String[] languages = new String[translateImages.size()];
        for (int i = 0; i < translateImages.size(); i++) {
            languages[i] = translateImages.get(i).languages[typeLanguages.getTypeValue()];
        }
        return languages;
    }

    public String getLanguage(TypeLanguages typeLanguages, int position) {
        if (typeLanguages == null || position < 0 || position > arrayListLanguages.size() - 1) {
            return null;
        }
        return arrayListLanguages.get(position).languages[typeLanguages.getTypeValue()];
    }

    public static boolean isSameData(TranslateImage translateImage, TranslateImage otherTranslateImage) {
        return translateImage.getImageRes() == otherTranslateImage.getImageRes();
    }
}
