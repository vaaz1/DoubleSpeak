package com.example.android.doublespeak.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.example.android.doublespeak.R;

import java.util.Locale;


public class TextSay implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;

    public TextSay(Context context){
        textToSpeech = new TextToSpeech(context,this);

    }

    public enum LocaleLanguage{
        ENGLISH(Locale.US),GERMAN(Locale.GERMAN), HEBREW(new Locale("iw"));

        private Locale locale;

        LocaleLanguage(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }


    public void say(LocaleLanguage localeLanguage,String text){
        textToSpeech.setLanguage(localeLanguage.getLocale());
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,String.valueOf(this.hashCode()));
    }

    public void sayInGerman(String text){
        say(LocaleLanguage.GERMAN, text);
    }
    public void sayInHebrew(String text){
        say(LocaleLanguage.HEBREW, text);
    }
    public void sayInEnglish(String text){
        say(LocaleLanguage.ENGLISH, text);
    }

    @Override
    public void onInit(int status) {

    }
}
