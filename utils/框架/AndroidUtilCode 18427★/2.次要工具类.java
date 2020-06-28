https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/README-CN.md	//次要工具类
从下面选择拷贝你需要用到的类到你项目中即可。

APIs
应用商店相关 -> AppStoreUtils.java -> Demo
getAppStoreIntent: 获取跳转应用商店意图

剪贴板相关 -> ClipboardUtils.java -> Test
copyText  : 复制文本到剪贴板
getText   : 获取剪贴板的文本
copyUri   : 复制 uri 到剪贴板
getUri    : 获取剪贴板的 uri
copyIntent: 复制意图到剪贴板
getIntent : 获取剪贴板的意图

坐标转换相关 -> CoordinateUtils.java -> Test
bd09ToGcj02 : BD09 坐标转 GCJ02 坐标
gcj02ToBd09 : GCJ02 坐标转 BD09 坐标
gcj02ToWGS84: GCJ02 坐标转 WGS84 坐标
wgs84ToGcj02: WGS84 坐标转 GCJ02 坐标
bd09ToWGS84 : BD09 坐标转 WGS84 坐标
wgs84ToBd09 : WGS84 坐标转 BD09 坐标

国家相关 -> CountryUtils.java -> Demo
getCountryCodeBySim     : 根据 Sim 卡获取国家码
getCountryCodeByLanguage: 根据系统语言获取国家码
getCountryBySim         : 根据 Sim 卡获取国家
getCountryByLanguage    : 根据系统语言获取国家

危险相关 -> DangerousUtils.java -> Demo
installAppSilent    : 静默安装 App
uninstallAppSilent  : 静默卸载 App
shutdown            : 关机
reboot              : 重启
reboot2Recovery     : 重启到 recovery
reboot2Bootloader   : 重启到 bootloader
setMobileDataEnabled: 打开或关闭移动数据
sendSmsSilent       : 发送短信

定位相关 -> LocationUtils.java -> Demo
isGpsEnabled     : 判断 Gps 是否可用
isLocationEnabled: 判断定位是否可用
openGpsSettings  : 打开 Gps 设置界面
register         : 注册
unregister       : 注销
getAddress       : 根据经纬度获取地理位置
getCountryName   : 根据经纬度获取所在国家
getLocality      : 根据经纬度获取所在地
getStreet        : 根据经纬度获取所在街道
isBetterLocation : 是否更好的位置
isSameProvider   : 是否相同的提供者

拼音相关 -> PinyinUtils.java -> Demo
ccs2Pinyin           : 汉字转拼音
ccs2Pinyin           : 汉字转拼音
getPinyinFirstLetter : 获取第一个汉字首字母
getPinyinFirstLetters: 获取所有汉字的首字母
getSurnamePinyin     : 根据名字获取姓氏的拼音
getSurnameFirstLetter: 根据名字获取姓氏的首字母










