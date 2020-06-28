package com.ly.hihifriend.gift;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ly.hihifriend.R;
import com.ly.hihifriend.utils.LogUtils;
import com.zhangyf.gift.RewardLayout;

/**
 * Description: 礼物显示
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/5/15 on 17:00
 */
public class MyGiftAdapter implements RewardLayout.GiftAdapter<SendGiftBean> {

    private Context context;

    public MyGiftAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View onInit(View view, SendGiftBean bean) {//初始化
        ImageView ivGiftMyAvatar = view.findViewById(R.id.iv_gift_my_avatar);//头像
        ImageView ivGiftImg = view.findViewById(R.id.iv_gift_img);//礼物图片
        TextView tvGiftNum = view.findViewById(R.id.tv_gift_amount);//礼物数量
        TextView tvUserName = view.findViewById(R.id.tv_user_name);//昵称
        TextView tvGiftName = view.findViewById(R.id.tv_gift_name);//礼物名称

        Glide.with(context).load(bean.getHeadUrl()).circleCrop().into(ivGiftMyAvatar);
        Glide.with(context).load(bean.getGiftImg()).into(ivGiftImg);
        tvGiftNum.setText("x" + bean.getTheSendGiftSize());
        bean.setTheGiftCount(bean.getTheSendGiftSize());
        tvUserName.setText(bean.getUserName());
        tvGiftName.setText("送出 " + bean.getGiftName());
        return view;
    }

    @Override
    public View onUpdate(View view, SendGiftBean bean) {//更新
        ImageView ivGiftImage = view.findViewById(R.id.iv_gift_img);//礼物图片
        TextView tvGiftNum = view.findViewById(R.id.tv_gift_amount);//礼物数量

        int showNum = bean.getTheGiftCount() + bean.getTheSendGiftSize();
        // 刷新已存在的giftview界面数据
        Glide.with(context).load(bean.getGiftImg()).into(ivGiftImage);
        tvGiftNum.setText("x" + showNum);
        // 数字刷新动画
        new NumAnim().start(tvGiftNum);
        // 更新tag
        bean.setTheGiftCount(showNum);
        bean.setTheLatestRefreshTime(System.currentTimeMillis());
        view.setTag(bean);
        return view;
    }

    @Override
    public void onKickEnd(SendGiftBean bean) {
        //礼物展示结束，可能由于送礼者过多，轨道被替换导致结束
//        Log.e("zyfff", "onKickEnd:" + bean.getTheGiftId() + "," + bean.getGiftName() + "," + bean.getUserName() + "," + bean.getTheGiftCount());
        logError("onKickEnd礼物展示结束，可能由于送礼者过多，轨道被替换导致结束:".concat(bean.toString()));
    }

    @Override
    public void onComboEnd(SendGiftBean bean) {
        //礼物连击结束,即被系统自动清理时回调
//        Log.e("zyfff","onComboEnd:"+bean.getTheGiftId()+","+bean.getGiftName()+","+bean.getUserName()+","+bean.getTheGiftCount());
        logError("onComboEnd礼物连击结束,即被系统自动清理时回调".concat(bean.toString()));
    }

    @Override
    public void addAnim(View view) {//添加进入动画
        ImageView ivGiftImg = view.findViewById(R.id.iv_gift_img);//礼物图片
        final TextView tvGiftAmount = view.findViewById(R.id.tv_gift_amount);//礼物数量
        // 整个giftview动画
        Animation giftInAnim = AnimUtils.getInAnimation(context);
        // 礼物图像动画
        Animation imgInAnim = AnimUtils.getInAnimation(context);
        // 首次连击动画
        final NumAnim comboAnim = new NumAnim();
        imgInAnim.setStartTime(500);
        imgInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvGiftAmount.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvGiftAmount.setVisibility(View.VISIBLE);
                comboAnim.start(tvGiftAmount);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(giftInAnim);
        ivGiftImg.startAnimation(imgInAnim);
    }

    @Override
    public AnimationSet outAnim() {//添加退出动画
        return AnimUtils.getOutAnimation(context);
    }

    @Override
    public boolean checkUnique(SendGiftBean o, SendGiftBean t) {//鉴别礼物唯一性
        return o.getTheGiftId() == t.getTheGiftId() && o.getTheUserId() == t.getTheUserId();
    }

    @Override
    public SendGiftBean generateBean(SendGiftBean bean) {
        try {
            return (SendGiftBean) bean.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void logError(String msg) {
        LogUtils.Error(msg, false);
    }

    private void logFormat(String format, Object... args) {
        LogUtils.formatError(format, false, args);
    }
}
