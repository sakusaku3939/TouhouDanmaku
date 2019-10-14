package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity implements View.OnClickListener {

    private boolean soundStart = false;

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.title_layout);

        //ボタンにタッチ判定をセット
        findViewById(R.id.imageButton3).setOnClickListener(this);
        findViewById(R.id.imageButton4).setOnClickListener(this);
        findViewById(R.id.imageButton5).setOnClickListener(this);
        findViewById(R.id.imageButton6).setOnClickListener(this);

        findViewById(R.id.imageButton3).setOnTouchListener(new TitleButtonUI());
        findViewById(R.id.imageButton4).setOnTouchListener(new TitleButtonUI());
        findViewById(R.id.imageButton5).setOnTouchListener(new TitleButtonUI());
        findViewById(R.id.imageButton6).setOnTouchListener(new TitleButtonUI());

        Sounds.init(getBaseContext());
    }

    @Override

    //ボタンが押されたときに呼ばれるメソッド----------------------------------------------------------
    public void onClick(View v) {
        if (v != null) {
            Sounds.playButtonSE();
            switch (v.getId()) {
                case R.id.imageButton3:
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                    intent();
                    break;
                case R.id.imageButton4:
                    System.out.println("ボタン２が押された");
                    break;
                case R.id.imageButton5:
                    System.out.println("ボタン３が押された");
                    break;
                case R.id.imageButton6:
                    Sounds.stopBGM();
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void intent() {
        soundStart = false;
        Intent intent = new Intent(TitleActivity.this, MainActivity.class);
        startActivity(intent);
        Sounds.stopBGM();
    }

    @Override
    protected void onResume(){
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (soundStart) {
            Sounds.startBGM();
        } else {
            Sounds.titleBGM();
            soundStart = true;
        }

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
}
