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
    private Bitmap bgImage;
    private Bitmap furan;
    private Bitmap tama;

    //画面(canvas)の長さを格納する変数
    int canvasCX;
    int canvasCY;

    //最初だけ実行を処理するための変数
    private boolean firstProcessingDone;

    //秒数を格納する変数
    int second = 16;

    //描画する弾幕を変える変数
    private int bulletMode = 0;

    //弾との当たり判定を格納する変数
    private boolean hit = false;
    private boolean hit2 = false;
    private boolean hit3 = false;
    private boolean playerReturn = false;
    private int returnTime = 0;
    private boolean playerDraw = true;
    private boolean end = false;

    //タイマークラス
    GameTimer timer = new GameTimer();

    //文字の描画クラス
    Text text = new Text();
    //画像を描画するクラス
    Background background = new Background();
    //プレイヤーの描画クラス
    Player player = new Player();
    //敵の描画クラス
    Enemy enemy = new Enemy();

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
        reimu = BitmapFactory.decodeResource(res, R.drawable.reimu);
        bgImage = BitmapFactory.decodeResource(res, R.drawable.bg);
        furan = BitmapFactory.decodeResource(res, R.drawable.furan);
        tama = BitmapFactory.decodeResource(res, R.drawable.tama1);
        tama = Bitmap.createScaledBitmap(tama, 40, 40, true);

        Sounds.init(context);
    }


    @Override

    //画面に描画するメソッド------------------------------------------------------------------------
    public void onDraw(Canvas canvas) {

        //最初だけ実行する処理
        firstProcessing(canvas);

        //ゲーム背景を描画
        background.bgDraw(canvas, bgImage);

        //敵を描画
        enemy.enemyDraw(canvas, furan);

        //プレイヤーを描画
        if (playerDraw) {
            player.playerDraw(canvas, reimu);
        }

        //弾に当たった時に実行
        if (playerReturn) {
            playerReturn = player.playerReturn(reimu, canvasCX, canvasCY);
        }

        //15秒間隔で弾幕を変える
        bulletChange(canvas);

        //テキストを描画
        text.textWhite(canvas, "残り秒数: " + second, 10, 100, 50);

        invalidate();
    }

    //タッチ判定メソッド----------------------------------------------------------------------------
    public boolean onTouchEvent(MotionEvent motion) {

        //タッチされている座標をセット
        touch.setTouch( motion.getAction(), (int) motion.getX(), (int) motion.getY());

        //プレイヤーを操作
        player.playerOperation(touch, playerReturn);
        player.playerOutside(canvasCX, canvasCY);

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
        second = timer.secondTimer(second);

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
                end = true;
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
