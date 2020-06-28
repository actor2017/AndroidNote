@echo 建立/生成信任密钥库,这个秘钥库没有私钥.(将客户端证书,导入到服务端的信任密钥库中)
@echo.
@echo keytool -import -alias cer文件别名 -file cer文件名.cer -keystore cer文件名_already_import.jks
@echo 输入这个生成的密钥库口令:
@echo 再次输入新口令(务必和上面一致):
@echo 是否信任此证书? [否]:y/n?
keytool -import -alias zhy_client -file zhy_client.cer -keystore zhy_client_already_import.jks
pause