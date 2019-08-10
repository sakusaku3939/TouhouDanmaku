package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy {

    //プレイヤーの座標を格納する変数
    int enemyX;
    int enemyY;

    Image image = new Image();

    //敵の初期位置をセット--------------------------------------------------------------------------
    public void enemyInitial(int enemyX_, int enemyY_) {
        enemyX = enemyX_;
        enemyY = enemyY_;
    }

    //敵を描画するメソッド--------------------------------------------------------------------------
    public void enemyDraw(Canvas canvas, Bitmap enemy) {

        image.imageDraw(canvas, enemy, enemyX, enemyY);
    }
}
