package com.ly.hihifriend.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.ly.hihifriend.info.MessageEvent;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.LogUtils;
import com.ly.hihifriend.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: 微信支付必须要求返回界面, 这个activity必须放在: 包名/wxapi/目录下
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/1 on 10:57
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        iwxapi = WXAPIFactory.createWXAPI(this, Global.WX_APPID);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，
        // 如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，
        // 避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        boolean result =  iwxapi.handleIntent(getIntent(), this);
        if(!result){
            LogUtils.Error("微信登录参数不合法，未被SDK处理，退出", true);
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {//微信发送的请求将回调到onReq方法
    }

    @Override
    public void onResp(BaseResp baseResp) {//发送到微信请求的响应结果将回调到onResp方法
        String jsonString = JSON.toJSONString(baseResp);
        //transaction 用与唯一标示一个请求null
        LogUtils.Error("jsonString: " + jsonString, true);

        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX://微信支付
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
//                        toast("支付成功!");
                        finish();
                        EventBus.getDefault().post(new MessageEvent<>(Global.MSG_EVT_WX_PAY_RESULT));
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        finish();
                        toast("用户取消支付!");
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        finish();
                        toast("用户拒绝支付!");
                        break;
                    default:
                        finish();
                        toast("支付失败, 错误码: " + baseResp.errCode);
                        break;
                }
                break;
            default:
                finish();
                LogUtils.Error("baseResp.getType():" + baseResp.getType(), true);
                break;
        }
    }

    private void toast(String msg) {
        ToastUtils.show(msg);
    }
}
