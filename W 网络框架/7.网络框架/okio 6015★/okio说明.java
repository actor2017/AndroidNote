https://github.com/square/okio

https://www.jianshu.com/p/f033a64539a1 大概是最完全的Okio源码解析文章 - 简书.html
https://github.com/ut2014/Okio_1.9 okio demo, 简书分析的demo


1.添加依赖:
compile 'com.squareup.okio:okio:2.2.2'

2.地址
https://github.com/square/okio/blob/master/okio/jvm/src/main/resources/META-INF/proguard/okio.pro

3.混淆
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*


https://www.jianshu.com/p/f033a64539a1
https://github.com/ut2014/Okio_1.9
自从Google官方将OkHttp作为底层的网络请求之后，作为OkHttp底层IO操作的Okio也是走进开发者的视野，这个甚至是取代了java的原生IO库的存在到底有什么特殊的本领呢？
Okio的代码比较精巧，核心的代码大约5000行，对文章不尽兴的也可以直接通读源码，这样就能理解的更清晰


Okio中特有的两个类Source，Sink代表的就是传统的输入流，和输出流:
1.输入流, 读取
Source source = null;
BufferedSource bSource = null;
File file = new File(filename);
source = Okio.source(file);//读文件
bSource = Okio.buffer(source);//通过source拿到 bufferedSource
String read = bSource.readString(Charset.forName("utf-8"));

2.输出流, 写入. Sink:下沉；消沉；渗透
Sink sink = Okio.sink(file);
BufferedSink bSink = Okio.buffer(sink);
bSink.writeUtf8("1");
bSink.writeUtf8("\n");
bSink.writeUtf8("this is new file!");
bSink.writeUtf8("\n");
bSink.writeString("我是每二条", Charset.forName("utf-8"));
bSink.flush();
bSink.close();

3.判断是否是png图片
这个是Okio官方提供了一个Png图片的解码的例子，我们知道一般判断一个文件的格式就是依靠前面的校验码，比如class文件中前面的16进制代码就是以 cafebabe 开头，同样的常规的png，jpg，gif之类的都可以通过前面的魔数来进行判断文件类型，这里就以一个图片输入流转换成一个BufferedSource，并且通过 readByteString 方法拿到一个字节串 ByteString 这样就能验证这个文件是不是一个png的图片，同样的方法也能用在其他文件的校验上。
private static final ByteString PNG_HEADER = ByteString.decodeHex("89504e470d0a1a0a");
public void decodePng(InputStream in) throws IOException {
  BufferedSource pngSource = Okio.buffer(Okio.source(in));

  ByteString header = pngSource.readByteString(PNG_HEADER.size());
  if (!header.equals(PNG_HEADER)) {
    throw new IOException("Not a PNG.");
  }
}

Okio除了这些外还有很多额外的功能，而且官方也提供了许多包括对于zip文件的处理，各种MD5，SHA-1.SHA256，Base64之类编码的处理，如果需要额外的一些操作，也可以自己实现Sink，Source对应的方法。


























