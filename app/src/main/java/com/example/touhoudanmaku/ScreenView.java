package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class ScreenView {

    private int buttonX, buttonY;

    private Canvas canvas;
    private int canvasCX,canvasCY;

    Image image = new Image();

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.canvasCX = canvas.getWidth();
        this.canvasCY = canvas.getHeight();
    }

    public void pauseButtonDraw(Bitmap pause) {
        this.buttonX = canvasCX / 2 - 60;
        this.buttonY = 30;
        image.imageDraw(canvas, pause, buttonX, buttonY);
    }

    public void pauseButtonTouch(TouchEvent touch) {

        int touchX = touch.getTouchX();
        int touchY = touch.getTouchY();

        if (touch.touchUp()) {
            if (touchX > buttonX && touchX < buttonX + 120 && touchY > buttonY && touchY < buttonY + 120) {
                GameView.pauseFlag = true;
                GameView.screenFlag = true;
                Sounds.playPauseSE();
            }
        }
    }

    public void pauseDraw(Bitmap screen) {
        drawScreen(screen, canvasCY / 4, (canvasCY / 4) * 3);
    }

    private void result() {
        Text text = new Text();

        String score = String.valueOf(GameView.score);
        int size = 260;

        int textCX = canvasCX / 2 - text.textWide(score, size) / 2;
        int textCY = canvasCY / 3 + 400;
        text.textScore(canvas, score, textCX, textCY, size);
    }

    public void resultDraw(Bitmap screen) {

        drawScreen(screen, canvasCY / 10, (canvasCY / 10) * 9);

        result();
    }

    private void drawScreen(Bitmap screen, int y1, int y2) {
        BitmapDrawable resultScreen = new BitmapDrawable(screen);
        resultScreen.setBounds(0, y1, canvasCX, y2);
        resultScreen.setAlpha(200);
        resultScreen.draw(canvas);
    }
}
