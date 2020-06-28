package com.kuchuan.doorcontrolsystem.activity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.kuchuan.doorcontrolsystem.R;

public class MainActivity extends BaseActivity {

	private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.opendoormusic);
        player.setVolume(1, 1);//左右声道音量 0-1, 最大, 使用的是多媒体的音量通道, 所以即使电话静音,也能够播放
        player.setLooping(false);//单曲循环
		player.start();//开始播放提示音
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) {//正在播放
                player.stop();//停止.pause:暂停
            }
            player.release();//释放
            player = null;
        }
    }
}
