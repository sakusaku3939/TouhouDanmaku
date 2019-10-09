package com.example.touhoudanmaku;

import android.graphics.Bitmap;
import android.graphics.Canvas;

//画像の描画クラス----------------------------------------------------------------------------------
public class Image {

    //画像を描画するメソッド
    public void imageDraw(Canvas canvas, Bitmap image, int x, int y) {
        canvas.drawBitmap(image, x, y, null);
    }
}
