package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private View view;
    private View screen;
    private int interval = 1;
    private boolean flg = false;
    private boolean pauseFlag = false;

    Timer timer = new Timer();

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();

        Sounds.playGameBGM();

        //GameViewの呼び出し
        view = new GameView(this);
        setContentView(view);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //リザルト画面
        results(handler);
    }

    public void results(final Handler handler) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (GameView.screenFlag) {

                            if (!flg) {
                                if (GameView.pauseFlag) {
                                    screen = getLayoutInflater().inflate(R.layout.screen_pause, null);
                                } else {
                                    screen = getLayoutInflater().inflate(R.layout.screen_image, null);
                                }
                                addContentView(screen, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                                flg = true;
                            }

                            ImageView imageView;
                            ImageView imageView2;

                            if (GameView.pauseFlag) {
                                imageView = findViewById(R.id.pause);
                                imageView.setImageResource(R.drawable.screen_pause);

                                ImageView imageView5 = findViewById(R.id.button3);
                                imageView5.setImageResource(R.drawable.screen_resume);

                                findViewById(R.id.button3).setOnTouchListener(new TitleButtonUI());
                                findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GameView.screenFlag = false;
                                        GameView.pauseFlag = false;
                                        flg = false;

                                        Sounds.playButtonSE();

                                        ViewGroup p = (ViewGroup) view.getParent();
                                        p.removeView(screen);
                                        view.invalidate();
                                    }
                                });
                            } else {
                                imageView = findViewById(R.id.results);
                                imageView2 = findViewById(R.id.score);
                                imageView.setImageResource(R.drawable.screen_result);
                                imageView2.setImageResource(R.drawable.screen_score);
                            }

                            ImageView imageView3 = findViewById(R.id.button1);
                            ImageView imageView4 = findViewById(R.id.button2);

                            imageView3.setImageResource(R.drawable.screen_again);
                            imageView4.setImageResource(R.drawable.screen_title);

                            findViewById(R.id.button1).setOnTouchListener(new TitleButtonUI());
                            findViewById(R.id.button2).setOnTouchListener(new TitleButtonUI());

                            findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GameView.screenFlag = false;
                                    GameView.pauseFlag = false;
                                    flg = false;
                                    Sounds.playButtonSE();
                                    saveScore();

                                    reload();
                                }
                            });
                            findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GameView.screenFlag = false;
                                    GameView.pauseFlag = false;
                                    flg = false;
                                    Sounds.stopBGM();
                                    Sounds.playButtonSE();
                                    saveScore();

                                    finish();
                                }
                            });
                        }
                    }
                });
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

    public void saveScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (GameView.score > highScore) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", GameView.score);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (GameView.screenFlag && !GameView.pauseFlag) {
            GameView.screenFlag = false;
            flg = false;
            GameView.bulletMode = 10;
            ViewGroup p = (ViewGroup) view.getParent();
            p.removeView(screen);

        } else if (pauseFlag) {
            pauseFlag = false;
            GameView.screenFlag = true;
            GameView.pauseFlag = true;
            flg = false;
            ViewGroup p = (ViewGroup) view.getParent();
            p.removeView(screen);
        }

        Sounds.startBGM();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseFlag = true;
        Sounds.pauseBGM();
    }

    @Override
    protected void onStop() {
        pauseFlag = true;
        super.onStop();
    }

    @Override
    public void onBackPressed() {
    }
}