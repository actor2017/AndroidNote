ctrl + /	:★加注释, 在xml中会跑到这行开头...
ctrl + Shift + /	:多行注释, 在xml中不会跑到这行开头
ctrl + d	:★复制一行
ctrl + f	:★查找
ctrl + g	:跳到某一行	Go to Line
ctrl + h	:类的继承体系(也可以点右上角Hierarchy窗口)		ctrl + t
ctrl + I	:Select Methods to Implement(重写接口未重写的方法)
ctrl + N	:查找某个类,源码
ctrl + O	:★查看本类中可以重写的方法	例:重写handleMessage方法★
ctrl + p	:★可以看()传什么参数★
ctrl + Q	:文档提示
ctrl + r	:替换
ctrl + x	:★剪切一行(不选中)
ctrl + y	:删除一行		ctrl + d
ctrl + z	:★撤销
ctrl + alt + 	:提示			ctrl + /
ctrl + Enter	:普通换行,但光标不换行(没什么卵用)
ctrl + home	:跳到最前面
ctrl + end	:跳到最后面
ctrl + 左键	:★点击tabs = "Show in Explore"★
ctrl + F11	:★添加书签,并添加Bookmark Mnemonic(书签助记符)★, 或者按F11也可以
ctrl + F12	:★快速查找/定位类中某个方法或属性, 根据名称排序, 使用"alt + 7" 效果更好★
ctrl + alt + ←	:上一步
ctrl + alt + →	:下一步
ctrl + alt + b	:查看继承关系,或者谁实现了这个方法
ctrl + alt + c	:Extract Constant(提取常量)
ctrl + alt + Ent:跳到上一行,Shift + Enter:跳到下一行
ctrl + alt + f	:★Extract Field(提取字段) 生成全局变量		ctrl + 1
ctrl + alt + l	:★代码格式化		ctrl + alt + f★
ctrl + alt + m	:Extract Variable(提取方法) (右击→Refactor→Extract→Method...)	eclipse:alt + Shift + m
ctrl + alt + p	:Extract Parameter(提取参数)
ctrl + alt + t	:自动try-catch
ctrl + alt + v	:★Extract Variable(提取变量) 生成局部变量(Eclipse的ctrl + 1),如果按键失灵,检查是不是有道词典占用了

ctrl +Shift+空格:自动补全(StringBuffer buffer = new )
ctrl +Shift + a :万能命令行, 输入某些命令, 快速多某些事情. 还可以快速使用某个插件??
				 比如搜索: set background image, 设置背景图片

ctrl + e	:★最近打开的文件★
ctrl + n	:★查找Class★
ctrl +Shift + f :全局查找
ctrl +Shift + g :显示/隐藏Code Glance(https://plugins.jetbrains.com/plugin/7275-codeglance)
ctrl +Shift + n :搜索文件,快速跳到这个文件(.java, .xml, .gradle...)
ctrl +Shift + r :全局查找替换
ctrl +shift + U :★大小写转换快捷键★
ctrl +Shift+  V :剪贴板(复制过的历史内容)
ctrl +Shift+↑↓:移动代码(可多行)
alt  +Shift+↑↓:移动代码(可多行)

alt+鼠标↑↓拖动:★可多行同时选中/输入★
alt + F7	:或 alt + 3, 查看哪些地方在使用
alt + 7		:★★打开左侧7:Structure, 查看全部方法★★
alt + 9		:打开下方9:Version Control版本控制(非小键盘的9)
alt + R		:点击Convert Anonymous to Inner...抽取内部类
alt + enter	:★提示★,报错后自动修复		ctrl + 1
alt + ←	:切换左边一个tab
alt + →	:切换右边一个tab
alt + ins	:方法重写	重写toString等
ctrl+ Shift+ z  :重做		ctrl + z:撤销
alt + Shift+↑↓:移动代码(可多行)
alt + Shift+ ins:开启/关闭列选择模式	★解决光标不跳到每行的最后,而是点哪儿就在哪儿的问题★
ctrl +Shift+↑↓:移动代码(可多行)

Shift + F6	:改名			F2
Shift + F11	:查看书签列表
Shift + Tab	:往左跳
Shift + Enter	:★跳到下一行,ctrl + alt + Ent:跳到上一行★
Shift + Shift	:★★★Shift双击,查找全部,包括:Recent&Class&File&Symbol...★★★

fori		:for循环		Settings,Live Templates里面可更改快捷方式(if,for,fbi)
arr.for		:★遍历数组,集合,Map...★
fbc		:findViewById
psf		:public static final
psfs		:★public static final String★
sout		:★System.out.println();★
tag		:private static final String TAG = "MainActivity";
选中方法体→右击Refactor→Extract→Method...	抽取方法
右击标签(例:TextView)→Reflect→Extract→Style...把标签抽取成style
选中控件(要抽取出来的)→Reflect→Extract→Layout..把view抽取出来成layout
File→New→Import Project...			:打开新工程

修改 描述,公司名称,作者,时间 等:
Setting-->Editor-->File and Code Templates-->Includes-->File Header

//TODO	: 测完之后一定要改回来 待实现
//XXX	: 待优化
//FIXME	: xxxxx 待修复

versionName:版本名	1.0.0	1.10	新年特惠版	双11剁手版
1.0.0		第一个版本
1.0.1		修复小bug
1.1.0	1.2.0	添加某个功能
2.0.0		重大更新,颠覆式更新

在build.gradle最后加一行:compile 'com.zhy:okhttputils:2.6.2'
10.0.3.2	:genymotion访问PC服务器的默认的IP地址
10.0.2.2	:原生模拟器访问PC服务器的默认的IP地址

右键-->U...	查看谁实现了本方法