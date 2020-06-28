https://blog.csdn.net/qq_32175491/article/details/78664821

MediaRecorder recorder = new MediaRecorder();//1. 创建录音机

AudioSource.DEFAULT
AudioSource.MIC
AudioSource.VOICE_UPLINK
AudioSource.VOICE_DOWNLINK
AudioSource.VOICE_CALL
AudioSource.CAMCORDER			:
AudioSource.VOICE_RECOGNITION
AudioSource.VOICE_COMMUNICATION
AudioSource.REMOTE_SUBMIX
AudioSource.UNPROCESSED
recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//2. 设置音频源

OutputFormat.DEFAULT:
OutputFormat.THREE_GPP:	//3GPP media file format
OutputFormat.MPEG_4:	//MPEG4 media file format
OutputFormat.RAW_AMR:	//录制原始文件，只支持音频录制，同时要求音频编码为AMR_NB. The following formats are audio only .aac or .amr formats
OutputFormat.AMR_NB:	//编码的是无视频纯声音3gp文件就是amr,他的文件比AAC的小，他的音乐效果没ACC的好
OutputFormat.AMR_WB:	//新型可变速率多模式宽带语音编解码器，专为无线 CDMA 2000标准而设计
OutputFormat.AAC_ADIF:	
OutputFormat.AAC_ADTS:	//Audio Data Transport Stream。是AAC音频的传输流格式。是AAC的一种非常常见的传输格式
OutputFormat.MPEG_2_TS:	
OutputFormat.WEBM:		//编码为VP8/VORBIS的输出格式
recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//3. 设置音频的格式(其它格式要收费?)

AudioEncoder.DEFAULT:
AudioEncoder.AMR_NB:	//AMR (Narrowband) audio codec窄带音频编解码器
AudioEncoder.AMR_WB:	//AMR (Wideband) audio codecAMR(宽带)音频编解码器
AudioEncoder.AAC:		//AAC Low Complexity (AAC-LC) audio codec低复杂度(AAC- lc)音频编解码器
AudioEncoder.HE_AAC:	//High Efficiency AAC (HE-AAC) audio codec高效AAC (HE-AAC)音频编解码器
AudioEncoder.AAC_ELD:	//Enhanced Low Delay AAC (AAC-ELD) audio codec增强低延迟AAC (AAC- eld)音频编解码器
AudioEncoder.VORBIS:	//Ogg Vorbis audio codecOgg Vorbis音频编解码器
recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//4. 设置编译音频

recorder.setOutputFile("/mnt/sdcard/luyin.3gp");//5. 设置音频存储的位置

recorder.prepare();//6.准备录音

recorder.start();   //7. 开始录音 Recording is now started

recorder.stop();//8. 停止录音

recorder.reset();   //9.重置 You can reuse the object by going back to setAudioSource() step

recorder.release(); //10. 释放资源 Now the object cannot be reused
recorder = null;