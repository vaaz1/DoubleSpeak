package com.doublespeak.android.doublespeak.utils;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.RawRes;

import com.example.android.doublespeak.R;

import java.io.IOException;


public class SoundPlayer {

    private static final MediaPlayer mediaPlayer;
    private Context context;
    private String packageName;

    static {
        mediaPlayer = new MediaPlayer();
    }

    private static final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }
    };

    public SoundPlayer(Context context) {
        this.context = context;
        packageName = context.getPackageName();
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void makeSound(Sound sound) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(context, getUri(sound));
        mediaPlayer.prepare();
        mediaPlayer.setOnPreparedListener(onPreparedListener);
    }

    public void makeSoundSuccess() throws IOException {
        makeSound(Sound.Success);
    }

    public void makeSoundFail() throws IOException {
        makeSound(Sound.Fail);
    }

    public void makeSoundGameOver() throws IOException {
        makeSound(Sound.GameOver);
    }

    public void makeSoundCardClicked() throws IOException {
        makeSound(Sound.CardClicked);
    }

    public void makeSoundGameCompleted() throws IOException {
        makeSound(Sound.GameCompleted);
    }


    private Uri getUri(Sound sound) {
        return Uri.parse("android.resource://" + packageName + "/" + sound.getRawResSound());
    }

    public enum Sound {
        Success(R.raw.success_sound), Fail(R.raw.fail_sound), GameOver(R.raw.sound_game_over), GameCompleted(R.raw.game_completed_sound), CardClicked(R.raw.click_card_sound);

        private int rawResSound;

        Sound(@RawRes int rawResSound) {
            this.rawResSound = rawResSound;
        }

        public int getRawResSound() {
            return rawResSound;
        }


    }
}
