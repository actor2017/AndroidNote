https://www.imooc.com/video/11671
1.1. QRCode简称
全称: Quick Response Code
通过在一个矩形区域内使用黑白像素来进行编码
高纠错性, 高可用性, 高识别性

ZXing简介
ZXing是一个开源的, 用Java实现的多种格式的1D/2D条码图像处理库,
它包含了联系到其它语言的端口.
地址
https://github.com/zxing/zxing



二维码分类: https://www.imooc.com/video/10311
就码制的编码原理而言, 通常分为3中类型:
1.线性堆叠式二维码(图3.1.1)
  编码原理:
  建立在以为条码基础上, 按需要堆叠成2行或多行.
  PDF417, U1tracode, Code49, Code16K

2.矩阵式二维码(图3.1.2)
  在一个矩形空间通过黑, 白像素在矩阵中的不同分布进行编码.
  在矩阵相应元素位置上, 用点(放点, 圆点, 其它形状)的出现表示1, 不出现表示0.
  Data Matrix, Maxi Code, Aztec Code, QR Code(常用), Vericode

3.邮政码(图3.1.3)
  通过不同长度的条进行编码, 主要用于邮件编码
  POSTNET, BPO 4-STATE


二维码优缺点: https://www.imooc.com/video/10312
4.1.1.二维码优点(图4.1.1)
  高密度编码, 信息容量大(多达1850个字母, 2170数字, 1108字节, 500多汉字)
  编码范围广
  容错能力强(损坏50%任然可以读取)
  译码可靠性高(错误率<=1‰)
  可引入加密措施(防伪, 保密)
  成本低, 易制作, 持久耐用
  
4.1.2.二维码缺点(图4.1.2)
  二维码技术成为手机病毒, 钓鱼网站传播的新渠道
  信息泄露

5.1.QR Code: https://www.imooc.com/video/10313
  目前流行的3大国际标准(图5.1.1)
  
6.2.使用zxing生成二维码
    private void generite() {
        int width = 300;//定义图片宽度
        int height = 300;//定义图片高度
        String format ="png";//定义图片格式
        String content ="www.imooc.com";//定义二维码内容

        //定义二维码参数
        HashMap<EncodeHintType, Object> hints =  new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//设置编码
        //设置容错等级，等级越高，容量越小
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN,2);//设置边距

        //生成矩阵
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            //设置路径
            Path file = new File("D:/code/img.png").toPath();

            //\zxing\javase\src\main\java\com\google\zxing\client\j2se\MatrixToImageWriter.java
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);//输出图像(还有其它输出模式)
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

6.3.使用zxing进行二维码解析: https://www.imooc.com/video/10316
    public void parse() {
        try {
            //MultiFormatReader 多格式读取
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File("D:/code/img.png");

            //读取图片buffer中(import java.awt.image.BufferedImage;)
            BufferedImage bufferedImage = ImageIO.read(file);

            /**
             * BinaryBitmap         二进制位图
             * HybridBinarizer      混合二值化器
             * BufferedImageLuminanceSource   图像缓存区 亮度 资源
             * import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
             */
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));

            //定义二维码参数
            HashMap<DecodeHintType, Object> hashMap = new HashMap<>();
            hashMap.put(DecodeHintType.CHARACTER_SET, "utf-8");//编码方式
            Result result = formatReader.decode(binaryBitmap, hashMap);
            System.out.println("解析结果："+result.toString());//www.imooc.com
            System.out.println("二维码格式类型："+result.getBarcodeFormat());//QR_CODE
            System.out.println("二维码文本内容："+result.getText());//www.imooc.com
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

6.4.使用QR Code方式生成和解析二维码(区别于zxing的另外一个框架)
  生成: http://www.swetake.com/qrcode/index-e.html
  读取: https://osdn.jp/projects/qrcode/

6.5.jquery-qrcode生成二维码
  https://github.com/jeromeetienne/jquery-qrcode

6.6.其它形式的二维码
  1. 添加Logo
  2. 各种其它形状
  
6.7.二维码扩展
  如何实现二维码扫描名片? (微信名片)
  VCard 是标准通信薄基本格式:
    https://zh.wikipedia.org/wiki/VCard
	https://en.wikipedia.org/wiki/VCard






