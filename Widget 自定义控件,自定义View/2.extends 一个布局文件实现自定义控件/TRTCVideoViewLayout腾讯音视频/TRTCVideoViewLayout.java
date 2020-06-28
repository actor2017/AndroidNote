package com.ly.bridgeemergency.widget.tencentaudiovideo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.bridgeemergency.R;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloudDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Module:   TRTCVideoViewLayout
 *
 * Function: 用于计算每个视频画面的位置排布和大小尺寸
 *
 */
public class TRTCVideoViewLayout extends RelativeLayout {
    private final static String                      TAG        = TRTCVideoViewLayout.class.getSimpleName();
    public static final  int                         MAX_USER   = 8;
    private              Context                     mContext;
    private List<View>                               itemViews = new ArrayList<>();
    private              ArrayList<TXCloudVideoView> mVideoViewList;
    private              ArrayList<LayoutParams>     mGrid9ParamList;
    private              RelativeLayout              mLayout;
    private              int                         mCount     = 0;
    private              int                         mMode;
    private int                                      screenWidth;

    private String                                       mSelfUserId;
    //    private WeakReference<ITRTCVideoViewLayoutListener> mListener = new WeakReference<>(null);
    private OnItemClickListener onClickListener;

//    private HashMap<Integer, Integer> mapNetworkQuality = new HashMap<>();
    private boolean showClose;//是否显示关闭按钮
    private int dp5;

    public TRTCVideoViewLayout(Context context) {
        super(context);
        initView(context);
    }


    public TRTCVideoViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TRTCVideoViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public interface ITRTCVideoViewLayoutListener {
        void onEnableRemoteVideo(String userId, boolean enable);//是否能播放视频
        void onEnableRemoteAudio(String userId, boolean enable);//是否能播放音频
        void onChangeVideoFillMode(String userId, boolean adjustMode);//全屏切换
    }
    public interface OnItemClickListener {
        void onItemClick(@NonNull int userId);

        /**
         * 点击屏蔽音视频按钮
         * @param rtcIdentifier
         * @param videoView
         * @param shieldStateShould 目前应该屏蔽状态
         */
        default void onCloseClick(String rtcIdentifier, TXCloudVideoView videoView, boolean shieldStateShould){}
    }

    //视频点击事件
    public void setUserId(boolean showClose, String userId, OnItemClickListener onItemClickListener) {
        this.showClose = showClose;
        mSelfUserId = userId;
        this.onClickListener = onItemClickListener;
    }

    public void setListener(ITRTCVideoViewLayoutListener listener) {
//        mListener = new WeakReference<>(listener);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.room_show_view, this);
        mLayout = (RelativeLayout) findViewById(R.id.ll_mainview);

        initTXCloudVideoView();
        initGridLayoutParams();
        showView();

//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Down, R.mipmap.signal1);
//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Vbad, R.mipmap.signal2);
//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Bad, R.mipmap.signal3);
//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Poor, R.mipmap.signal4);
//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Good, R.mipmap.signal5);
//        mapNetworkQuality.put(TRTCCloudDef.TRTC_QUALITY_Excellent, R.mipmap.signal6);
    }

    public void initTXCloudVideoView() {
        mVideoViewList = new ArrayList<TXCloudVideoView>();
        for (int i = 0; i < MAX_USER; i++) {
            View view = null;
//            TextView tvRole = null;
//            if (i < 2) {
//                view = LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar_for_video1, null);
//                tvRole = view.findViewById(R.id.tv_role);
//                tvRole.setSelected(i == 0);
//            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar_for_video2, null);
//            }
            TXCloudVideoView cloudVideoView = view.findViewById(R.id.tx_cloud_video_view);//new TXCloudVideoView(mContext);
//            if (i == 0) {
//                tvRole.setText("主持人");
//                tvRole.setTextColor(getResources().getColor(R.color.white));
//            } else if (i == 1) {
//                tvRole.setText("嘉宾席");
//                tvRole.setTextColor(getResources().getColor(R.color.black));
//            } else {
                TextView tvNumber = view.findViewById(R.id.tv_number_for_trtc);//序号
                tvNumber.setText(String.valueOf(i + 1));
//            }
            cloudVideoView.setVisibility(GONE);
//            cloudVideoView.setId(1000 + i);
//            cloudVideoView.setClickable(true);
            cloudVideoView.setTag(i);//R.string.str_tag_pos,
//            cloudVideoView.setBackgroundColor(Color.BLACK);
//            addToolbarLayout(cloudVideoView);
            itemViews.add(view);
            mVideoViewList.add(i, cloudVideoView);
        }
    }

