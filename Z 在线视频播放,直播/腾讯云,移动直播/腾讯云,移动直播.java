https://cloud.tencent.com/document/product/454/6555
�ƶ�ֱ�� SDK


Сֱ�� App(����: Сֱ��)
https://github.com/tencentyun/MLVBSDK/tree/master/Android/XiaoZhiBo

����� Demo(����: ��Ѷ�ƹ��߰�)
https://github.com/tencentyun/MLVBSDK/tree/master/Android/Demo

�ƶ�ֱ��SDK�ĵ�
https://cloud.tencent.com/document/product/454/7886
��Ƶ�� SDK �еĲ�����ֻ֧�� FLV ��RTMP �� HLS��m3u8�����ָ�ʽ��ֱ����ַ���Լ� MP4�� HLS��m3u8���� FLV ���ָ�ʽ�ĵ㲥��ַ????
ע��: ʵ������ SDK 3.5 �汾��ʼ�����㲥���ܵ���������������� TXVodPlayer ����, ��Ҫ���ⵥ������:https://github.com/tencentyun/MLVBSDK/tree/master/Android/SDK ???

<com.tencent.rtmp.ui.TXCloudVideoView
            android:id="@+id/video_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_centerInParent="true"
            android:visibility="gone"/>


step 2: ���� Player
��Ƶ�� SDK �е� TXLivePlayer ģ�鸺��ʵ��ֱ�����Ź��ܣ���ʹ�� setPlayerView �ӿڽ����������Ǹո���ӵ������ϵ� video_view �ؼ����й�����
//mPlayerView �� step1 ����ӵĽ��� view
TXCloudVideoView mView = (TXCloudVideoView) view.findViewById(R.id.video_view);

//���� player ����
TXLivePlayer mLivePlayer = new TXLivePlayer(getActivity());

//�ؼ� player ��������� view
mLivePlayer.setPlayerView(mView);


step 3: ��������
String flvUrl = "http://2157.liveplay.myqcloud.com/live/2157_xxxx.flv";
mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //�Ƽ� FLV
��ѡֵ	ö��ֵ	����
PLAY_TYPE_LIVE_RTMP	0	����� URL Ϊ RTMP ֱ����ַ
PLAY_TYPE_LIVE_FLV	1	����� URL Ϊ FLV ֱ����ַ
PLAY_TYPE_LIVE_RTMP_ACC	5	���ӳ���·��ַ�����ʺ������󳡾���
PLAY_TYPE_VOD_HLS	3	����� URL Ϊ HLS��m3u8�����ŵ�ַ


step 4: �������
// �������ģʽ
mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//����or��Ӧ
��ѡֵ	����
RENDER_MODE_FULL_FILL_SCREEN	��ͼ��ȱ�������������Ļ�����ಿ�ֲü�������ģʽ�»��治�����ڱߣ���������Ϊ�������򱻲ü�����ʾ��ȫ��
RENDER_MODE_ADJUST_RESOLUTION	��ͼ��ȱ������ţ�������ߣ����ź�Ŀ�͸߶����ᳬ����ʾ���򣬾�����ʾ��������ܻ����кڱߡ�

// ���û�����Ⱦ����
mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);//������ת
��ѡֵ	����
RENDER_ROTATION_PORTRAIT	�������ţ�Home ���ڻ������·���
RENDER_ROTATION_LANDSCAPE	����˳ʱ����ת 270 �ȣ�Home ���ڻ������󷽣�


step 5: ��ͣ����
����ֱ�����Ŷ��ԣ���û�����������ϵ���ͣ����ν��ֱ����ͣ��ֻ�ǻ��涳��͹ر����������ƶ˵���ƵԴ���ڲ��ϵظ����ţ����Ե������� resume ��ʱ�򣬻�����µ�ʱ��㿪ʼ���ţ����Ǻ͵㲥�Աȵ����ͬ�㣨�㲥����������ͣ�ͼ����벥�ű�����Ƶ�ļ�ʱ�ı�����ͬ)��

 // ��ͣ
mLivePlayer.pause();
// ����
mLivePlayer.resume();


step 6: ��������
��������ʱ�ǵ����� view �ؼ������������´� startPlay ֮ǰ�����������������ڴ�й¶�Լ��������⡣
ͬʱ�����˳����Ž���ʱ���ǵ�һ��Ҫ������Ⱦ View �� onDestroy() ������������ܻ�����ڴ�й¶�͡�Receiver not registered��������

 @Override
public void onDestroy() {
    super.onDestroy();
    mLivePlayer.stopPlay(true); // true ����������һ֡����
    mView.onDestroy(); //TXCloudVideoView.onDestroy();
}
stopPlay �Ĳ����Ͳ�������Ϊ���� ���Ƿ�������һ֡���桱�����ڰ汾�� RTMP SDK ��ֱ��������û�� pause �ĸ������ͨ���������ֵ���������һ֡����������
����ǵ㲥���Ž�����Ҳ�뱣�����һ֡���棬���������յ����Ž����¼���ʲôҲ������Ĭ��ͣ�����һ֡��


