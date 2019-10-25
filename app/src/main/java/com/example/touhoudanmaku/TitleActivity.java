package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity implements View.OnClickListener {

    private boolean soundStart = false;

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override

    //ボタンが押されたときに呼ばれるメソッド----------------------------------------------------------
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {

                case R.id.imageButton3:
                    Sounds.playButtonSE();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                    intent( MainActivity.class);
                    break;

                case R.id.imageButton4:
                    Sounds.playButtonSE();
                    intent( ScoreActivity.class);
                    break;

                case R.id.imageButton5:
                    Sounds.playButtonSE();
                    intent( OptionActivity.class);
                    break;

                case R.id.imageButton6:
                    Sounds.playCancelSE();
                    Sounds.stopBGM();
                    finish();
                    break;

                default:
                    break;
            }
        }
    }

    private void intent(Class goToClass) {
        soundStart = false;
        Intent intent = new Intent(TitleActivity.this, goToClass);
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

        SharedPreferences sharedPreferences = getSharedPreferences("OPTION", MODE_PRIVATE);
        Sounds.init(this, sharedPreferences);

        if (soundStart) {
            Sounds.startBGM();
        } else if (sharedPreferences.getBoolean( "BGM", true)) {
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
