package com.ly.hihifriend.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ly.hihifriend.R;
import com.ly.hihifriend.gsyvideoplayer.GSYVideoMultiManager;
import com.ly.hihifriend.gsyvideoplayer.MultiGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 聊天室(观众端)
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/9 on 10:51
 */
public class ChatRoomPersonActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private boolean isPause;
    private String url = "http://3891.liveplay.myqcloud.com/live/3891_8aa4278a321d2845ed9f18db6fc4115e.flv";
    private String url2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private List<String> urls = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_person);
        ButterKnife.bind(this);

        for (int i = 0; i < 8; i++) {
            urls.add(i % 2 == 0 ? url : url2);
        }
        if (myAdapter == null) {
            myAdapter = new MyAdapter(R.layout.item_gsy_video_player, urls);
            recyclerView.setAdapter(myAdapter);
        }
    }

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private String fullKey = "null";

        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {//item就是url
            MultiGSYVideoPlayer player = helper.getView(R.id.multi_gsy_video_player);
            int position = helper.getAdapterPosition();
            //多个播放时必须在setUpLazy、setUp和getGSYVideoManager()等前面设置
            player.setPlayTag(TAG);
            player.setPlayPosition(position);//设置播放位置防止错位
            player.getTitleTextView().setVisibility(View.GONE);//不显示标题
            player.getBackButton().setVisibility(View.GONE);//不显示返回键
            player.getStartButton().setVisibility(View.GONE);//不显示开始播放按钮

            boolean isPlaying = player.getCurrentPlayer().isInPlayingState();//根据状态判断是否播放中
            if (!isPlaying) {
                /**
                 * 设置播放URL
                 * String url                       播放地址
                 * boolean cacheWithPlay            播放时是否缓存
                 * File cachePath                   缓存路径，可不设置
                 * Map<String, String> mapHeadData  http request header
                 * String title                     标题
                 */
//                player.setUp(item, false, null, null, null);
                player.setUpLazy(item, false, null, null, null);//在点击播放的时候才进行真正setup
            }

            //设置全屏按键功能
//            player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    resolveFullBtn(player);
//                }
//            });
            player.setRotateViewAuto(false);//是否开启自动旋转
            //一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
            player.setLockLand(false);
            player.setReleaseWhenLossAudio(false);//长时间失去音频焦点，暂停播放器
            player.setShowFullAnimation(true);
            player.setIsTouchWiget(false);//是否可以滑动界面改变进度，声音等
            player.setNeedLockFull(true);//是否需要全屏锁定屏幕功能
//            player.loadCoverImage(url, R.mipmap.ic_launcher);//封面

            player.setVideoAllCallBack(new GSYSampleCallBack() {

                @Override
                public void onQuitFullscreen(String url, Object... objects) {
                    super.onQuitFullscreen(url, objects);
                    fullKey = "null";
                }

                @Override
                public void onEnterFullscreen(String url, Object... objects) {
                    super.onEnterFullscreen(url, objects);
                    player.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    fullKey = player.getKey();
                }

                //播放完了，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
                @Override
                public void onAutoComplete(String url, Object... objects) {
                    super.onAutoComplete(url, objects);
                }
            });
            player.startPlayLogic();//自动播放
        }

        /**
         * 全屏幕按键处理
         */
        private void resolveFullBtn(StandardGSYVideoPlayer standardGSYVideoPlayer) {
            standardGSYVideoPlayer.startWindowFullscreen(activity, false, true);
        }

        public String getFullKey() {
            return fullKey;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoMultiManager.onResumeAll();
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoMultiManager.onPauseAll();
        isPause = true;
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoMultiManager.backFromWindowFull(this, myAdapter.getFullKey())) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoMultiManager.clearAllVideo();
        myAdapter = null;
    }
}
