package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Bullet {

    List<Integer> bulletX = new ArrayList<>();
    List<Integer> bulletY = new ArrayList<>();

    List<Double> bulletVX = new ArrayList<>();
    List<Double> bulletVY = new ArrayList<>();

    //弾幕の座標を格納する変数
    private double x;
    private double y;

    //カウント用変数
    int frameIndex = 0;

    //画像の描画クラス
    Image image = new Image();

    //弾幕をセットするメソッド----------------------------------------------------------------------
    protected void bulletStart(double angle, int speed, int i) {

        //弾の初期位置をセット
        bulletX.add(522);
        bulletY.add(630);

        //弾の角度から動かす量を計算
        bulletVX.add(Math.cos(Math.PI / 180 * (angle * i)) * speed);
        bulletVY.add(Math.sin(Math.PI / 180 * (angle * i)) * speed);
    }

    //弾幕を動かすメソッド--------------------------------------------------------------------------
    public void bulletAnimation(Canvas canvas, Bitmap tama, int i) {

        int bx = bulletX.get(i);
        int by = bulletY.get(i);

        //画面内にある弾のみ描画
        if (bx > 0 && bx < canvas.getWidth()) {
            if (by > 0 && by < canvas.getHeight()) {

                image.imageDraw(canvas, tama, bx, by);

                x = bx + bulletVX.get(i);
                y = by + bulletVY.get(i);

                bulletX.set(i, (int) x);
                bulletY.set(i, (int) y);
            }
        }
    }

    //弾に当たったかを調べるメソッド----------------------------------------------------------------
    public boolean bulletJudgment(Bitmap tama, int playerX, int playerY) {

        int judgment = 0;
        int bulletJudgmentX;
        int bulletJudgmentY;

        for (int i = 0; i < bulletX.size(); i++) {

            bulletJudgmentX = bulletX.get(i) + tama.getWidth() / 2;
            bulletJudgmentY = bulletY.get(i) + tama.getHeight() / 2;

            if (bulletJudgmentX > playerX + 25 && bulletJudgmentX < playerX + 90) {
                if (bulletJudgmentY > playerY + 55 && bulletJudgmentY < playerY + 115) {
                    judgment = 1;
                    break;
                }
            }
        }

        if (judgment == 1) {
            return true;
        } else {
            return false;
        }
    }
}
