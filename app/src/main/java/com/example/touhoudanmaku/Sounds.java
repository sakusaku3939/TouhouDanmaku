package com.example.touhoudanmaku;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public final class Sounds {

    private static Context context;
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    private static int sidSE;
    private static int titleSE;
    private static int pauseSE;
    private static int endSE;


    public static void init(final Context context){
        Sounds.context = context;
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        sidSE = soundPool.load(context, R.raw.nc899, 1);
        titleSE = soundPool.load(context, R.raw.nc129731, 1);
        pauseSE = soundPool.load(context, R.raw.nc130657, 1);
        endSE = soundPool.load(context, R.raw.nc46122, 1);
    }

    public static void playSE(){
        soundPool.play(sidSE, 0.4F, 0.4F, 0, 0, 1.0F);
    }

    public static void playEndSE(){
        soundPool.play(endSE, 0.4F, 0.4F, 0, 0, 1.0F);
    }

    public static void playPauseSE(){
        soundPool.play(pauseSE, 0.4F, 0.4F, 0, 0, 1.0F);
    }

    public static void playButtonSE(){
        soundPool.play(titleSE, 0.4F, 0.4F, 0, 0, 1.0F);
    }

    public static void playGameBGM(){
        initBGM(R.raw.owen);
    }

    public static void titleBGM(){
        initBGM(R.raw.title);
    }

    public static void startBGM(){
        if (mediaPlayer != null) mediaPlayer.start();
    }

    public static void pauseBGM(){
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public static void stopBGM(){
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    private static synchronized void initBGM(final int resourceId){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(context, resourceId);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.8F, 0.8F);
        mediaPlayer.start();
    }
}