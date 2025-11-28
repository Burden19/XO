package com.example.xo.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.xo.R;

public class SoundManager {

    private SoundPool soundPool;
    private MediaPlayer backgroundMusic;
    private int clickSound, moveSound, winSound, drawSound, coinFlipSound;
    private boolean soundEnabled = true;
    private final Context context;

    public SoundManager(Context context) {
        this.context = context;
        //initializeSoundPool();
        //initializeBackgroundMusic();
    }

    private void initializeSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        // Load sounds (you need to add these sound files to res/raw/)
        //clickSound = soundPool.load(context, R.raw.click, 1);
        //moveSound = soundPool.load(context, R.raw.move, 1);
        //winSound = soundPool.load(context, R.raw.win, 1);
        //drawSound = soundPool.load(context, R.raw.draw, 1);
        //coinFlipSound = soundPool.load(context, R.raw.coin_flip, 1);
    }

    private void initializeBackgroundMusic() {
        //backgroundMusic = MediaPlayer.create(context, R.raw.background_music);
        //backgroundMusic.setLooping(true);
        //backgroundMusic.setVolume(0.3f, 0.3f);
    }

    public void playClickSound() {
        //if (soundEnabled) {
            //soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f);
        //}
    }

    public void playMoveSound() {
        //if (soundEnabled) {
            //soundPool.play(moveSound, 1.0f, 1.0f, 1, 0, 1.0f);
        //}
    }

    public void playWinSound() {
        //if (soundEnabled) {
            //soundPool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
        //}
    }

    public void playDrawSound() {
        //if (soundEnabled) {
            //soundPool.play(drawSound, 1.0f, 1.0f, 1, 0, 1.0f);
        //}
    }

    public void playCoinFlipSound() {
        //if (soundEnabled) {
            //soundPool.play(coinFlipSound, 1.0f, 1.0f, 1, 0, 1.0f);
        //}
    }

    public void playBackgroundMusic() {
        //if (backgroundMusic != null && !backgroundMusic.isPlaying() && soundEnabled) {
            //backgroundMusic.start();
        //}
    }

    public void pauseBackgroundMusic() {
        //if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            //backgroundMusic.pause();
        //}
    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
        if (!soundEnabled) {
            //pauseBackgroundMusic();
        } else {
            //playBackgroundMusic();
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (backgroundMusic != null) {
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }
}
