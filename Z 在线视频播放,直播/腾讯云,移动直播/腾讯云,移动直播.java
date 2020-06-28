https://cloud.tencent.com/document/product/454/6555
移动直播 SDK


小直播 App(名称: 小直播)
https://github.com/tencentyun/MLVBSDK/tree/master/Android/XiaoZhiBo

精简版 Demo(名称: 腾讯云工具包)
https://github.com/tencentyun/MLVBSDK/tree/master/Android/Demo

移动直播SDK文档
https://cloud.tencent.com/document/product/454/7886
视频云 SDK 中的播放器只支持 FLV 、RTMP 和 HLS（m3u8）三种格式的直播地址，以及 MP4、 HLS（m3u8）和 FLV 三种格式的点播地址????
注意: 实际上在 SDK 3.5 版本开始，将点播功能单独分离出来，交由 TXVodPlayer 负责, 需要另外单独依赖:https://github.com/tencentyun/MLVBSDK/tree/master/Android/SDK ???

<com.tencent.rtmp.ui.TXCloudVideoView
            android:id="@+id/video_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_centerInParent="true"
            android:visibility="gone"/>


step 2: 创建 Player
视频云 SDK 中的 TXLivePlayer 模块负责实现直播播放功能，并使用 setPlayerView 接口将这它与我们刚刚添加到界面上的 video_view 控件进行关联。
//mPlayerView 即 step1 中添加的界面 view
TXCloudVideoView mView = (TXCloudVideoView) view.findViewById(R.id.video_view);

//创建 player 对象
TXLivePlayer mLivePlayer = new TXLivePlayer(getActivity());

//关键 player 对象与界面 view
mLivePlayer.setPlayerView(mView);


step 3: 启动播放
String flvUrl = "http://2157.liveplay.myqcloud.com/live/2157_xxxx.flv";
mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
可选值	枚举值	含义
PLAY_TYPE_LIVE_RTMP	0	传入的 URL 为 RTMP 直播地址
PLAY_TYPE_LIVE_FLV	1	传入的 URL 为 FLV 直播地址
PLAY_TYPE_LIVE_RTMP_ACC	5	低延迟链路地址（仅适合于连麦场景）
PLAY_TYPE_VOD_HLS	3	传入的 URL 为 HLS（m3u8）播放地址


step 4: 画面调整
// 设置填充模式
mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//铺满or适应
可选值	含义
RENDER_MODE_FULL_FILL_SCREEN	将图像等比例铺满整个屏幕，多余部分裁剪掉，此模式下画面不会留黑边，但可能因为部分区域被裁剪而显示不全。
RENDER_MODE_ADJUST_RESOLUTION	将图像等比例缩放，适配最长边，缩放后的宽和高都不会超过显示区域，居中显示，画面可能会留有黑边。

// 设置画面渲染方向
mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);//画面旋转
可选值	含义
RENDER_ROTATION_PORTRAIT	正常播放（Home 键在画面正下方）
RENDER_ROTATION_LANDSCAPE	画面顺时针旋转 270 度（Home 键在画面正左方）


step 5: 暂停播放
对于直播播放而言，并没有真正意义上的暂停，所谓的直播暂停，只是画面冻结和关闭声音，而云端的视频源还在不断地更新着，所以当您调用 resume 的时候，会从最新的时间点开始播放，这是和点播对比的最大不同点（点播播放器的暂停和继续与播放本地视频文件时的表现相同)。

 // 暂停
mLivePlayer.pause();
// 继续
mLivePlayer.resume();


step 6: 结束播放
结束播放时记得销毁 view 控件，尤其是在下次 startPlay 之前，否则会产生大量的内存泄露以及闪屏问题。
同时，在退出播放界面时，记得一定要调用渲染 View 的 onDestroy() 函数，否则可能会产生内存泄露和“Receiver not registered”报警。

 @Override
public void onDestroy() {
    super.onDestroy();
    mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
    mView.onDestroy(); //TXCloudVideoView.onDestroy();
}
stopPlay 的布尔型参数含义为—— “是否清除最后一帧画面”。早期版本的 RTMP SDK 的直播播放器没有 pause 的概念，所以通过这个布尔值来控制最后一帧画面的清除。
如果是点播播放结束后，也想保留最后一帧画面，您可以在收到播放结束事件后什么也不做，默认停在最后一帧。


