package com.ly.hihifriend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ly.hihifriend.R;
import com.ly.hihifriend.info.BaseInfo2;
import com.ly.hihifriend.info.ChargeWXPayInfo;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.LogUtils;
import com.ly.hihifriend.utils.MyOkhttpUtils.BaseCallback;
import com.ly.hihifriend.utils.MyOkhttpUtils.MyOkHttpUtils;
import com.ly.hihifriend.utils.ToastUtils;
import com.ly.hihifriend.utils.ZfbPay.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.Map;

import okhttp3.Call;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/26 on 11:07
 */
public class MoneyAddBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {

    private TextView            tvMoney2;//下方显示的money
    private TextView            tvTradeNo;//下方显示的订单号
    private LinearLayout        llWxPay;//微信支付
    private View                llAliPay;//支付宝支付
    private boolean             isWxPay = true;//是否是微信支付
    private IWXAPI              iwxapi;
    private KProgressHUD        loadingDialog;
    private Map<Object, Object> params;
    private int totalMoney;
    private String tradeNo;

    @Override
    public int getLayoutId() {
        return R.layout.popup_money_add;
    }

    public void setData(IWXAPI iwxapi, KProgressHUD loadingDialog, Map<Object, Object> params, int totalMoney, String tradeNo) {
        this.iwxapi = iwxapi;
        this.loadingDialog = loadingDialog;
        this.params = params;
        this.totalMoney = totalMoney;
        this.tradeNo = tradeNo;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyOnClickListener onClickListener = new MyOnClickListener();
        view.findViewById(R.id.iv_back).setOnClickListener(onClickListener);
        tvMoney2 = view.findViewById(R.id.tv_money);//充值多少钱
        tvTradeNo = view.findViewById(R.id.tv_order_number);//订单号
        llWxPay = view.findViewById(R.id.ll_wx_pay);//微信支付
        llAliPay = view.findViewById(R.id.ll_ali_pay);//支付宝支付
        llAliPay.setBackground(null);
        llWxPay.setOnClickListener(onClickListener);
        llAliPay.setOnClickListener(onClickListener);
        view.findViewById(R.id.view_add_friend).setOnClickListener(onClickListener);

        tvMoney2.setText("￥" + totalMoney);
        tvTradeNo.setText(tradeNo);
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    dismiss();
                    break;
                case R.id.ll_wx_pay://微信支付
                    isWxPay = true;
                    llWxPay.setBackground(getResources().getDrawable(R.drawable.shape_rec_green_44be19_border_2dp));
                    llAliPay.setBackground(null);
                    break;
                case R.id.ll_ali_pay://支付宝支付
                    isWxPay = false;
                    llAliPay.setBackground(getResources().getDrawable(R.drawable.shape_rec_blue_1296db_border_2dp));
                    llWxPay.setBackground(null);
                    break;
                case R.id.view_add_friend://确认付款
                    if (isWxPay) {
                        weChatPay();
                    } else aliPay();
                    break;
            }
        }
    }

    //微信支付
    private void weChatPay() {
        boolean wxAppInstalled = iwxapi.isWXAppInstalled();//是否安装微信
        if (!wxAppInstalled) {
            toast("您未安装微信, 请先安装");
            return;
        }
        showLoadingDialog();
        params.clear();
        params.put(Global.tradeNo, tvTradeNo.getText().toString().trim());//订单号
        MyOkHttpUtils.post(Global.BASE_URL.concat(Global.CHARGE_WXPAY), params,
                new BaseCallback<ChargeWXPayInfo>(this) {
                    @Override
                    public void onOk(@NonNull ChargeWXPayInfo response, int id) {
                        dismissLoadingDialog();
                        if (response.code == 1000) {
                            ChargeWXPayInfo.DataBean data = response.data;
                            if (data != null) {
                                wxPay(data);
                            }
                        } else toast(response.msg);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        dismissLoadingDialog();
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay(ChargeWXPayInfo.DataBean data) {
        //在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        // iwxapi.registerApp(Global.WX_APPID);
        PayReq req = new PayReq();
        req.appId = data.appid;//你的微信appid
        req.partnerId = data.partnerid;//商户号
        req.prepayId = data.prepayid;//预支付交易会话ID
        req.nonceStr = data.noncestr;//随机字符串
        req.timeStamp = data.timestamp;//时间戳
        req.packageValue = data.packageX;//扩展字段,这里固定填写Sign=WXPay
        req.sign = data.sign;//签名
        //      req.extData         = "app data"; // optional
        iwxapi.sendReq(req);
    }

    /**
     * 支付宝支付
     */
    private void aliPay() {
        showLoadingDialog();
        params.clear();
        params.put(Global.tradeNo, tvTradeNo.getText().toString().trim());//订单号
        MyOkHttpUtils.post(Global.BASE_URL.concat(Global.CHARGE_ALIPAY), params, new BaseCallback<BaseInfo2>(this) {
            @Override
            public void onOk(@NonNull BaseInfo2 response, int id) {
                dismissLoadingDialog();
                if (response.code == 1000) {
                    String data = response.data;
                    if (data != null) {
                        zfbPay(data);
                    }
                } else toast(response.msg);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                dismissLoadingDialog();
            }
        });
    }

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
        PayTask alipay = new PayTask(getActivity());
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
                    FragmentActivity activity = getActivity();
                    if (activity != null) activity.runOnUiThread(() -> dismiss());
                }
            }
        }).start();
    }

    private void logError(String msg) {
        LogUtils.Error(msg, true);
    }

    private void toast(String msg) {
        ToastUtils.show(msg);
    }

    private void showLoadingDialog() {
        if (loadingDialog != null) loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null) loadingDialog.dismiss();
    }
}
