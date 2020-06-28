1.添加系统变量
ANDROID_ADB
D:\AndroidStudioSDK\platform-tools

2.把系统变量添加到Path中
%ANDROID_ADB%


adb help				//查看adb命令帮助信息
adb devices				//显示当前运行的全部设备
adb install 包名		//安装
adb install xxx.apk		//直接安装
adb shell				//进入的shell模式
adb shell netcfg		//查看设备IP地址(前提:设备已经和PC建立了usb连接)
adb shell pm clear 包名	//清除缓存
adb uninstall 包名		//卸载apk


//卸载系统app, 已测试 https://www.toutiao.com/i6823525407623479811/
//vivo: 设置-更多设置-应用程序-全部-可看见已卸载的系统app, 卸载后注意备份, 重启看有没有什么问题!
1.adb shell						//如果出现 $ ,说明进入 shell 模式成功
2.pm uninstall -k --user 0 包名	//-k: 卸载后保留data & cacheDir
								//--user 0: Android 系统支持多个用户，默认用户只有一个，id=0

//导出系统app, 已测试
adb shell pm path 包名	//会输出安装包的APK路径: /system/app/ZKFramework/ZKFramework.apk
adb pull /system/app/ZKFramework/ZKFramework.apk F:\Users\actor\Desktop\	//导出到桌面


//安装系统app, 已测试
adb shell
adb push xxx.apk /system/app	//将app拖入命令窗口
adb push xxx.apk /system/priv-app//有的是这个目录
pm list packages | grep com.ibimuyu.lockscreen

//未测试
pm disable -k --user 0 packageName （禁用APP）
pm enable -k --user 0 packageName （恢复APP）

//未测试
adb connect xxx.xxx.xxx.xxx:5555	//通过wifi连接设备（比如：192.168.10.103:5555）
adb pull <remote> <local>		//获取模拟器中的文件
adb push <local> <remote>		//向模拟器中写文件

adb tcpip 5555				//手机设置5555接口?
android					//启动SDK，文档，实例下载管理器
adb install -r D://xxx/应用程序.apk	//安装应用程序

cd data/app
  rm apk包
  exit

adb logcat -s 标签名	//在命令行中查看LOG信息

启动Activity：
adb shell am start -n 包名/包名＋类名（-n 类名,-a action,-d date,-m MIME-TYPE,-c category,-e 扩展数据,等）。

获取设备的ID和序列号：
adb get-product 
adb get-serialno

访问数据库SQLite3
adb shell 
sqlite3

debug模式
adb logcat -s TAG:*


#cd system/sd/data //进入系统内指定文件夹 
#ls //列表显示当前文件夹内容 
#rm -r xxx //删除名字为xxx的文件夹及其里面的所有文件 
#rm xxx //删除文件xxx 
#rmdir xxx //删除xxx的文件夹