    public void initGridLayoutParams() {
        mGrid9ParamList = new ArrayList<LayoutParams>();
        int statusH = getStatusBarHeight(mContext);
        screenWidth = getScreenWidth(mContext);
        int screenH = getScreenHeight(mContext);
        int margin = getScreenPercentWidth(0.053328);
        int topMargin = dip2px(85);//顶部距离

        initGrid9Param(statusH, screenWidth, screenH, topMargin, margin);
    }

    private void initGrid9Param(int statusH, int screenW, int screenH, int topMargin, int margin) {
        //间隙
        int interval = 1;//getScreenPercentWidth(0.009)
        int interval2 = 15;//间隙2
        int grid9W0 = (screenW - margin * 2 - interval * 2) / 3;
        int grid9H0 = grid9W0;//高度

        LayoutParams layoutParams0 = new LayoutParams(grid9W0, grid9H0);
        layoutParams0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams0.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams0.topMargin = topMargin;//fixed 修改高度 = 100, 让视频更下来一些
        layoutParams0.leftMargin = grid9W0 / 2;


        LayoutParams layoutParams1 = new LayoutParams(grid9W0, grid9H0);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams1.topMargin = topMargin;
        layoutParams1.rightMargin = grid9W0 / 2;

        int grid9W2 = grid9W0;
        int grid9H2 = (int) (grid9W0 * 143D / 106);//高度
        LayoutParams layoutParams2 = new LayoutParams(grid9W2, grid9H2);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams2.topMargin = topMargin + grid9H0 + interval2;
        layoutParams2.leftMargin = margin;

        LayoutParams layoutParams3 = new LayoutParams(grid9W2, grid9H2);
        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams3.topMargin = topMargin + grid9H0 + interval2;

        LayoutParams layoutParams4 = new LayoutParams(grid9W2, grid9H2);
        layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams4.rightMargin = margin;
        layoutParams4.topMargin = topMargin + grid9H0 + interval2;

        LayoutParams layoutParams5 = new LayoutParams(grid9W2, grid9H2);
        layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams5.leftMargin = margin;
        layoutParams5.topMargin = topMargin + grid9H0 + grid9H2 + interval + interval2;

        LayoutParams layoutParams6 = new LayoutParams(grid9W2, grid9H2);
        layoutParams6.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams6.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams6.topMargin = topMargin + grid9H0 + grid9H2 + interval + interval2;

        LayoutParams layoutParams7 = new LayoutParams(grid9W2, grid9H2);
        layoutParams7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams7.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams7.rightMargin = margin;
        layoutParams7.topMargin = topMargin + grid9H0 + grid9H2 + interval + interval2;

//        LayoutParams layoutParams8 = new LayoutParams(grid9W, grid9H);
//        layoutParams8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        layoutParams8.addRule(RelativeLayout.ALIGN_PARENT_TOP);//ALIGN_PARENT_BOTTOM
////        layoutParams8.bottomMargin = margin + bottomMargin;
//        layoutParams8.topMargin = margin + grid9H * 2 + topMargin * 2;
//        layoutParams8.rightMargin = margin;

        mGrid9ParamList.add(layoutParams0);
        mGrid9ParamList.add(layoutParams1);
        mGrid9ParamList.add(layoutParams2);
        mGrid9ParamList.add(layoutParams3);
        mGrid9ParamList.add(layoutParams4);
        mGrid9ParamList.add(layoutParams5);
        mGrid9ParamList.add(layoutParams6);
        mGrid9ParamList.add(layoutParams7);
//        mGrid9ParamList.add(layoutParams8);
    }