step 7: ��Ϣ����
�˹��ܿ����������˽�һЩ�Զ��� message ��������Ƶ��·ֱ���·������ڶˣ����ó������磺
�嶥��᣺�����˽���Ŀ�·������ڶˣ�������������-��-�⡱����ͬ����
�㳡ֱ���������˽�����·������ڶˣ������ڲ��Ŷ�ʵʱ���Ƴ������Ч�����������Ƶ����Ľ���Ӱ�졣
���߽����������˽�����ʺ�Ϳѻ�����·������ڶˣ������ڲ��Ŷ�ʵʱ�ػ�Ȧ�����ߡ�
ͨ�����·�������ʹ�ô˹��ܣ�

TXLivePlayConfig �е� setEnableMessage ������Ϊ true��
TXLivePlayer ͨ�� TXLivePlayListener ������Ϣ����Ϣ��ţ�PLAY_EVT_GET_MESSAGE ��2012��
 //Android ʾ������
    mTXLivePlayer.setPlayListener(new ITXLivePlayListener() {
        @Override
        public void onPlayEvent(int event, Bundle param) {
            if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                roomListenerCallback.onDebugLog("[AnswerRoom] ����ʧ�ܣ�����Ͽ�");
                roomListenerCallback.onError(-1, "����Ͽ�������ʧ��");
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
		
		
step 8: ��Ļ��ͼ
ͨ������ snapshot �����Խ�ȡ��ǰֱ������Ϊһ֡��Ļ���˹���ֻ���ȡ��ǰֱ��������Ƶ���棬�������Ҫ��ȡ��ǰ������ UI ���棬����� Android ��ϵͳ API ��ʵ�֡�
mLivePlayer.snapshot(new ITXSnapshotListener() {
    @Override
    public void onSnapshot(Bitmap bmp) {
        if (null != bmp) {
           //��ȡ����ͼ bitmap
        }
    }
});


step 9: ����¼��
����¼����ֱ�����ų����µ�һ����չ���ܣ������ڹۿ�ֱ��ʱ������ͨ������¼�ư�ť��һ��ֱ��������¼����������ͨ����Ƶ�ַ�ƽ̨��������Ѷ�Ƶĵ㲥ϵͳ��������ȥ�������Ϳ�����΢������Ȧ���罻ƽ̨���� UGC ��Ϣ����ʽ���д�����
//ָ��һ�� ITXVideoRecordListener ����ͬ��¼�ƵĽ��Ⱥͽ��
mLivePlayer.setVideoRecordListener(recordListener);
//����¼�ƣ��ɷ���¼�ư�ť����Ӧ�����Ŀǰֻ֧��¼����ƵԴ����Ļ��Ϣ�ȵ�Ŀǰ����֧��
mLivePlayer.startRecord(int recordType);
// ...
// ...
//����¼�ƣ��ɷ��ڽ�����ť����Ӧ������
mLivePlayer.stopRecord();

¼�ƵĽ�����ʱ��Ϊ��λ���� ITXVideoRecordListener �� onRecordProgress ֪ͨ������
¼�ƺõ��ļ��� MP4 �ļ�����ʽ���� ITXVideoRecordListener �� onRecordComplete ֪ͨ������
��Ƶ���ϴ��ͷ����� TXUGCPublish ���𣬾���ʹ�÷������Բο� ��Ƶ�ϴ���Android����//https://cloud.tencent.com/document/product/584/15535


step 10: �������޷��л�
�ճ�ʹ���У���������ڲ��Ϸ����仯��������ϲ������£�����ʶȽ��ͻ��ʣ��Լ��ٿ��٣���֮�����ٱȽϺã�������߹ۿ����ʡ�
��ͳ������ʽһ�������²��ţ��ᵼ���л�ǰ�����νӲ��ϡ����������ٵ����⡣ʹ���޷��л��������ڲ��ж�ֱ��������£���ֱ���е��������ϡ�

�������л���ֱ����ʼ������ʱ�䶼���Ե��á����÷�ʽ���£�

 // ���ڲ��ŵ�����http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e.flv��
// ���л�������Ϊ900kbps��������
mLivePlayer.switchStream("http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e_900.flv");
step 11: ֱ���ؿ�
ʱ�ƹ�������Ѷ���Ƴ�����ɫ������������ֱ�������У���ʱ�ۿ����˵�����ֱ����ʷʱ��㣬�����ڴ�ʱ���һֱ�ۿ�ֱ�����ǳ��ʺ���Ϸ�������Ȼ����Բ��ߣ����ۿ������Խ�ǿ�ĳ�����

 // ����ֱ���ؿ�ǰ���ȵ���startPlay
// ��ʼ���� ...
TXLiveBase.setAppID("1253131631"); // ����appId
mLivePlayer.prepareLiveSeek();     // ��̨����ֱ����ʼʱ��

���кܶ๦��......