step 7: 消息接收
此功能可以在推流端将一些自定义 message 随着音视频线路直接下发到观众端，适用场景例如：
冲顶大会：推流端将题目下发到观众端，可以做到“音-画-题”完美同步。
秀场直播：推流端将歌词下发到观众端，可以在播放端实时绘制出歌词特效，因而不受视频编码的降质影响。
在线教育：推流端将激光笔和涂鸦操作下发到观众端，可以在播放端实时地划圈、划线。
通过如下方案可以使用此功能：

TXLivePlayConfig 中的 setEnableMessage 开关置为 true。
TXLivePlayer 通过 TXLivePlayListener 监听消息，消息编号：PLAY_EVT_GET_MESSAGE （2012）
 //Android 示例代码
    mTXLivePlayer.setPlayListener(new ITXLivePlayListener() {
        @Override
        public void onPlayEvent(int event, Bundle param) {
            if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                roomListenerCallback.onDebugLog("[AnswerRoom] 拉流失败：网络断开");
                roomListenerCallback.onError(-1, "网络断开，拉流失败");
            }
            else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
                String msg = null;
                try {
                    msg = new String(param.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
                    roomListenerCallback.onRecvAnswerMsg(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onNetStatus(Bundle status) {
        }
        });
		
		
step 8: 屏幕截图
通过调用 snapshot 您可以截取当前直播画面为一帧屏幕，此功能只会截取当前直播流的视频画面，如果您需要截取当前的整个 UI 界面，请调用 Android 的系统 API 来实现。
mLivePlayer.snapshot(new ITXSnapshotListener() {
    @Override
    public void onSnapshot(Bitmap bmp) {
        if (null != bmp) {
           //获取到截图 bitmap
        }
    }
});


step 9: 截流录制
截流录制是直播播放场景下的一种扩展功能：观众在观看直播时，可以通过单击录制按钮把一段直播的内容录制下来，并通过视频分发平台（比如腾讯云的点播系统）发布出去，这样就可以在微信朋友圈等社交平台上以 UGC 消息的形式进行传播。
//指定一个 ITXVideoRecordListener 用于同步录制的进度和结果
mLivePlayer.setVideoRecordListener(recordListener);
//启动录制，可放于录制按钮的响应函数里，目前只支持录制视频源，弹幕消息等等目前还不支持
mLivePlayer.startRecord(int recordType);
// ...
// ...
//结束录制，可放于结束按钮的响应函数里
mLivePlayer.stopRecord();

录制的进度以时间为单位，由 ITXVideoRecordListener 的 onRecordProgress 通知出来。
录制好的文件以 MP4 文件的形式，由 ITXVideoRecordListener 的 onRecordComplete 通知出来。
视频的上传和发布由 TXUGCPublish 负责，具体使用方法可以参考 视频上传（Android）。//https://cloud.tencent.com/document/product/584/15535


step 10: 清晰度无缝切换
日常使用中，网络情况在不断发生变化。在网络较差的情况下，最好适度降低画质，以减少卡顿；反之，网速比较好，可以提高观看画质。
传统切流方式一般是重新播放，会导致切换前后画面衔接不上、黑屏、卡顿等问题。使用无缝切换方案，在不中断直播的情况下，能直接切到另条流上。

清晰度切换在直播开始后，任意时间都可以调用。调用方式如下：

 // 正在播放的是流http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e.flv，
// 现切换到码率为900kbps的新流上
mLivePlayer.switchStream("http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e_900.flv");
step 11: 直播回看
时移功能是腾讯云推出的特色能力，可以在直播过程中，随时观看回退到任意直播历史时间点，并能在此时间点一直观看直播。非常适合游戏、球赛等互动性不高，但观看连续性较强的场景。

 // 设置直播回看前，先调用startPlay
// 开始播放 ...
TXLiveBase.setAppID("1253131631"); // 配置appId
mLivePlayer.prepareLiveSeek();     // 后台请求直播起始时间

还有很多功能......
