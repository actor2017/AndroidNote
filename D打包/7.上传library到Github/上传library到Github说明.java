1.注册Github账号并到https://jitpack.io/用github账号登录(图1)

2.下载Github Desktop(图2),并设置ignore:
Repository-->Repository settings...-->Ignored files,示例:
.gradle/
.idea/
.svn/
app/build/generated/
app/build/intermediates/
app/build/tmp/
*.iml
local.properties

在app/.gitignore中(要传示例apk, 所以这儿没屏蔽output文件夹):
/build/generated/
/build/intermediates/
/build/tmp/

如果先上传再添加忽略, 那么修改.gitignore文件无效, 可本地物理删除, 再提交 就可以了.


3.把library(可包含sample)上传到Github网址,可以新建文件夹captures,把效果图片&其它文件放里面
  README.md中资源地址:
    图片:<img src="captures/chatlayout.png(就写本地地址)" width=35%></img>
    其它:<a href="https://github.com/actor20170211030627/ChatLayout/raw/master/app/build/outputs/apk/debug/app-debug.apk">download apk</a> or scan qrcode:  <br/>
<img src="captures/qrcode.png" width=35%></img>

4.进入自己library的网页,点击"0 release"(图4)

5.点击create a new release,填写一下信息:(图5)
  1.Tag version		示例:1.0
  2.Release title		示例:1.0
  3.Describe this release

6.进入https://jitpack.io/,登录或刷新,
  点击自己的library,然后点击Git it,会自动打包(图6),打包后有日志, 日志最后有打包的地址.
打包后下载地址示例:
https://jitpack.io/com/github/actor20170211030627/MyAndroidFrameWork/1.0/MyAndroidFrameWork-1.0.pom
如果这个pom文件为空, 说明打包失败...


7.需要注意的是:需要在library的build.gradle中添加如下代码,否则别人依赖这个项目会没注释
android {
    //以下为配置library注释在打包jar后保留:https://www.jianshu.com/p/b04ef4029b90
    // 打包源码jar
    task sourcesJar(type: Jar) {
        from android.sourceSets.main.java.srcDirs
        classifier = 'sources'
    }
    task javadoc(type: Javadoc) {
        failOnError false
        source = android.sourceSets.main.java.sourceFiles
        classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
        classpath += configurations.compile
    }
    // 打包文档jar
    task javadocJar(type: Jar, dependsOn: javadoc) {
        classifier = 'javadoc'
        from javadoc.destinationDir
    }
	//打包成 xxx.jar
    task makeJar(type: Jar) {
        baseName 'sdk'//指定生成的jar名
        from('build/intermediates/classes/debug/org/cmdmac/cloud/pluginsdk/')//从哪里打包class文件
        into('org/cmdmac/cloud/pluginsdk/')//打包到jar后的目录结构
        //去掉不需要打包的目录和文件('test/', 'BuildConfig.class', 'R.class')
        exclude('DaoMaster.class', 'DaoSession.class')
        exclude{ it.name.startsWith('R$');}//去掉R$开头的文件
    }
    artifacts {
        archives sourcesJar
        archives javadocJar
        //archives makeJar
    }
}
