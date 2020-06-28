https://www.imooc.com/video/16777
视频压缩技术详解

1.组内宏块查找
  查找一组帧(GOF)内的小球, 扫描一组帧内的小球...

2.运动估算
  第1张图小球有个位置, 第2张图的根据方向和距离算出运动矢量, 将所有的图进行两两比较,
  最后形成了右边图红色部分, 每个红色标注都是一个矢量, 很多帧最后形成运动估算

3.运动矢量与补偿压缩
  经过上面的运动估算后, 最后 Compression(压缩) = ,//帧间压缩,针对I帧
  Motion Vector(运动矢量) +
  Residual Picture(残差图片)

4.帧内预测
  对每一个宏块先进行计算,选择用9中模式(intra prediction moe,内部预测模式)中哪种压缩模式,
  最后形成右边predicted picture预测图
  
5.计算帧内预测残差值
  Predicted Picture: 预测图,第一种是算出的图片
  Original Picture : 第二张是原图
  通过原图和预测图计算出第三张图中灰色部分,就是残差值

6.预测模式与残差值压缩
  压缩,只存Prediction Mode Info(每个宏块选择模式)的数据 和 Residual Picture(残差值) 数据,
  解码时, 根据模式信息回复预测图, 然后再和残差值进行累加,就能还原出原始图像

7.DCT压缩(整数离散余弦变换)
  左侧是一个宏块, 左下方是用数字进行量化,

8.压缩后的结果
  只有左上角有数据, 其余地方都没数据, (离散余弦转换系数 DCT coefficient).
  
9.VLC压缩(无损压缩)
  VLC实际是MPEG2使用的技术,
  H264使用的是CABAC,
  和音频哈夫曼编码原理相同, 把A频率高的编为11短码, 把Z频率低的编为长码0000001011,

10.CABAC压缩(Context Adaptive Binary Arithmetic Coding)
  MPEG2的VLC压缩, 压缩后产生一块一块的数据.
  根据上下文, 能把后面的一块快的数据压缩成小块.

