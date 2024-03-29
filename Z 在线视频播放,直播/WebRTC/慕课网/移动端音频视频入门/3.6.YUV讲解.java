https://www.imooc.com/video/16780
3.6.YUV讲解
  图像存储格式, 除了RGB, 还有YUV
  GRB每个占1个字节,
  
1.YUV
  也成YCbCr, 是电视系统所采用的的一种'颜色编码方案'

  Y: 表示明亮度,也就是灰阶值, 它是基础信号(黑白电视)
  U: 表示色度, UV的作用是描述影像色彩,他们用于指定像素的颜色
  V: 饱和度, 

  摄像机的视频要在电视上放,录出来的视频就是YUV的,

2.YUV常见格式
  YUV4:2:0 (YCbCr 4:2:0) 4个Y, 2个U, 0个V, 以下同理. RGB相当于8:8:8,存储空间大得多
  YUV4:2:2 (YCbCr 4:2:2)
  YUV4:4:4 (YCbCr 4:4:4)

3.YUV4:2:0
  1.现在常用格式, 它并不意味着只有Y、Cb两个分量, 而没有Cr分量.
  它实际指的是对每行扫描线来说, 只有一种色度分量, 它以2:1的抽样率存储.
  (电视扫描显示第一行:Y和U分量, 第二行:Y和V分量)

  2.相邻的扫描行存储不同的色度分量, 也就是说,如果一行是4:2:0的话,
  下一行就是4:0:2, 再下一行是4:2:0...以此类推.

4.YUV存储格式
  1.planar(平面存储), 分为2种:
    I420 : YYYYYYYY UU VV => YUV420P(P代表平面), PC端经常使用
	YV12 : YYYYYYYY VV UU => YUV420P

  2.packed(打包存储), 分为2种:
    NV12 : YYYYYYYY UVUV => YUV420SP, IOS默认
	NV21 : YYYYYYYY VUVU => YUV420SP, Android默认

  转成YUV后出现 图像倒置, 反转 等, 都是转成YUV格式不一样导致.