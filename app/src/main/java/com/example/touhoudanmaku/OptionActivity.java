package com.example.touhoudanmaku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class OptionActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.option_layout);

        seekTouch();
        spinnerStock();
    }

    private void seekTouch() {
        final SeekBar sb0 = findViewById(R.id.seekBar);
        final TextView tv0 = findViewById(R.id.tv_value);

        int seek = sb0.getProgress() + 10;

        // シークバーの初期値をTextViewに表示
        tv0.setText(seek + "％");

        sb0.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        int seek = sb0.getProgress() + 10;
                        tv0.setText(seek + "％");
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );
    }

    private void spinnerStock() {
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getSelectedItem();
                fullScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //何も選択されなかった時の処理
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        fullScreen();
    }

    private void fullScreen() {
        //全画面表示にする
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
