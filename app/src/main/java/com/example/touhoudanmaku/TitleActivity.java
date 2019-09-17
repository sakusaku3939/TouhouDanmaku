package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends Activity {

    @Override

    //アプリ生成時に呼ばれるメソッド----------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.title_layout);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //タイトル画面が終わったらMainActivityに戻る
        //if (titleFinish) finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton3:
                System.out.println("ボタン１が押された");
                break;
            case R.id.imageButton4:
                System.out.println("ボタン２が押された");
                break;
            case R.id.imageButton5:
                System.out.println("ボタン３が押された");
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //Sounds.playBGM();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Sounds.pauseBGM();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Sounds.stopBGM();
    }
}
