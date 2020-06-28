https://github.com/Blankj/AndroidUtilCode
https://blankj.com/2016/07/31/android-utils-code/	����Ĺ���˵���б�

https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md	//��Ҫ������
https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/README-CN.md	//��Ҫ������

1.�������
implementation 'com.blankj:utilcode:1.25.9'
// if u use AndroidX, use the following
implementation 'com.blankj:utilcodex:1.25.9'

3.����Ҫ��ӻ���

4.����������
Warning:com.blankj.utilcode.util.PhoneUtils: can't find referenced method 'java.lang.String getImei()' in library class android.telephony.TelephonyManager
...
��ӻ���:
##-------Begin: proguard configuration for AndroidUtilCode-------
-dontwarn com.blankj.utilcode.**
##-------End: proguard configuration for AndroidUtilCode---------


AndroidUtilCode :
��һ��ǿ�����õİ�׿������⣬������ط�װ�˰�׿�����г��õĺ������������Ƶ�Demo�͵�Ԫ���ԣ��������װ�õ� APIs ���Դ����߿���Ч�ʣ��������Ҫ����������ģ�飬
��һ����������ģ�飺utilcode�����еĹ������ǿ����г��õ��ģ�
������ӹ�����ģ�飺subutil���������Ĺ����ಢ���Ǻܳ��ã����ĳ�����Ϊ�˷�ֹ���������ӷ�ס�
Activity ��� -> ActivityUtils.java
App ��� -> AppUtils.java
����� -> BarUtils.java
������� -> BrightnessUtils.java -> Demo
Bus ��� -> BusUtils.java -> README		https://github.com/Blankj/AndroidUtilCode/tree/master/plugin/bus-gradle-plugin
���̻������ -> CacheDiskUtils.java
����������� -> CacheDoubleUtils.java
�ڴ滺����� -> CacheMemoryUtils.java
������� -> CacheUtils.java(�ѹ�ʱ)
������ -> CleanUtils.java
�ر���� -> CloseUtils.java
ת����� -> ConvertUtils.java
������� -> CrashUtils.java
�豸��� -> DeviceUtils.java
�������� -> FlashlightUtils.java
���������� -> EncodeUtils.java
���ܽ������ -> EncryptUtils.java
�ļ���� -> FileIOUtils.java
�ļ���� -> FileUtils.java
Fragment ��� -> FragmentUtils.java
ͼƬ��� -> ImageUtils.java
��ͼ��� -> IntentUtils.java
������� -> KeyboardUtils.java
��־��� -> LogUtils.java
������� -> NetworkUtils.java
������� -> ObjectUtils.java
Ȩ����� -> PermissionUtils.java
�ֻ���� -> PhoneUtils.java
������� -> ProcessUtils.java
������� -> ReflectUtils.java
������� -> RegexUtils.java
��Դ��� -> ResourceUtils.java
��Ļ��� -> ScreenUtils.java
SD ����� -> SDCardUtils.java
������� -> ServiceUtils.java
Shell ��� -> ShellUtils.java
�ߴ���� -> SizeUtils.java
Snackbar ��� -> SnackbarUtils.java
SpannableString ��� -> SpanUtils.java(���ı�)
SP ��� -> SPUtils.java
�ַ������ -> StringUtils.java
�߳���� -> ThreadUtils.java
ʱ����� -> TimeUtils.java
��˾��� -> ToastUtils.java
URI ��� -> UriUtils.java
ѹ����� -> ZipUtils.java



Activity ��� -> ActivityUtils.java -> Demo

isActivityExists               : �ж� Activity �Ƿ����
startActivity                  : ���� Activity
startActivityForResult         : ���� Activity Ϊ���ؽ��
startActivities                : ������� Activity
startHomeActivity              : �ص�����
getActivityList                : ��ȡ Activity ջ����
getLauncherActivity            : ��ȡ������ Activity
getTopActivity                 : ��ȡջ�� Activity
isActivityExistsInStack        : �ж� Activity �Ƿ����ջ��
finishActivity                 : ���� Activity
finishToActivity               : ������ָ�� Activity
finishOtherActivities          : ���������������͵� Activity
finishAllActivities            : �������� Activity
finishAllActivitiesExceptNewest: ����������֮������� Activity
App ��� -> AppUtils.java -> Demo

