package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private View view;
    private int interval = 10;

    Timer timer = new Timer();

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //GameViewの呼び出し
        view = new GameView(this);
        setContentView(view);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if (GameView.end) {
                    GameView.end = false;
                    finish();
                }
            }
        }, 0, interval);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (GameView.end) {
            finish();
        }

        Sounds.playGameBGM();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Sounds.pauseBGM();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Sounds.stopBGM();
    }
}