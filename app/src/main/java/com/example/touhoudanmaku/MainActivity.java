package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private View view;
    private int interval = 1;
    private boolean result = false;

    Timer timer = new Timer();

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        final Handler handler = new Handler();

        //GameViewの呼び出し
        view = new GameView(this);
        setContentView(view);

        View screen = getLayoutInflater().inflate(R.layout.screen_image, null);
        addContentView(screen, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        //screen.findViewById(R.id.score);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if (GameView.result) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            ImageView imageView = findViewById(R.id.score);
                            imageView.setImageResource(R.drawable.screen_score);


                        }
                        //finish();
                    });
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

//        if (GameView.result) {
//            finish();
//        }

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
    }

    @Override
    public void onBackPressed(){
    }
}