registerAppStatusChangedListener  : ע�� App ǰ��̨�л�������
unregisterAppStatusChangedListener: ע�� App ǰ��̨�л�������
installApp                        : ��װ App��֧�� 8.0��
installAppSilent                  : ��Ĭ��װ App
uninstallApp                      : ж�� App
uninstallAppSilent                : ��Ĭж�� App
isAppInstalled                    : �ж� App �Ƿ�װ
isAppRoot                         : �ж� App �Ƿ��� root Ȩ��
isAppDebug                        : �ж� App �Ƿ��� Debug �汾
isAppSystem                       : �ж� App �Ƿ���ϵͳӦ��
isAppForeground                   : �ж� App �Ƿ���ǰ̨
launchApp                         : �� App
relaunchApp                       : ���� App
launchAppDetailsSettings          : �� App ��������
exitApp                           : �ر�Ӧ��
getAppIcon                        : ��ȡ App ͼ��
getAppPackageName                 : ��ȡ App ����
getAppName                        : ��ȡ App ����
getAppPath                        : ��ȡ App ·��
getAppVersionName                 : ��ȡ App �汾��
getAppVersionCode                 : ��ȡ App �汾��
getAppSignature                   : ��ȡ App ǩ��
getAppSignatureSHA1               : ��ȡӦ��ǩ���ĵ� SHA1 ֵ
getAppInfo                        : ��ȡ App ��Ϣ
getAppsInfo                       : ��ȡ�����Ѱ�װ App ��Ϣ
����� -> BarUtils.java -> Demo

getStatusBarHeight                   : ��ȡ״̬���߶ȣ�px��
setStatusBarVisibility               : ����״̬���Ƿ�ɼ�
isStatusBarVisible                   : �ж�״̬���Ƿ�ɼ�
setStatusBarLightMode                : ����״̬���Ƿ�Ϊǳɫģʽ
addMarginTopEqualStatusBarHeight     : Ϊ view ���� MarginTop Ϊ״̬���߶�
subtractMarginTopEqualStatusBarHeight: Ϊ view ���� MarginTop Ϊ״̬���߶�
setStatusBarColor                    : ����״̬����ɫ
setStatusBarAlpha                    : ����״̬��͸����
setStatusBarColor4Drawer             : Ϊ DrawerLayout ����״̬����ɫ
setStatusBarAlpha4Drawer             : Ϊ DrawerLayout ����״̬��͸����
getActionBarHeight                   : ��ȡ ActionBar �߶�
setNotificationBarVisibility         : ����֪ͨ���Ƿ�ɼ�
getNavBarHeight                      : ��ȡ�������߶�
setNavBarVisibility                  : ���õ������Ƿ�ɼ�
setNavBarImmersive                   : ���õ���������ʽ
setNavBarColor                       : ���õ�������ɫ
getNavBarColor                       : ��ȡ��������ɫ
isNavBarVisible                      : �жϵ������Ƿ�ɼ�
���̻������ -> CacheDiskUtils.java -> Test

getInstance             : ��ȡ����ʵ��
Instance.put            : ������д������
Instance.getBytes       : �����ж�ȡ�ֽ�����
Instance.getString      : �����ж�ȡ String
Instance.getJSONObject  : �����ж�ȡ JSONObject
Instance.getJSONArray   : �����ж�ȡ JSONArray
Instance.getBitmap      : �����ж�ȡ Bitmap
Instance.getDrawable    : �����ж�ȡ Drawable
Instance.getParcelable  : �����ж�ȡ Parcelable
Instance.getSerializable: �����ж�ȡ Serializable
Instance.getCacheSize   : ��ȡ�����С
Instance.getCacheCount  : ��ȡ�������
Instance.remove         : ���ݼ�ֵ�Ƴ�����
Instance.clear          : ������л���
����������� -> CacheDoubleUtils.java -> Test

getInstance                 : ��ȡ����ʵ��
Instance.put                : ������д������
Instance.getBytes           : �����ж�ȡ�ֽ�����
Instance.getString          : �����ж�ȡ String
Instance.getJSONObject      : �����ж�ȡ JSONObject
Instance.getJSONArray       : �����ж�ȡ JSONArray
Instance.getBitmap          : �����ж�ȡ Bitmap
Instance.getDrawable        : �����ж�ȡ Drawable
Instance.getParcelable      : �����ж�ȡ Parcelable
Instance.getSerializable    : �����ж�ȡ Serializable
Instance.getCacheDiskSize   : ��ȡ���̻����С
Instance.getCacheDiskCount  : ��ȡ���̻������
Instance.getCacheMemoryCount: ��ȡ�ڴ滺�����
Instance.remove             : ���ݼ�ֵ�Ƴ�����
Instance.clear              : ������л���
�ڴ滺����� -> CacheMemoryUtils.java -> Test

getInstance           : ��ȡ����ʵ��
Instance.put          : ������д������
Instance.get          : �����ж�ȡ�ֽ�����
Instance.getCacheCount: ��ȡ�������
Instance.remove       : ���ݼ�ֵ�Ƴ�����
Instance.clear        : ������л���
������� -> CacheUtils.java