    //原来的布局, 备份, 效果见: F:\Android\HiHiFriend\资料\hihi社交效果图\多人视角.jpg
//    private void initGrid9Param(int statusH, int screenW, int screenH, int topMargin, int margin) {
////        int grid9W = (screenW - margin * 2) / 3;
////        int grid9H = (screenH - statusH - margin * 2 - bottomMargin) / 3;
//        int grid9W0 = getScreenPercentWidth(0.533328);
//        int grid9H0 = grid9W0;//fixed 修改高度= 宽度
//
//        LayoutParams layoutParams0 = new LayoutParams(grid9W0, grid9H0);
//        layoutParams0.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams0.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams0.topMargin = topMargin;//fixed 修改高度 = 100, 让视频更下来一些
//        layoutParams0.leftMargin = margin;
//
//        //间隙screenW - grid9W - margin * 2
//        int interval = getScreenPercentWidth(0.009);//0.018656
//        int grid9W1 = getScreenPercentWidth(0.34136);
//        int grid9H1 = grid9W1;
////        interval -= grid9W;
//        LayoutParams layoutParams1 = new LayoutParams(grid9W1, grid9H1);
//        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams1.topMargin = topMargin;
//        layoutParams1.rightMargin = margin;
//
//        LayoutParams layoutParams2 = new LayoutParams(grid9W1, grid9H1);
//        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams2.topMargin = topMargin + grid9H1 + interval;
//        layoutParams2.rightMargin = margin;
//
//        int grid9W3 = getScreenPercentWidth(0.256);
//        int grid9H3 = grid9W3;
//        LayoutParams layoutParams3 = new LayoutParams(grid9W3, grid9H3);
//        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams3.leftMargin = margin;
//        layoutParams3.topMargin = topMargin + grid9H0 + interval;
//
//        LayoutParams layoutParams4 = new LayoutParams(grid9W3, grid9H3);
//        layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams4.leftMargin = margin + grid9W3 + interval;
//        layoutParams4.topMargin = topMargin + grid9H0 + interval;
//
//        LayoutParams layoutParams5 = new LayoutParams(grid9W3, grid9H3);
//        layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams5.leftMargin = margin;
//        layoutParams5.topMargin = topMargin + grid9H0 + interval * 2 + grid9H3;
//
//        LayoutParams layoutParams6 = new LayoutParams(grid9W3, grid9H3);
//        layoutParams6.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams6.addRule(RelativeLayout.ALIGN_PARENT_TOP);//ALIGN_PARENT_BOTTOM
//        layoutParams6.leftMargin = margin + grid9W3 + interval;
//        layoutParams6.topMargin = topMargin + grid9H0 + interval * 2 + grid9H3;
//
//        LayoutParams layoutParams7 = new LayoutParams(grid9W1, grid9H1);
//        layoutParams7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        layoutParams7.addRule(RelativeLayout.ALIGN_PARENT_TOP);//ALIGN_PARENT_BOTTOM
//        layoutParams7.rightMargin = margin;
//        layoutParams7.topMargin = topMargin + grid9H1 * 2 + interval * 2;
//
////        LayoutParams layoutParams8 = new LayoutParams(grid9W, grid9H);
////        layoutParams8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
////        layoutParams8.addRule(RelativeLayout.ALIGN_PARENT_TOP);//ALIGN_PARENT_BOTTOM
//////        layoutParams8.bottomMargin = margin + bottomMargin;
////        layoutParams8.topMargin = margin + grid9H * 2 + topMargin * 2;
////        layoutParams8.rightMargin = margin;
//
//        mGrid9ParamList.add(layoutParams0);
//        mGrid9ParamList.add(layoutParams1);
//        mGrid9ParamList.add(layoutParams2);
//        mGrid9ParamList.add(layoutParams3);
//        mGrid9ParamList.add(layoutParams4);
//        mGrid9ParamList.add(layoutParams5);
//        mGrid9ParamList.add(layoutParams6);
//        mGrid9ParamList.add(layoutParams7);
////        mGrid9ParamList.add(layoutParams8);
//    }

    private void showView() {
        mLayout.removeAllViews();
        for (int i = 0; i < itemViews.size(); i++) {
            View view = itemViews.get(i);
            LayoutParams layoutParams = mGrid9ParamList.get(i);
            view.setLayoutParams(layoutParams);
            mLayout.addView(view);
        }
//        for (int i = 0; i < mVideoViewList.size(); i++) {
//            TXCloudVideoView cloudVideoView = mVideoViewList.get(i);
//            RelativeLayout.LayoutParams layoutParams = mGrid9ParamList.get(i);
//            cloudVideoView.setLayoutParams(layoutParams);
//            mLayout.addView(cloudVideoView);
//        }
    }

    //获取屏幕百分比
    private int getScreenPercentWidth(double percent) {
        return (int) (screenWidth * percent);
    }

    public TXCloudVideoView getCloudVideoViewByIndex(int index) {
        return mVideoViewList.get(index);
    }

