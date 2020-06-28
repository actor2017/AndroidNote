
public class AppManagerActivity extends AppCompatActivity {

    private PopupWindow mPopup; //小弹窗

    @Override
    protected void onStart() {
        super.onStart();
        //设置条目点击事件
        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appInfo = info.get(position);
                boolean isTitle = appInfo.isTitle;
                //如果不是标题,才打卡小弹窗
                if (!isTitle) {
                    //把view传进去
                    showPopup(view);
                }
            }
        });
    }

    //小弹窗
    private void showPopup(final View itemView) {
        if (mPopup == null) {
            View view = View.inflate(this, R.layout.popup_appinfo, null);

            //找到控件,添加点击事件
            view.findViewById(R.id.tv_uninstall).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗消失
                    mPopup.dismiss();
                    //如果是用户应用
                    if (appInfo.isUserApp) {
                        //如果不是安全卫士,才卸载
                        if (!appInfo.packageName.equals(getPackageName())) {
                            //卸载应用
                            Uri packageURI = Uri.parse("package:" + appInfo.packageName);
                            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                            startActivity(uninstallIntent);
                        } else {
                            ToastUtils.show(AppManagerActivity.this, "不能卸载自己哦!");
                        }
                    }else {
                        ToastUtils.show(AppManagerActivity.this,"系统应用无法卸载,需要Root权限!");
                    }
                }
            });
            view.findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗消失
                    mPopup.dismiss();

                    //启动
                    PackageManager pm = getPackageManager();

                    //获取某个app的入口页面的intent
                    Intent launchIntentForPackage = pm.getLaunchIntentForPackage(appInfo.packageName);
                    //有些系统应用没有名字
                    if (launchIntentForPackage != null) {
                        startActivity(launchIntentForPackage);
                    } else {
                        ToastUtils.show(AppManagerActivity.this, "无法启动该应用!");
                    }
                }
            });
            view.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗消失
                    mPopup.dismiss();
                    //分享, 使用当前系统安装的可分享的应用程序来分享
                    //原理: 发送隐式意图, 会展示系统弹窗, 用户选择要分享的应用后, 始终&仅此一次, 跳到该应用进行分享
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "发现了一个非常好的应用, 赶紧下载吧! 下载地址: https://play.google" +
                            ".com/store/apps/details?id=" + appInfo.packageName);
                    intent.setType("text/plain");//分享类型, 纯文本类型
                    //设置分享列表的标题，并且每次都显示分享列表
                    startActivity(intent);
                }
            });
            view.findViewById(R.id.tv_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗消失
                    mPopup.dismiss();
                    //信息: 隐式意图, 跳到系统应用信息页面
                    //搜索技巧: 先百度->谷歌->换英文搜索
                    //google.com
                    //github.com
                    //http://stackoverflow.com/
                    Intent infoIntent = new Intent();
                    infoIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", appInfo.packageName, null);
                    infoIntent.setData(uri);
                    startActivity(infoIntent);
                }
            });

            //宽高包裹内容PopupWindow(View contentView, int width, int height, boolean focusable)
            mPopup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            //必须设置背景图片,点击弹窗外面和返回键,小弹窗才会消失(这儿点进去是什么都没设置)
            mPopup.setBackgroundDrawable(new ColorDrawable());

            //设置动画样式
            mPopup.setAnimationStyle(R.style.PopupAnim);
        }

        //     showAsDropDown(View anchor, int xoff, int yoff)把弹窗向右,上平移
        //itemView.getHeight()  :获取条目的"高度"
        mPopup.showAsDropDown(itemView, 70, -itemView.getHeight());
    }
}
