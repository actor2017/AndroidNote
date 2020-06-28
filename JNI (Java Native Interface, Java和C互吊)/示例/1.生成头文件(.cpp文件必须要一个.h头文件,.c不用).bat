@echo off
color 2a
@echo 1.获取帮助:javah 或javah -help
javah
@echo.
@echo 2.生成头文件,首先打开.java对应文件夹
cd F:\Android\CppTest\app\src\main\java
F:
@echo.
@echo 3.javah com.package.name.xxxClass
@echo   由于出现 错误: 编码GBK的不可映射字符,所以进行了改进:
@echo   javah -jni -encoding UTF-8 com.package.name.xxxClass
@echo   最后生成的.h头文件在src\main\java文件夹里,把它移动到cpp目录(eclipse是jni目录)
javah -jni -encoding UTF-8 com.kuchuanyun.cpptest.MainActivity
pause