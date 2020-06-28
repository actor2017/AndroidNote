package com.shijing.huanxin.view.impl.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.shijing.huanxin.R;
import com.shijing.huanxin.bean.ContactsNeedToUpdate;
import com.shijing.huanxin.presenter.ContactsPresenter;
import com.shijing.huanxin.utils.ToastUtils;
import com.shijing.huanxin.view.IContactsView;
import com.shijing.huanxin.view.impl.activity.AddFriendActivity;
import com.shijing.huanxin.view.impl.activity.ChatActivity;
import com.shijing.huanxin.widget.QuickSearchBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 联系人fragment
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 13:52.
 */

public class ContactsFragment extends BaseFragment implements IContactsView {

    ContactsPresenter contactsPresenter = new ContactsPresenter(this);

    private SwipeRefreshLayout srl_swiperefreshlayout;
    private RecyclerView rv_recyclerview;
    private QuickSearchBar qsb_quicksearchbar;
    private MyAdapter mAdapter;
    private ArrayList<String> goodFriends = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    //声明和注释你的订阅方法,选择指定线程模式(onCreate之后写)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusUpdateContacts(ContactsNeedToUpdate bean) {
        if(bean.needToUpdate) {
            contactsPresenter.updateContacts();         //更新好友
            srl_swiperefreshlayout.setRefreshing(true); //刷新
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_contacts,null);

        srl_swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_swiperefreshlayout);
        qsb_quicksearchbar = (QuickSearchBar) view.findViewById(R.id.qsb_quicksearchbar);
        rv_recyclerview = (RecyclerView) view.findViewById(R.id.rv_recuclerview);
        rv_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        final TextView tv_tips = (TextView) view.findViewById(R.id.tvTips);

        srl_swiperefreshlayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.CYAN,Color.YELLOW);
        srl_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_swiperefreshlayout.setRefreshing(true);
                //请求最新的网络数据
                contactsPresenter.updateContacts();
            }
        });

        qsb_quicksearchbar.setOnLetterChangedListener(new QuickSearchBar.OnLetterChangedListener() {
            @Override
            public void onLetterChangedthrow(String letter){
                if (letter != "") {
                    tv_tips.setVisibility(View.VISIBLE);
                    tv_tips.setText(letter);
                }else {
                    tv_tips.setVisibility(View.GONE);
                }
            }
        });
        mAdapter = new MyAdapter();
        rv_recyclerview.setAdapter(mAdapter);
        contactsPresenter.updateContacts();//调用p层方法,更新好友列表
        srl_swiperefreshlayout.setRefreshing(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddFriendActivity.class,false);//添加好友
            }
        });
    }

    @Override
    public String setTitle() {
        return "联系人";
    }

    //更新好友列表
    public void UpdateContacts(List<String> allContactsFromServer){
        goodFriends.clear();
        goodFriends.addAll(allContactsFromServer);
        mAdapter.notifyDataSetChanged();
        srl_swiperefreshlayout.setRefreshing(false);
    }

    //适配器
    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //ListView时,使用此方式加载没问题,但如果使用RecyclerView,此方法加载有可能导致条目的宽度不能填充屏幕
            //View view = View.inflate(parent.getContext(),R.layout.item_contacts,null);
            //参1:布局文件id.参2:当前条目的父控件.参3:是否将当前条目的布局加载到父控件中
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_contacts,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            String name = goodFriends.get(position);                //item显示的姓名
            String letter = name.substring(0,1).toUpperCase();      //item上方显示的字母
            myViewHolder.tvLetter.setText(letter);
            myViewHolder.tvName.setText(name);
            System.out.println(position);
            if (position == 0 && goodFriends.size() > 0) {      //第一个item
                myViewHolder.tvLetter.setVisibility(View.VISIBLE);
            } else {                                            //后面的item
                String nextLetter = goodFriends.get(position-1).substring(0,1).toUpperCase();//上一个位置显示的名称
                if (letter.equals(nextLetter)) {                //如果和上方的一样
                    myViewHolder.tvLetter.setVisibility(View.GONE);//隐藏
                }else {
                    myViewHolder.tvLetter.setVisibility(View.VISIBLE);//显示
                }
            }
            myViewHolder.itemView.setTag(name);             //itemView固定写法
            myViewHolder.itemView.setOnClickListener(listener());//ctrl+alt+m抽取方法
            myViewHolder.itemView.setOnLongClickListener(onLongClickListener());
        }

        @Override
        public int getItemCount() {
            return goodFriends.size();
        }
    }

    //viewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder{

        private  TextView tvName;
        private  TextView tvLetter;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvLetter = (TextView) itemView.findViewById(R.id.tvLetter);
        }
    }

    //点击监听
    @NonNull
    private View.OnClickListener listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //跳到聊天界面
                String name = (String) view.getTag();
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        };
    }

    //长按监听
    @NonNull
    private View.OnLongClickListener onLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String name = (String) view.getTag();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("删除好友");
                alertDialog.setMessage("确定删除"+name+"吗?");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            EMClient.getInstance().contactManager().deleteContact(name);
                            ToastUtils.show(getActivity(),"删除成功");
                            EventBus.getDefault().post(new ContactsNeedToUpdate(true));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ToastUtils.show(getActivity(),"删除失败");
                        }
                    }
                });
                alertDialog.setNegativeButton("取消",null);
                alertDialog.show();
                return false;
            }
        };
    }
}
