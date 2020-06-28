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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: 微信分享/登录 返回页面
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/1 on 14:57
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

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
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String jsonString = JSON.toJSONString(baseResp);
        LogUtils.Error("jsonString: " + jsonString, true);
        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_SENDAUTH://微信登录
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
                        final String code = authResp.code;//用户换取access_token的code，仅在ErrCode为0时有效
                        /**
                         * 第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，
                         * 由微信终端回传，state字符串长度不能超过1K
                         */
                        String state = authResp.state;
                        String lang = authResp.lang;//微信客户端当前语言
                        String country = authResp.country;//微信用户当前国家信息

                        finish();
                        EventBus.getDefault().post(new MessageEvent<>(Global.MSG_EVT_WX_LOGIN, code));
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        finish();
                        toast("用户取消登录!");
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        finish();
                        toast("用户拒绝登录!");
                        break;
                    default:
                        finish();
                        toast("登录失败, 错误码: " + baseResp.errCode);
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
