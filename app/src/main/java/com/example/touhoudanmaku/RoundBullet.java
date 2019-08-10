package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//円形弾を描画するクラス
public class RoundBullet extends Bullet{

    private int speed = 20;

    //円形弾をセットするメソッド--------------------------------------------------------------------
    private void roundStart(int angle) {

        for(int i = 0; i < 360 / angle; i++) {
            bulletStart(angle, speed, i);
        }
    }

    //円形弾を動かすメソッド------------------------------------------------------------------------
    public boolean roundAnimation(Canvas canvas, Bitmap tama, int angle, int playerX, int playerY) {

        //弾をセット
        if(frameIndex == 0) {
            roundStart(angle);
        }

        //弾を描画
        for(int i = 0; i < bulletX.size(); i++) {
            bulletAnimation(canvas, tama, i);
        }

        //時間をカウント
        if (frameIndex < 25) {
            frameIndex++;
        } else {
            frameIndex = 0;
        }

        if (bulletJudgment(tama,playerX, playerY)) {
            return true;
        } else {
            return false;
        }
    }
}
