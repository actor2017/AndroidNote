@echo off
color 2a
@echo 1.��ȡ����:javah ��javah -help
javah
@echo.
@echo 2.����ͷ�ļ�,���ȴ�.java��Ӧ�ļ���
cd F:\Android\CppTest\app\src\main\java
F:
@echo.
@echo 3.javah com.package.name.xxxClass
@echo   ���ڳ��� ����: ����GBK�Ĳ���ӳ���ַ�,���Խ����˸Ľ�:
@echo   javah -jni -encoding UTF-8 com.package.name.xxxClass
@echo   ������ɵ�.hͷ�ļ���src\main\java�ļ�����,�����ƶ���cppĿ¼(eclipse��jniĿ¼)
javah -jni -encoding UTF-8 com.kuchuanyun.cpptest.MainActivity
pause