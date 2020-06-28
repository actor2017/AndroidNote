package cn.itcast.huanxin12.view.impl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.itcast.huanxin12.R;
import cn.itcast.huanxin12.pressenter.PlugInPressenter;
import cn.itcast.huanxin12.utils.ToastUtils;
import cn.itcast.huanxin12.view.IPlugInView;
import cn.itcast.huanxin12.view.impl.activity.LoginActivity;

import static cn.itcast.huanxin12.R.id.btnLogout;

/**
 * Created by zhengping on 2017/3/18,16:25.
 */

public class PluginFragment extends BaseFragment implements View.OnClickListener, IPlugInView {

    private TextView tv;
    private PlugInPressenter plugInPressenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        /*System.out.println("PluginFragment.onCreateView");
        if(tv == null) {
            System.out.println("PluginFragment.创建View对象");
            tv = new TextView(getActivity());
            tv.setText("动态Fragment");
        }*/
        View view = View.inflate(getActivity(), R.layout.fragment_plugin, null);
        plugInPressenter = new PlugInPressenter(this);
        String currentUsername = plugInPressenter.getCurrentUsername();
        Button btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setText("退出(" + currentUsername + ")");

        btnLogout.setOnClickListener(this);

        /*TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("");*/

        return view;

       // return tv;
    }

    @Override
    public String getTitle() {
        return "动态";
    }

    @Override
    public void onClick(View v) {
        //退出
        plugInPressenter.startLogout();

    }

    @Override
    public void logoutResult(boolean success, String errMsg) {
        if(success) {
            startActivity(LoginActivity.class,true);

        } else {
            ToastUtils.toast(getContext(),errMsg);
        }
    }
}
