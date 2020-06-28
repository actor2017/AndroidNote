#include <jni.h>
#include <stdlib.h>

//=============C调用Java: 主要是利用反射,这样就能调用Java代码了,Java的Private方法也可以.==================
JNIEXPORT void JNICALL
Java_com_kuchuanyun_cpptest_CCallJava_callVoid(JNIEnv *env, jobject instance) {
    //一直写到class,不写方法名称
    jclass clazz = (*env)->FindClass(env, "com/kuchuanyun/cpptest/CCallJava");
    //参数3:方法名称,参4:该函数的签名:(String参数类型)V是返回类型
    jmethodID methodId = (*env)->GetMethodID(env, clazz, "calledByC", "(Ljava/lang/String;)V");
    jobject object = (*env)->AllocObject(env, clazz);//使用AllocObject方法,实例化该class对应的实例
    jstring stringUTF = (*env)->NewStringUTF(env, "Java中的方法被C调用了");
    (*env)->CallVoidMethod(env, object, methodId, stringUTF);//调用方法
}

/**
 * C调用Java的静态方法
 */
JNIEXPORT void JNICALL
Java_com_kuchuanyun_cpptest_CCallJava_staticMethodCalledVoid(JNIEnv *env, jclass type) {
    jclass clazz = (*env)->FindClass(env, "com/kuchuanyun/cpptest/CCallJava");
    jmethodID staticMethodId = (*env)->GetStaticMethodID(env, clazz, "StaticMethodCalledByC", "(I)V");
    //    jobject object = (*env)->AllocObject(env, clazz);//静态方法就不需要实例化对象
    (*env)->CallStaticVoidMethod(env, clazz, staticMethodId, rand());
}