    public TXCloudVideoView getCloudVideoViewByUseId(String userId) {
        for (TXCloudVideoView videoView: mVideoViewList) {
            String tempUserID = videoView.getUserId();
            if (tempUserID != null && tempUserID.equalsIgnoreCase(userId)) {
                return videoView;
            }
        }
        return null;
    }

    public void updateLayoutGrid() {
        ArrayList<LayoutParams> paramList;
        paramList = mGrid9ParamList;
        int layoutIndex = 1;
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXCloudVideoView cloudVideoView = mVideoViewList.get(i);
//            cloudVideoView.setClickable(false);
//            cloudVideoView.setOnClickListener(null);
            String userId = cloudVideoView.getUserId();
            if (!TextUtils.isEmpty(userId)) {
                itemViews.get(i).setLayoutParams(paramList.get(i));//layoutIndex++
//                if (userId.equalsIgnoreCase(mSelfUserId)) {//把自己排第1个位置
//                    cloudVideoView.setLayoutParams(paramList.get(0));
//                } else if (layoutIndex < paramList.size()){
//                    cloudVideoView.setLayoutParams(paramList.get(layoutIndex++));
//                }
            }
        }
    }

//    public void swapViewByIndex(int src, int dst) {
//        TXLog.i(TAG, "swapViewByIndex src:" + src + ",dst:" + dst);
//        TXCloudVideoView srcView = mVideoViewList.get(src);
//        TXCloudVideoView dstView = mVideoViewList.get(dst);
//        mVideoViewList.set(src, dstView);
//        mVideoViewList.set(dst, srcView);
//
//        if (mMode == MODE_FLOAT) {
//            updateLayoutFloat();
//        } else {
//            updateLayoutGrid();
//        }
//    }

    public void appendEventMessage(String userId, String message) {
        for (int i=0; i<mVideoViewList.size(); i++){
            if (userId.equalsIgnoreCase(mVideoViewList.get(i).getUserId())) {
                mVideoViewList.get(i).appendEventInfo(message);
                break;
            }
        }
    }
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void showDebugView(int type) {
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXCloudVideoView renderView = mVideoViewList.get(i);
            if (renderView != null) {
                String vUserId = renderView.getUserId();
                if (!TextUtils.isEmpty(vUserId)){
                    renderView.showVideoDebugLog(type);
                }

            }
        }
    }

    public TXCloudVideoView getFreeCloudVideoView() {
        for (TXCloudVideoView videoView : mVideoViewList) {
            String tempUserID = videoView.getUserId();
            if (TextUtils.isEmpty(tempUserID)) {
                return videoView;
            }
        }
        return null;
    }

    /**
     * 更新进入房间人数，4个人以下用四宫格，4个人以上用9宫格
     *
     */
    public TXCloudVideoView onMemberEnter(String userId) {
        Log.e(TAG, "onMemberEnter: userId = " + userId);

        if (TextUtils.isEmpty(userId)) return null;
        TXCloudVideoView videoView = null;
        int posIdx = 0;
        int posLocal = mVideoViewList.size();
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXCloudVideoView renderView = mVideoViewList.get(i);
            if (renderView != null) {
                String vUserId = renderView.getUserId();
                if (userId.equalsIgnoreCase(vUserId)){
                    return renderView;
                }
                if (videoView == null && TextUtils.isEmpty(vUserId)){//找到第1个没有视频的videoview
                    renderView.setUserId(userId);
                    videoView = renderView;
                    posIdx = i;
                    mCount++;
                } else if (!TextUtils.isEmpty(vUserId) && vUserId.equalsIgnoreCase(mSelfUserId)) {
                    posLocal = i;
                }
            }
        }
//        TXLog.i("lyj", "onMemberEnter->posIdx: " + posIdx + ", posLast: " + posLocal);
//         if (0 == posLocal) {
//            swapViewByIndex(posIdx, posLocal);
//        }
//         mMode = mCount == 1 ? MODE_FLOAT : MODE_GRID;//新增1,更改显示模式
//        if (mMode == MODE_FLOAT) {
//            updateLayoutFloat();
//        } else {
            updateLayoutGrid();
