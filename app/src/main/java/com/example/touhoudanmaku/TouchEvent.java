package com.example.touhoudanmaku;

import android.view.MotionEvent;

//タッチを判定するクラス----------------------------------------------------------------------------
public class TouchEvent {
    private int action, x, y;

    //タッチの状態を「onTouchEvent」からセットする--------------------------------------------------
    public void setTouch(int touchAction, int touchX, int touchY) {
        action = touchAction;
        this.x = touchX;
        this.y = touchY;
    }

    //タッチされているX座標をゲットするメソッド-----------------------------------------------------
    public int getTouchX() {
        return x;
    }

    //タッチされているY座標をゲットするメソッド
    public int getTouchY() {
        return y;
    }

    //タッチされたかを判定--------------------------------------------------------------------------
    public boolean touchPress() {
        if(action == MotionEvent.ACTION_DOWN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean touchUp() {
        if(action == MotionEvent.ACTION_UP) {
            return true;
        } else {
            return false;
        }
    }

    //タッチしたまま指を動かしたかを判定
   public boolean touchMove() {
        if (action == MotionEvent.ACTION_MOVE) {
            return true;
        } else {
            return false;
        }
    }
}