getInstance             : ��ȡ����ʵ��
Instance.put            : ������д������
Instance.getBytes       : �����ж�ȡ�ֽ�����
Instance.getString      : �����ж�ȡ String
Instance.getJSONObject  : �����ж�ȡ JSONObject
Instance.getJSONArray   : �����ж�ȡ JSONArray
Instance.getBitmap      : �����ж�ȡ Bitmap
Instance.getDrawable    : �����ж�ȡ Drawable
Instance.getParcelable  : �����ж�ȡ Parcelable
Instance.getSerializable: �����ж�ȡ Serializable
Instance.getCacheSize   : ��ȡ�����С
Instance.getCacheCount  : ��ȡ�������
Instance.remove         : ���ݼ�ֵ�Ƴ�����
Instance.clear          : ������л���
������ -> CleanUtils.java -> Demo

cleanInternalCache   : ����ڲ�����
cleanInternalFiles   : ����ڲ��ļ�
cleanInternalDbs     : ����ڲ����ݿ�
cleanInternalDbByName: ��������������ݿ�
cleanInternalSp      : ����ڲ� SP
cleanExternalCache   : ����ⲿ����
cleanCustomDir       : ����Զ���Ŀ¼�µ��ļ�
�ر���� -> CloseUtils.java

closeIO       : �ر� IO
closeIOQuietly: �����ر� IO
ת����� -> ConvertUtils.java -> Test

bytes2Bits, bits2Bytes                  : bytes �� bits ��ת
bytes2Chars, chars2Bytes                : bytes �� chars ��ת
bytes2HexString, hexString2Bytes        : bytes �� hexString ��ת
memorySize2Byte, byte2MemorySize        : �� unit Ϊ��λ���ڴ��С���ֽ�����ת
byte2FitMemorySize                      : �ֽ���ת�����ڴ��С
timeSpan2Millis, millis2TimeSpan        : �� unit Ϊ��λ��ʱ�䳤�������ʱ�����ת
millis2FitTimeSpan                      : ����ʱ���ת����ʱ�䳤��
input2OutputStream, output2InputStream  : inputStream �� outputStream ��ת
inputStream2Bytes, bytes2InputStream    : inputStream �� bytes ��ת
outputStream2Bytes, bytes2OutputStream  : outputStream �� bytes ��ת
inputStream2String, string2InputStream  : inputStream �� string �����뻥ת
outputStream2String, string2OutputStream: outputStream �� string �����뻥ת
bitmap2Bytes, bytes2Bitmap              : bitmap �� bytes ��ת
drawable2Bitmap, bitmap2Drawable        : drawable �� bitmap ��ת
drawable2Bytes, bytes2Drawable          : drawable �� bytes ��ת
view2Bitmap                             : view ת Bitmap
dp2px, px2dp                            : dp �� px ��ת
sp2px, px2sp                            : sp �� px ��ת
������� -> CrashUtils.java

init: ��ʼ��
�豸��� -> DeviceUtils.java -> Demo

isDeviceRooted   : �ж��豸�Ƿ� rooted
getSDKVersionName: ��ȡ�豸ϵͳ�汾��
getSDKVersionCode: ��ȡ�豸ϵͳ�汾��
getAndroidID     : ��ȡ�豸 AndroidID
getMacAddress    : ��ȡ�豸 MAC ��ַ
getManufacturer  : ��ȡ�豸����
getModel         : ��ȡ�豸�ͺ�
getABIs          : ��ȡ�豸 ABIs
shutdown         : �ػ�
reboot           : ����
reboot2Recovery  : ������ recovery
reboot2Bootloader: ������ bootloader
���������� -> EncodeUtils.java -> Test

urlEncode          : URL ����
urlDecode          : URL ����
base64Encode       : Base64 ����
base64Encode2String: Base64 ����
base64Decode       : Base64 ����
htmlEncode         : Html ����
htmlDecode         : Html ����
���ܽ������ -> EncryptUtils.java -> Test

encryptMD2, encryptMD2ToString                        : MD2 ����
encryptMD5, encryptMD5ToString                        : MD5 ����
encryptMD5File, encryptMD5File2String                 : MD5 �����ļ�
encryptSHA1, encryptSHA1ToString                      : SHA1 ����
encryptSHA224, encryptSHA224ToString                  : SHA224 ����
encryptSHA256, encryptSHA256ToString                  : SHA256 ����
encryptSHA384, encryptSHA384ToString                  : SHA384 ����
encryptSHA512, encryptSHA512ToString                  : SHA512 ����
encryptHmacMD5, encryptHmacMD5ToString                : HmacMD5 ����
encryptHmacSHA1, encryptHmacSHA1ToString              : HmacSHA1 ����
encryptHmacSHA224, encryptHmacSHA224ToString          : HmacSHA224 ����
encryptHmacSHA256, encryptHmacSHA256ToString          : HmacSHA256 ����
encryptHmacSHA384, encryptHmacSHA384ToString          : HmacSHA384 ����
encryptHmacSHA512, encryptHmacSHA512ToString          : HmacSHA512 ����
encryptDES, encryptDES2HexString, encryptDES2Base64   : DES ����
decryptDES, decryptHexStringDES, decryptBase64DES     : DES ����
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES ����
decrypt3DES, decryptHexString3DES, decryptBase64_3DES : 3DES ����
encryptAES, encryptAES2HexString, encryptAES2Base64   : AES ����
decryptAES, decryptHexStringAES, decryptBase64AES     : AES ����
encryptRSA, encryptRSA2HexString, encryptRSA2Base64   : RSA ����
decryptRSA, decryptHexStringRSA, decryptBase64RSA     : RSA ����
�ļ���� -> FileIOUtils.java -> Test

