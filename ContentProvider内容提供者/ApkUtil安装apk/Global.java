
public class Global {

    //下面是自定义字段
    //安装apk的时候,在清单文件中配置的authorities,如果在Android 7.0版本上,必须传入这个.
    public static final String authorities = MyApplication.instance.getPackageName() + FileProvider.class.getSimpleName();
}
