ContentProvider	:内容提供者,把应用程序的私有数据暴露出来,例:QQ读取电话联系人
1.BankProvider extends ContentProvider
2.清单文件注册:
        <!--authorities:ContetProvider唯一标识-->
        <!--permission:另一外应用需要访问该ContentProvider需要声明该权限-->
        <!--process:开启一个新进程-->
        <!--exported:是否支持其它应用调用当前组件
            默认值：如果包含有intent-filter 默认值为true; 没有intent-filter默认值为false
        -->
        <provider
            android:name=".BankProvider"
            android:authorities="${applicationId}.provider">

当接收到ContentResolver 发出的请求后，内容提供者被激活。

android平台提供了Content Provider使一个应用程序的指定数据集提供给其他应用程序。
这些数据可以存储在文件系统中、在一个SQLite数据库、或以任何其他合理的方式,
其他应用可以通过ContentResolver类(见ContentProviderAccessApp例子)
从该内容提供者中获取或存入数据.(相当于在应用外包了一层壳),
只有需要在多个应用程序间共享数据是才需要内容提供者。例如，通讯录数据被多个应用程序使用，
且必须存储在一个内容提供者中,它的好处:统一数据访问方式。

android系统自带的内容提供者(顶级的表示数据库名,非顶级的都是表名)
这些内容提供者在SDK文档的android.provider Java包中都有介绍。
见：http://developer.android.com/reference/android/provider/package-summary.html

├────Browser
├────CallLog
├────Contacts
│                ├────Groups
│                ├────People
│                ├────Phones
│                └────Photos
├────Images
│                └────Thumbnails
├────MediaStore
│                ├────Albums
│                ├────Artists
│                ├────Audio
│                ├────Genres
│                └────Playlists
├────Settings
└────Video

 CallLog：地址和接收到的电话信息

 Contact.People.Phones：存储电话号码

 Setting.System：系统设置和偏好设置

使用Content Provider对外共享数据的步骤
