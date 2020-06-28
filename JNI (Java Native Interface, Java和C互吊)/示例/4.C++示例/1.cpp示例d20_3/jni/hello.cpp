//#include <jni.h>//0.应该可以不导入了?
#include "com_itheima_cpp_MainActivity.h"//1.必须导入自己的头文件
//导入系统的头文件: <>
//导入自定义头文件: ""

//  机器语言   汇编语言   C语言(C++在C的基础上进行了包装,有了对象的概念)    C++  C+++(Java)  C++++(C#)

/**
 * env	：一级`结构体`指针,不是二级`结构体`指针
 */
JNIEXPORT jstring JNICALL Java_com_itheima_cpp_MainActivity_helloCpp
  (JNIEnv * env, jobject thiz){

	char* str = "曲终未必人散，有缘自会相逢";
	return env->NewStringUTF(str);//可以直接写字符串
}
