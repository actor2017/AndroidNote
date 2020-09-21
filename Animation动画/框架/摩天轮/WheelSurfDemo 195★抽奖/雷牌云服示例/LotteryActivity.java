package com.rm.lpsj.activity.me;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.actor.myandroidframework.utils.ConfigUtils;
import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.cretin.www.wheelsruflibrary.listener.RotateListener;
import com.cretin.www.wheelsruflibrary.view.WheelSurfView;
import com.flyco.dialog.widget.NormalDialog;
import com.rm.lpsj.R;
import com.rm.lpsj.activity.BaseActivity;
import com.rm.lpsj.utils.BaseCallback3;
import com.runman.baselibrary.base.BaseInfo;
import com.runman.baselibrary.base.LuckdrawRandomInfo;
import com.runman.baselibrary.control.Global;
import com.runman.baselibrary.model.LuckdrawDrawoneInfo;
import com.runman.baselibrary.view.CustomTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * description: 抽奖
 *
 * @author : 李大发
 * date       : 2020/9/14 on 15:49
 * @version 1.0
 */
public class LotteryActivity extends BaseActivity {

    @BindView(R.id.custom_title_view)
    CustomTitleView customTitleView;
    @BindView(R.id.wheel_surf_view)
    WheelSurfView   wheelSurfView;
    @BindView(R.id.btn_lottery)
    Button btnLottery;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private int[]                               allColors = {
            R.color.a,
            R.color.b,
            R.color.c,
            R.color.d,
            R.color.e,
            R.color.f,
            R.color.g};
    private List<LuckdrawDrawoneInfo> datas;
    private int                                 lotteryCount;
    private Bitmap iconBitmap;
    private List<Bitmap> icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);

        customTitleView.setTitle("抽奖");
        customTitleView.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (ConfigUtils.isDebugMode) {
            btnAdd.setVisibility(View.VISIBLE);
        }
//        iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_leipai_logo);
        //透明图片
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(getResources().getColor(android.R.color.transparent));

        iconBitmap = bitmap;
        //旋转监听
        wheelSurfView.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int i, String s) {
                new NormalDialog(activity)
                        .content(s)
                        .btnNum(1)
                        .btnText("确定")
                        .show();
            }
            @Override
            public void rotating(ValueAnimator valueAnimator) {
            }
            @Override
            public void rotateBefore(ImageView imageView) {
            }
        });
        getCount(false);
        getGoodsList(false);
    }

    @OnClick({R.id.btn_lottery, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_lottery://抽奖
                getCount(true);
                break;
            case R.id.btn_add://增加一次抽奖次数
                addLottery();
                break;
            default:
                break;
        }
    }


    /**
     * 用户剩余抽奖次数
     * @param isStartLottery 是否开始抽奖
     */
    private void getCount(boolean isStartLottery) {
        params.clear();
        params.put(Global.userId, SPUtils.getString(Global.userId));
        MyOkHttpUtils.post(Global.LUCKDRAW_DRAWONE, params, new BaseCallback3<BaseInfo<String>>(this, isStartLottery) {
            @Override
            public void onOk(@NonNull BaseInfo<String> info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    lotteryCount = 0;
                    try {
                        String datas = info.datas;
                        lotteryCount = Integer.parseInt(datas);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    btnLottery.setText(getStringFormat("抽奖(剩余 %d 次)", lotteryCount));
                    //如果抽奖
                    if (requestIsRefresh) {
                        if (lotteryCount > 0) {
                            lottery();
                        } else {
                            toast("没有抽奖次数了");
                        }
                    }
                } else {
                    toast(info.responseMsg);
                }
            }
        });
    }

    //奖品列表
    private void getGoodsList(boolean needLottery) {
        MyOkHttpUtils.post(Global.LUCKDRAW_LIST, null, new BaseCallback3<BaseInfo<List<LuckdrawDrawoneInfo>>>(this, needLottery) {
            @Override
            public void onOk(@NonNull BaseInfo<List<LuckdrawDrawoneInfo>> info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    datas = info.datas;
                    if (LotteryActivity.this.datas != null) {
//                        for (LuckdrawDrawoneInfo.DatasBean data : datas) {
//
//                        }
                        Integer[] colors = new Integer[LotteryActivity.this.datas.size()];
                        String[] texts = new String[LotteryActivity.this.datas.size()];
                        icons.clear();
                        for (int i = 0; i < LotteryActivity.this.datas.size(); i++) {//i = 0-10
                            colors[i] = allColors[i % allColors.length];//0-6
                            texts[i] = LotteryActivity.this.datas.get(i).prize;
                            icons.add(iconBitmap);
                        }
                        wheelSurfView.setConfig(new WheelSurfView.Builder()
                                .setmTypeNum(texts.length)
                                .setmColors(colors)//每一片背景颜色
                                .setmDeses(texts)//每一片描述
//                                .setmTextColor(R.color.colorAccent)//字体颜色
//                                .setmMainImgRes()
                                .setmIcons(icons)//必须设置
                                .build());
                        //如果需要抽奖
                        if (requestIsRefresh) {
                            lottery();
                        }
                    }
                } else {
                    toast(info.responseMsg);
                }
            }
        });
    }

    //用户抽奖
    private void lottery() {
        if (datas == null) {
            getGoodsList(true);
            return;
        }
        params.clear();
        params.put(Global.userId, SPUtils.getString(Global.userId));
        MyOkHttpUtils.post(Global.LUCKDRAW_RANDOM, params, new BaseCallback3<BaseInfo<LuckdrawRandomInfo>>(this) {
            @Override
            public void onBefore(@Nullable Request request, int id) {
//                super.onBefore(request, id);
            }
            @Override
            public void onOk(@NonNull BaseInfo<LuckdrawRandomInfo> info, int id) {
                btnLottery.setText(getStringFormat("抽奖(剩余 %d 次)", -- lotteryCount));
                if (info.isSuccess()) {
                    LuckdrawRandomInfo infos = info.datas;
                    if (infos != null) {
                        for (int i = 0; i < datas.size(); i++) {
                            if (datas.get(i).id == infos.id) {
                                wheelSurfView.startRotate(i - 1);//开始旋转
                                break;
                            }
                        }
                    } else {
                        toast("未返回数据");
                    }
                } else {
                    toast(info.responseMsg);
                }
            }
        });
    }

    //增加一次抽奖次数
    private void addLottery() {
        params.clear();
        params.put(Global.userId, SPUtils.getString(Global.userId));
        MyOkHttpUtils.post(Global.LUCKDRAW_ADD, params, new BaseCallback3<BaseInfo<String>>(this) {
            @Override
            public void onOk(@NonNull BaseInfo<String> info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    lotteryCount = Integer.parseInt(info.datas);
                    btnLottery.setText(getStringFormat("抽奖(剩余 %d 次)", lotteryCount));
                }
            }
        });
    }
}