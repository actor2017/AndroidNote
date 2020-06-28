https://www.imooc.com/video/16778
H264结构与码流

1.H264结构图
  视频序列 -> 图像 -> 片(切片,Slice) ->宏块 ->子块

2.H264编码分层
  H264编码时分成了2层:
  NAL 层: Network Abstraction Layer, 视频数据网络抽象层
        由于网络包的最大传输单元是1500byte, 而H264的帧一般大于1500byte,
		就要将一个帧拆成多个包进行传输, 所有的拆包/组包都由NAL处理.
  
  VCL 层: Video Coding Layer, 视频数据编码层
        对视频原始数据进行压缩

3.码流基本概念一
  SODB: String Of Data Bits,原始数据比特流,长度不一定是8的倍数,它是由VCL层产生.
  
  RBSP: Raw Byte Sequence Payload, 就是SODB + trailing bits(末尾位),
        算法是在SODB最后一位补1, 如果不是8位对齐,不按字节对齐,则再在后面补多个0,使它成为8位对齐

4.码流基本概念二
  EBSP: Encapsulate Byte Sequence Payload,遇到两个连续的0x00就增加一个0x03
      生成压缩流之后,要在每一个帧的开头加起始位,一般是0x00000001?,未避免压缩的数据和起始位冲突,
	  当压缩的数据遇到连续2个0x00,就再在后面加一个0x03

  NALU(NAL Unit): NAL Header(1B) + EBSP, 就是在EBSP的基础上,加一个byte的网络头

5.NAL Unit
  NAL单元, 由NALU头部 + 一个切片(Slice),
  切片 由切片头 + 切片数据

6.Slice 与 MacroBlock (切片与宏块)的关系
  每一个切片都包含了 切片头 + 切片数据,//slice header + slice data
  每一个切片数据都包含了很多宏块,//MB MB skip_run MB ..... MB
  每个宏块包含了 mb_type, mb_pred, coded residual//宏块类型,宏块预测, 编码残差数据

7.H264切片
  一帧图片 里至少有1个切片,

8.H264码流分层
  层一		A.Annexb格式					B.RTP格式
			起始码+Nal Unit		...			RTP Packet(RTP包)	...
  
  层二		NAL Unit
			NALU Header 	+ 	NALU主体(里有一个Slice切片)

  层三		Slice
								Slice Header + Slice Data(里面包括n个宏块)

  层四		Slice Data
								flags + Macroblock Layer + Macroblock Layer(宏块) ......

  层五		PCM类
			mb_type(宏块类型)	PCM Data(宏块数据)		mb_type	Sub_mb_pred或mb_pred	Residual Data(残差数据)

  层六		Residual
																						Residual Block

说明:
  1.Annexb格式的H264数据,由起始码0x00000001 + NAL Unit
  1. 或 从网络上来分层,RTP Packet = NALU Header + NALU主体
  2.NAL 单元 = NALU Header + NALU主体(body)
  3.NALU主体(body) = Slice Header + Slice Data;
  4.Slice Data = flags + Macroblock Layer + Macroblock Layer ......
  5.Macroblock Layer = mb_type		PCM Data + Sub_mb_pred或mb_pred + Residual Data
  6.Residual Data = Residual Block * 1?

