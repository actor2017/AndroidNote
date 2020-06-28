package cn.itcast.huanxin12.view.impl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.huanxin12.R;
import cn.itcast.huanxin12.adapter.ConversationAdapter;
import cn.itcast.huanxin12.bean.MessageReceiveEvent;
import cn.itcast.huanxin12.pressenter.ContactsPressenter;
import cn.itcast.huanxin12.pressenter.ConversationPressenter;
import cn.itcast.huanxin12.view.IConversationView;
import cn.itcast.huanxin12.view.impl.activity.ChatActivity;

/**
 * Created by zhengping on 2017/3/18,16:25.
 */

public class ConversationFragment extends BaseFragment implements IConversationView {

    //private TextView tv;

    private ConversationAdapter conversationAdapter;
    private List<EMConversation> emConversations = new ArrayList<>();
    private ConversationPressenter conversationPressenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void heihei(MessageReceiveEvent event) {
        //重新获取会话信息
        conversationPressenter.getAllConversations();
    }

    @Override
    public void onResume() {
        super.onResume();
        conversationPressenter.getAllConversations();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
       /* System.out.println("ConversationFragment.onCreateView");
        if(tv == null) {
            System.out.println("ConversationFragment.创建View对象");
            tv = new TextView(getActivity());
            tv.setText("会话Fragment");
        }*/
        View view = View.inflate(getActivity(), R.layout.fragment_conversation, null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter(emConversations);
        conversationAdapter.setMyItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toUser = (String) v.getTag();
                //item点击事件的响应
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChatActivity.class);
                intent.putExtra("toUser", toUser);
                getActivity().startActivity(intent);

            }
        });
        conversationAdapter.setMyItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //最好弹出一个选择框
                String toUser = (String) v.getTag();
                //删除会话
                conversationPressenter.deleteConversation(toUser);
                return true;//消费了长按事件
            }
        });
        recyclerView.setAdapter(conversationAdapter);

        // recyclerView.setAdapter();
        conversationPressenter = new ConversationPressenter(this);
        conversationPressenter.getAllConversations();

        return view;
    }

    @Override
    public String getTitle() {
        return "会话";
    }

    @Override
    public void getConversationFinish(List<EMConversation> conversations) {
        emConversations.clear();
        emConversations.addAll(conversations);
        conversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteConversationDone() {
        //重新获取会话信息
        conversationPressenter.getAllConversations();
    }
}