writeFileFromIS            : ��������д���ļ�
writeFileFromBytesByStream : ���ֽ�����д���ļ�
writeFileFromBytesByChannel: ���ֽ�����д���ļ�
writeFileFromBytesByMap    : ���ֽ�����д���ļ�
writeFileFromString        : ���ַ���д���ļ�
readFile2List              : ��ȡ�ļ����ַ���������
readFile2String            : ��ȡ�ļ����ַ�����
readFile2BytesByStream     : ��ȡ�ļ����ֽ�������
readFile2BytesByChannel    : ��ȡ�ļ����ֽ�������
readFile2BytesByMap        : ��ȡ�ļ����ֽ�������
setBufferSize              : ���û������ߴ�
�ļ���� -> FileUtils.java -> Test

getFileByPath             : �����ļ�·����ȡ�ļ�
isFileExists              : �ж��ļ��Ƿ����
rename                    : �������ļ�
isDir                     : �ж��Ƿ���Ŀ¼
isFile                    : �ж��Ƿ����ļ�
createOrExistsDir         : �ж�Ŀ¼�Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
createOrExistsFile        : �ж��ļ��Ƿ���ڣ����������ж��Ƿ񴴽��ɹ�
createFileByDeleteOldFile : �ж��ļ��Ƿ���ڣ��������ڴ���֮ǰɾ��
copyDir                   : ����Ŀ¼
copyFile                  : �����ļ�
moveDir                   : �ƶ�Ŀ¼
moveFile                  : �ƶ��ļ�
deleteDir                 : ɾ��Ŀ¼
deleteFile                : ɾ���ļ�
deleteAllInDir            : ɾ��Ŀ¼�����ж���
deleteFilesInDir          : ɾ��Ŀ¼�������ļ�
deleteFilesInDirWithFilter: ɾ��Ŀ¼�����й��˵��ļ�
listFilesInDir            : ��ȡĿ¼�������ļ�
listFilesInDirWithFilter  : ��ȡĿ¼�����й��˵��ļ�
getFileLastModified       : ��ȡ�ļ�����޸ĵĺ���ʱ���
getFileCharsetSimple      : �򵥻�ȡ�ļ������ʽ
getFileLines              : ��ȡ�ļ�����
getDirSize                : ��ȡĿ¼��С
getFileSize               : ��ȡ�ļ���С
getDirLength              : ��ȡĿ¼����
getFileLength             : ��ȡ�ļ�����
getFileMD5                : ��ȡ�ļ��� MD5 У����
getFileMD5ToString        : ��ȡ�ļ��� MD5 У����
getDirName                : ����ȫ·����ȡ�Ŀ¼
getFileName               : ����ȫ·����ȡ�ļ���
getFileNameNoExtension    : ����ȫ·����ȡ�ļ���������չ��
getFileExtension          : ����ȫ·����ȡ�ļ���չ��
Fragment ��� -> FragmentUtils.java -> Demo

add                   : ���� fragment
show                  : ��ʾ fragment
hide                  : ���� fragment
showHide              : ����ʾ������ fragment
replace               : �滻 fragment
pop                   : ��ջ fragment
popTo                 : ��ջ��ָ�� fragment
popAll                : ��ջ���� fragment
remove                : �Ƴ� fragment
removeTo              : �Ƴ���ָ�� fragment
removeAll             : �Ƴ����� fragment
getTop                : ��ȡ���� fragment
getTopInStack         : ��ȡջ�ж��� fragment
getTopShow            : ��ȡ�����ɼ� fragment
getTopShowInStack     : ��ȡջ�ж����ɼ� fragment
getFragments          : ��ȡͬ����� fragment
getFragmentsInStack   : ��ȡͬ����ջ�е� fragment
getAllFragments       : ��ȡ���� fragment
getAllFragmentsInStack: ��ȡջ������ fragment
findFragment          : ���� fragment
dispatchBackPress     : ���� fragment ���˼�
setBackgroundColor    : ���ñ���ɫ
setBackgroundResource : ���ñ�����Դ
setBackground         : ���ñ���
ͼƬ��� -> ImageUtils.java -> Demo

