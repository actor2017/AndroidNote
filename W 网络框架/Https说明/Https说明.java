Https相关知识
关于特别理论的东西大家可以百度下自己去了解下，这里就简单说一下，HTTPS相当于HTTP的安全版本了，为什么安全呢？
因为它在HTTP的之下加入了SSL (Secure Socket Layer)，安全的基础就靠这个SSL了。SSL位于TCP/IP和HTTP协议之间，那么它到底能干嘛呢？

它能够：
1.认证用户和服务器，确保数据发送到正确的客户机和服务器；(验证证书)
2.加密数据以防止数据中途被窃取；（加密）
3.维护数据的完整性，确保数据在传输过程中不被改变。（摘要算法）

HTTPS在传输数据之前需要客户端（浏览器）与服务端之间进行一次握手，在握手过程中将确立双方加密传输数据的密码信息。握手过程的简单描述如下：
1.浏览器将自己支持的一套加密算法、HASH算法发送给网站。
2.网站从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给浏览器。证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息。
3.浏览器获得网站证书之后，开始验证证书的合法性，如果证书信任，则生成一串随机数字作为通讯过程中对称加密的秘钥。然后取出证书中的公钥，将这串数字以及HASH的结果进行加密，然后发给网站。
4.网站接收浏览器发来的数据之后，通过私钥进行解密，然后HASH校验，如果一致，则使用浏览器发来的数字串使加密一段握手消息发给浏览器。
5.浏览器解密，并HASH校验，没有问题，则握手结束。接下来的传输过程将由之前浏览器生成的随机密码并利用对称加密算法进行加密。
握手过程中如果有任何错误，都会使加密连接断开，从而阻止了隐私信息的传输。

四、tomcat下使用自签名证书部署服务
1.生成一个zhy_server.jks文件.
2.用zhy_server.jks来签发证书,生成一个zhy_server.cer证书.
3.Tomcat找到tomcat/conf/sever.xml文件,在Service标签中，加入:
    <Connector SSLEnabled="true" acceptCount="100" clientAuth="false"
        disableUploadTimeout="true" enableLookups="true" keystoreFile="xxx/zhy_server.jks" keystorePass="123456" maxSpareThreads="75"
        maxThreads="200" minSpareThreads="5" port="8443"
        protocol="org.apache.coyote.http11.Http11NioProtocol" scheme="https"
        secure="true" sslProtocol="TLS"
    />
4.客户端:配置zhy_server.cer证书,可以copy到assets通过getAssets().open获取，或者通过命令拿到内部包含的字符串.

五、双向证书验证
1.极少数的应用需要双向证书验证，比如银行、金融类app.
首先对于双向证书验证，也就是说，客户端也会有个“jks文件”，服务器那边会同时有个“cer文件”与之对应。
2.生成 zhy_client.jks文件和签发一个zhy_client.cer证书.
3.建立/生成信任密钥库(将客户端证书,导入到服务端的信任密钥库中)
    keytool -import -alias zhy_client -file zhy_client.cer -keystore zhy_client_already_import.jks
4.配置服务端,依然是刚才的Connector标签，不过需要添加些属性。
    <Connector//其他属性与前面一致
        clientAuth="true"
        truststoreFile="xxx/zhy_client_already_import.jks"//直接使用zhy_client.cer会报错:Invalid keystore format
    />
5.此时再拿浏览器已经无法访问到我们的服务了，会显示基于证书的身份验证失败。所以客户端需要配置zhy_client.jks.
6.然后启动报错?：java.io.IOException: Wrong version of key store.需要把zhy_client.jks转换成zhy_client.bks
	因为：Java平台默认识别jks格式的证书文件，但是android平台只识别bks格式的证书文件
7.去Portecle下载portecle:https://sourceforge.net/projects/portecle/files/
   双击portecle.jar打开
   Ctrl+O打开文件夹,找到.jks文件
   Tools->Change KeyStore Type->BKS
   关闭->提示保存->

8.至此,服务端和客户端文件如下:
   服务端:zhy_server.jks, zhy_client_already_import.jks(生成见3)
   客户端:zhy_server.cer, zhy_client.bks
   中间文件(没有用):zhy_client.jks, zhy_client.cer

9.




