package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class ScreenView {

    public void pause() {

    }

    public void result(int score) {
    }

    public void resultDraw(Canvas canvas, Bitmap screen, int canvasCX, int canvasCY) {

        BitmapDrawable resultScreen = new BitmapDrawable(screen);
        resultScreen.setBounds(0,canvasCY / 10, canvasCX,(canvasCY / 10) * 9);
        resultScreen.setAlpha(200);

        resultScreen.draw(canvas);

        //GameView.end = true;
    }
}
