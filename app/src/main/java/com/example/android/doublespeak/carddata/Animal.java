package com.example.android.doublespeak.carddata;


import com.example.android.doublespeak.R;

public class Animal extends CardLanguage {

    public Animal(Level level) {
        super(level);
    }


    protected void addEasyLanguages( String... languages) {
        addEasyLanguages(R.drawable.putin, languages);
    }

    @Override
    protected void addEasyLevel() {
        addEasyLanguages("Löwe");
        addEasyLanguages("Igel");
        addEasyLanguages("Adler");
        addEasyLanguages("Fuchs");
        addEasyLanguages("Eule");
        addEasyLanguages("Affe");
        addEasyLanguages("Kuh");
        addEasyLanguages("Esel");
        addEasyLanguages("Raupe");
        addEasyLanguages("Pfau");
        addEasyLanguages("Hai");
        addEasyLanguages("Reh");
    }

    @Override
    protected void addMediumLevel() {
    }

    @Override
    protected void addHardLevel() {

    }
}
