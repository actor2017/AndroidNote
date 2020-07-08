﻿apply plugin: 'java-library'			//java的Library
apply plugin: 'com.android.library'		//android的Library (需要AndroidManifest.xml)
apply plugin: 'com.android.application'	//android工程

allprojects {
	//父工程的配置, 子工程能用, javaEE项目中可这样配置
	group ..., version ..., apply ..., sourceCompatibility = 1.8, repositories {}, dependencies {}
}

android {
    defaultConfig {
        multiDexEnabled true//1. ★★解决安卓的方法数量不够，只能6K(6x1024)个方法的错误.★★
        versionCode 2018050901//int最大值2³¹-1=2147483647
    
		//buildConfigField 用于给 "BuildConfig" 文件添加一个字段(可用于 defaultConfig & 渠道包字段 & buildTypes & others)
		//library 的 BuildConfig 构建永远是使用release模式(library中的BuildConfig.DEBUG不可用)//https://www.jianshu.com/p/e7b486384d71
		buildConfigField("String","testKey","\"testValue\"")
		buildConfigField("int","testInt","1")
		buildConfigField("String[]", "DNSS", "{\"http://119.29.29.29\",\"http://8.8.8.8\",\"http://114.114.114.114\"}")
		
		//https://segmentfault.com/a/1190000004338384
		//用来跑我们所写的所有的测试用例的。当我们采用test的模式来构建工程时，这个Runner便会自动为我们执行所有的的测试用例，并且返回相应的测试结果。
		testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
		
        //在 defaultConfig { 中添加ndk
        ndk {
            //"armeabi", "armeabi-v7a", "x86","arm64-v8a","x86_64", 'mips', 'mips64'(最多前面3中就差不多了, 第3个其实也相对不多)
            abiFilters "armeabi"
        }

		//清单文件替换, 添加在 defaultConfig { 中
		manifestPlaceholders = [
                JPUSH_PKGNAME: applicationId,	//清单文件中获取: android:name="${JPUSH_PKGNAME}"
                JPUSH_APPKEY : "59887eb5f2842968be241646", //JPush 上注册的包名对应的 Appkey.
                JPUSH_CHANNEL: "developer-default", //暂时填写默认值即可.

        ]
    }
    signingConfigs {//签名配置,写在 buildTypes 前面
        debug {
            keyAlias 'actor'
            keyPassword '123456'
            storeFile file('actorKey.jks')
            storePassword '123456'
        }
        release {
            //melonspirit
            keyAlias 'key0'
            keyPassword '123456'
            storeFile file('key.jks')//放在gradle同一文件夹
            storePassword '123456'
        }
    }
    buildTypes {
        release {
            minifyEnabled true// 混淆
            zipAlignEnabled true// Zipalign优化
            shrinkResources false// 移除无用的resource文件,最好不移除,比如这次出错:Error inflating class android.support.design.widget.TextInputLayout
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.releaseConfig//使用下面的正式签名配置
			
			//buildConfigField 用于给 "BuildConfig" 文件添加一个字段
            //三个参数:1.要定义的常量的类型 2.该常量的命名 3.该常量的值
            buildConfigField("String", "HTTP_BASE", '"https://www.baidu.com/api/release/"')
            buildConfigField("String","HAHA","\"haahahah\"")
			
			//AppKey 取值定义在gradle.properties文件中: AppKey=123456
			buildConfigField("String","KEY","\"${AppKey}\"")
        }
        debug {
            signingConfig signingConfigs.debug
            debuggable true
            zipAlignEnabled false
            signingConfig signingConfigs.releaseConfig//使用下面的正式签名配置
			
			buildConfigField "boolean", "LOG_DEBUG", "true"
			buildConfigField("String", "HTTP_BASE", '"https://www.baidu.com/api/debug"')
			buildConfigField("String","HAHA","\"haahahah\"")
        }
    }
    repositories {
        flatDir {//也可写在Project的gradle里.   也可以写在 app 里, 和 dependencies{} 同级(如果这样写就不要上方 repositories{} 这一句)
            dirs 'libs', '../easeui/libs'//第2参数:引用某个module下的.aar文件,如果引用module下的.jar文件不用写第2个参数
        }
    }
    //添加so库, android内(不添加的话, 不会打包进apk)
	//如果是在eclipse中，需要放到libs下对应库的目录。
	//如果是在Android Studio中，则会默认匹配main下的jniLibs目录，如果没有目录需要自己手动创建。并且库的名称也不能随便更改。
	//但是这里会有一个问题，就是如果使用的是AndroidStudio，但是想用libs下的库，还需要手动去指定库的位置
    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }

	/**
	 * Android Studio 引入Lambda表达式:
	 * 1.确保jdk 8+
	 *
	 * 2.Project 的build.gradle / dependencies节点添加(可以不添加)
	 *   //classpath 'me.tatarka:gradle-retrolambda:3.2.5'
	 *
	 * 3.Module 的build.gradle根节点添加(可以不添加)
	 * //apply plugin: 'me.tatarka.retrolambda'
	 *
	 * 4.告诉编译器这是一个函数式接口,这个函数中只有一个抽象方法
	 * @FunctionalInterface
	 * public interface Runnable {
	 *     public abstract void run();
	 * }
	 */
    compileOptions {//Lambda表达式, 在android节点内添加
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

https://blog.csdn.net/wangliblog/article/details/81366095
https://www.jianshu.com/p/825004db000c
之前				现在
					implementation	//编译时仅对当前的Module提供接口,运行时这个依赖对所有模块可见,会参与编译和打包(加快编译速度。2. 隐藏对外不必要的接口)
compile				api				//会参与编译和打包, 无法隐藏自己使用的依赖
provided			compileOnly		//只在编译的时候有效， 不参与打包(编译时不得不需要的情况, 可以避免支持包版本冲突)

apk					runtimeOnly				//只在打包的时候有效，编译不参与(很少用)
testCompile			tesImplementation		//在单元测试代码的编译(src/test) 和 打包测试apk的时候有效
debugCompile		debugImplementation		// 只在 "debug 模式的编译" 和 "最终的 debug apk 打包" 时有效
releaseCompile		releaseImplementation	//仅针对 "Release 模式的编译" 和 "最终的 Release apk 打包"

//待考证
androidTestCompile	androidTestImplementation	//在单元测试代码的编译(src/androidTest) 和 打包测试apk的时候有效?



dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])//app/libs
	testImplementation 'junit:junit:4.12'					//Java单元测试: ExampleUnitTest.java
	androidTestImplementation 'com.android.support.test:runner:1.0.2'	//设备单元测试, 将在Android设备上运行: ExampleInstrumentedTest.java
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
	
    //2. 64k问题
	implementation 'com.android.support:multidex:1.0.3'//MyApplication extends MultiDexApplication, 或者不继承, 在自己Application中: MultiDex.install(this);
    implementation ('com.github.bumptech.glide:glide:4.7.1') {
        exclude group: 'com.android.support', module: 'support-v4'
    }
	
    implementation ('com.github.actor20170211030627:MyAndroidFrameWork:1.2.5') {//★★★示例★★★
        exclude group: 'com.github.bumptech.glide'
        exclude group: 'com.google.code.gson', module: 'gson'
    }
	
    api files('libs/BaiduLBS_Android.jar')//如果这个 .jar/.aar 在module中,想让引入的project也使用的话, 用api开头
	implementation project(path: ':base')//依赖其它模块(base模块必须是library)
	
    compileOnly  'com.android.support:recyclerview-v7:28.0.0'//见BaseRecyclerViewAdapterHelper
}





