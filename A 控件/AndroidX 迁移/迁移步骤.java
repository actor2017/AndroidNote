https://www.jianshu.com/p/41de8689615d

1.project的 gradle.properties 中配置:

#当前项目启用 AndroidX
android.useAndroidX=true
#将依赖包也迁移到AndroidX
android.enableJetifier=true


2.Refactor > Migrate to AndroidX
在执行该操作时会提醒我们是否将当前项目打包备份。如果你提前已经做好了备份，可以忽略；
如果没有备份，需要打钩。

3 迁移后续
3.1 手动修改错误包名
由于 Migrate to AndroidX 执行之后，部分控件的包名/路径名转换的有问题，所以还需要我们手动调整（包括修改 xml 布局文件和 .java 或 .kt 文件）。

5.support, compat 包和 androidx 对照:
https://www.jianshu.com/p/1466ebefe4d0