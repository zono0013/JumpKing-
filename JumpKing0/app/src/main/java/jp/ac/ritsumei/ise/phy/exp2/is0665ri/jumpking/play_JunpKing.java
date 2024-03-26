package jp.ac.ritsumei.ise.phy.exp2.is0665ri.jumpking;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class play_JunpKing extends AppCompatActivity {

    private MySurfaceView mySurfaceView;

    private long clickStartTime = 0;
    private static final long LONG_CLICK_THRESHOLD = 1000; // ロングクリックと判定する時間（ミリ秒）

    private MediaPlayer mediaPlayer;

    private int n;

    private boolean block = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // アクティビティの方向を横向きに設定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // コンテンツビューを指定されたレイアウトに設定する
        setContentView(R.layout.activity_play_junp_king);

        // MySurfaceViewのオブジェクトを取得
        mySurfaceView = findViewById(R.id.mySurfaceView);

        // Jumpボタンのオブジェクトを取得
        Button jumpButton = findViewById(R.id.jump);

        // Jumpボタンにタッチイベントリスナーを設定
        jumpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (block) {
                    long currentTime = System.currentTimeMillis();
                    switch (event.getAction()) {

                        // タッチが始まった瞬間
                        case MotionEvent.ACTION_DOWN:

                            //しゃがむ画像に切り替えるメソッドを呼び出す
                            mySurfaceView.crouch_boolean();

                            // タッチが始まった瞬間の時間を保存する
                            clickStartTime = currentTime;
                            break;

                        // タッチが終わった瞬間
                        case MotionEvent.ACTION_UP:

                            //タッチしていた秒数を求める
                            long clickDuration = currentTime - clickStartTime;

                            //タッチしていた秒数を引数にメソッドを呼び出す
                            handleJumpButtonClick(clickDuration);
                            break;
                    }
                }
                return true; // イベントをボタンにのみ消費するために true にする
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true); // BGMをループ再生する場合
        mediaPlayer.start();
    }

    // ボタンを押すと表示/非表示が切り替わる処理
    public void onList(View view) {

        n++;
        n = n % 2;
        // ボタンのIDを取得
        int homeButtonId = R.id.home;
        int reloadButtonId = R.id.reload;

        // ボタンのオブジェクトを取得
        Button homeButton = findViewById(homeButtonId);
        Button reloadButton = findViewById(reloadButtonId);

        if (n == 1) {
            // ボタンのVisibilityをVISIBLEに設定
            homeButton.setVisibility(View.VISIBLE);
            reloadButton.setVisibility(View.VISIBLE);
            block = false;
        } else if (n == 0) {
            // ボタンのVisibilityをVISIBLEに設定
            homeButton.setVisibility(View.GONE);
            reloadButton.setVisibility(View.GONE);
            block = true;
        }
        mySurfaceView.move_boolean();
    }

    public void onSurfaceClick(View view){

        if(mySurfaceView.finish()) {
            int homeButtonId = R.id.home1;
            int reloadButtonId = R.id.reload1;

            // ボタンのオブジェクトを取得
            Button homeButton = findViewById(homeButtonId);
            Button reloadButton = findViewById(reloadButtonId);

            // ボタンを表示する
            homeButton.setVisibility(View.VISIBLE);
            reloadButton.setVisibility(View.VISIBLE);
        }
    }

    public void onExitButtonTapped(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onReloadTapped(View view) {
        Intent intent = new Intent(this, play_JunpKing.class);
        startActivity(intent);
    }

    public void onLeft(View view) {
        if (block) {
            mySurfaceView.handleLeftButtonTouch();
        }
    }

    public void onRight(View view) {
        if (block) {
            mySurfaceView.handleRightButtonTouch();
        }
    }

    // ジャンプボタンのクリック処理
    private void handleJumpButtonClick(long clickDuration) {
        if (block) {
            // クリックしている時間に基づいて処理を行う
            if (clickDuration < 300) {// 300ミリ秒未満の場合
                mySurfaceView.handleJumpButtonTouch_short();
            } else if (clickDuration < 600) {// 300ミリ秒以上，600ミリ秒未満の場合
                mySurfaceView.handleJumpButtonTouch_middle();
            } else {// それ以外の場合
                mySurfaceView.handleJumpButtonTouch_long();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // アクティビティが一時停止したときにBGMを一時停止する
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // アクティビティが再開されたときにBGMを再生する
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // アクティビティが破棄される際にMediaPlayerも解放する
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

//    public void crouch() {mySurfaceView.crouch_boolean();}

}