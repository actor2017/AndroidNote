package cn.itcast.huanxin12.view.impl.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.itcast.huanxin12.R;
import cn.itcast.huanxin12.adapter.ContactsAdapter;
import cn.itcast.huanxin12.bean.ContactsUpdateBean;
import cn.itcast.huanxin12.pressenter.ContactsPressenter;
import cn.itcast.huanxin12.utils.ToastUtils;
import cn.itcast.huanxin12.view.IContactsView;
import cn.itcast.huanxin12.view.impl.activity.ChatActivity;
import cn.itcast.huanxin12.view.impl.activity.SearchActivity;
import cn.itcast.huanxin12.widget.QuickSearchBar;

import static cn.itcast.huanxin12.R.id.iv;

/**
 * Created by zhengping on 2017/3/18,16:25.
 */

public class ContactsFragment extends BaseFragment implements QuickSearchBar.LetterChangedListener, IContactsView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, View.OnLongClickListener {

    private TextView tv;
    private TextView tvTips;
    private ContactsPressenter contactsPressenter;
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;

    private List<String> friendList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//所谓的注册，其实就是将当前这个对象放到一个集合中保存起来
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void haha(ContactsUpdateBean bean) {
        if(bean.needToUpdate) {
            contactsPressenter.initContacts();
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//所谓的注册，其实就是将当前这个对象从集合中移除
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
       /* System.out.println("ContactsFragment.onCreateView");
        if(tv == null) {
            System.out.println("ContactsFragment.创建View对象");
            tv = new TextView(getActivity());
            tv.setText("联系人Fragment");
        }
        return tv;*/
        View view = View.inflate(getActivity(), R.layout.fragment_contacts, null);
        tvTips = (TextView) view.findViewById(R.id.tvTips);

        //ivAdd.setVisibility(View.VISIBLE);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.CYAN,Color.YELLOW);

        swipeRefreshLayout.setOnRefreshListener(this);

        QuickSearchBar quickSearchBar = (QuickSearchBar) view.findViewById(R.id.quickSearchBar);
        quickSearchBar.setLetterChangedListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //设置布局管理器之后，RecyclerView才能正常的显示
        //new GridLayoutManager();//显示GridView的样子
        //new StaggeredGridLayoutManager()//显示瀑布流的样子
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsAdapter = new ContactsAdapter(friendList);
        contactsAdapter.setOnMyItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savedData = (String) v.getTag();
                //每一个item的点击事件都跑到这里来了
                //ToastUtils.toast(getContext(), "item的点击事件," + savedData);
                //跳转聊天界面    和savedData人聊天界面
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChatActivity.class);
                intent.putExtra("toUser", savedData);
                getActivity().startActivity(intent);
                //startActivity(ChatActivity.class, false);
            }
        });

        contactsAdapter.setMyItemOnLongClickListener(this);
        recyclerView.setAdapter(contactsAdapter);

        /*ListView lv;
        lv.setOnItemLongClickListener();*/
        //recyclerView.setonite
        // 只要是view，就一定有长按事件

        //recyclerView.addItemDecoration();
        /*ListView lv;
        lv.setOnItemClickListener();*/
        //recyclerView.setonite
        //只要是View  就一定有点击事件
        /*View view;
        view.setOnClickListener();*/
       /* ListView listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        //获取好友的列表
        contactsPressenter = new ContactsPressenter(this);
        contactsPressenter.initContacts();
        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivAdd.setVisibility(View.VISIBLE);
        //ivBack.setVisibility(View.VISIBLE);
        ivAdd.setOnClickListener(this);
    }

    @Override
    public String getTitle() {
        return "联系人";
    }

    private HashMap<String, Integer> mSectionMap = new HashMap<>();

    @Override
    public void onLetterChanged(String letter) {
        System.out.println("onLetterChange.letter=" + letter);
        tvTips.setVisibility(View.VISIBLE);
        tvTips.setText(letter);
        //发送延迟消息的时候，问一问自己是否需要将之前在消息队列中的延迟消息移除
        mHandler.removeMessages(0);
       // mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, 2000);

        //找出数据集合中  首字母为letter第一次出现的位置
        /*for (int i = 0; i < friendList.size(); i++) {
            String name = friendList.get(i);
            String firstLetter = name.substring(0, 1).toUpperCase();
            if(firstLetter.equals(letter)) {
                recyclerView.smoothScrollToPosition(i);
                break;
            }
        }*/
        Integer integer = mSectionMap.get(letter);
        if(integer != null) {
            recyclerView.smoothScrollToPosition(integer);
        }


    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvTips.setVisibility(View.GONE);
        }
    };

    @Override
    public void showContacts(List<String> goodFriends) {
        friendList.clear();
        friendList.addAll(goodFriends);
        cal();
        contactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateContacts(List<String> goodFriends) {
        friendList.clear();
        friendList.addAll(goodFriends);
        cal();
        contactsAdapter.notifyDataSetChanged();

        //将swipeRefreshLayout刷新的效果隐藏起来
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void removeFriendResult(boolean success, String errMsg) {

        if (success) {
            ToastUtils.toast(getContext(),"删除好友成功");
        } else {
            ToastUtils.toast(getContext(), errMsg);
        }
    }

    private void cal() {
        mSectionMap.clear();
        for (int i = 0; i < friendList.size(); i++) {
            String name = friendList.get(i);
            String firstLetter = name.substring(0, 1).toUpperCase();
            if(!mSectionMap.containsKey(firstLetter)) {
                mSectionMap.put(firstLetter, i);
            }
        }
    }


    @Override
    public void onRefresh() {
        //请求最新的网络数据
        contactsPressenter.initContacts();
    }

    @Override
    public void onClick(View v) {
        startActivity(SearchActivity.class,false);
    }

    @Override
    public boolean onLongClick(View v) {
        String toBeDeleteUsername = (String) v.getTag();
        showDeleteConfirmDialog(toBeDeleteUsername);
        return true;//处理了长按事件
    }

    private void showDeleteConfirmDialog(final String toBeDeleteUsername) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("你确定和" + toBeDeleteUsername + "友尽了吗?");
        builder.setPositiveButton("相忘江湖", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactsPressenter.removeFriend(toBeDeleteUsername);
            }
        });
        builder.setNegativeButton("再续前缘", null);
        builder.show();
    }
}
