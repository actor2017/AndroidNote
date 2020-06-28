https://www.imooc.com/video/16772
2.4.音频编解码器选型

1.OPUS, AAC 我们改选谁?
  1.1.常见的音频编解码器包括:
      OPUS, AAC, Vorbis, Speex, iLBC, AMR, G.711 等
	
	OPUS:现在很流行,firefox牵头,包含2个核, 基于口和耳的建的模型,适用范围广.
	     对于实时互动,要求"实时性高",我们使用的模型,
		 对于声音&音乐,要求"高保真", 我们使用耳的模型.
    
	AAC:常用于泛娱乐化直播系统中, 实时性要求"不是特别高"
								  但是对声音"音质要求高"
	
	Speex:在OPUS和AAC之前常见的一种,包含 音频编解码器,回音消除,降噪,
	
	G.711:有点视频会议,还会与PSDN互联(固话),固话就是用的G.711或G.722
	
	RTMP协议:
	  支持AAC, Speex, 但是不支持OPUS(因为OPUS是最近2年才推出)

  1.2.网上性能对比评测结果: OPUS > AAC > Vorbis (其它的逐渐被淘汰掉,或很少使用)


2.音频编解码器性能对比,见图2.4.2
  绿色:royalty-free, open-source       协议自由, 开源
  蓝色:free license, not open-source   可以随意使用, 不开源
  红色:licensing fees, not open-source 收费, 不开源
  
  x轴: 编码器把数据压缩成多大,单位比特率(kb/s)
  y轴:narrowband      窄频带
      wideband        宽频带
	  super-wideband  超宽频
	  fullband        全频段
	  fullband stereo 全频段立体声

3.总结
  AAC: 泛娱乐化直播
  实时互动: OPUS
  2个系统融合: OPUS 和 AAC 互转
  实时互动 & 电话 互联: G.711, G.722

