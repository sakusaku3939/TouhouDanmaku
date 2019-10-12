package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.Intent;
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
    private boolean flg = false;

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

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //リザルト画面
        results(handler);
    }

    public void results(final Handler handler) {
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if (GameView.result) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (!flg) {
                                View screen = getLayoutInflater().inflate(R.layout.screen_image, null);
                                addContentView(screen, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                                flg = true;
                            }

                            ImageView imageView = findViewById(R.id.results);
                            ImageView imageView2 = findViewById(R.id.score);
                            ImageView imageView3 = findViewById(R.id.button1);
                            ImageView imageView4 = findViewById(R.id.button2);

                            imageView.setImageResource(R.drawable.screen_result);
                            imageView2.setImageResource(R.drawable.screen_score);
                            imageView3.setImageResource(R.drawable.screen_again);
                            imageView4.setImageResource(R.drawable.screen_title);

                            findViewById(R.id.button1).setOnTouchListener(new TitleButtonUI());
                            findViewById(R.id.button2).setOnTouchListener(new TitleButtonUI());

                            findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GameView.result = false;
                                    flg = false;
                                    Sounds.playTitleSE();

                                    reload();
                                }
                            });
                            findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GameView.result = false;
                                    flg = false;
                                    Sounds.playTitleSE();

                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        }, 0, interval);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (GameView.result) {
            GameView.result = false;
            flg = false;
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
    }

    @Override
    public void onBackPressed(){
    }
}