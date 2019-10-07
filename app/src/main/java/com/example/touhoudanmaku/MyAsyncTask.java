package com.example.touhoudanmaku;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    private Listener listener;

    // 非同期処理
    @Override
    protected Integer doInBackground(Integer... params) {

        // 10秒数える処理
        do{
            try {
                //　1sec sleep
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Log.d("debug",""+params[0]);
            params[0]++;
            // 途中経過を返す
            publishProgress(params[0]);

        }while(params[0]<10);

        return params[0] ;
    }

    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (listener != null) {
            listener.onSuccess(progress[0]);
        }
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(Integer result) {
        if (listener != null) {
            listener.onSuccess(result);
        }
    }


    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(int count);
    }
}