bitmap2Bytes, bytes2Bitmap      : bitmap �� bytes ��ת
drawable2Bitmap, bitmap2Drawable: drawable �� bitmap ��ת
drawable2Bytes, bytes2Drawable  : drawable �� bytes ��ת
view2Bitmap                     : view ת bitmap
getBitmap                       : ��ȡ bitmap
scale                           : ����ͼƬ
clip                            : �ü�ͼƬ
skew                            : ��бͼƬ
rotate                          : ��תͼƬ
getRotateDegree                 : ��ȡͼƬ��ת�Ƕ�
toRound                         : תΪԲ��ͼƬ
toRoundCorner                   : תΪԲ��ͼƬ
addCornerBorder                 : ���Բ�Ǳ߿�
addCircleBorder                 : ���Բ�α߿�
addReflection                   : ��ӵ�Ӱ
addTextWatermark                : �������ˮӡ
addImageWatermark               : ���ͼƬˮӡ
toAlpha                         : תΪ alpha λͼ
toGray                          : תΪ�Ҷ�ͼƬ
fastBlur                        : ����ģ��
renderScriptBlur                : renderScript ģ��ͼƬ
stackBlur                       : stack ģ��ͼƬ
save                            : ����ͼƬ
isImage                         : �����ļ����ж��ļ��Ƿ�ΪͼƬ
getImageType                    : ��ȡͼƬ����
compressByScale                 : ������ѹ��
compressByQuality               : ������ѹ��
compressBySampleSize            : ��������Сѹ��
��ͼ��� -> IntentUtils.java

getInstallAppIntent              : ��ȡ��װ App��֧�� 6.0������ͼ
getUninstallAppIntent            : ��ȡж�� App ����ͼ
getLaunchAppIntent               : ��ȡ�� App ����ͼ
getLaunchAppDetailsSettingsIntent: ��ȡ App �������õ���ͼ
getShareTextIntent               : ��ȡ�����ı�����ͼ
getShareImageIntent              : ��ȡ����ͼƬ����ͼ
getComponentIntent               : ��ȡ����Ӧ���������ͼ
getShutdownIntent                : ��ȡ�ػ�����ͼ
getCaptureIntent                 : ��ȡ���յ���ͼ
������� -> KeyboardUtils.java -> Demo

showSoftInput                     : ��̬��ʾ�����
hideSoftInput                     : ��̬���������
toggleSoftInput                   : �л�������ʾ���״̬
isSoftInputVisible                : �ж�������Ƿ�ɼ�
registerSoftInputChangedListener  : ע������̸ı������
unregisterSoftInputChangedListener: ע������̸ı������
fixSoftInputLeaks                 : �޸�������ڴ�й©
clickBlankArea2HideSoftInput      : �����Ļ�հ��������������
��־��� -> LogUtils.java -> Demo

getConfig                : ��ȡ log ����
Config.setLogSwitch      : ���� log �ܿ���
Config.setConsoleSwitch  : ���� log ����̨����
Config.setGlobalTag      : ���� log ȫ�� tag
Config.setLogHeadSwitch  : ���� log ͷ����Ϣ����
Config.setLog2FileSwitch : ���� log �ļ�����
Config.setDir            : ���� log �ļ��洢Ŀ¼
Config.setFilePrefix     : ���� log �ļ�ǰ׺
Config.setBorderSwitch   : ���� log �߿򿪹�
Config.setSingleTagSwitch: ���� log ��һ tag ���أ�Ϊ���� AS 3.1 �� Logcat��
Config.setConsoleFilter  : ���� log ����̨������
Config.setFileFilter     : ���� log �ļ�������
Config.setStackDeep      : ���� log ջ���
Config.setStackOffset    : ���� log ջƫ��
log                      : �Զ��� tag �� type ��־
v                        : tag Ϊ������ Verbose ��־
vTag                     : �Զ��� tag �� Verbose ��־
d                        : tag Ϊ������ Debug ��־
dTag                     : �Զ��� tag �� Debug ��־
i                        : tag Ϊ������ Info ��־
iTag                     : �Զ��� tag �� Info ��־
w                        : tag Ϊ������ Warn ��־
wTag                     : �Զ��� tag �� Warn ��־
e                        : tag Ϊ������ Error ��־
eTag                     : �Զ��� tag �� Error ��־
a                        : tag Ϊ������ Assert ��־
aTag                     : �Զ��� tag �� Assert ��־
file                     : log ���ļ�
json                     : log �ַ���֮ json
xml                      : log �ַ���֮ xml
������� -> NetworkUtils.java -> Demo

openWirelessSettings  : ���������ý���
isConnected           : �ж������Ƿ�����
isAvailableByPing     : �ж������Ƿ����
getMobileDataEnabled  : �ж��ƶ������Ƿ��
setMobileDataEnabled  : �򿪻�ر��ƶ�����
isMobileData          : �ж������Ƿ����ƶ�����
is4G                  : �ж������Ƿ��� 4G
getWifiEnabled        : �ж� wifi �Ƿ��
setWifiEnabled        : �򿪻�ر� wifi
isWifiConnected       : �ж� wifi �Ƿ�����״̬
isWifiAvailable       : �ж� wifi �����Ƿ����
getNetworkOperatorName: ��ȡ�ƶ�������Ӫ������
getNetworkType        : ��ȡ��ǰ��������
getIPAddress          : ��ȡ IP ��ַ
getDomainAddress      : ��ȡ���� ip ��ַ
������� -> ObjectUtils.java -> Test

isEmpty       : �ж϶����Ƿ�Ϊ��
isNotEmpty    : �ж϶����Ƿ�ǿ�
equals        : �ж϶����Ƿ����
requireNonNull: ������ǿ�
getOrDefault  : ��ȡ�ǿջ�Ĭ�϶���
hashCode      : ��ȡ�����ϣֵ
Ȩ����� -> PermissionUtils.java -> Demo

getPermissions          : ��ȡӦ��Ȩ��
isGranted               : �ж�Ȩ���Ƿ�����
launchAppDetailsSettings: ��Ӧ�þ�������
permission              : ��������Ȩ��
rationale               : ���þܾ�Ȩ�޺��ٴ�����Ļص��ӿ�
callback                : ���ûص�
theme                   : ��������
request                 : ��ʼ����
�ֻ���� -> PhoneUtils.java -> Demo

isPhone            : �ж��豸�Ƿ����ֻ�
getDeviceId        : ��ȡ�豸��
getIMEI            : ��ȡ IMEI ��
getMEID            : ��ȡ MEID ��
getIMSI            : ��ȡ IMSI ��
getPhoneType       : ��ȡ�ƶ��ն�����
isSimCardReady     : �ж� sim ���Ƿ�׼����
getSimOperatorName : ��ȡ Sim ����Ӫ������
getSimOperatorByMnc: ��ȡ Sim ����Ӫ������
getPhoneStatus     : ��ȡ�ֻ�״̬��Ϣ
dial               : �������Ž���
call               : ���� phoneNumber
sendSms            : �������Ͷ��Ž���
sendSmsSilent      : ���Ͷ���
������� -> ProcessUtils.java -> Demo

getForegroundProcessName  : ��ȡǰ̨�̰߳���
killAllBackgroundProcesses: ɱ�����еĺ�̨�������
killBackgroundProcesses   : ɱ����̨�������
isMainProcess             : �ж��Ƿ�������������
getCurrentProcessName     : ��ȡ��ǰ��������(��app)
������� -> ReflectUtils.java -> Test

reflect    : ����Ҫ�������
newInstance: ʵ�����������
field      : ���÷�����ֶ�
method     : ���÷���ķ���
get        : ��ȡ������Ҫ��ȡ��
������� -> RegexUtils.java -> Test

isMobileSimple : ��֤�ֻ��ţ��򵥣�
isMobileExact  : ��֤�ֻ��ţ���ȷ��
isTel          : ��֤�绰����
isIDCard15     : ��֤���֤���� 15 λ
isIDCard18     : ��֤���֤���� 18 λ
isEmail        : ��֤����
isURL          : ��֤ URL
isZh           : ��֤����
isUsername     : ��֤�û���
isDate         : ��֤ yyyy-MM-dd ��ʽ������У�飬�ѿ���ƽ����
isIP           : ��֤ IP ��ַ
isMatch        : �ж��Ƿ�ƥ������
getMatches     : ��ȡ����ƥ��Ĳ���
getSplits      : ��ȡ����ƥ�����
getReplaceFirst: �滻����ƥ��ĵ�һ����
getReplaceAll  : �滻��������ƥ��Ĳ���
��Դ��� -> ResourceUtils.java -> Demo

copyFileFromAssets: �� assets �п����ļ�
readAssets2String : �� assets �ж�ȡ�ַ���
readAssets2List   : �� assets �а��ж�ȡ�ַ���
copyFileFromRaw   : �� raw �п����ļ�
readRaw2String    : �� raw �ж�ȡ�ַ���
readRaw2List      : �� raw �а��ж�ȡ�ַ���
��Ļ��� -> ScreenUtils.java -> Demo

getScreenWidth     : ��ȡ��Ļ�Ŀ�ȣ���λ��px��
getScreenHeight    : ��ȡ��Ļ�ĸ߶ȣ���λ��px��
getScreenDensity   : ��ȡ��Ļ�ܶ�
getScreenDensityDpi: ��ȡ��Ļ�ܶ� DPI
setFullScreen      : ������ĻΪȫ��
setLandscape       : ������ĻΪ����
setPortrait        : ������ĻΪ����
isLandscape        : �ж��Ƿ����
isPortrait         : �ж��Ƿ�����
getScreenRotation  : ��ȡ��Ļ��ת�Ƕ�
screenShot         : ����
isScreenLock       : �ж��Ƿ�����
setSleepDuration   : ���ý�������ʱ��
getSleepDuration   : ��ȡ��������ʱ��
isTablet           : �ж��Ƿ���ƽ��
SD ����� -> SDCardUtils.java -> Demo

