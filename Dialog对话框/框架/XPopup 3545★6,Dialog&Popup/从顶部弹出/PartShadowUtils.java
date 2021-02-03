package com.ysytech.zhongjiao.dialog;

import android.view.View;

import com.lxj.xpopup.XPopup;
import com.ysytech.zhongjiao.adapter.SelectEnergyAdapter;
import com.ysytech.zhongjiao.widget.SelectDateView;
import com.ysytech.zhongjiao.widget.SelectDeviceView;
import com.ysytech.zhongjiao.widget.SelectEnergyTypeView;
import com.ysytech.zhongjiao.widget.SelectMaterialView;
import com.ysytech.zhongjiao.widget.SelectPartsView;

import java.util.List;

/**
 * description: 从顶部弹出工具类
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 *
 * @author : 李大发
 * date       : 2021/1/14 on 09
 * @version 1.0
 */
public class PartShadowUtils {

    /**
     * 获取"选择零部件"View, 需要自己在Activity中缓存这个对象
     * @param atView 在哪个view的下方
     * @param listener 回调
     * @return
     */
    public static SelectPartsView getSelectPartsView(View atView, SelectPartsView.OnYesClickListener listener) {
        return (SelectPartsView) new XPopup.Builder(atView.getContext())
                .atView(atView)
                .isClickThrough(true)//是否点击弹窗背景时将点击事件透传到Activity下，默认是false
                .dismissOnTouchOutside(false)//点击外部消失
                .autoOpenSoftInput(true)//是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
                .asCustom(new SelectPartsView(atView.getContext(), listener));
    }

    /**
     * 获取"选择辅材"View, 需要自己在Activity中缓存这个对象
     * @param atView 在哪个view的下方
     * @param listener 回调
     * @return
     */
    public static SelectMaterialView getSelectMaterialView(View atView, SelectMaterialView.OnYesClickListener listener) {
        return (SelectMaterialView) new XPopup.Builder(atView.getContext())
                .atView(atView)
                .isClickThrough(true)//是否点击弹窗背景时将点击事件透传到Activity下，默认是false
                .dismissOnTouchOutside(false)//点击外部消失
                .autoOpenSoftInput(true)//是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
                .asCustom(new SelectMaterialView(atView.getContext(), listener));
    }

    /**
     * 获取"选择设备"View, 需要自己在Activity中缓存这个对象
     * @param atView 在哪个view的下方
     * @param listener 回调
     * @return
     */
    public static SelectDeviceView getSelectDeviceView(View atView, SelectDeviceView.OnYesClickListener listener) {
        return (SelectDeviceView) new XPopup.Builder(atView.getContext())
                .atView(atView)
                .isClickThrough(true)//是否点击弹窗背景时将点击事件透传到Activity下，默认是false
                .dismissOnTouchOutside(false)//点击外部消失
                .autoOpenSoftInput(true)//是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
                .asCustom(new SelectDeviceView(atView.getContext(), listener));
    }

    /**
     * 获取"选择能源类型"View, 需要自己在Activity中缓存这个对象
     * @param atView 在哪个view的下方
     * @param items 列表数据
     * @param listener 回调
     * @return
     */
    public static SelectEnergyTypeView getSelectEnergyTypeView(View atView, List<Object> items, SelectEnergyAdapter.OnItemClickedListener listener) {
        return (SelectEnergyTypeView) new XPopup.Builder(atView.getContext())
                .atView(atView)
                .isClickThrough(true)//是否点击弹窗背景时将点击事件透传到Activity下，默认是false
                .dismissOnTouchOutside(false)//点击外部消失
                .dismissOnBackPressed(false)
                .autoOpenSoftInput(true)//是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
                .asCustom(new SelectEnergyTypeView(atView.getContext(), items, listener));
    }

    /**
     * 获取"选择能源类型"View, 需要自己在Activity中缓存这个对象
     * @param atView 在哪个view的下方
     * @param listener 回调
     * @return
     */
    public static SelectDateView getSelectDateView(View atView, SelectDateView.OnDataSelectedListener listener) {
        return (SelectDateView) new XPopup.Builder(atView.getContext())
                .atView(atView)
                .isClickThrough(true)//是否点击弹窗背景时将点击事件透传到Activity下，默认是false
                .dismissOnTouchOutside(false)//点击外部消失
                .dismissOnBackPressed(false)
                .autoOpenSoftInput(true)//是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
                .asCustom(new SelectDateView(atView.getContext(), listener));
    }
}
