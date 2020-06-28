@echo https://blog.csdn.net/qq_31810357/article/details/71122765
@echo keytool -genkey -alias 别名 -keypass 密钥口令(别名密码) -keyalg RSA -keystore 文件名.jks -validity 364635(单位天:999年) -storepass 秘钥库口令(密码)
@echo 您的名字与姓氏是什么?
@echo   [Unknown]:  示例输入:song.Lee
@echo 您的组织单位名称是什么?
@echo   [Unknown]:  示例输入:kuchuanyun
@echo 您的组织名称是什么?
@echo   [Unknown]:  示例输入:kuchuanyun
@echo 您所在的城市或区域名称是什么?

@echo   [Unknown]:  示例输入:xi'an
@echo 您所在的省/市/自治区名称是什么?

@echo   [Unknown]:  示例输入:shanxi
@echo 该单位的双字母国家/地区代码是什么?
@echo   [Unknown]:  示例输入:0086
@echo CN=song.Lee, OU=kuchuanyun, O=kuchuanyun, L=xi'an, ST=shanxi, C=0086是否正确?(输入y/n)
@echo   [否]:  示例输入:y

keytool -genkey -alias zhy_server -keypass 123456 -keyalg RSA -keystore zhy_server1.jks -validity 364635 -storepass 123456
pause