@echo 获取.cer文件公钥:使用keytool命令,以rfc样式输出.keytool命令是JDK里面自带的.
@echo 注意:★★★要把"BEGIN"和"END CERTIFICATE"这2行一起复制★★★
keytool -printcert -rfc -file 12306.cer
pause