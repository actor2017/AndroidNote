package com.cmcc.yyn.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.actor.myandroidframework.dialog.BaseDialog;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cmcc.feinno.entitiy.AreaList;
import com.cmcc.yyn.R;
import com.cmcc.yyn.adapter.AreaAdapter;

import java.util.List;

/**
 * description: 区域选择, 使用:
 * private AreaSelectDialog areaSelectDialog;
 *
 * areaSelectDialog = new AreaSelectDialog(activity);
 * //选择区域回调
 * areaSelectDialog.setOnItemClickListener(new AreaSelectDialog.OnItemClickListener() {
 *     @Override
 *     public void onItemClick(DialogInterface dialog, String name, String code) {
 *         toast(name);
 *         dialog.dismiss();
 *     }
 * });
 * areaSelectDialog.setData(areaListData);
 * int titleBarHeight = rvTitleBar.getHeight();
 * areaSelectDialog.setTitleBarHeight(titleBarHeight);
 * areaSelectDialog.show();
 *
 *
 * @author : 李大发
 * date       : 2020/8/30 on 16:10
 * @version 1.0
 */
public class AreaSelectDialog extends BaseDialog {

    private RecyclerView rvAreaList;
    private AreaAdapter areaAdapter;
    private OnItemClickListener itemClickListener;
    private int barHeight;
    private List<AreaList> data;
    private Activity activity;

    public AreaSelectDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_area_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗口的位置
        window.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        //设置窗口的属性，以便设设置
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ScreenUtils.getScreenWidth() / 3; // 宽度

        //这种方式在有NavBar的时候不行
//        boolean supportNavBar = BarUtils.isSupportNavBar();
//        supportNavBar = BarUtils.isNavBarVisible(window);
//        int marginTop = barHeight + BarUtils.getStatusBarHeight() + (supportNavBar ? BarUtils.getNavBarHeight() : 0);
//        layoutParams.height = ScreenUtils.getScreenHeight() - marginTop; // 高度

        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        layoutParams.height = outRect.height() - barHeight; // 高度


        window.setAttributes(layoutParams);
        window.setDimAmount(0);

        rvAreaList = findViewById(R.id.listview_area);
        areaAdapter = new AreaAdapter();
        //选择区域回调
        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AreaList item = areaAdapter.getItem(position);
                if (item != null) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(AreaSelectDialog.this, item.name, item.code);
                    }
                }
            }
        });
        areaAdapter.setNewData(data);
        rvAreaList.setAdapter(areaAdapter);
    }

    //设置标题栏高度
    public void setTitleBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    //设置列表数据
     public void setData(List<AreaList> data) {
        this.data = data;
        if (areaAdapter != null) {
            areaAdapter.setNewData(data);
        }
    }

    //数据是否为空
    public boolean isDataEmpty() {
        return data == null;
    }

    //设置监听
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(DialogInterface dialog, String name, String code);
    }
}
