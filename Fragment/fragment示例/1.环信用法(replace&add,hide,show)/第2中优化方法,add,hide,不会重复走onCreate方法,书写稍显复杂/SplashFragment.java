package cn.itcast.huanxin12.view.impl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.itcast.huanxin12.pressenter.SplashPresenter;
import cn.itcast.huanxin12.view.ISplashView;

/**
 * Created by zhengping on 2017/3/18,10:22.
 */

public class SplashFragment extends Fragment implements ISplashView {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.checkLogin();
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void switchToMainActivity() {

    }

    @Override
    public void switchToLoginActivity() {

    }
}