isSDCardEnableByEnvironment: ���� Environment �ж� SD ���Ƿ����
getSDCardPathByEnvironment : ���� Environment ��ȡ SD ��·��
isSDCardEnable             : �ж� SD ���Ƿ����
getSDCardPaths             : ��ȡ SD ��·��
������� -> ServiceUtils.java

getAllRunningServices: ��ȡ�������еķ���
startService         : ��������
stopService          : ֹͣ����
bindService          : �󶨷���
unbindService        : ������
isServiceRunning     : �жϷ����Ƿ�����
Shell ��� -> ShellUtils.java

execCmd: �Ƿ����� root ��ִ������
�ߴ���� -> SizeUtils.java

dp2px, px2dp     : dp �� px ת��
sp2px, px2sp     : sp �� px ת��
applyDimension   : ���ֵ�λת��
forceGetViewSize : �� onCreate �л�ȡ��ͼ�ĳߴ�
measureView      : ������ͼ�ߴ�
getMeasuredWidth : ��ȡ������ͼ���
getMeasuredHeight: ��ȡ������ͼ�߶�
Snackbar ��� -> SnackbarUtils.java -> Demo

with           : ���� snackbar ���� view
setMessage     : ������Ϣ
setMessageColor: ������Ϣ��ɫ
setBgColor     : ���ñ���ɫ
setBgResource  : ���ñ�����Դ
setDuration    : ������ʾʱ��
setAction      : ������Ϊ
setBottomMargin: ���õױ߾�
show           : ��ʾ snackbar
showSuccess    : ��ʾԤ��ɹ��� snackbar
showWarning    : ��ʾԤ�辯��� snackbar
showError      : ��ʾԤ������ snackbar
dismiss        : ��ʧ snackbar
getView        : ��ȡ snackbar ��ͼ
addView        : ��� snackbar ��ͼ
SpannableString ��� -> SpanUtils.java -> Demo

setFlag           : ���ñ�ʶ
setForegroundColor: ����ǰ��ɫ
setBackgroundColor: ���ñ���ɫ
setLineHeight     : �����и�
setQuoteColor     : ���������ߵ���ɫ
setLeadingMargin  : ��������
setBullet         : �����б���
setIconMargin     : ����ͼ��
setFontSize       : ��������ߴ�
setFontProportion : �����������
setFontXProportion: ��������������
setStrikethrough  : ����ɾ����
setUnderline      : �����»���
setSuperscript    : �����ϱ�
setSubscript      : �����±�
setBold           : ���ô���
setItalic         : ����б��
setBoldItalic     : ���ô�б��
setFontFamily     : ��������ϵ��
setTypeface       : ��������
setAlign          : ���ö���
setClickSpan      : ���õ���¼�
setUrl            : ���ó�����
setBlur           : ����ģ��
setShader         : ������ɫ��
setShadow         : ������Ӱ
setSpans          : ������ʽ
append            : ׷����ʽ�ַ���
appendLine        : ׷��һ����ʽ�ַ���
appendImage       : ׷��ͼƬ
appendSpace       : ׷�ӿհ�
create            : ������ʽ�ַ���
SP ��� -> SPUtils.java -> Demo

getInstance        : ��ȡ SP ʵ��
Instance.put       : SP ��д������
Instance.getString : SP �ж�ȡ String
Instance.getInt    : SP �ж�ȡ int
Instance.getLong   : SP �ж�ȡ long
Instance.getFloat  : SP �ж�ȡ float
Instance.getBoolean: SP �ж�ȡ boolean
Instance.getAll    : SP �л�ȡ���м�ֵ��
Instance.contains  : SP ���Ƿ���ڸ� key
Instance.remove    : SP ���Ƴ��� key
Instance.clear     : SP �������������
�ַ������ -> StringUtils.java -> Test

isEmpty         : �ж��ַ����Ƿ�Ϊ null �򳤶�Ϊ 0
isTrimEmpty     : �ж��ַ����Ƿ�Ϊ null ��ȫΪ�ո�
isSpace         : �ж��ַ����Ƿ�Ϊ null ��ȫΪ�հ��ַ�
equals          : �ж����ַ����Ƿ����
equalsIgnoreCase: �ж����ַ������Դ�Сд�Ƿ����
null2Length0    : null תΪ����Ϊ 0 ���ַ���
length          : �����ַ�������
upperFirstLetter: ����ĸ��д
lowerFirstLetter: ����ĸСд
reverse         : ��ת�ַ���
toDBC           : ת��Ϊ����ַ�
toSBC           : ת��Ϊȫ���ַ�
�߳���� -> ThreadUtils.java -> Test

