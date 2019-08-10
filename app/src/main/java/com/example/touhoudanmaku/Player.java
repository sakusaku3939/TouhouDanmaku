package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//プレイヤーの描画クラス----------------------------------------------------------------------------
public class Player {

    Bitmap playerImage;

    //プレイヤーの座標を格納する変数
    private int playerX;
    private int playerY;

    //タッチ座標とプレイヤー座標の差を格納する変数
    private int CX;
    private int CY;

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
    public void playerOperation(TouchEvent touch, boolean playerReturn) {

        if (playerReturn) {
            CX = playerX - touch.getTouchX();
            CY = playerY - touch.getTouchY();
        } else {
            if(touch.touchPress()) {
                CX = playerX - touch.getTouchX();
                CY = playerY - touch.getTouchY();

            } else if (touch.touchMove()) {
                playerX = touch.getTouchX() + CX;
                playerY = touch.getTouchY() + CY;
            }
        }
    }

    //プレイヤーがはみ出ないようにするメソッド
    public void playerOutside(int CanvasX, int CanvasY) {
        if (playerX < 0) {
            playerX = 0;
        } else if (playerX > CanvasX - 220) {
            playerX = CanvasX - 220;
        }
        if (playerY < 0) {
            playerY = 0;
        } else if (playerY > CanvasY - 90) {
            playerY = CanvasY - 90;
        }
    }

    //弾に当たった時に初期位置に戻すメソッド
    public boolean playerReturn(Bitmap reimu, int CanvasX, int CanvasY) {

        if (!count) {
            playerY = CanvasY;
            count = true;
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