//        }
        freshToolbarLayout();//新增2
        return videoView;
    }

    public void onMemberLeave(String userId) {
        Log.e(TAG, "onMemberLeave: userId = " + userId);

        int posIdx = 0, posLocal = mVideoViewList.size();
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXCloudVideoView renderView = mVideoViewList.get(i);
            if (renderView != null && null != renderView.getUserId()) {
                if (renderView.getUserId().equals(userId)) {
                    renderView.setUserId(null);
                    renderView.setVisibility(View.GONE);
                    freshToolbarLayoutOnMemberLeave(renderView);

                    View view = itemViews.get(i);
                    if (view != null) {
                        TextView tvNickName = view.findViewById(R.id.tv_item_nick_name);//昵称
//                        View ivSex = view.findViewById(R.id.iv_sex_4_trtc_video_view_layout);//性别
                        View ivClose = view.findViewById(R.id.iv_close_for_trtc);//关闭
                        ImageView ivCover = view.findViewById(R.id.iv_cover_for_trtc);//封面
                        if (tvNickName != null) tvNickName.setVisibility(GONE);
//                        if (ivSex != null) ivSex.setVisibility(GONE);
                        if (ivClose != null) {
                            ivClose.setSelected(false);
                            ivClose.setVisibility(GONE);
                        }
                        if (ivCover != null) {
                            ivCover.setWillNotDraw(true);
                            ivCover.setVisibility(GONE);
                        }
                    }

                    posIdx = i;
                    mCount--;
                } else if (renderView.getUserId().equalsIgnoreCase(mSelfUserId)) {
                    posLocal = i;
                }
            }
        }

        if (0 == posIdx) {
//            swapViewByIndex(posIdx, posLocal);
        }
//        mMode = mCount == 1 ? MODE_FLOAT : MODE_GRID;//新增1,更改显示模式
//        if (mMode == MODE_FLOAT) {
//            updateLayoutFloat();
//        } else {
            updateLayoutGrid();
