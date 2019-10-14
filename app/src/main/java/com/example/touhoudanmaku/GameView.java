package com.example.touhoudanmaku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.view.MotionEvent;

//ゲームの見た目を描画するクラス--------------------------------------------------------------------
public class GameView extends View {

    //画像の格納フィールド
    private Bitmap reimu;
    private Bitmap furan;
    private Bitmap tama;
    private Bitmap screen;
    private Bitmap pause;

    //画面(canvas)の長さを格納する変数
    int canvasCX;
    int canvasCY;

    //最初だけ実行を処理するための変数
    private boolean firstProcessingDone;

    //秒数を格納する変数
    int second = 16;

    //描画する弾幕を変える変数
    private int bulletMode = 0;

    //弾との当たり判定についてを格納する変数
    private boolean hit = false;
    private boolean hit2 = false;
    private boolean hit3 = false;
    private boolean playerReturn = false;
    private int returnTime = 0;
    private boolean playerDraw = true;

    //ポーズ画面かどうかの状態
    static boolean pauseFlag = false;
    //ポーズ画面・リザルト画面の状態
    static boolean screenFlag = false;

    //スコアを格納する変数

    //タイマークラス
    GameTimer timer = new GameTimer();

    //文字の描画クラス
    Text text = new Text();
    //プレイヤーの描画クラス
    Player player = new Player();
    //敵の描画クラス
    Enemy enemy = new Enemy();
    //ポーズ＆リザルト描画クラス
    ScreenView screenView = new ScreenView();

    //タッチ判定クラス
    TouchEvent touch = new TouchEvent();

    //円形弾を描画するクラス
    RoundBullet round = new RoundBullet();
    //渦巻弾を描画するクラス
    SpiralBullet spiral = new SpiralBullet();
    //ばらまき弾を描画するクラス
    RandomBullet randomBullet = new RandomBullet();
    //自機狙い弾を描画するクラス
    TargetBullet targetBullet = new TargetBullet();
    //n-way弾を描画するクラス
    NwayBullet nwayBullet = new NwayBullet();

    //コンストラクタ--------------------------------------------------------------------------------
    public GameView(Context context) {
        super(context);

        //画像をセット
        Resources res = this.getResources();
        reimu = BitmapFactory.decodeResource(res, R.drawable.player);
        reimu = Bitmap.createScaledBitmap(reimu, 110, 170, true);
        furan = BitmapFactory.decodeResource(res, R.drawable.furan);
        tama = BitmapFactory.decodeResource(res, R.drawable.tama1);
        tama = Bitmap.createScaledBitmap(tama, 40, 40, true);
        screen = BitmapFactory.decodeResource(res, R.drawable.screen);
        pause = BitmapFactory.decodeResource(res, R.drawable.pause);
        pause = Bitmap.createScaledBitmap(pause, 120, 120, true);

        Sounds.init(context);
    }


    @Override

    //画面に描画するメソッド------------------------------------------------------------------------
    public void onDraw(Canvas canvas) {

        //最初だけ実行する処理
        firstProcessing(canvas);

        //敵を描画
        enemy.enemyDraw(canvas, furan);

        //プレイヤーを描画
        if (playerDraw) {
            player.playerDraw(canvas, reimu);
        }

        //弾に当たった時に実行
        if (playerReturn) {
            playerReturn = player.playerReturn(reimu, canvasCX, canvasCY, screenView);
        }

        //テキストを描画
        text.textWhite(canvas, "残り秒数: " + second, 10, 100, 50);
        text.textWhite(canvas, "SCORE: " + screenView.getScore(), canvasCX / 2 + 240, 100, 50);

        //ScreenViewにCanvasをセット
        screenView.setCanvas(canvas);

        //ポーズボタンを描画
        screenView.pauseButtonDraw(pause);

        //15秒間隔で弾幕を変える
        bulletChange(canvas);

        if (!screenFlag) {
            screenView.setScore(screenView.getScore() + (second - (second - 1)));
            invalidate();
        } else {
            if (pauseFlag) screenView.pauseDraw(screen);
        }
    }

