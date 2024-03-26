package jp.ac.ritsumei.ise.phy.exp2.is0665ri.jumpking;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // アクティビティの方向を横向きに設定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // コンテンツビューを指定されたレイアウトに設定する
        setContentView(R.layout.activity_main);

        // MediaPlayerを使用して「pizzaman」のBGMを初期化する
        mediaPlayer = MediaPlayer.create(this, R.raw.pizzaman);

        // BGMをループ再生する場合は、ループを設定する
        mediaPlayer.setLooping(true);

        // BGMの再生を開始する
        mediaPlayer.start();
    }

    // 開始ボタンがタップされた時のコールバックメソッド
    public void onStartButtonTapped(View view) {
        // play_JumpKing アクティビティを開始するためのインテントを作成する
        Intent intent = new Intent(this, play_JunpKing.class);

        // play_JumpKing アクティビティを開始する
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // アクティビティが一時停止した際にBGMも一時停止する
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // アクティビティが再開された際にBGMを再生する
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // アクティビティが破棄される際にMediaPlayerのリソースも解放する
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
