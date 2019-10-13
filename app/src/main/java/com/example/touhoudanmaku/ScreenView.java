package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class ScreenView {

    private int x,y;

    public void pauseButtonDraw(Canvas canvas, Bitmap pause) {
        this.x = canvas.getWidth() / 2 - 60;
        this.y = 30;
        new Image().imageDraw(canvas, pause, x, y);
    }

    public void pauseButtonTouch(TouchEvent touch) {

        int touchX = touch.getTouchX();
        int touchY = touch.getTouchY();

        if (touch.touchUp()) {
            if (touchX > x && touchX < x + 120 && touchY > y && touchY < y + 120) {
                System.out.println("ok");
            }
        }
    }



    private void result(Canvas canvas, int score, int canvasCX, int canvasCY) {
        Text text = new Text();
        text.textScore(canvas, ""+score, canvasCX / 2 - 300, canvasCY / 3 + 400, 260);
    }

    public void resultDraw(Canvas canvas, Bitmap screen, int canvasCX, int canvasCY, int score) {

        BitmapDrawable resultScreen = new BitmapDrawable(screen);
        resultScreen.setBounds(0,canvasCY / 10, canvasCX,(canvasCY / 10) * 9);
        resultScreen.setAlpha(200);

        resultScreen.draw(canvas);

        result(canvas, score, canvasCX, canvasCY);
    }
}
