package com.shijing.huanxin.view.impl.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.shijing.huanxin.R;
import com.shijing.huanxin.bean.ContactsNeedToUpdate;
import com.shijing.huanxin.presenter.MainPresenter;
import com.shijing.huanxin.utils.ThreadUtils;
import com.shijing.huanxin.utils.ToastUtils;
import com.shijing.huanxin.view.impl.fragment.FragmentFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 本页面2个控件:一个帧布局,一个↓
 * BottomNavigationBar:页面下方的bar
 */
public class MainActivity extends BaseActivity {

    private MainPresenter mainPresenter;
    ArrayList<Fragment> fragmentsList = FragmentFactory.getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter();
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bnb_bottombar);

        BadgeItem numberBadgeItem = new BadgeItem()				//徽章；证章；标记(item右上角的小圆点)
                //.setBorderWidth(0)//Badge内容和边界的边距 类似于内边距
                //.setBackgroundColorResource(R.color.red)	//Badge的背景色
                .setText("5")					//设置Badge的文字
                //.setTextColor(R.color.white)
                .setHideOnSelect(true); //当点击这个BottomNavigationItem时，隐藏它身上的Badge

        bottomNavigationBar
                .setActiveColor("#00ACFF")//蓝色
                .setInActiveColor("#A9ADB9")//灰色
                .addItem(new BottomNavigationItem(R.drawable.contact_selected_2, "消息"))//添加下方的item
                .addItem(new BottomNavigationItem(R.drawable.conversation_selected_2, "联系人").setBadgeItem(numberBadgeItem))   //可单独设置小圆点(可不设置)
                .addItem(new BottomNavigationItem(R.drawable.plugin_selected_2, "动态"))
                .setFirstSelectedPosition(0)			//默认选择第0个item
                .initialise();

        bottomNavigationBar.setAutoHideEnabled(false);//自动隐藏,用户滑动改变,就一句

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mainPresenter.switchToPosition(MainActivity.this,position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

           }
        });

        //初始化
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentsList.size(); i++) {
                fragmentTransaction.add(R.id.fl_fram,fragmentsList.get(i));
        }
        fragmentTransaction.commit();

        //默认切换到第0条
        mainPresenter.switchToPosition(this,0);

        //添加联系人改变的监听
        addContactsChangedListener();
    }

    //联系人改变的方法,写在MainActivity,没有写在Fragment里面
    private void addContactsChangedListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onFriendRequestAccepted(String username) {
                //好友请求被同意
                ToastUtils.show(MainActivity.this,username+"同意了你的好友请求");
                EventBus.getDefault().post(new ContactsNeedToUpdate(true));
            }

            @Override
            public void onFriendRequestDeclined(String username) {
                //好友请求被拒绝
                ToastUtils.show(MainActivity.this,username+"拒绝了你的好友请求");
                EventBus.getDefault().post(new ContactsNeedToUpdate(false));
            }


            @Override
            public void onContactInvited(final String username, final String reason) {
                System.out.println("currentThreadId:" + Thread.currentThread().getId());
                //收到好友邀请
                ToastUtils.show(MainActivity.this,"收到了:" + username + "的好友邀请,验证信息为:" + reason);
                ThreadUtils.RunOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(username,reason);
                    }
                });
                EventBus.getDefault().post(new ContactsNeedToUpdate(false));
            }


            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
                ToastUtils.show(MainActivity.this,"被" + username + "移除了好友");
                EventBus.getDefault().post(new ContactsNeedToUpdate(true));
                //刷新好友的列表
                //ContactsFragment
                //如何通知ContactsFragment刷新好友的列表呢?
                //通知谁，做一件什么事情   调用这个对象中一个方法
                /*ContactsFragment fragment = (ContactsFragment) FragmentFactory.getFragment(1);
                fragment.update*/
                //组件与组件之间  控件与控件之间的方法调用
                //1、对象调用方法
                //2、观察者设计模式   回调
                //3、广播    杀鸡用牛刀   广播干嘛的？跨进程的通信
                //4、EventBus 更加通用，更加方便
            }


            @Override
            public void onContactAdded(String username) {
                //添加好友后回调此方法
                ToastUtils.show(MainActivity.this,"添加好友:" + username+"成功");
                EventBus.getDefault().post(new ContactsNeedToUpdate(true));
            }
        });
    }

    private void showAlertDialog(final String title, final String message) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(title);
                    ToastUtils.show(MainActivity.this,"添加好友成功"+message);
                    EventBus.getDefault().post(new ContactsNeedToUpdate(true));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ToastUtils.show(MainActivity.this,"添加好友失败"+message+",请检查网络");
                }
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //拒绝添加好友
                try {
                    EMClient.getInstance().contactManager().declineInvitation(title);
                    ToastUtils.show(MainActivity.this,"拒绝添加好友成功"+message);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ToastUtils.show(MainActivity.this,"拒绝添加好友失败"+message+",请检查网络");
                }
            }
        });
        alertDialog.show();
    }
}
