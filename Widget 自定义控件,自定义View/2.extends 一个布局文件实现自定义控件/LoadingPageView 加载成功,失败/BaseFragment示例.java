package cn.googleplay12.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.googleplay12.utils.UiUtils;
import cn.googleplay12.widget.LoadingPageView;

/**
 * Created by zhengping on 2017/4/2,11:24.
 */

public abstract class BaseFragment extends Fragment {
    private LoadingPageView loadingPageView;

    //private FrameLayout flContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadingPageView = new LoadingPageView(UiUtils.getContext()){

            /**
             * 返回一个枚举类型:请求后的状态,例:LoadingPageView.ResultState.SUCESS
             */
            @Override
            public ResultState resultState() {
                return fragmentLoadData();
            }

            /**
             * 网络数据加载成功后的布局的初始化
             */
            @Override
            public View initSuccessView() {
                return fragmentCreateSuccessView();
            }
        };
        return loadingPageView;
    }

    /**
     * 返回一个枚举类型:请求后的状态,示例(只是示例,根据情况而定):
     * if(object == null) {
     *     return LoadingPageView.ResultState.ERROR;
     * } else {
     *     if(object instanceof List) {
     *         List list = (List) object;
     *         if(list.size() == 0) {
     *             return LoadingPageView.ResultState.EMPTY;
     *         } else {
     *             return LoadingPageView.ResultState.SUCESS;
     *         }
     *     }
     * }
     * return LoadingPageView.ResultState.ERROR;
     */
    public abstract LoadingPageView.ResultState fragmentLoadData() ;

    /**
     * 网络数据加载成功后的布局的初始化
     */
    public abstract View fragmentCreateSuccessView() ;

    /**
     * 显示加载中的状态(圆形进度条)
     */
    public void loadData() {
        if(loadingPageView != null) {
            loadingPageView.loadData();
        }
    }

//    public LoadingPageView.ResultState checkData(Object object) {
//        if(object == null) {
//            return LoadingPageView.ResultState.ERROR;
//        } else {
//            if(object instanceof List) {
//                List list = (List) object;
//                if(list.size() == 0) {
//                     return LoadingPageView.ResultState.EMPTY;
//                } else {
//                    return LoadingPageView.ResultState.SUCESS;
//                }
//            }
//        }
//        return LoadingPageView.ResultState.ERROR;
//    }
}