//        }
        freshToolbarLayout();//新增2
    }

    public void onRoomEnter() {
        mCount++;
//        if (mMode == MODE_FLOAT) {
//            updateLayoutFloat();
//        } else {
            updateLayoutGrid();
//        }
    }
    public int getScreenWidth(Context context) {
        if (context == null) return 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public int getScreenHeight(Context context) {
        if (context == null) return 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    //小视频窗口
    private void addToolbarLayout(TXCloudVideoView videoView) {
//        View view = videoView.findViewById(R.id.layout_toolbar);
//
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar_for_video2, videoView);
//            view.setVisibility(GONE);
//
//            隐藏几个按钮
//            final Button btnRemoteVideo = (Button)view.findViewById(R.id.btn_remote_video);
//            btnRemoteVideo.setTag(R.mipmap.remote_video_enable);
//            btnRemoteVideo.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String userId = videoView.getUserId();
//                    if (userId != null && userId.length() > 0) {
//                        userId = userId.substring(0, userId.length() - 1);
//                    }
//                    if (userId != null && userId.length() > 0) {
//                        int currentTag = (int)btnRemoteVideo.getTag();
//                        boolean enable = currentTag != R.mipmap.remote_video_enable;
//                        ITRTCVideoViewLayoutListener listener = mListener.get();
//                        if (listener != null) {
//                            listener.onEnableRemoteVideo(userId, enable);
//                        }
//                        btnRemoteVideo.setTag(enable ? R.mipmap.remote_video_enable : R.mipmap.remote_video_disable);
//                        btnRemoteVideo.setBackgroundResource(enable ? R.mipmap.remote_video_enable : R.mipmap.remote_video_disable);
//                    }
//                }
//            });
//
//            final Button btnRemoteAudio = (Button)view.findViewById(R.id.btn_remote_audio);
//            btnRemoteAudio.setTag(R.mipmap.remote_audio_enable);
//            btnRemoteAudio.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String userId = videoView.getUserId();
//                    if (userId != null && userId.length() > 0) {
//                        userId = userId.substring(0, userId.length() - 1);
//                    }
//                    if (userId != null && userId.length() > 0) {
//                        int currentTag = (int)btnRemoteAudio.getTag();
//                        boolean enable = currentTag != R.mipmap.remote_audio_enable;
//                        ITRTCVideoViewLayoutListener listener = mListener.get();
//                        if (listener != null) {
//                            listener.onEnableRemoteAudio(userId, enable);
//                        }
//                        btnRemoteAudio.setTag(enable ? R.mipmap.remote_audio_enable : R.mipmap.remote_audio_disable);
//                        btnRemoteAudio.setBackgroundResource(enable ? R.mipmap.remote_audio_enable : R.mipmap.remote_audio_disable);
//                    }
//                }
//            });
//
//            final Button btnFillMode = (Button)view.findViewById(R.id.btn_fill_mode);
//            btnFillMode.setTag(R.mipmap.fill_scale);
//            btnFillMode.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String userId = videoView.getUserId();
//                    if (userId != null && userId.length() > 0) {
//                        userId = userId.substring(0, userId.length() - 1);
//                    }
//                    if (userId != null && userId.length() > 0) {
//                        int currentTag = (int)btnFillMode.getTag();
//                        boolean adjustMode = currentTag != R.mipmap.fill_scale;
//                        ITRTCVideoViewLayoutListener listener = mListener.get();
//                        if (listener != null) {
//                            listener.onChangeVideoFillMode(userId, adjustMode);
//                        }
//                        btnFillMode.setTag(adjustMode ? R.mipmap.fill_scale : R.mipmap.fill_adjust);
//                        btnFillMode.setBackgroundResource(adjustMode ? R.mipmap.fill_scale : R.mipmap.fill_adjust);
//                    }
//                }
//            });
//        }
    }

//    private void clearVideoViewExtraData(TXCloudVideoView videoView) {
//        Button btnRemoteVideo = (Button)videoView.findViewById(R.id.btn_remote_video);
//        btnRemoteVideo.setTag(R.mipmap.remote_video_enable);
//        btnRemoteVideo.setBackgroundResource(R.mipmap.remote_video_enable);
//
//        Button btnRemoteAudio = (Button)videoView.findViewById(R.id.btn_remote_audio);
//        btnRemoteAudio.setTag(R.mipmap.remote_audio_enable);
//        btnRemoteAudio.setBackgroundResource(R.mipmap.remote_audio_enable);
//
//        Button btnFillMode = (Button)videoView.findViewById(R.id.btn_fill_mode);
//        btnFillMode.setTag(R.mipmap.fill_scale);
//        btnFillMode.setBackgroundResource(R.mipmap.fill_scale);
//    }

    public void freshToolbarLayout() {
        for (TXCloudVideoView videoView: mVideoViewList) {
            String userId = videoView.getUserId();

            //隐藏
//            View layoutToolbar = videoView.findViewById(R.id.layout_toolbar_for_video2);
//            if (userId != null && !userId.isEmpty()) {
//                if (userId.equalsIgnoreCase(mSelfUserId)) {
//                    View view = videoView.findViewById(R.id.layout_no_video);
//                    if (view != null) {
//                        Object tag = view.getTag();
//                        if (tag != null) {
//                            if ((int) tag == VISIBLE) {
//                                view.setVisibility(VISIBLE);
//                                if (layoutToolbar != null) {
//                                    layoutToolbar.bringToFront();
//                                    layoutToolbar.setVisibility(VISIBLE);
//                                }
//                            }
//                            else {
//                                view.setVisibility(GONE);
//                            }
//                        }
//                    }
//                    showToolbarButtons(videoView, false);
//                } else {
//                    if (videoView.getVisibility() == VISIBLE) {
//                        if (layoutToolbar != null) {
//                            layoutToolbar.bringToFront();
//                            layoutToolbar.setVisibility(VISIBLE);
//                            showToolbarButtons(videoView, mMode == MODE_GRID);
//                        }
//                    }
//                    else {
//                        layoutToolbar.setVisibility(GONE);
//                        freshToolbarLayoutOnMemberLeave(videoView);
//                    }
//                }
//            }
//            else {
//                layoutToolbar.setVisibility(GONE);
//                freshToolbarLayoutOnMemberLeave(videoView);
//            }
        }
    }

    public void freshToolbarLayoutOnMemberEnter(String userID) {
        for (TXCloudVideoView videoView: mVideoViewList) {
            String tempUserID = videoView.getUserId();
            // TODO: 2019/5/13 不显示视频&音量&全屏, 被注释掉了
//            if (tempUserID != null && tempUserID.equalsIgnoreCase(userID)) {
//                View layoutToolbar = videoView.findViewById(R.id.layout_toolbar_for_video2);
//                if (layoutToolbar != null) {
//                    layoutToolbar.bringToFront();
//                    layoutToolbar.setVisibility(VISIBLE);
//                    showToolbarButtons(videoView, mMode == MODE_GRID);
//                }
//            }
        }
    }

    // TODO: 2019/5/13 不显示视频&音量&全屏, 被注释掉了
    private void freshToolbarLayoutOnMemberLeave(TXCloudVideoView videoView) {
//        showAudioVolumeProgressBar(videoView, false);
//        showToolbarButtons(videoView, false);
//        showNoVideoLayout(videoView, false);
//        clearVideoViewExtraData(videoView);
    }

//    private void showToolbarButtons(TXCloudVideoView videoView, boolean bShow) {
//        View view = videoView.findViewById(R.id.toolbar_buttons);
//        if (view != null) {
//            view.setVisibility(bShow ? VISIBLE : GONE);
//        }
//    }

    public void hideAudioVolumeProgressBar() {
        for (TXCloudVideoView videoView: mVideoViewList) {
            showAudioVolumeProgressBar(videoView, false);
        }
    }

    private void showAudioVolumeProgressBar(TXCloudVideoView videoView, boolean bShow) {
        //隐藏
//        View view = videoView.findViewById(R.id.audio_volume);
//        if (view != null) {
//            view.setVisibility(bShow ? VISIBLE : GONE);
//        }
    }

    private void showNoVideoLayout(TXCloudVideoView videoView, boolean bShow) {
        //隐藏
//        View view = videoView.findViewById(R.id.layout_no_video);
//        if (view != null) {
//            view.setVisibility(bShow ? VISIBLE : GONE);
//            view.setTag(Integer.valueOf(bShow ? VISIBLE : GONE));
//        }
    }

    public void updateAudioVolume(String userID, int audioVolume) {
        for (TXCloudVideoView videoView: mVideoViewList) {
            if (videoView.getVisibility() == VISIBLE) {
                //隐藏
//                String tempUserID = videoView.getUserId();
//                if (tempUserID != null && tempUserID.startsWith(userID)) {
//                    View layoutToolbar = videoView.findViewById(R.id.layout_toolbar_for_video2);
//                    if (layoutToolbar != null) {
//                        layoutToolbar.bringToFront();
//                        layoutToolbar.setVisibility(VISIBLE);
//                    }
//
//                    int maxVolume = 15; //>TRTC接口显示audioVolume的取值范围是0-100，实际回调出来的数据非常小，为了效果明显一点，先设置为15
//
//                    int adjustVolume = (int)Math.ceil(maxVolume * audioVolume / maxVolume);
//                    if (adjustVolume < 0) {
//                        adjustVolume = 0;
//                    }
//                    if (adjustVolume > maxVolume) {
//                        adjustVolume = maxVolume;
//                    }
//
//                    ProgressBar progressBar = (ProgressBar)videoView.findViewById(R.id.audio_volume);
//                    if (progressBar != null) {
//                        progressBar.setVisibility(VISIBLE);
//                        progressBar.setMax(maxVolume);
//                        progressBar.setProgress(adjustVolume);
//                    }
//                }
            }
        }
    }

    public void updateNetworkQuality(String userID, int quality) {
        for (TXCloudVideoView videoView: mVideoViewList) {
            if (videoView.getVisibility() == VISIBLE) {
                String tempUserID = videoView.getUserId();
                if (tempUserID != null && tempUserID.startsWith(userID)) {
                    ImageView imageView = (ImageView)videoView.findViewById(videoView.hashCode());
                    if (imageView == null) {
                        imageView = new ImageView(mContext);
                        imageView.setId(videoView.hashCode());
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(60, 45, Gravity.TOP | Gravity.RIGHT);
                        params.setMargins(0,8,8,0);
                        videoView.addView(imageView, params);
                    }

                    if (quality < TRTCCloudDef.TRTC_QUALITY_Excellent) {
                        quality = TRTCCloudDef.TRTC_QUALITY_Excellent;
                    }
                    if (quality > TRTCCloudDef.TRTC_QUALITY_Down) {
                        quality = TRTCCloudDef.TRTC_QUALITY_Down;
                    }

                    if (imageView != null) {
                        imageView.bringToFront();
                        imageView.setVisibility(VISIBLE);
//                        imageView.setImageResource(mapNetworkQuality.get(Integer.valueOf(quality).intValue()));
                    }
                }
            }
        }
    }

    public void updateVideoStatus(String userID, boolean bHasVideo) {
        for (TXCloudVideoView videoView: mVideoViewList) {
            if (videoView.getVisibility() == VISIBLE) {
                String tempUserID = videoView.getUserId();
                //隐藏
//                if (tempUserID != null && tempUserID.startsWith(userID)) {
//                    TextView textView = (TextView)videoView.findViewById(R.id.textview_userid);
//                    if (textView != null) {
//                        if (mSelfUserId.equalsIgnoreCase(userID)) {
//                            userID += "(您自己)";
//                        }
//                        textView.setText(userID);
//                    }
//                    if (bHasVideo == false) {
//                        View layoutToolbar = videoView.findViewById(R.id.layout_toolbar_for_video2);
//                        if (layoutToolbar != null) {
//                            layoutToolbar.bringToFront();
//                            layoutToolbar.setVisibility(VISIBLE);
//                        }
//
//                        showNoVideoLayout(videoView, true);
//                    }
//                    else {
//                        showNoVideoLayout(videoView, false);
//                    }
//                }
            }
        }
    }

    /**
     * 更新状态, 显示性别, 昵称
     */
    public synchronized void updateVideoStatus() {
//        List<DateInfo> alreadyEnterDateRoom = Global.alreadyEnterDateRoom;
//        List<DateInfo> remove = new ArrayList<>(alreadyEnterDateRoom.size());
//        for (int i = 0; i < alreadyEnterDateRoom.size(); i++) {
//            DateInfo dateInfo = alreadyEnterDateRoom.get(i);
//            if (dateInfo != null) {
//                for (int j = 0; j < mVideoViewList.size(); j++) {
//                    TXCloudVideoView videoView = mVideoViewList.get(j);
//                    if (videoView == null) continue;
//                    String tempUserID = videoView.getUserId();
//                    if (tempUserID != null && tempUserID.contains(dateInfo.rtcIdentifier)) {
//                        videoView.setTag(dateInfo.userId);
//                        videoView.setOnClickListener(v -> {
//                            if (onClickListener != null) {
//                                Object tag = v.getTag();//int userId
//                                if (tag != null) {
//                                    onClickListener.onItemClick((Integer) tag);
//                                }
//                            }
//                        });
//                        remove.add(dateInfo);
////                        View layoutToolbar = videoView.findViewById(R.id.layout_toolbar);
//                        //关键代码, 否则不显示框框...
////                        if (layoutToolbar != null) layoutToolbar.bringToFront();
////                        TextView tvNickName = videoView.findViewById(R.id.tv_item_nick_name);//昵称
////                        View ivSex = videoView.findViewById(R.id.iv_sex_4_trtc_video_view_layout);//性别
//                        TextView tvNickName = itemViews.get(j).findViewById(R.id.tv_item_nick_name);//昵称
//                        View ivSex = itemViews.get(j).findViewById(R.id.iv_sex_4_trtc_video_view_layout);//性别
//                        View ivClose = itemViews.get(j).findViewById(R.id.iv_close_for_trtc);//关闭
//                        ivClose.setTag(dateInfo.headUrl);
//                        if (dateInfo.hasVideo) {
//                            if (tvNickName != null) {
//                                tvNickName.setVisibility(VISIBLE);
//                                tvNickName.setText(dateInfo.nickName);
//                            }
//                            if (ivSex != null) {
//                                ivSex.setVisibility(VISIBLE);
//                                ivSex.setSelected(dateInfo.sex == 1);
//                            }
//                        } else {
//                            if (tvNickName != null) tvNickName.setText("");
//                            if (ivSex != null) ivSex.setVisibility(GONE);
//                        }
//                        if (showClose && ivClose != null && onClickListener != null) {
////                            if (!TextUtils.equals(mSelfUserId, tempUserID)) {//如果不是自己
//                                ivClose.setVisibility(VISIBLE);
//                                ivClose.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {//屏蔽音视频
//                                        boolean selected = v.isSelected();
//                                        v.setSelected(!selected);
//                                        onClickListener.onCloseClick(videoView.getUserId(), videoView, selected);
//                                        String headUrl = (String) v.getTag();
//                                        if (headUrl != null) {
//                                            ImageView ivCover = ((View)v.getParent()).findViewById(R.id.iv_cover_for_trtc);//封面
//                                            if (ivCover != null) {
//                                                String url = "";
//                                                if (!selected) {//屏蔽状态, 要显示封面
//                                                    url = headUrl;
//                                                }
//                                                if (dp5 < 1) dp5 = dip2px(5);
//                                                Glide.with(v).load(Global.HOST_PIC.concat(url))
//                                                        .transform(new CenterCrop(), new RoundedCorners(dp5))
//                                                        .into(ivCover);
//                                            }
//                                        }
//                                    }
//                                });
////                            } else ivClose.setVisibility(GONE);//自己不能屏蔽自己
//                        }
//                        break;
//                    }
//                }
//            }
//        }
////        alreadyEnterDateRoom.removeAll(remove);
    }
}
