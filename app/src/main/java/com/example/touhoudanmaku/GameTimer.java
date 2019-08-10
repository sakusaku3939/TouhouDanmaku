package com.example.touhoudanmaku;

import java.util.Timer;
import java.util.TimerTask;

//タイマークラス------------------------------------------------------------------------------------
public class GameTimer {

    boolean time = true;

    public int secondTimer(int second) {
        if (time) {
            time = false;
            setTimer();
            return second - 1;
        } else {
            return second;
        }
    }

    public void setTimer() {

        TimerTask task = new TimerTask() {

            //秒ごとに実行されるメソッド
            public void run() {
                time = true;
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);

    }
}
