#include <jni.h>
#include "Log.c"

/**
 * jstring  :返回类型
 *           Java + 包名 + 调用类的名 + 方法名,中间用_隔开
 * JNIEnv*  :二级结构体(struct)指针,里面很多方便的Api
 * jobject  :C函数的调用者,MainActivity
 */
jstring Java_com_kuchuanyun_cpptest_JavaCallC_stringFromJNI(JNIEnv* env, jobject instance) {
        jstring hello = "Hello from C++";
    return (*env)->NewStringUTF(env, hello);
}

//JNIEXPORT:jni生成,可写可不写
//JNICALL   :表示用java本地函数调用这个方法,可写可不写
JNIEXPORT jint JNICALL
Java_com_kuchuanyun_cpptest_JavaCallC_add(JNIEnv* env, jobject instance, jint x, jint y){
    LOGD("x + y =%d\n", x+y);
    LOGI("也可以只写一个参数");
    LOGE("x + y =%s\n", "y+z");
    return x+y;
}

JNIEXPORT void JNICALL
Java_com_kuchuanyun_cpptest_JavaCallC_add10(JNIEnv* env, jobject instance, jintArray arr_) {
    //1. 获取数组的大小
    //  jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jsize length = (*env)->GetArrayLength(env, arr_);

    //2. 获取数组首元素的地址
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
    jint* address = (*env)->GetIntArrayElements(env, arr_, JNI_FALSE);//代码是否复制一份,false表示不复制,直接使用java数组的内存地址

    //3. 给每项元素+10
//    int i;
    for(int i = 0; i<length; i++){
        *(address+i) += 10;
    }
    /**
     * 释放本地数组内存
     * 0表示将值修改到java数组中,然后释放本地数组,这个参数还有两个可选值:
     *      JNI_COMMIT: 修改值到java数组，但是不释放本地数组内存
     *      JNI_ABORT: 不修改值到java数组，但是会释放本地数组内存
     */
    (*env)->ReleaseIntArrayElements(env, arr_, address, 0);//关键代码啊,否则数组的值不会改变!!!
}

