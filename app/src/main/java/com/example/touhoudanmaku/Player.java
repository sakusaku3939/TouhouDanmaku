package com.example.touhoudanmaku;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;

//プレイヤーの描画クラス----------------------------------------------------------------------------
public class Player {

    //プレイヤーの座標を格納する変数
    private int playerX;
    private int playerY;

    //タッチ座標とプレイヤー座標の差を格納する変数
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    boolean count = false;

    Image image = new Image();

    //プレイヤーの初期位置をセット------------------------------------------------------------------
    public void playerInitial(int playerX_, int playerY_) {
        playerX = playerX_;
        playerY = playerY_;
    }

    //プレイヤーを描画するメソッド------------------------------------------------------------------
    public void playerDraw(Canvas canvas, Bitmap player) {

        image.imageDraw(canvas, player, playerX, playerY);
    }

    //プレイヤーをタッチに応じて動かすメソッド------------------------------------------------------
    public void playerOperation(TouchEvent touch, boolean playerReturn, SharedPreferences sharedPreferences) {

        double touchSpeed = (sharedPreferences.getInt("TOUCH", 50) + 50.0) / 100.0;

        if (playerReturn) {
            x1 = touch.getTouchX();
            y1 = touch.getTouchY();

        } else {
            if(touch.touchPress()) {
                x1 = touch.getTouchX();
                y1 = touch.getTouchY();

            } else if (touch.touchMove()) {

                x2 = x1;
                y2 = y1;
                x1 = touch.getTouchX();
                y1 = touch.getTouchY();

                playerX += (int)((double)(x1 - x2) * touchSpeed);
                playerY += (int)((double)(y1 - y2) * touchSpeed);

            }
        }
    }

    //プレイヤーがはみ出ないようにするメソッド
    public void playerOutside(int CanvasX, int CanvasY) {
        if (playerX < 0) {
            playerX = 0;
        } else if (playerX > CanvasX - 100) {
            playerX = CanvasX - 100;
        }
        if (playerY < 0) {
            playerY = 0;
        } else if (playerY > CanvasY - 160) {
            playerY = CanvasY - 160;
        }
    }

    //弾に当たった時に初期位置に戻すメソッド
    public boolean playerReturn(Bitmap reimu, int CanvasX, int CanvasY, ScreenView screenView) {

        if (!count) {
            playerY = CanvasY;
            count = true;

            GameView.score -= 300;
            if (GameView.score < 0) GameView.score = 0;
        }

        playerX = CanvasX / 2 - reimu.getWidth() / 2;
        playerY = playerY - 12;

        if (playerY < CanvasY / 2 - reimu.getHeight() / 2 + 600) {
            return false;
        } else {
            return true;
        }
    }

    //プレイヤーの座標をゲットするメソッド----------------------------------------------------------
    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
