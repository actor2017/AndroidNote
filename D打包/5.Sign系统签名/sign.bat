@ECHO OFF
Echo Auto-sign Created By TTD
Echo Update zip is now being signed and will be renamed to update_signed.zip

Echo .pem�ǹ�Կ�ļ�  .pk8��˽Կ�ļ�
Echo ǩ����apk����META-INF�ļ���,����3���ļ�:MANIFEST.MF,CERT.SF,CERT.RSA
Echo signapk.jar�����������⼸���ļ�(�����ļ�û���κθı�,so���ǿ��Ժ�����ȥ��ԭ��ǩ����Ϣ)
java -jar signapk.jar -w platform.x509.pem  platform.pk8  app.apk app(sign).apk

Echo Signing Complete

Pause
Echo EXIT�˳��������ע����