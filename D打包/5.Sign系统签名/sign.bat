@ECHO OFF
Echo Auto-sign Created By TTD
Echo Update zip is now being signed and will be renamed to update_signed.zip

Echo .pem是公钥文件  .pk8是私钥文件
Echo 签名的apk多了META-INF文件夹,里面3个文件:MANIFEST.MF,CERT.SF,CERT.RSA
Echo signapk.jar就是生成了这几个文件(其他文件没有任何改变,so我们可以很容易去掉原有签名信息)
java -jar signapk.jar -w platform.x509.pem  platform.pk8  app.apk app(sign).apk

Echo Signing Complete

Pause
Echo EXIT退出这儿被我注释了