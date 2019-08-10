package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class RandomBullet extends Bullet {



    //ばらまき弾をセットするメソッド----------------------------------------------------------------
    private void randomStart(int speed) {
        double angle = Math.random() * 360;
        bulletStart((int)angle, speed, 1);
    }

    //ばらまき弾を動かすメソッド--------------------------------------------------------------------
    public boolean randomAnimation(Canvas canvas, Bitmap tama, int speed, int playerX, int playerY) {

        //弾をセット
        if (frameIndex % 2 == 0) {
            randomStart(speed);
        }

        //弾を描画
        for (int i = 0; i < bulletX.size(); i++) {
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
