这里包括project的gradle 和 module的gradle.

Error:Failed to resolve: com.android.support:appcompat-v7:27.0.1
修改project的gradle,示例:
allprojects {
    repositories {
        google()	//https://dl.google.com/dl/android/maven2/
        jcenter()	//https://jcenter.bintray.com/
				比如:implementation "io.reactivex.rxjava3:rxjava:3.0.0-RC2"
					 https://jcenter.bintray.com/io/reactivex/rxjava2/rxjava/2.2.6/rxjava-2.2.6.jar
        maven { url "https://jitpack.io"}
//        mavenCentral()//https://oss.sonatype.org/content/repositories/releases/老版本Studio默认
//        maven { url "https://maven.google.com" }
//        maven { url "http://maven.aliyun.com/nexus/content/groups/public" }//阿里云仓库
		maven { url 'http://maven.aliyun.com/nexus/content/repositories/google' }
		maven { url 'http://maven.aliyun.com/nexus/content/repositories/jcenter'}
//        maven { url 'http://maven.oschina.net/content/groups/public/'}//oschian的镜像,已经不能用
}


问题应该是由于某些框架依赖于26版本,所以添加依赖的时候,最好不要用+代替版本号.
Error:(15, 21) No resource found that matches the given name: attr ‘android:keyboardNavigationCluster’.
修改module的gradle 
1.把compileSdkVersion改26 
2.buildToolsVersion改”26.0.1” 
3.compile ‘com.android.support:appcompat-v7:26.0.1’替换之前的版本


Error:Unable to find method 'com.android.build.gradle.tasks.factory.AndroidJavaCompile.setDependencyCacheDir(Ljava/io/File;)V'.
Re-download dependencies and sync project (requires network)
Stop Gradle build processes (requires restart)
找到项目\gradle\wrapper\gradle-wrapper.properties, 修改里面gradle-x.x-all.zip版本


莫名其妙的bug, AndroidStudio的Terminal中输入:
gradlew compileDebugJavaWithJavac

查看 AndroidManifest.xml 合并情况:
1.查看"清单文件合并情况.jpg"
  或者点击 清单文件下方的"Merged Manifest", 查看右上角

查看包依赖情况:
1.查看"2.查看项目依赖树(如果右侧没出来,在AllModules和app模块间多切换几次).jpg"
  或者 gradlew -q app:dependencies
