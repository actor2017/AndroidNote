@echo off
color 2a
@echo 1.获取帮助:javap 或javap -help
javap
@echo.
@echo 2.获取内部类型签名,首先打开对应debug/release文件夹
cd F:\Android\CppTest\app\build\intermediates\classes\debug
F:
@echo.
@echo 3.javap -s com.package.name.Class获取所有方法签名,descriptor:后面就是签名
javap -s com.kuchuanyun.cpptest.MainActivity
pause