一个项目如何编译多个不同签名、包名、资源等，的apk？.html
https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650826652&idx=1&sn=8bb07e42bcfde541d3477ce07a4f41a2&chksm=80b7b302b7c03a14b95e8ebddd929e5dfd7706292ce62c211c6c6e70f71538889954a1b18443&mpshare=1&scene=24&srcid=1115ZNwqc2f1FThe8oCCEhUu#rd


//缺点：
1)每生成一个渠道包。都要又一次执行一遍构建流程，效率太低，仅仅适用于渠道较少的场景。
2)Gradle会为每一个渠道包生成一个不同的BuildConfig.java类，记录渠道信息，导致每一个渠道包的DEX的CRC值都不同。普通情况下，这是没有影响的。可是假设你使用了微信的Tinker热补丁方案，那么就须要为不同的渠道包打不同的补丁，这全然是不能够接受的。（由于Tinker是通过对照基础包APK和新包APK生成差分补丁，然后再把补丁和基础包APK一起合成新包APK。这就要求用于生成差分补丁的基础包DEX和用于合成新包的基础包DEX是全然一致的。即：每一个基础渠道包的DEX文件是全然一致的，不然就会合成失败）

<!--1.AndroidManifest的<Application>中,友盟多渠道打包-->
<!--@mipmap/ic_launcher,@string/app_name-->
    <application
        android:icon="${app_icon}"
        android:label="${app_name}">

        <!--友盟多渠道打包-->
        <meta-data
            android:name="UMENG_CHANNEL"
            android:value="${UMENG_CHANNEL_VALUE}"/><!--baidu, xiaomi...-->

    /**
     * 2.1.module/build.gradle/android{}中,productFlavors:是android节点的一个自节点。
     * 你需要打什么渠道的包,就在这里按umeng的要求用渠道名给"清单文件"中UMENG_CHANNEL_VALUE赋值
     */
    productFlavors {//android{ 节点内, 产品的特性
        baidu{
            applicationId 'com.google.package.baidu'             //自定义包名
			versionCode 32										//可不配置
			versionName '1.3.2'									//可不配置
            manifestPlaceholders = [app_name: "@string/app_name_baidu",//每个渠道包的应用名都不一样
                                    app_icon: "@mipmap/icon_baidu",//每个渠道包的图标都不一样
                                    UMENG_CHANNEL_VALUE: "baidu"]//替换清单文件中的值
			buildConfigField("String", "APP_DOMAIN", '"http://183.64.5.22:8546"')//内网
        }
        xiaomi{
            manifestPlaceholders=[UMENG_CHANNEL_VALUE: "xiaomi"]
        }

    //2.2.上面写法太麻烦,每个渠道就是flavor的名称不同,优化如下:
    productFlavors {//android{ 节点内, 产品的特性
        baidu{}
        xiaomi{}
    }
    //productFlavors.all写在上面个标签productFlavors{}里也是可以的
    productFlavors.all { flavor ->
        flavor.manifestPlaceholders = [app_name: "@string/app_name_${name}",//app_name_baidu
                                       app_icon: "@mipmap/icon_${name}",//icon_baidu
                                       UMENG_CHANNEL_VALUE: name]
    }

    //3.1.修改apk生成名称(没必要可不写)
    //Gradle版本3.0以下可以这样配置,把生成的apk加上版本名:app-xiaomi-release.apk --> app-xiaomi-release-1.0.apk
    applicationVariants.all { variant ->
        variant.outputs.each { output ->
            def outputFile = output.outputFile
            if (outputFile != null && outputFile.name.endsWith('.apk')) {
                def fileName = outputFile.name.replace(".apk", "-${defaultConfig.versionName}.apk")
                output.outputFile = new File(outputFile.parent, fileName)
            }
        }
    }

    //3.2.如果Gradle版本在3及以上(没必要可不写)
    //如果报错误:Error:All flavors must now belong to a named flavor dimension.Learn more at https://d.android.com/r/tools/flavorDimensions-missing-error-message.html,那么在defaultConfig {}标签里加上:flavorDimensions "versionCode"//flavor dimension它的维度就是该版本号,这样维度就是都是统一的了
    applicationVariants.all { variant ->    //批量修改Apk名字
        variant.outputs.all { output ->
            if (!variant.buildType.isDebuggable()) {
                //获取签名的名字 variant.signingConfig.name
                //要被替换的源字符串
//                def sourceFile = "-${variant.flavorName}-${variant.buildType.name}"//baidu/xiaomi-release
				//def date = new Date().format("yyyyMMdd" , TimeZone.getTimeZone("GMT+08"))//20200326
                def sourceFile = ".apk"
                //替换的字符串 //输出apk名称为：渠道名_版本名_时间.apk
//                def replaceFile = "${variant.productFlavors[0].name}_V${variant.versionName}_${variant.flavorName}_${variant.buildType.name}"
                def replaceFile = "${variant.flavorName}_${variant.versionName}_${versionName}_${releaseTime()}.apk"
                outputFileName = output.outputFile.name.replace(sourceFile, replaceFile)
            }
			if(variant.buildType.name.equals('debug')) {
				//
			}
			if (variant.flavorName == 'baidu') {
				buildConfigField("String", "DNS", "\"http://119.29.29.29\"")
			} else if (variant.flavorName == 'xiaomi') {
				buildConfigField("String", "DNS", "\"http://8.8.8.8\"")
			}
        }
    }
}
//获取日期(没必要可不写)
def releaseTime() {
    return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
}

4.代码中我们可以通过读取mate-data信息来获取渠道
    private String getChannel() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }
