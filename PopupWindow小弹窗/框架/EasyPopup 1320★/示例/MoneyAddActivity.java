package com.ly.hihifriend.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.ly.hihifriend.R;
import com.ly.hihifriend.info.CoinChargeWXPayInfo;
import com.ly.hihifriend.info.MessageEvent;
import com.ly.hihifriend.network.Network;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.RVItemDecoration;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyyoona7.popup.EasyPopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description: 我的(点击金币)-->金币-->充值
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/29 on 10:50
 */
public class MoneyAddActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView     tvTitle;
    @BindView(R.id.tv_money)
    TextView     tvMoney;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int        selectedPosition = 0;//已选中item
    private List<Item> datas = new ArrayList<>();
    private MyAdapter  myAdapter;
    private EasyPopup  easyPopup;
    private IWXAPI     iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_add);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 30);
        EventBus.getDefault().register(this);

        tvTitle.setText("充值");
        iwxapi = WXAPIFactory.createWXAPI(this, Global.WX_APPID, true);
        iwxapi.registerApp(Global.WX_APPID);// 将应用的appId注册到微信,只需注册一次

        datas.add(new Item("7X币", "1元"));
        datas.add(new Item("42X币", "6元"));
        datas.add(new Item("210X币", "30元"));
        datas.add(new Item("686X币", "98元"));
        datas.add(new Item("2086X币", "298元"));
        datas.add(new Item("4116X币", "30元"));
        myAdapter = new MyAdapter(R.layout.item_money_add, datas);
        myAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectedPosition = position;
            myAdapter.notifyDataSetChanged();
            toast(String.valueOf(position));
        });
        int dp18 = ConvertUtils.dp2px(18);
        recyclerView.addItemDecoration(new RVItemDecoration(dp18, dp18));
        recyclerView.setAdapter(myAdapter);
        easyPopup = EasyPopup.create()
                .setContentView(this, R.layout.popup_money_add, ScreenUtils.getScreenWidth(), -2)
//                .setAnimationStyle(R.style.BottomPopAnim)
                .setBackgroundDimEnable(true)
                .apply();
        easyPopup.findViewById(R.id.iv_back).setOnClickListener(v -> easyPopup.dismiss());
        easyPopup.findViewById(R.id.tv_money);//多少钱
        easyPopup.findViewById(R.id.tv_order_number);//订单编号
        easyPopup.findViewById(R.id.ll_payway).setOnClickListener(v -> {//支付方式
            toast("支付方式");
        });
        easyPopup.findViewById(R.id.iv_payway);//支付方式图标
        easyPopup.findViewById(R.id.tv_payway);//支付方式文字: 微信&支付宝
        easyPopup.findViewById(R.id.view_add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确认付款
                pay();
            }
        });
    }

    private void pay() {
        Network.getCoinChargeWXPayApi()
                .pay(100, "1")//.00
                .enqueue(new Callback<CoinChargeWXPayInfo>() {
                    @Override
                    public void onResponse(Call<CoinChargeWXPayInfo> call, Response<CoinChargeWXPayInfo> response) {
                        CoinChargeWXPayInfo body = response.body();
                        if (body != null) {
                            if (body.code == 1000) {
                                CoinChargeWXPayInfo.DataBean data = body.data;
                                if (data != null) {
                                    wxPay(data);
                                }
                            } else toast(body.msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<CoinChargeWXPayInfo> call, Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay(CoinChargeWXPayInfo.DataBean data) {
        //在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        // iwxapi.registerApp(Global.WX_APPID);
        PayReq req = new PayReq();
        req.appId           = data.appid;//你的微信appid
        req.partnerId       = data.partnerid;//商户号
        req.prepayId        = data.prepayid;//预支付交易会话ID
        req.nonceStr        = data.noncestr;//随机字符串
        req.timeStamp       = data.timestamp;//时间戳
        req.packageValue    = data.packageX;//扩展字段,这里固定填写Sign=WXPay
        req.sign            = data.sign;//签名
        //      req.extData         = "app data"; // optional
        iwxapi.sendReq(req);
    }

    /**
     * Eventbus微信支付回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedPayResult(MessageEvent messageEvent) {
        if (messageEvent.code == Global.MSG_EVT_WX_PAY_RESULT) {
            toast("支付成功!");
        }
    }

    private class MyAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<Item> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Item item) {
            int position = helper.getAdapterPosition();
            helper.setText(R.id.tv_title, item.title)
                    .setText(R.id.tv_yuan, item.yuan)
                    .itemView.setSelected(position == selectedPosition);
        }
    }

    @OnClick({R.id.iv_back, R.id.view_add_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.view_add_friend://确认充值
                easyPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private class Item {
        private String title;
        private String yuan;

        public Item(String title, String yuan) {
            this.title = title;
            this.yuan = yuan;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
