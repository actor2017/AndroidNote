#include <jni.h>
#include <stdlib.h>
#include <unistd.h>

int isCheck = JNI_FALSE;//是否检测

//Java & C互调,开始检测锅炉压力,JNI中的C代码运行在主线程中
JNIEXPORT void JNICALL Java_com_kuchuanyun_cpptest_MainActivity_startMonitor(JNIEnv *env, jobject instance) {
    isCheck = JNI_TRUE;
    while (isCheck) {
        //1. 获取类的字节码文件
        jclass clazz = (*env)->FindClass(env, "com/kuchuanyun/cpptest/MainActivity");
        jobject object = (*env)->AllocObject(env, clazz);
        //2. 获取方法
        jmethodID methodId = (*env)->GetMethodID(env, clazz, "setPress", "(I)V");

        int press = getPress();
        //3. 调用方法
        (*env)->CallVoidMethod(env, object, methodId, press);
        //睡眠1秒
        sleep(1);//unistd.h
    }
}

//Java调C,停止检测
JNIEXPORT void JNICALL Java_com_kuchuanyun_cpptest_MainActivity_stopMonitor(JNIEnv *env, jobject instance) {
    isCheck = JNI_FALSE;
}

//返回锅炉压力值,取值范围是 0~ 100
int getPress() {
    //获取高低电压
    //高低电压转成二进制的数据
    //把二进制的值转成十进制的压力值
    int press = rand() % 101;//stdlib.h
    return press;
}
