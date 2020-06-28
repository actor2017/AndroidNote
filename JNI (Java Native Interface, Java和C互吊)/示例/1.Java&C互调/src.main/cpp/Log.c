#include <android/log.h>
#define LOG_TAG "System.out"//设置TAG
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

/**
 * 日志库,定义一个Log.c文件,然后在需要用日志的地方引入#include "Log.c"即可,注意本库要加入CMakeLists.txt
 * 示例使用:
 * LOGD("Example........");//debug级别日志
 * LOGI("value=%d\n", x+y);//info级别日志
 * LOGE("x+y=%s\n", "y+z");//error级别日志
 */