https://github.com/getActivity/GsonFactory
Gson 解析容错框架，愿从此再无 Json 解析报错

dependencies {
    // Gson 解析容错：https://github.com/getActivity/GsonFactory
    implementation 'com.hjq.gson:factory:3.0'
    // Json 解析框架：https://github.com/google/gson
    implementation 'com.google.code.gson:gson:2.8.0'
}


//使用文档
// 获取单例的 Gson 对象（已处理容错）
Gson gson = GsonFactory.getSingletonGson();

// 创建一个 Gson 构建器（已处理容错）
GsonBuilder gsonBuilder = GsonFactory.createGsonBuilder();
