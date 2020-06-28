package com.ly.hihifriend.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.ly.hihifriend.R;
import com.ly.hihifriend.fragment.MoneyAddBottomSheetDialogFragment;
import com.ly.hihifriend.info.ChargePrepayInfo;
import com.ly.hihifriend.info.ChargeProportionInfo;
import com.ly.hihifriend.info.MessageEvent;
import com.ly.hihifriend.info.MyUserInfo;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.MyOkhttpUtils.BaseCallback;
import com.ly.hihifriend.utils.MyOkhttpUtils.MyOkHttpUtils;
import com.ly.hihifriend.utils.RVItemDecoration;
import com.ly.hihifriend.utils.retrofit.BaseCallback2;
import com.ly.hihifriend.utils.retrofit.Network;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
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
    @BindView(R.id.tv_right)
    TextView     tvRight;
    @BindView(R.id.tv_money)
    TextView     tvMoney;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int                                                selectedPosition = 0;//已选中item
    private List<ChargeProportionInfo.DataBean.ChargeListBean> items            = new ArrayList<>();
    private MyAdapter                                          myAdapter;
    private MoneyAddBottomSheetDialogFragment                  bottomSheetDialogFragment;
    private IWXAPI                                             iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_add);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 30);
        EventBus.getDefault().register(this);

        tvTitle.setText("充值");
        tvRight.setText("充值记录");
        tvRight.setVisibility(View.VISIBLE);
        iwxapi = WXAPIFactory.createWXAPI(this, Global.WX_APPID, true);
        iwxapi.registerApp(Global.WX_APPID);// 将应用的appId注册到微信,只需注册一次

        myAdapter = new MyAdapter(R.layout.item_money_add, items);
        myAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectedPosition = position;
            myAdapter.notifyDataSetChanged();
        });
        int dp18 = ConvertUtils.dp2px(18);
        recyclerView.addItemDecoration(new RVItemDecoration(dp18, dp18));
        recyclerView.setAdapter(myAdapter);

        bottomSheetDialogFragment = new MoneyAddBottomSheetDialogFragment();

        getChargeProportion();
        requestPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyUserInfo();
    }

    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
//        AndPermission.with(this)
//                .runtime()
//                .permission(Permission.Group.STORAGE)
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//
//                    }
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//
//                    }
//                }).start();
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

    //获取我的信息
    private void getMyUserInfo() {
        Network.getMyUserInfoApi()
                .get()
                .enqueue(new BaseCallback2<MyUserInfo>() {
                    @Override
                    public void onOk(retrofit2.Call<MyUserInfo> call, Response<MyUserInfo> response) {
                        MyUserInfo body = response.body();
                        if (body != null) {
                            if (checkCode(body.code)) {
                                MyUserInfo.DataBean data = body.data;
                                if (data != null && data.coin != null) {
                                    tvMoney.setText(String.valueOf(data.coin.coinAmount));//金币总数
                                }
                            } else toast(body.msg);
                        }
                    }
                });
    }

    //获取充值兑换比例
    private void getChargeProportion() {
        MyOkHttpUtils.post(getUrl(Global.CHARGE_GET_CHARGE_PROPORTION), null,
                new BaseCallback<ChargeProportionInfo>(this) {
            @Override
            public void onOk(@NonNull ChargeProportionInfo response, int id) {
                if (checkCode(response.code)) {
                    ChargeProportionInfo.DataBean data = response.data;
                    if (data != null && data.chargeList != null) {
                        myAdapter.replaceData(data.chargeList);
                    }
                } else toast(response.msg);
            }
        });
    }

    /**
     * Eventbus微信支付回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedPayResult(MessageEvent messageEvent) {
        if (messageEvent.code == Global.MSG_EVT_WX_PAY_RESULT) {
            toast("支付成功!");
            if (tvTitle != null) tvTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomSheetDialogFragment.dismiss();
                }
            }, 1000);
        }
    }

    private class MyAdapter extends BaseQuickAdapter<ChargeProportionInfo.DataBean.ChargeListBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<ChargeProportionInfo.DataBean.ChargeListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper,
                               ChargeProportionInfo.DataBean.ChargeListBean item) {
            int position = helper.getAdapterPosition();
            helper.setText(R.id.tv_title, item.coinAmount + "金币")
                    .setText(R.id.tv_yuan, item.totalMoney + "元")
                    .itemView.setSelected(position == selectedPosition);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right, R.id.view_add_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_right://充值记录
                startActivity(new Intent(this, MoneyAddRecordActivity.class));
                break;
            case R.id.view_add_friend://确认充值
                prepay(view);
                break;
        }
    }

    //预支付,获取订单号
    private void prepay(View view) {
        showLoadingDialog(false);
        ChargeProportionInfo.DataBean.ChargeListBean chargeListBean = items.get(selectedPosition);
        params.clear();
        params.put(Global.coinAmount, chargeListBean.coinAmount);//金币数量, 多少个
        params.put(Global.totalMoney, chargeListBean.totalMoney);//总计金额, 1元: 1.00
        MyOkHttpUtils.post(getUrl(Global.CHARGE_PREPAY), params,
                new BaseCallback<ChargePrepayInfo>(this) {
            @Override
            public void onOk(@NonNull ChargePrepayInfo response, int id) {
                dismissLoadingDialog();
                if (checkCode(response.code)) {
                    ChargePrepayInfo.DataBean data = response.data;
                    if (data != null && data.tradeNo != null) {
                        bottomSheetDialogFragment.setData(iwxapi, getLoadingDialog(false), params, items.get(selectedPosition).totalMoney, data.tradeNo);
                        bottomSheetDialogFragment.show(getSupportFragmentManager());
                    } else toast("未获取到返回数据");
                } else toast(response.msg);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
