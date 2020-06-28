https://www.imooc.com/video/16773
2.5.AAC讲解

1.AAC介绍
  1.应用范围广,现在市面上直播系统90%+都是AAC编解码器
  2.现在的传输协议RTMP支持AAC和Speex, 不支持OPUS
  3.AAC音频编解码质量高,音频高保真

2.AAC(Advanced Audio Coding)目的是取代MP3格式(MPEG-2编解码规范,有损压缩)
  MPEG-4压缩标准出现后,AAC加入了SBR和PS技术,对原始数据损耗小,压缩率更高
  
3.AAC目前常用的规格有: AAC LC, AAC HE V1, AAC HE V2
  三者之间的关系:
    AAC HE V1    =    AAC LC    +    SBR
	AAC HE V2    =    AAC HE V1 +    PS

4.AAC规格描述
	SBR: (Spectral Band Replication,分频复用,音频频带分成低频和高频,分别进行编码.
	      低频[20 Hz等]要不了44.1K, 减少多余数据. 高频[2W Hz等]增加采样)
    PS:  (Parametric Stereo,立体声、双声道分别保存,一个声道完整报错,另一个声道只存
	      一些差异的参数的东西[音频2个声道相关性很强])
	
	AAC LC:    (Low Complexity)低复杂度, 码流128kb/s ÷8= 16KB/s
	AAC HE:    AAC LC + SBR            , 码流64kb/s
	AAC HE V2: AAC LC + SBR + PS       , 码流32kb/s

5.AAC格式
  ADIF(Audio Data Interchange Format,音频数据交换格式)
  在文件头存一个小头,包含:采样率,采样大小,声道数 等, 每拿出一帧都套用头部参数解析,
  这种格式只能从头开始解码,常用在磁盘文件中
  
  ADTS(Audio Data Transport Stream,音频数据传输流)
  在每一帧的头加一个7-9byte, 如果是流传输的话,每一个音频帧都可以解析出来,
  这种格式在每一帧都有一个同步字,可以在音频流的任何位置开始解码,它类似数据流格式

所以在RTMP协议 & FLV文件里, 都有ADTS头

6.AAC编码库哪个好
  Libfdk_AAC > ffmpeg AAC > libfaac > libvo_aacenc > ...
  
  