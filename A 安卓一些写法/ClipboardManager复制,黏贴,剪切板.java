https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/README-CN.md
剪贴板相关 -> ClipboardUtils.java -> Test
copyText  : 复制文本到剪贴板
getText   : 获取剪贴板的文本
copyUri   : 复制 uri 到剪贴板
getUri    : 获取剪贴板的 uri
copyIntent: 复制意图到剪贴板
getIntent : 获取剪贴板的意图



Android应用开发之（通过ClipboardManager, ClipData进行复制粘贴）

在开发一些系统应用的时候，我们会用到Android的剪贴板功能，比如将文本文件、或者其他格式的内容复制到剪贴板或者从剪贴板获取数据等操作。Android平台中每个常规的应用运行在自己的进程空间中，相对于Win32而言Android上之间的进程间传递主要有IPC、剪切板。当然今天我们说下最简单的ClipboardManager。使用剪切板可以直接实现数据的传输。整个实现比较简单，注意剪切板中的类型判断。
使用起来很简单，系统给我们提供了很方便的接口，如下文本信息复制如下所示：
//获取剪贴板管理服务
ClipboardManager cm =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//将文本数据复制到剪贴板
cm.setText(message);
//读取剪贴板数据
cm.getText();