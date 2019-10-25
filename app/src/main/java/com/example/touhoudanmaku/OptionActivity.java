package com.example.touhoudanmaku;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class OptionActivity extends Activity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.option_layout);

        seekTouch();
        spinnerStock();
        switchBGM();
        switchSE();

        findViewById(R.id.bt_back).setOnClickListener(this);
        findViewById(R.id.bt_back).setOnTouchListener(new TitleButtonUI());
    }

    //タッチ感度バー
    private void seekTouch() {
        final SeekBar sb0 = findViewById(R.id.seekBar);
        final TextView tv0 = findViewById(R.id.tv_value);

        final SharedPreferences sharedPreferences = getSharedPreferences("OPTION", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        int lastSeek = sharedPreferences.getInt("TOUCH", 90);

        sb0.setProgress(lastSeek);
        final int seek = sb0.getProgress() + 10;

        // 初期値をTextViewに表示
        tv0.setText(seek + "％");

        sb0.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        int seek = sb0.getProgress() + 10;
                        tv0.setText(seek + "％");

                        editor.putInt("TOUCH", seek - 10);
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                        editor.apply();
                    }
                }
        );
    }

    //残機数選択
    private void spinnerStock() {
        Spinner spinner = findViewById(R.id.spinner);

        final SharedPreferences sharedPreferences = getSharedPreferences("OPTION", MODE_PRIVATE);
        int lastSpinner = sharedPreferences.getInt("STOCK", 4);

        spinner.setSelection(lastSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //選択された項目
                String item = (String) adapterView.getSelectedItem();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("STOCK", i);
                editor.apply();

                reFullScreen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //何も選択されなかった時の処理
            }
        });
    }

    //BGMスイッチ
    private void switchBGM() {

        Switch switchButton = findViewById(R.id.sw_bgm);

        final SharedPreferences sharedPreferences = getSharedPreferences("OPTION", MODE_PRIVATE);
        Boolean lastSwitch = sharedPreferences.getBoolean( "BGM", true);

        switchButton.setChecked(lastSwitch);

        switchButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener(){

                    public void onCheckedChanged(CompoundButton comButton, boolean isChecked){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean( "BGM", isChecked);
                        editor.apply();
                    }
                }
        );
    }

    //SEスイッチ
    private void switchSE() {

        Switch switchButton = findViewById(R.id.sw_se);

        final SharedPreferences sharedPreferences = getSharedPreferences("OPTION", MODE_PRIVATE);
        Boolean lastSwitch = sharedPreferences.getBoolean( "SE", true);

        switchButton.setChecked(lastSwitch);

        switchButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener(){

                    public void onCheckedChanged(CompoundButton comButton, boolean isChecked){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean( "SE", isChecked);
                        editor.apply();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        fullScreen();
    }

    //全画面表示にする
    private void fullScreen() {
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    //再度全画面表示にする
    private void reFullScreen() {
        View decor = getWindow().getDecorView();

        decor.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                fullScreen();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            Sounds.playCancelSE();
            finish();
        }
    }
}
