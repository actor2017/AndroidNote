https://blog.csdn.net/wl532882877/article/details/78296662?locationNum=5&fps=1
好久没写文章了，其实是想记录一下自己在Android开发的成长历程。谈到音视频这块，对于新手来说刚接触到这一块，那是非常非常的恶心~我自己弄这一块也是从头开始，在 网上也翻阅了无数的资料、浏览了无数的博客，尝试了多种方法，网上大致方法是采用第三方的，如webRtc、speex等。甚至花钱买积分去下demo，也发现了好多demo就是    骗积分的，呵呵~好气~ 不谈了。成长的过程也是比较艰辛的，最后也还是自己解决了这个问题~~也希望这篇文章能帮助处在当时的我的一样的情况下。

  

   1.Android声音录制
          1. Android中使用AudioRecord录制声音，在Android中录制声音需要相应的权限，注意动态申请权限的问题（6.0以上）。

//处理权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.v(TAG, "处理权限申请回调");
        PermissionUtils.requestPermissionsResult(ActivityCameraLiveView.this, requestCode, permissions, grantResults, mPermissionGrant);
    }
 
 
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Log.v(TAG, "PermissionGrant: CODE_RECORD_AUDIO audio = true");
                    audio = true;
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    WRITE_EXTERNAL_STORAGE_PERMISSION = true;
                    Log.v(TAG, "PermissionGrant: WRITE_EXTERNAL_STORAGE_PERMISSION = true");
                    break;
                case PermissionUtils.CODE_STOP_CAMERA:
                    Log.v(TAG, "PermissionUtils.CODE_STOP_CAMERA");
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    break;
            }
        }
    };

	nMinBufSize = AudioRecord.getMinBufferSize(8000,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

2.Android回声消除
       网上回音消除方式大概有两种 ：1.通过安卓自带的 VOICE_COMMUNICATION模式进行录音，自动消除回音。2.使用第三方库进行消除（webRtc、Speex..），消除回音。
       用第三方的话，比较麻烦,而且不好实现，用起来效果不是很明显，试了一下Speex的，感觉手机端与手机端语音还可以，但是如果是手机端（APP）和硬件语音的话，就要考虑到码流格式的问题，因为我们公司采用的是G711格式的音频流，所以就必须要先转pcm格式然后再转speex格式，最后转711格式的。实在不行，还有一种就是在录音的时候强制关闭扬声器，在录音的时候就只录到手机的声音隔断了扬声器的声音，不过效果不是很明显，还是会有滋滋的噪声。
audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
                                audioManager.setSpeakerphoneOn(false);
                                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0,
                                        AudioManager.STREAM_VOICE_CALL);
                                audioManager.setMode(AudioManager.MODE_IN_CALL);

								
这里我推荐第一种方法，不仅考虑到了手机机型的适配，还比较简单就改变一个参数：
audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION, 8000,
                        AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, nMinBufSize);
                audioRecord.startRecording();

写到这里基本上回声和噪声就没有了~

