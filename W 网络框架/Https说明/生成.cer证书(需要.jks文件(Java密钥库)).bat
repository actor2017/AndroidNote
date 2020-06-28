@echo 导出密钥库公钥、信息等到证书中
@echo keytool -export -alias 秘钥库别名 -file 证书名.cer -keystore 秘钥库文件.jks -storepass 秘钥库口令(密码),非别名密码
keytool -export -alias zhy_server -file zhy_server.cer -keystore zhy_server.jks -storepass 123456