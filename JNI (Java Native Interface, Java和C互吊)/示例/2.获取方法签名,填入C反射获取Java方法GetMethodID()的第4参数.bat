@echo off
color 2a
@echo 1.��ȡ����:javap ��javap -help
javap
@echo.
@echo 2.��ȡ�ڲ�����ǩ��,���ȴ򿪶�Ӧdebug/release�ļ���
cd F:\Android\CppTest\app\build\intermediates\classes\debug
F:
@echo.
@echo 3.javap -s com.package.name.Class��ȡ���з���ǩ��,descriptor:�������ǩ��
javap -s com.kuchuanyun.cpptest.MainActivity
pause