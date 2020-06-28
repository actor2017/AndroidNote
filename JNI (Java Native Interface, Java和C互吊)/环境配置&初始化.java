百度下载NDK, 现在最新版本android-ndk-r9.
Windows 32-bit 版本下载地址:
http://dl.google.com/android/ndk/android-ndk-r9-windows-x86.zip
Windows 64-bit 版本下载地址:
http://dl.google.com/android/ndk/android-ndk-r9-windows-x86_64.zip
或者在AndroidStudio里直接下载sdk,见图片.

* docs : 开发文档
* build: linux下编译的批处理命令
* platform : 某种平台下编译需要的头文件和函数库
* prebuild : 预编译的工具
* sample: 实例代码
* sources : 一些工具链的源码
* toolschains: 工具链
* ndk-build.cmd: ndk编译的命令脚本,编译成.so
//=================================================================================

Android 原生开发工具包 (NDK)：这套工具集允许您为 Android 使用 C 和 C++ 代码，并提供众多平台库，让您可以管理原生 Activity 和访问物理设备组件，例如传感器和触摸输入。
CMake：一款外部构建工具，可与 Gradle 搭配使用来构建原生库。如果您只计划使用 ndk-build，则不需要此组件。
LLDB：一种调试程序，Android Studio 使用它来调试原生代码。


创建支持 C/C++ 的新项目
在向导的 Configure your new project 部分，选中 Include C++ Support 复选框。
点击 Next。
正常填写所有其他字段并完成向导接下来的几个部分。
在向导的 Customize C++ Support 部分，您可以使用下列选项自定义项目：
C++ Standard：使用下拉列表选择您希望使用哪种 C++ 标准。选择 Toolchain Default 会使用默认的 CMake 设置。
Exceptions Support：如果您希望启用对 C++ 异常处理的支持，请选中此复选框。如果启用此复选框，Android Studio 会将 -fexceptions 标志添加到模块级 build.gradle 文件的 cppFlags 中，Gradle 会将其传递到 CMake。
Runtime Type Information Support：如果您希望支持 RTTI，请选中此复选框。如果启用此复选框，Android Studio 会将 -frtti 标志添加到模块级 build.gradle 文件的 cppFlags 中，Gradle 会将其传递到 CMake。
点击 Finish。


点击 Run 从菜单栏运行应用 后，Android Studio 将在您的 Android 设备或者模拟器上构建并启动一个显示文字“Hello from C++”的应用。下面的概览介绍了构建和运行示例应用时会发生的事件：
Gradle 调用您的外部构建脚本 CMakeLists.txt。
CMake 按照构建脚本中的命令将 C++ 源文件 native-lib.cpp 编译到共享的对象库中，并命名为 libnative-lib.so，Gradle 随后会将其打包到 APK 中。
运行时，应用的 MainActivity 会使用 System.loadLibrary() 加载原生库。现在，应用可以使用库的原生函数 stringFromJNI()。
MainActivity.onCreate() 调用 stringFromJNI()，这将返回“Hello from C++”并使用这些文字更新 TextView。

CMake语法:https://github.com/carl-wang-cn/demo/tree/master/cmake

NDK samples:https://github.com/googlesamples/android-ndk
javah xxx.java	//生成.h头文件,可用作.c或.cpp的参考(必须在src目录下生成?)
