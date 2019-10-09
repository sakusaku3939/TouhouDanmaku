package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class ScreenView {

    public void pause() {

    }

    private void result(Canvas canvas, int score, int canvasCX, int canvasCY) {
        Text text = new Text();
        text.textScore(canvas, ""+score, canvasCX / 3 - 110, canvasCY / 3 + 250, 260);
    }

    public void resultDraw(Canvas canvas, Bitmap screen, int canvasCX, int canvasCY, int score) {

        BitmapDrawable resultScreen = new BitmapDrawable(screen);
        resultScreen.setBounds(0,canvasCY / 10, canvasCX,(canvasCY / 10) * 9);
        resultScreen.setAlpha(200);

        resultScreen.draw(canvas);

        result(canvas, score, canvasCX, canvasCY);
    }
}
