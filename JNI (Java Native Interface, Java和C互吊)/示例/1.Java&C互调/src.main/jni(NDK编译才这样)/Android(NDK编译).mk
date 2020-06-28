# jni文件夹下创建Android.mk文件
# 编译时找项目里jni文件夹
LOCAL_PATH:= $(call my-dir)

# 清空上一次编译的
include $(CLEAR_VARS)
# 日志猫:android/log.h
LOCAL_LDLIBS += -llog

# 指定生成的动态库的名称，示例生成动态库文件后，文件名为libhello.so
LOCAL_MODULE:= hello
# 要编译的C所在源文件
LOCAL_SRC_FILES:= hello.c world.c
# LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)  什么作用?
# LOCAL_EXPORT_LDLIBS := -llog -landroid    什么作用?
# The linker will strip this as "unused" since this is a static library, but we
# need to keep it around since it's the interface for JNI.
# LOCAL_EXPORT_LDFLAGS := -u ANativeActivity_onCreate   什么作用?

# 指定了生成库的类型：BUILD_SHARED_LIBRARY表示动态库.so    文件小
                    BUILD_STATIC_LIBRARY表示静态库.a     文件大,包含头文件.h等
include $(BUILD_SHARED_LIBRARY)

# NDK需要配置环境变量D:\AndroidStudioSDK\android-ndk-r16b,然后在.c文件手动编译:ndk-build