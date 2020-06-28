# FingerprintManager `(Google在Android6.0后支持指纹识别)`

### 指纹识别大致应用在几种场景
1. 系统解锁
2. 应用锁
3. 支付认证
4. 普通的登录认证

### 指纹识别需要手机硬件支持才能使用。核心的API 是FingerprintManager。主类依赖三个内部类:
1. FingerprintManager.AuthenticationResult 指纹识别结果封装，从回调接口里面会传回来
2. FingerprintManager.CryptoObject 指纹识别数据传输加密对象
3. FingerprintManager.AuthenticationCallback 指纹识别成功、失败、错误的回调接口。

FingerprintManager 提供3个方法
启动指纹识别
![](./2.png)
这里cancel参数是用来主动取消指纹识别的。handle是默认会在主线程运行。


...
https://www.jianshu.com/p/0404310b6570

### 遇到的坑:
1. Google官方支持指纹识别的标准接口是在`Android6.0`开始的，如果各个厂商都升级到6.0并且硬件上都给予支持，那么我们按照标准的指纹识别接口使用就可以了。
2. 如果在android6.0发布以后，手机厂商来不及升级，但是工程师们参考了官方指纹识别的代码，把代码移植到他们的6.0版本以下的系统，或者参照Google提供的接口自己实现了一套指纹识别机制，只是对开发者暴露的接口一样，这样就可以像使用标准接口一样使用，但是这种情况就难说了，实现不好的可能本身就有很多bug，适配起也比较麻烦，不过起码还是能用的。
3. 如果厂商在Google之前就已经做了指纹识别，那这种情况肯定不能使用官方标准接口，如果要适配这种设备，只能使用厂商提供的第三方指纹识别SDK。

所以建议 6.0及以上系统选择性屏蔽一些机型（有些厂商支持不好）
