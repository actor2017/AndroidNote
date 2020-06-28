
public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        PackageManager pm = context.getPackageManager();
        //安装成功(如果是第一次安装,根据打印日志:安装)
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        //替换成功(如果是覆盖安装,根据打印日志:卸载-->安装-->替换)
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        //卸载成功(如果是卸载:根据打印日志:卸载)
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        }
    }
}
