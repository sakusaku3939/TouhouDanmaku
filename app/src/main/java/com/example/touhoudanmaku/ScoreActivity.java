package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //向きを縦画面に固定
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.score_layout);
        drawScore();

        findViewById(R.id.bt_back).setOnClickListener(this);
        findViewById(R.id.bt_back).setOnTouchListener(new TitleButtonUI());
    }

    @Override
    public void onClick(View v) {
        if (v != null) finish();
    }

    public int loadScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);
        return highScore;
    }

    public void drawScore() {
        TextView text = findViewById(R.id.textView);
        text.setText( String.valueOf(loadScore()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
