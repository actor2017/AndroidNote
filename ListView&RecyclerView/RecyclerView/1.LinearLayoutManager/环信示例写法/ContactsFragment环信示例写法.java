package com.shijing.huanxin.view.impl.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

    private RecyclerView rv_recuclerview;
    private MyAdapter mAdapter;
    private ArrayList<String> goodFriends = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_contacts,null);

        rv_recuclerview = (RecyclerView) view.findViewById(R.id.rv_recuclerview);
        mAdapter = new MyAdapter();
        rv_recuclerview.setAdapter(mAdapter);
        return view;
    }

    //适配器
    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(),R.layout.item_contacts,null);
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

            if (position == 0 && goodFriends.size() > 0) {      //第一个item
                myViewHolder.tvLetter.setVisibility(View.VISIBLE);
            } else {                                            //后面的item
                String nextLetter = goodFriends.get(position).substring(0,1).toUpperCase();//上一个位置显示的名称
                if (letter.equals(nextLetter)) {                //如果和上方的一样
                    myViewHolder.tvLetter.setVisibility(View.GONE);//隐藏
                }else {
                    myViewHolder.tvLetter.setVisibility(View.VISIBLE);//显示
                }
            }
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
}
