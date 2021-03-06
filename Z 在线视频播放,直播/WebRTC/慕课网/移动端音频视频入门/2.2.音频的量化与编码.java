https://www.imooc.com/video/16766
音频的量化与编码

1.音频量化过程,将模拟信号转换成数字信号,见图片2.2.1
    模拟数据(音频波形片段)
	    |
		↓
       采样(对音频做10次采样,每次采样获取一个y值)
	    |
		↓
	   量化(再对y轴进行量化, 常用的是16bit(1-16))
		|
		↓
	   编码(将量化的y值进行二进制编码:2->010, 3->011 ...)
		|
		↓
	数字信号(01001110...)

2.量化基本概念
  采样大小: 一个采样用多少bit存放. 常用的是16bit, 2^16(0~65535)
  采样率: 采样频率,每秒在模拟信号采样多少次 8k, 16k, 32k, 44.1k(aac一般是这个), 48k
          如果频率=20HZ, 每个正玄波采样次数 = 44.1k/20 = 2205次
  声道数: 单声道, 双声道, 多声道(每个喇叭一个声道)

3.码率计算(原始数据的码率计算)
  要计算一个PCM音频流的码率是一件很轻松的事情,
  码率 = 采样率 x 采样大小 x 声道数

  例如:采样率=44.1k, 采样大小为16bit, 双声道的PCM编码的WAV文件,
       码率 = 44.1k x 16 x 2 = 1411.2Kb/s ÷8= 176.4KB/s(一百多kb)

    如果做完AAC LC编码,最终压缩后的数据: 128Kb/s
	如果是AAC HE VR编码,最终压缩后的数据:32Kb/s(压缩1411/32=44倍)
	