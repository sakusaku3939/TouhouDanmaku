package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class NwayBullet extends Bullet {

    private int speed = 20;
    private int count = 0;

    private boolean judge;

    double angle;

    //n-way弾をセットするメソッド-------------------------------------------------------------------
    private void nwayStart(double angle) {

        bulletStart(angle, speed, 1);
    }

    //n-way弾を動かすメソッド-----------------------------------------------------------------------
    public boolean nwayAnimation(Canvas canvas, Bitmap tama,int time, int playerX, int playerY) {

        //弾をセット
        if(frameIndex >= 0 && frameIndex % 8 == 0) {

            double radian = Math.atan2((playerY + 82) - 668, (playerX + 111) - 540);
            angle = radian * 180 / Math.PI + 2;

            nwayStart(angle);
            nwayStart(angle + 40);
            nwayStart(angle - 40);

            count++;
        }

        //一度に弾を出す回数
        if(count > 4) {
            count = 0;
            frameIndex = -time;
        }

        //弾を描画
        for (int i = 0; i < bulletX.size(); i++) {
            bulletAnimation(canvas, tama, i);
        }

        //時間をカウント
        frameIndex++;

        judge = bulletJudgment(tama,playerX, playerY);

        if (judge) {
            return true;
        } else {
            return false;
        }
    }
}