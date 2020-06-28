https://github.com/Tencent/GT
https://github.com/Tencent/GT/tree/master/android
https://gt.qq.com/
https://gt.qq.com/docs/a/APIUserGuide4Android.pdf	//文档(照文档抄代码)

https://blog.csdn.net/huxp370/article/details/80655322	//原创 GT性能测试Android版使用说明

github下载目录说明:
GT\android\GT_APP: 就是"GT"这个鸟软件
GT\android\GT_SDK: sdk源码
GT\android\GTDemo: 集成示例




GT（随身调）是APP的随身调测平台，它是直接运行在手机上的“集成调测环境”(IDTE, Integrated Debug Environment)。利用GT，仅凭一部手机，无需连接电脑，您即可对APP进行快速的性能测试(CPU、内存、流量、电量、帧率/流畅度等等)、开发日志的查看、Crash日志查看、网络数据包的抓取、APP内部参数的调试、真机代码耗时统计等。如果您觉得GT提供的功能还不够满足您的需要，您还可以利用GT提供的基础API自行开发有特殊功能的GT插件，帮助您解决更加复杂的APP调试问题。

GT支持iOS和Android两个手机平台，其中：
iOS版是一个Framework包，必须嵌入APP工程，编译出带GT的APP才能使用；iPhone和iPad应用都能支持。
Android版由一个可直接安装的GT控制台APP和GT SDK组成，GT控制台可以独立安装使用，SDK需嵌入被调测的应用、并利用GT控制台进行信息展示和参数修改。


一、GT3.1相对于2.x版本的变化
2.x的版本测试流畅值需要root手机，这一点在6.x之后的手机上越来越难。3.1采用了在被测应用内嵌sdk的方式来获取流畅度，因此3.1版本的使用必须集成SDK，不再支持独立使用 ；

GT3.1引入了hook能力，可以获取更丰富的应用信息，例如页面加载速度，卡顿代码调用栈、IO使用情况等等；

3.1直接以测试报告的形式图文展示所有性能数据；

新增了卡顿代码调用栈、页面加载速度、页面布局渲染速度、IO使用情况、分线程CPU时间片统计等多个纬度的性能数据；


1.添加依赖
//https://github.com/Tencent/GT/tree/master
implementation 'com.tencent.wstt.gt:gt-sdk:3.1.0'

2.注意事项
2.1.引入的sdk包含so库，目前支持的abi有armeabi, armeabi-v7a, arm64-v8a, 和x86。若工程有自编译so库，或者引入其它第三方so库，请注意匹配相应的so库路径；
    defaultConfig {
        ndk{
			//gt-sdk目前支持的abi有armeabi, armeabi-v7a, arm64-v8a, 和x86
            abiFilters "armeabi", "armeabi-v7a", "x86","arm64-v8a"
        }
    }
	
2.2.GT目前最低支持Android 5.0(API level 21)。

3."云微配" 项目示例
    1. Application 的 onCreate 方法中
        GT.connect(this, new AbsGTParaLoader() {
            @Override
            public void loadInParas(InParaManager im) {
                //注册输入参数, 将在GT控制台上显示
                // 定义入参：变量名、缩写名、入参默认值及备选值
                im.register("pkPlan", "PKPL","plan2","originalPlan", "plan1");
                im.register("超时时间", "RTO", "5", "2", "1","3");
                im.register("segmentSize", "SS", "2048", "1024");

                //定义默认显示在GT悬浮窗的3个输入参数
                // 启动时默认放到GT控制台悬浮窗中展示的入参，如超过三个，只显示前三个
                im.defaultInParasInAC("pkPlan", "超时时间");
            }

            @Override
            public void loadOutParas(OutParaManager om) {
                //注册输出参数, 将在GT控制台上显示
                // 定义出参：变量名、缩写名
                om.register("NetType", "NTPE");
                om.register("NetSpeed", "NSPD");
                om.register("SendFileSize", "SFS");
                om.register("发送成功率", "SSR");
                om.register("接收成功率", "RSR");

                // 启动时默认放到GT控制台悬浮窗中展示的出参，如超过三个，只显示前三个
                om.defaultOutParasInAC("发送成功率", "NetType", "SendFileSize");
            }
        });
	2.MainActivity 的 onDestroy 方法中
	GT.disconnect(this);


三、测试和查看报告：
① 测试流程：
安装GT工具

在GT工具中选取被测应用(必须引入GT SDK， 否则获取不到测试数据)，并点击“开始”，开始对被测应用的测试；此时可以在GT中打开悬浮窗，查看被测应用实时数据；

测试完成后，在GT工具中点击“导出数据”，然后选取导出方式(从3.1开始，可以选择导出到微信还是本地目录/sdcard/GTRData)，然后选择要导出的数据，选择完后，数据会保存到/sdcard/GTRData或者微信中。

将导出的文件data.js复制到GT/GT_Report/data目录下，替换原有的data.js即可

双击”GT/GT_Report/result.html”即可查看报告

② 注意事项：
测试期间不要杀死被测应用，重新启动应用相当于开始新的测试；

每次点击间隔尽量大于5秒；

尽可能遍历应用所有应用的所有功能和页面；


三、混淆：
若被测应用需要混淆，请将SDK工程gtrsdk模块下proguard-rules.pro文件中的相关内容拷贝到您工程使用的proguard文件中。












