package com.example.touhoudanmaku;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

//文字の描画クラス----------------------------------------------------------------------------------
public class Text {

    Paint textPaint = new Paint();

    //文字を描画するメソッド------------------------------------------------------------------------0------------------------------
    private void textDraw(Canvas canvas, String text, int x, int y, int size) {
        textPaint.setTextSize(size);
        canvas.drawText(text, x, y, textPaint);
    }

    //黒色の文字をセットするメソッド
    public void textScore(Canvas canvas, String text, int x, int y, int size) {

        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));
        textPaint.setColor(Color.WHITE);

        textDraw(canvas, text, x, y, size);
    }

    //白色の文字をセットするメソッド
    public void textWhite(Canvas canvas, String text, int x, int y, int size) {

        textPaint.setColor(Color.WHITE);
        textDraw(canvas, text, x, y, size);
    }
}
