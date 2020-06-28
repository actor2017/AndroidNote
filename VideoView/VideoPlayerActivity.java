package com.itheima.mobileplayer.ui.activity;

public class VideoPlayerActivity extends BaseActivity {

    private VideoView                mVideoView;
    private TextView                 tvTitle;//视频的标题
    private View                     btnPre;//上一个
    private View                     btnNext;//下一个

    @Override
    public void initView() {
        mVideoView = (VideoView) findViewById(R.id.vv);
        tvTitle = (TextView) findViewById(R.id.tv_video_title);
        btnPre = findViewById(R.id.btn_pre);
        btnNext = findViewById(R.id.btn_next);

        //加载中
        llLoading = findViewById(R.id.ll_loading);
        llLoading.setVisibility(View.VISIBLE);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data == null) {
            mVideoList = (ArrayList<VideoBean>) intent.getSerializableExtra("list");
            mPosition = intent.getIntExtra("Position", -1);
            playItem();
		/**
		 * 从文件管理器/浏览器/其它 地方跳过来
		 */
        } else {
            mVideoView.setVideoURI(data);
            tvTitle.setText(data.toString());
            btnNext.setEnabled(false);
            btnPre.setEnabled(false);
        }
    }
}
