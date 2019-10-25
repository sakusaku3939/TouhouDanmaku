package com.example.touhoudanmaku;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public final class Sounds {

    private static Context context;
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    private static int sidSE;
    private static int titleSE;
    private static int cancelSE;
    private static int pauseSE;
    private static int endSE;
    private static boolean SE;

    public static void init(final Context context, SharedPreferences sharedPreferences){
        Sounds.context = context;
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        sidSE = soundPool.load(context, R.raw.nc899, 1);
        titleSE = soundPool.load(context, R.raw.nc129731, 1);
        pauseSE = soundPool.load(context, R.raw.nc130657, 1);
        endSE = soundPool.load(context, R.raw.nc46122, 1);
        cancelSE = soundPool.load(context, R.raw.nc129733, 1);

        SE = sharedPreferences.getBoolean( "SE", true);
    }

    public static void playSE(){
        initSE(sidSE);
    }

    public static void playEndSE(){
        initSE(endSE);
    }

    public static void playPauseSE(){
        initSE(pauseSE);
    }

    public static void playButtonSE(){
        initSE(titleSE);
    }

    public static void playCancelSE(){
        initSE(cancelSE);
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

    private static synchronized void initSE(final int resourceId) {
        if (SE) {
            soundPool.play(resourceId, 0.4F, 0.4F, 0, 0, 1.0F);
        }
    }
}