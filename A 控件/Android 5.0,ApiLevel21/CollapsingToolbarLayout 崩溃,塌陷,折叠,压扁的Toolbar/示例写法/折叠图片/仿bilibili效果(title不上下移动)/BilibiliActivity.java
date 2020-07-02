package com.kuchuanyun.test;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BilibiliActivity extends AppCompatActivity {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.bbl_play)
    ButtonBarLayout bblPlay;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.ctl_collapsingToolbarLayout)
    CollapsingToolbarLayout ctlCollapsingToolbarLayout;
    @BindView(R.id.nsv_nestedScrollView)
    NestedScrollView nsvNestedScrollView;
    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    private CollapsingToolbarLayoutState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilibili);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {//完全展开状态
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    tbToolBar.setTitle("展开状态");//设置title为EXPANDED
                    bblPlay.setVisibility(View.GONE);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//收缩状态
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    tbToolBar.setTitle("收缩状态");//设置title不显示
                    ivPlay.setVisibility(View.GONE);
                    ivHeader.setVisibility(View.VISIBLE);
                    bblPlay.setVisibility(View.VISIBLE);//显示播放按钮
                } else {//中间状态
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                        tbToolBar.setTitle("中间状态");//设置title为INTERNEDIATE
                        bblPlay.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                    }
                }
            }
        });
    }

    @OnClick({R.id.bbl_play, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bbl_play:
                appBar.setExpanded(true, false);//如果设置true(有展开动画),图片显示/隐藏无效,不知道为什么???????
                ivHeader.setVisibility(View.GONE);
                ivPlay.setVisibility(View.VISIBLE);
                //点击播放/暂停,使AppBarLayout不响应(播放时)/响应(暂停时)滚动事件
                switchCollapsingToolbarLayoutScrollable(ctlCollapsingToolbarLayout);
                break;
            case R.id.fab:
                //如果正在播放视屏
                if (ivHeader.getVisibility() == View.INVISIBLE || ivHeader.getVisibility() ==
                        View.GONE) {
                    switchCollapsingToolbarLayoutScrollable(ctlCollapsingToolbarLayout);
                }
                break;
        }
    }

    /**
     * 切换CollapsingToolbarLayout的滑动状态
     * @param collapsingToolbarLayout
     */
    int scrollFlags = -1;
    AppBarLayout.LayoutParams layoutParams;
    private void switchCollapsingToolbarLayoutScrollable(CollapsingToolbarLayout collapsingToolbarLayout){
        if (layoutParams == null) {
            layoutParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        }
        if (scrollFlags == -1) {//初始化
            scrollFlags = layoutParams.getScrollFlags();
        }
        if (layoutParams.getScrollFlags() != scrollFlags) {//恢复滑动状态
            layoutParams.setScrollFlags(scrollFlags);
        } else {
            layoutParams.setScrollFlags(0);//清空滑动状态
        }
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,//展开状态
        COLLAPSED,//收缩状态
        INTERNEDIATE//中间状态
    }
}
