#JNI
Java代码调用C函数

#常见概念
-----
### 01 交叉编译
* 在一个平台上编译出来另外一个平台可以运行的二进制代码

* 不同的操作系统：windows, mac os,linux,unix
* 不同的处理器平台：x86,arm,MIPS,麒麟

### 02 交叉编译的原理
* 源代码--->编译--->链接--->可执行性程序

* 原理：模拟另外一种平台的特性去进行编译

### 03 交叉编译的工具链
一大堆工具,放在一起,链式调用（一个调用另一个）,工具链

### 04 常见工具
* ndk : native develop kits 本地开发工具集(交叉编译工具链)

* cdt : c/c++ develop tools
	*  c和c++开发工具 (eclipse的一个插件)  
	*  语法的高亮显示
* Cygwin : windows下一个linux模拟器

### 05 NDK的目录结构
* docs : 开发文档
* build: linux下编译的批处理命令
* platform : 
	* usr：unix system resource
	* jni开发需要的头文件和动态链接库(so类库)
* prebuild : 预编译的工具
* sample: 实例代码
* sources : 一些工具链的源码
* toolschains: 工具链
* ndk-build.cmd: 开始编译打包C代码
	* 配置环境变量


#JNI开发
-----
### 06 JNI开发步骤
	使用C函数实现Java本地方法：
		1. 在java代码里面声明一个native的方法		
			 public native String helloFromC();	
		2. 在工程目录下面创建一个jni的文件夹
		3. 在jni文件夹里面编写c代码，实现java的本地方法	
			//JNIEnv* envJNI本地接口这个结构体的二级指针
			//该结构体中封装了很多函数指针方便程序员开发jni代码
			//jobject obj MainActivity的对象,调用者对象

			jstring Java_包名_类名_方法名(JNIEnv* env,jobject obj){}	
		
	编译运行C函数，生成so可执行文件：
		4. 在jni文件夹下创建Android.mk文件,c代码编译的脚本文件	
				# $表示调用ndk中定义好的方法或者变量
				# 加载本地的jni目录，当在dos窗口中执行ndk-build命令时，会找工程目录下中的jni目录；
				LOCAL_PATH := $(call my-dir)

				# 下次重新生成动态库时，清理掉上次生成的变量
			   	include $(CLEAR_VARS)
				# 指定生成的动态库的名称，生成动态库文件后，文件名为libhello.so
			    LOCAL_MODULE    := hello
				# 指定了本地的c文件
		    	LOCAL_SRC_FILES := Hello.c

				# 指定了生成库的类型：BUILD_SHARED_LIBRARY表示动态库.so，BUILD_STATIC_LIBRARY表示静态库.a
				# 动态库.so文件小，静态库.a文件大。
		    	include $(BUILD_SHARED_LIBRARY)	

		5. 调用ndk-build指令编译c代码	
			注意配置环境变量	
			生成一个.so的文件 ( c代码编译出来的二进制可执行文件)
		6. 在java代码里面写静态代码块,加载so文件	
			static{
				System.loadLibrary("hello");
			}	
		7. 像使用一般java方法一样调用native的方法.

### 07 常见错误 & 添加X86支持
	1. java.lang.UnsatisfiedLinkError: 
		Couldn't load hello: **findLibrary returned null**

		解决方案：
			* 如果处理器平台不匹配,返回的lib就是空
				在Application.mk文件中编写APP_ABI := all 
			* 检查lib的名字是否拼写错误
	
	2. java.lang.UnsatisfiedLinkError: Native method not found:  	com.itheima.hello2.MainActivity.add:(II)I

		* C函数名写错了	
			 检查c语言里面编写的方法名是否符合规范 Java_包名_类名_方法名(参数)
		* 忘记加载动态链接库

	3. C文件含有中文的话，要把C文件改成UTF-8的格式

### 08 javah
* 生成jni样式的标头文件，文件中包含c函数的函数名
* JDK1.7、1.8：在src目录下执行 javah 包名.类名
* JDK1.6：在bin/classes目录下执行 javah 包名.类名

### 09 添加本地支持（重点）
* 指定NDK的路径
		在菜单栏window->Preferences->Android->NDK配置ndk的路径

* 项目右键->Add Native Support

* 项目自动生成jni文件夹，文件夹下自动生成cpp文件和Android.mk文件

* 项目可以指定jni.h头文件的路径
	* 项目右键-->C/C++->Paths and Symbo(路径和语法)-->Add-->File System
		添加：ndk\platforms\android-16\arch-arm\usr\include
	* c代码中的jni类型，都可以直接查看源码，并且函数指针可以使用alt+/来提示

* 项目写完后，直接部署，部署前会自动的编译打包so类库，然后再发布到手机


#java向C传递数据
-----
### 10 传递数组
* 数组是个对象，传递对象就是传递地址，修改地址上的值，数组的内容就会改变

		//获取数组首地址
		int* p = (*env)->GetIntArrayElements(env, array, 0);
		//获取数组长度
		int length = (*env)->GetArrayLength(env, array);


项目中调用别人写的c代码：
	1.得到so和开发文档；
	2.把so文件放到libs/armeabi/目录下；
	3.根据开发文档在java代码中写一个本地方法；
	4.加载动态库，调用本地方法；

# C代码中输出日志
-----

### 11 在C代码中使用logcat
	Android.mk文件增加（放到 include $(CLEAR_VARS)下面）
		LOCAL_LDLIBS += -llog

	C代码中增加
		#include <android/log.h>
		#define LOG_TAG "System.out"
		#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
		#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

		 LOGI("info\n");
		 LOGD("debug\n");