isMainThread            : �жϵ�ǰ�Ƿ����߳�
getFixedPool            : ��ȡ�̶��̳߳�
getSinglePool           : ��ȡ���̳߳�
getCachedPool           : ��ȡ�����̳߳�
getIoPool               : ��ȡ IO �̳߳�
getCpuPool              : ��ȡ CPU �̳߳�
executeByFixed          : �ڹ̶��̳߳�ִ������
executeByFixedWithDelay : �ڹ̶��̳߳���ʱִ������
executeByFixedAtFixRate : �ڹ̶��̳߳ذ��̶�Ƶ��ִ������
executeBySingle         : �ڵ��̳߳�ִ������
executeBySingleWithDelay: �ڵ��̳߳���ʱִ������
executeBySingleAtFixRate: �ڵ��̳߳ذ��̶�Ƶ��ִ������
executeByCached         : �ڻ����̳߳�ִ������
executeByCachedWithDelay: �ڻ����̳߳���ʱִ������
executeByCachedAtFixRate: �ڻ����̳߳ذ��̶�Ƶ��ִ������
executeByIo             : �� IO �̳߳�ִ������
executeByIoWithDelay    : �� IO �̳߳���ʱִ������
executeByIoAtFixRate    : �� IO �̳߳ذ��̶�Ƶ��ִ������
executeByCpu            : �� CPU �̳߳�ִ������
executeByCpuWithDelay   : �� CPU �̳߳���ʱִ������
executeByCpuAtFixRate   : �� CPU �̳߳ذ��̶�Ƶ��ִ������
executeByCustom         : ���Զ����̳߳�ִ������
executeByCustomWithDelay: ���Զ����̳߳���ʱִ������
executeByCustomAtFixRate: ���Զ����̳߳ذ��̶�Ƶ��ִ������
cancel                  : ȡ�������ִ��
ʱ����� -> TimeUtils.java -> Test

millis2String           : ��ʱ���תΪʱ���ַ���
string2Millis           : ��ʱ���ַ���תΪʱ���
string2Date             : ��ʱ���ַ���תΪ Date ����
date2String             : �� Date ����תΪʱ���ַ���
date2Millis             : �� Date ����תΪʱ���
millis2Date             : ��ʱ���תΪ Date ����
getTimeSpan             : ��ȡ����ʱ����λ��unit��
getFitTimeSpan          : ��ȡ����������ʱ���
getNowMills             : ��ȡ��ǰ����ʱ���
getNowString            : ��ȡ��ǰʱ���ַ���
getNowDate              : ��ȡ��ǰ Date
getTimeSpanByNow        : ��ȡ�뵱ǰʱ��Ĳ��λ��unit��
getFitTimeSpanByNow     : ��ȡ�������뵱ǰʱ��Ĳ�
getFriendlyTimeSpanByNow: ��ȡ�Ѻ����뵱ǰʱ��Ĳ�
getMillis               : ��ȡ�����ʱ�����ʱ����ʱ���
getString               : ��ȡ�����ʱ�����ʱ����ʱ���ַ���
getDate                 : ��ȡ�����ʱ�����ʱ���� Date
getMillisByNow          : ��ȡ�뵱ǰʱ�����ʱ����ʱ���
getStringByNow          : ��ȡ�뵱ǰʱ�����ʱ����ʱ���ַ���
getDateByNow            : ��ȡ�뵱ǰʱ�����ʱ���� Date
isToday                 : �ж��Ƿ����
isLeapYear              : �ж��Ƿ�����
getChineseWeek          : ��ȡ��ʽ����
getUSWeek               : ��ȡ��ʽʽ����
getValueByCalendarField : ���������ֶλ�ȡֵ
getChineseZodiac        : ��ȡ��Ф
getZodiac               : ��ȡ����
��˾��� -> ToastUtils.java -> Demo

setGravity     : ������˾λ��
setBgColor     : ���ñ�����ɫ
setBgResource  : ���ñ�����Դ
setMsgColor    : ������Ϣ��ɫ
setMsgTextSize : ������Ϣ�����С
showShort      : ��ʾ��ʱ��˾
showLong       : ��ʾ��ʱ��˾
showCustomShort: ��ʾ��ʱ�Զ�����˾
showCustomLong : ��ʾ��ʱ�Զ�����˾
cancel         : ȡ����˾��ʾ
URI ��� -> UriUtils.java

getUriForFile: ��ȡ�ļ� URI
ѹ����� -> ZipUtils.java -> Test

zipFiles          : ����ѹ���ļ�
zipFile           : ѹ���ļ�
unzipFile         : ��ѹ�ļ�
unzipFileByKeyword: ��ѹ���йؼ��ֵ��ļ�
getFilesPath      : ��ȡѹ���ļ��е��ļ�·������
getComments       : ��ȡѹ���ļ��е�ע������

