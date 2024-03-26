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
    }

    // 開始ボタンがタップされた時のコールバックメソッド
    public void onStartButtonTapped(View view) {
        // play_JumpKing アクティビティを開始するためのインテントを作成する
        Intent intent = new Intent(this, play_JunpKing.class);

        // play_JumpKing アクティビティを開始する
        startActivity(intent);
    }
}
