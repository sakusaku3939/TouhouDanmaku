package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpiralBullet extends Bullet {

    private int speed = 20;

    //渦巻弾をセットするメソッド--------------------------------------------------------------------
    private void spiralStart(int angle, int i) {

        bulletStart(angle, speed, i);
    }

    //渦巻弾を動かすメソッド------------------------------------------------------------------------
    public boolean spiralAnimation(Canvas canvas, Bitmap tama, int angle, int playerX, int playerY) {

        //弾をセット
        if(frameIndex % 2 == 0) {
            spiralStart(angle, frameIndex / 2);
        }

        //弾を描画
        for(int i = 0; i < bulletX.size(); i++) {
            bulletAnimation(canvas, tama, i);
        }

        //時間をカウント
        frameIndex++;

        if (bulletJudgment(tama,playerX, playerY)) {
            return true;
        } else {
            return false;
        }
    }
}
