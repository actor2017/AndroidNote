@echo ����/����������Կ��,�����Կ��û��˽Կ.(���ͻ���֤��,���뵽����˵�������Կ����)
@echo.
@echo keytool -import -alias cer�ļ����� -file cer�ļ���.cer -keystore cer�ļ���_already_import.jks
@echo ����������ɵ���Կ�����:
@echo �ٴ������¿���(��غ�����һ��):
@echo �Ƿ����δ�֤��? [��]:y/n?
keytool -import -alias zhy_client -file zhy_client.cer -keystore zhy_client_already_import.jks
pause