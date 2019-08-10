package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//ゲーム背景を描画するクラス
public class Background {

    Image image = new Image();

    //ゲーム背景を描画するメソッド
    public void bgDraw(Canvas canvas, Bitmap bgImage) {

        //画面サイズに応じて画像を拡大する
        bgImage = Bitmap.createScaledBitmap(bgImage, canvas.getWidth(), canvas.getHeight(), true);

        image.imageDraw(canvas, bgImage, 0, 0);
    }
}
