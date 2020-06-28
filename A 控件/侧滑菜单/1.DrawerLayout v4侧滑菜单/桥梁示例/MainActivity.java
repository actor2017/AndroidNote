package com.ly.bridgeemergency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.jaeger.library.StatusBarUtil;
import com.ly.bridgeemergency.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/10 on 15:03
 * @deprecated 暂时没跳这个页面, 这个页面有个左侧边栏
 */
@Deprecated
public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)//左侧抽屉布局
    DrawerLayout drawerLayout;
    @BindView(R.id.space_titlebar_height)//标题栏高度
    Space        spaceTitlebarHeight;
    @BindView(R.id.iv_head)//menu中头像
    ImageView    ivHead;
    @BindView(R.id.iv_head2)//主页头像
    ImageView    ivHead2;
    @BindView(R.id.iv_sex)//menu性别
    ImageView    ivSex;
    @BindView(R.id.tv_nick_name)//menu昵称
    TextView     tvNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout,
                getResources().getColor(R.color.white), 20);

        ViewGroup.LayoutParams layoutParams = spaceTitlebarHeight.getLayoutParams();
        layoutParams.height = BarUtils.getStatusBarHeight();
        spaceTitlebarHeight.setLayoutParams(layoutParams);
    }

    @OnClick({R.id.iv_head, R.id.iv_search, R.id.ll_danger_report, R.id.ll_task_manager,
            R.id.ll_contact, R.id.iv_head2, R.id.ll_self_info, R.id.ll_phone,
            R.id.ll_edit_password, R.id.ll_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head://menu头像
                toast("menu头像");
                break;
            case R.id.iv_search://右上角搜索
                startActivity(new Intent(this, TaskManagerListActivity.class));
                toast("搜索");
                break;
            case R.id.ll_danger_report://风险上报
                toast("风险上报");
                break;
            case R.id.ll_task_manager://任务管理
                toast("任务管理");
                break;
            case R.id.ll_contact://通讯录
                toast("通讯录");
                break;
            case R.id.iv_head2://主页头像
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.ll_self_info://menu个人信息
                toast("menu个人信息");
                break;
            case R.id.ll_phone://men手机号码
                toast("men手机号码");
                break;
            case R.id.ll_edit_password://menu修改密码
                toast("menu修改密码");
                break;
            case R.id.ll_exit://menu退出登录
                toast("menu退出登录");
                break;
        }
    }
}
