
    //显示下载对话框
    private void downLoad() {
        /**
         * 显示对话框,进度条,下载完成后安装
         */
        //1.获取一个进度对话框
        final ProgressDialog dialog = new ProgressDialog(this);
        //2.设置对话框的标题
        dialog.setTitle("拼命下载中...");
        //3.设置对话框的样式:水平
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //4.设置进度
        OkHttpUtils.get().url(downloadUrl).build().execute(new FileCallBack(Environment
                .getExternalStorageDirectory().getAbsolutePath(), "MobileSafe.apk") {

            //设置进度条显示
            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                dialog.setProgress((int) (progress * 100));
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                e.printStackTrace();
                Toast.makeText(HomeActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

            //下载成功后回调,进入安装界面
            @Override
            public void onResponse(File file, int i) {
                //1.弹窗消失
                dialog.dismiss();
                //2.进入安装界面
                //安装apk
                //跳到系统安装页面进行安装
                //Intent, action: 隐式意图
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        });

        //下载过程中,当点击对话框的外面/返回键的时候,设置不能取消对话框★★★★★★★★★★★★
        dialog.setCancelable(false);
        //下面这种方法的意思:按返回键可以关闭dialog
        dialog.setCanceledOnTouchOutside(false);
        //注意别忘了写这句,否则无法显示对话框
        dialog.show();
    }