package com.doublespeak.android.doublespeak.utils;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.RawRes;

import com.example.android.doublespeak.R;

import java.io.IOException;


public class SoundPlayer {

    private static final MediaPlayer mediaPlayer, backgroundMediaPlayer;
    private Context context;
    private String packageName;

    static {
        mediaPlayer = new MediaPlayer();
        backgroundMediaPlayer = new MediaPlayer();
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

    public void makeSoundBackground() throws IOException {
        backgroundMediaPlayer.reset();
        backgroundMediaPlayer.setDataSource(context, getUri(Sound.Background));
        backgroundMediaPlayer.prepare();
        backgroundMediaPlayer.setOnPreparedListener(onPreparedListener);
        float volume  = (((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager.STREAM_MUSIC)) / 3;
        backgroundMediaPlayer.setVolume(volume,volume);
    }

    public void stopBackgroundSong() {
        if (backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.stop();
        }
    }

    public void makeSoundGameCompleted() throws IOException {
        makeSound(Sound.GameCompleted);
    }

    public void pauseSoundBackground() {
        if (backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.pause();
        }
    }

    public void playSoundBackground() {
        if (!backgroundMediaPlayer.isPlaying()) {
            backgroundMediaPlayer.start();
        }
    }


    private Uri getUri(Sound sound) {
        return Uri.parse("android.resource://" + packageName + "/" + sound.getRawResSound());
    }

    public enum Sound {
        Success(R.raw.success_sound), Fail(R.raw.fail_sound),
        GameOver(R.raw.lose_sound), GameCompleted(R.raw.win_sound), CardClicked(R.raw.click_card_sound),
        Background(R.raw.bg_music);

        private int rawResSound;

        Sound(@RawRes int rawResSound) {
            this.rawResSound = rawResSound;
        }

        public int getRawResSound() {
            return rawResSound;
        }


    }
}
