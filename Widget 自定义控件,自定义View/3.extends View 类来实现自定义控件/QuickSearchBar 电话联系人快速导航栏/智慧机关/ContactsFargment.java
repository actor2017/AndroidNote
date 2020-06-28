package com.cisdi.wisdom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cisdi.wisdom.R;
import com.cisdi.wisdom.activity.SearchActivity;
import com.cisdi.wisdom.adapter.ContactsListAdapter;
import com.cisdi.wisdom.api.HttpUtils;
import com.cisdi.wisdom.base.BaseFragment;
import com.cisdi.wisdom.bean.ListAddressBean;
import com.cisdi.wisdom.bean.User;
import com.cisdi.wisdom.util.DataUtil;
import com.cisdi.wisdom.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Copyright (c) 重庆市了赢科技有限公司 <br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：ContactsFargment<br>
 * 摘要：联系人<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：李杰<br>
 * 完成日期：2020/1/8<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰<br>
 * 完成日期：2020/1/8<br>
 */
public class ContactsFargment extends BaseFragment {


    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    Unbinder unbinder;
    @BindView(R.id.title_ser)
    ImageView titleSer;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;

    private ContactsListAdapter contactsListAdapter;
    private ArrayList<User> list = new ArrayList<>();
    private HttpUtils utils;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_contacts, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView(root);
        return root;
    }


    private void initView(View root) {
        utils = new HttpUtils(this);
        listAddress();

        emptyView.setVisibility(View.GONE);
        tv.setText("暂无数据");

        contactsListAdapter = new ContactsListAdapter(mContext, list);
        listView.setAdapter(contactsListAdapter);
        sideBar.setOnStrSelectCallBack((index, selectStr) -> {
            for (int i = 0; i < list.size(); i++) {
                if (selectStr.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                    listView.setSelection(i); // 选择到首字母出现的位置
                    return;
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void listAddress() {
        utils.listAddress(v -> {
            for (ListAddressBean.DataBean datum : v.data) {
                list.add(new User(DataUtil.valueOf(datum.userName), DataUtil.valueOf(datum.mobile), datum.telephones, DataUtil.valueOf(datum.orgName), DataUtil.valueOf(datum.jobTitle)));
            }
            Collections.sort(list);
            contactsListAdapter.notifyDataSetChanged();
        });
    }

    @OnClick({R.id.title_ser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_ser:
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