    //タッチ判定メソッド----------------------------------------------------------------------------
    public boolean onTouchEvent(MotionEvent motion) {

        //タッチされている座標をセット
        touch.setTouch( motion.getAction(), (int) motion.getX(), (int) motion.getY());

        //プレイヤーを操作
        player.playerOperation(touch, playerReturn);
        player.playerOutside(canvasCX, canvasCY);

        //ポーズボタンのタッチ判定
        if (!screenFlag) screenView.pauseButtonTouch(touch);

        return true;
    }

    //最初だけ実行する処理--------------------------------------------------------------------------
    public void firstProcessing(Canvas canvas) {
        if (!firstProcessingDone) {

            //画面(canvas)の長さを格納
            canvasCX = canvas.getWidth();
            canvasCY = canvas.getHeight();

            //プレイヤーの初期位置をセット
            player.playerInitial( canvasCX / 2 - reimu.getWidth() / 2,
                    canvasCY / 2 - reimu.getHeight() / 2 + 500);

            //敵の初期位置をセット
            enemy.enemyInitial( canvasCX / 2 - furan.getWidth() / 2, 400);

            firstProcessingDone = true;
        }
    }

    //15秒間隔で弾幕を変えるメソッド----------------------------------------------------------------
    public void bulletChange(Canvas canvas) {

        //秒数のカウント
        if (!screenFlag) second = timer.secondTimer(second);

        //15秒経ったか調べる
        if (second < 0) {
            second = 15;

            bulletMode += 1;
        }

        //弾幕の種類を変える
        switch (bulletMode) {
            case 0:
                hit = spiral.spiralAnimation(canvas, tama, 27, player.getPlayerX(), player.getPlayerY());
                break;
            case 1:
                hit = spiral.spiralAnimation(canvas, tama, 21, player.getPlayerX(), player.getPlayerY());
                break;
            case 2:
                hit = nwayBullet.nwayAnimation(canvas, tama,60,player.getPlayerX(), player.getPlayerY());
                break;
            case 3:
                hit = nwayBullet.nwayAnimation(canvas, tama,30,player.getPlayerX(), player.getPlayerY());
                break;
            case 4:
                hit = randomBullet.randomAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                break;
            case 5:
                hit = randomBullet.randomAnimation(canvas, tama, 30, player.getPlayerX(), player.getPlayerY());
                break;
            case 6:
                hit = round.roundAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                hit2 = targetBullet.targetAnimation(canvas, tama, player.getPlayerX(), player.getPlayerY());
                break;
            case 7:
                hit = round.roundAnimation(canvas, tama, 15, player.getPlayerX(), player.getPlayerY());
                hit2 = targetBullet.targetAnimation(canvas, tama, player.getPlayerX(), player.getPlayerY());
                break;
            case 8:
                hit = targetBullet.targetAnimation(canvas, tama, player.getPlayerX(), player.getPlayerY());
                hit2 = randomBullet.randomAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                hit3 = spiral.spiralAnimation(canvas, tama, 27, player.getPlayerX(), player.getPlayerY());
                break;
            case 9:
                hit = targetBullet.targetAnimation(canvas, tama, player.getPlayerX(), player.getPlayerY());
                hit2 = randomBullet.randomAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                hit3 = round.roundAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                break;
            case 10:
                targetBullet.targetAnimation(canvas, tama, player.getPlayerX(), player.getPlayerY());
                randomBullet.randomAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());
                round.roundAnimation(canvas, tama, 20, player.getPlayerX(), player.getPlayerY());

                Sounds.playEndSE();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }

                screenView.resultDraw(screen);

                screenFlag = true;
                pauseFlag = false;
                bulletMode = 11;
                break;
        }

        //弾が当たった時
        if (hit || hit2 || hit3) {
            if (returnTime == 0) {
                Sounds.playSE();
                player.count = false;
                playerReturn = true;
                returnTime = 1;
            }
        }

        //敵に当たった後の無敵時間
        if (returnTime >= 1) {
            returnTime += 1;

            if (returnTime % 4 == 0) {
                if (playerDraw) {
                    playerDraw = false;
                } else if (!playerDraw) {
                    playerDraw = true;
                }
            }

            if (returnTime > 80) {
                returnTime = 0;
                playerDraw = true;
            }
        }
    }
}
