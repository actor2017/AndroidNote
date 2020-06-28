https://docs.open.alipay.com/204/105296/

1.Project/Module 中
buildTypes {
	release {
		minifyEnabled true
		proguardFiles 'proguard-project.txt'
	}
}

2.Module中
implementation (name: 'alipaySdk-15.6.2-20190416165036', ext: 'aar')//AliPay

3.权限
<!--AliPay支付宝权限-->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<!--如果应用已经升级 targetSdk 到 23 及以上，则需要在运行时向用户申请以下2个权限-->
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

4.如果您的应用已经升级 targetSdk 到 23 及以上，则需要在运行时向用户申请 WRITE_EXTERNAL_STORAGE 和 READ_PHONE_STATE 这两项权限。
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);

        } else logError("支付宝 SDK 已有所需的权限");
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length == 0) {// 用户取消了权限弹窗
                    logError("无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                    toast("无法获取支付宝所需权限, 请到系统设置开启");
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        logError("无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                        toast("无法获取支付宝所需的权限, 请到系统设置开启");
                        return;
                    }
                }
                logError("支付宝 SDK 所需的权限已经正常获取");// 所需的权限均正常获取
            }
        }
    }

5.支付
    /**
     * @param data 等于下面的字符串
     * alipay_sdk=alipay-sdk-java-3.7.4.ALL
     * &app_id=2019032863707626
     * &biz_content=%7B%22out_trade_no%22%3A%2220190507204548000010%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22HIHI%E4%BA%A4%E5%8F%8B%E2%80%94%E9%87%91%E5%B8%81%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%2210.00%22%7D
     * &charset=UTF-8
     * &format=json
     * &method=alipay.trade.app.pay
     * &notify_url=http%3A%2F%2F140.143.149.162%3A8001%2Fnotify%2Falipay%2Fpay_notify
     * &sign=topxyd5QQDBIsYhscG2mroMXk7WQDzLy1t71RwlKC2HkVnta435F1fhiKo8RDAoBW3mAJnPD1TAN3DnnuqkGsw2wYLJDMe1cbKc5aONviXj4ZkbIXHWOJgpcYAf%2B2OnSOTpW1FOJWMLYq2S1CxA53qvzxBHrTKNDnFvW4SZE%2FUBEPLrTINMDpMAeXukp9%2FMHCJYrZrq1WQybNl5Je4OlCacWWE8lPdxbGNBcQACzp71oCq0LXLJ1nv%2F2JDgQHmvT%2F02DwtwK78PEDrQKkEeDVqdrjSAdxjS%2FCPHn0FGU2VpxLErInz5Iwhc1mMza0OipCWks7kmmGgd9bqaE0zOnAQ%3D%3D
     * &sign_type=RSA2
     * &timestamp=2019-05-07+20%3A45%3A57
     * &version=1.0
     */
    private void zfbPay(String data) {
        PayTask alipay = new PayTask(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> result = alipay.payV2(data, true);
                //{resultStatus=6001, result=, memo=操作已经取消。}
                logError("msp:" + result.toString());

                PayResult payResult = new PayResult(result);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                logError("全部返回数据:" + payResult.toString());
                logError("同步返回需要验证的信息,result=:" + resultInfo);
                String resultStatus = payResult.getResultStatus();
                if (resultStatus != null) {
                    switch (resultStatus) {
                        case "9000"://订单支付成功
                            toast("支付成功!");
                            break;
                        case "8000"://正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                            toast("正在处理中...");
                            break;
                        case "4000"://订单支付失败
                            toast("支付失败");
                            break;
                        case "5000"://重复请求
                            toast("请勿重复支付");
                            break;
                        case "6001"://用户中途取消
                            toast("用户中途取消");
                            break;
                        case "6002"://网络连接出错
                            toast("网络连接出错");
                            break;
                        case "6004"://支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                            toast("支付结果未知,请查询充值记录");
                            break;
                        default://其它支付错误
                            toast("支付错误");
                            break;
                    }
                    runOnUiThread(() -> easyPopup.dismiss());
                }
            }
        }).start();
    }

6.通知参数说明 https://docs.open.alipay.com/204/105301
	参数 & 返回结果码

7.快速生成RSA私钥公钥
https://docs.open.alipay.com/291/106097
