package com.kuchuan.wisdompolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.fragment.CallPoliceFragment;
import com.kuchuan.wisdompolice.global.Global;

import org.androidpn.client.Constants;

import java.util.List;

/**
 * 采集(流动人口,酷云车辆信息,图片,人像,典当物品信息,房屋租赁信息)
 * 本类启动模式是singletop
 */
public class CollectionActivity extends BaseActivity {

    private List<Fragment> fragments;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String themeId = intent.getStringExtra(Constants.SERVER_ID);//主题id
        System.out.println("themeId:" + themeId);
        if (themeId != null) {
            //LOGINTYPE=1&LOGINCD=zy&ID=1&msgtype=12
            if (aCache.getAsString(Global.NOTIFICATION_THEMEID) == null || !themeId.equals(aCache
                    .getAsString(Global.NOTIFICATION_THEMEID))) {
                String loginType = intent.getStringExtra(Constants.LOGINTYPE);
                String logincd = intent.getStringExtra(Constants.LOGINCD);
                String msgType = intent.getStringExtra(Constants.MSGTYPE);
                System.out.println("LOGINTYPE:" + loginType);
                System.out.println("LOGINCD:" + logincd);
                System.out.println("MSGTYPE:" + msgType);
                aCache.put(Global.NOTIFICATION_THEMEID, themeId);
                aCache.put(Global.NOTIFICATION_LOGINTYPE, loginType);
                aCache.put(Global.NOTIFICATION_LOGINCD, logincd);
                aCache.put(Global.NOTIFICATION_MSGTYPE, msgType);
                //String uri = intent.getStringExtra(Constants.NOTIFICATION_URI);
                switchToPosition(1);
                CallPoliceFragment callPoliceFragment = (CallPoliceFragment) fragments.get(1);
                callPoliceFragment.requestCallPoliceList(themeId);
            }
        }
    }
}
