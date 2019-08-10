package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TargetBullet extends Bullet {

    private int speed = 15;

    private boolean judge;

    //自機狙い弾をセットするメソッド----------------------------------------------------------------
    private void targetStart(int playerX, int playerY) {

        double radian = Math.atan2((playerY + 82) - 668, (playerX + 111) - 540);
        double angle = radian * 180 / Math.PI - 2;
        bulletStart(angle, speed, 1);
    }

    //自機狙い弾を動かすメソッド--------------------------------------------------------------------
    public boolean targetAnimation(Canvas canvas, Bitmap tama, int playerX, int playerY) {

        //弾をセット
        if(frameIndex >= 0 && frameIndex % 20 == 0) {
            targetStart(playerX, playerY);
        }

        //弾を描画
        for (int i = 0; i < bulletX.size(); i++) {
            bulletAnimation(canvas, tama, i);
        }

        //時間をカウント
        frameIndex++;

        judge = bulletJudgment(tama,playerX, playerY);

        if (judge) {
            frameIndex = -30;
        }

        if (judge) {

            return true;
        } else {
            return false;
        }
    }
}