package com.example.touhoudanmaku;

import android.view.MotionEvent;
import android.view.View;

public class TitleButtonUI implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //((ImageView) v).setColorFilter(Color.argb(100, 255, 255, 255));
                v.setScaleX((float)0.9);
                v.setScaleY((float)0.9);
                break;
            case MotionEvent.ACTION_UP:
                //((ImageView) v).setColorFilter(null);
                v.setScaleX(1);
                v.setScaleY(1);
                break;
            case MotionEvent.ACTION_CANCEL:
                //((ImageView) v).setColorFilter(null);
                v.setScaleX(1);
                v.setScaleY(1);
                break;
        }
        return false;
    }
}
