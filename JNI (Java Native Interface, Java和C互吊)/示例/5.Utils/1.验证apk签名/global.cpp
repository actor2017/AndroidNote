#include <jni.h>
#include "com_kuchuanyun_cpptest_utils_Global.h"
#include <string.h>
#include "Log.c"

/**
 * Created by actor on 2018/11/13.
 * 1.存放一些保密的数据,比如"服务端IP","端口","接口地址","授权的key"等等.
 * 2.顺便 so里面验证 app的签名 来防止别人盗用so文件
 * 3.使用:
 *      1.先在Application里初始化:Global.jniInit(applicationContext, isDebugMode);
 *      2.再调用:Global.getString(Global.JNI_IP);
 */

static bool is_valid = false;//是否验证通过
static bool isDebugMode = false;//可以被jboolean赋值,是否是debug模式

typedef struct {
    char *key;
    char *value;
} value_string;//结构变量

static const value_string vals_message_type[] = {
        { "IP",        "127.0.0.1" },
        { "PORT",        "8080" },
        {"NULL", NULL}//用于循环判断
};

//本app的签名,只有和本签名一致的app 才会返回合法的 授权 key
const char* app_signature = "308201dd30820146020101300d06092a864886f70d010105050"
                            "030373116301406035504030c0d416e64726f69642044656275"
                            "673110300e060355040a0c07416e64726f6964310b300906035"
                            "5040613025553301e170d3137313130333032303833325a170d"
                            "3437313032373032303833325a30373116301406035504030c0"
                            "d416e64726f69642044656275673110300e060355040a0c0741"
                            "6e64726f6964310b300906035504061302555330819f300d060"
                            "92a864886f70d010101050003818d0030818902818100d17e0e"
                            "d107e542758342b45ca186c7a7e5980dcb982f38e6862221467"
                            "e1bcc652a4deabeea0360370dcad8bcce061bc662b943d4c042"
                            "3db9aeefeb61ff5d3590bfafafd27dc51caf59ea7af15c29a38"
                            "ee892e0aa58ee18d9fe4db612da8e3cea147382a4c8e91e0af8"
                            "f50b2c60d1962388146172d2bef8f8036e65c2ebc7234f02030"
                            "10001300d06092a864886f70d0101050500038181004df58f3d"
                            "19d34d28493d6310b1964d80fd16cfa12d855ddab5122c567d2"
                            "c8215d99a5ef4d55793a9b1591827f4661a44b24e15941a793c"
                            "84a2356c4608919043c1bf1e6e361b557ed42cf7d6e3ee477c1"
                            "aca93e57dde257c84998dfa3e4321f4ed76ea46b303d116b84d"
                            "e63577ab8b7730117a9c63fb7f0832e846d5167f4617";

JNIEXPORT void JNICALL
Java_com_kuchuanyun_cpptest_utils_Global_jniInit(JNIEnv *env, jclass type, jobject contextObject, jboolean isDebug) {
    isDebugMode = isDebug;
    jclass native_class = env->GetObjectClass(contextObject);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(contextObject, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(contextObject);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(contextObject, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray)signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char*)env->GetStringUTFChars(str,0);

    if(strcmp(c_msg,app_signature)==0)//签名一致  返回合法的 api key，否则返回错误
    {
//        return (env)->NewStringUTF(c_msg);
        is_valid = true;
    }else
    {
//        return (env)->NewStringUTF("error");
        is_valid = false;
    }
}

JNIEXPORT jstring JNICALL
Java_com_kuchuanyun_cpptest_utils_Global_getString(JNIEnv *env, jclass type, jstring key_) {
    if (key_ == NULL) return NULL;
    if (is_valid) {
        int i = 0;
        const char *str = env->GetStringUTFChars(key_, 0);//jstring --> char*
        while (vals_message_type[i].value != NULL && strcmp(vals_message_type[i].key, str) != 0) {
            i++;
        }
        return (env)->NewStringUTF(vals_message_type[i].value);
    } else return (env)->NewStringUTF("碑燞鶔t€sGVV鴩顊p9%z匌,砜?歧e?fpE?0涒鴿J慎蚾幘");//验证失败,返回乱码
}
