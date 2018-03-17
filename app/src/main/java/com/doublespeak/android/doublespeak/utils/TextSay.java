package com.doublespeak.android.doublespeak.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;


public class TextSay extends UtteranceProgressListener implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;
    private TextSayEndListener textSayEndListener;

    public TextSay(Context context){
        textToSpeech = new TextToSpeech(context,this);

    }

    public void setTextSayEndListener(TextSayEndListener TextSayEndListener) {
        this.textSayEndListener = TextSayEndListener;
    }

    @Override
    public void onStart(String utteranceId) {

    }

    @Override
    public void onDone(String utteranceId) {
        if (textSayEndListener != null) {
            textSayEndListener.onFinish();
            textSayEndListener = null;
        }
    }

    @Override
    public void onError(String utteranceId) {

    }

    public void say(LocaleLanguage localeLanguage,String text){
        textToSpeech.setLanguage(localeLanguage.getLocale());
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,String.valueOf(this.hashCode()));
        textToSpeech.setOnUtteranceProgressListener(this);

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

    public enum LocaleLanguage {
        ENGLISH(Locale.US), GERMAN(Locale.GERMAN), HEBREW(new Locale("iw"));

        private Locale locale;

        LocaleLanguage(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }

    public interface TextSayEndListener {
        void onFinish();
    }
}
