@echo https://blog.csdn.net/qq_31810357/article/details/71122765
@echo keytool -genkey -alias ���� -keypass ��Կ����(��������) -keyalg RSA -keystore �ļ���.jks -validity 364635(��λ��:999��) -storepass ��Կ�����(����)
@echo ����������������ʲô?
@echo   [Unknown]:  ʾ������:song.Lee
@echo ������֯��λ������ʲô?
@echo   [Unknown]:  ʾ������:kuchuanyun
@echo ������֯������ʲô?
@echo   [Unknown]:  ʾ������:kuchuanyun
@echo �����ڵĳ��л�����������ʲô?

@echo   [Unknown]:  ʾ������:xi'an
@echo �����ڵ�ʡ/��/������������ʲô?

@echo   [Unknown]:  ʾ������:shanxi
@echo �õ�λ��˫��ĸ����/����������ʲô?
@echo   [Unknown]:  ʾ������:0086
@echo CN=song.Lee, OU=kuchuanyun, O=kuchuanyun, L=xi'an, ST=shanxi, C=0086�Ƿ���ȷ?(����y/n)
@echo   [��]:  ʾ������:y

keytool -genkey -alias zhy_server -keypass 123456 -keyalg RSA -keystore zhy_server1.jks -validity 364635 -storepass 123456
pause