package com.cqzonjo.smartfactory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.actor.myandroidframework.widget.BaseItemDecoration;
import com.actor.myandroidframework.widget.BaseSpinner;
import com.cqzonjo.smartfactory.R;
import com.cqzonjo.smartfactory.activity.BaseActivity;
import com.cqzonjo.smartfactory.adapter.MonitorAdapter;
import com.cqzonjo.smartfactory.info.BaseListInfo;
import com.cqzonjo.smartfactory.info.GetAndroidCameraListInfo;
import com.cqzonjo.smartfactory.info.GetProductionLineByUserIdAndMixingPlantIdInfo;
import com.cqzonjo.smartfactory.utils.BaseCallback1;
import com.cqzonjo.smartfactory.utils.Global;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: 生产监控/原料监控/安防监控
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 *
 * @author : 李大发
 * date       : 2021/1/13 on 10:04
 */
public class MonitorFragment extends BaseFragment {

    @BindView(R.id.gsy_video_player)
    NormalGSYVideoPlayer gsyVideoPlayer;
    @BindView(R.id.spinner)
    BaseSpinner          spinner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int id;
    private boolean showSpinner;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Unbinder unbinder;
    private MonitorAdapter mAdapter;
    private Long productionLineId;//产线id
    private List<GetProductionLineByUserIdAndMixingPlantIdInfo> productionLines;

    public MonitorFragment() {
    }

    /**
     * @param id
     * @param showSpinner 生产监控 才显示spinner
     * @return
     */
    public static MonitorFragment newInstance(int id, boolean showSpinner) {
        MonitorFragment fragment = new MonitorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putBoolean(ARG_PARAM2, showSpinner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getInt(ARG_PARAM1);
            showSpinner = arguments.getBoolean(ARG_PARAM2, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner.setVisibility(showSpinner ? View.VISIBLE : View.GONE);
        recyclerView.addItemDecoration(new BaseItemDecoration(Global.densityInt * 14, Global.densityInt * 14));
        recyclerView.setAdapter(mAdapter = new MonitorAdapter((id, number) -> {
            startPlay(id, number);
        }));
        if (showSpinner) {
            productionLines = ((BaseActivity) activity).getProductionLines();
            spinner.setOnItemSelectedListener((parent, view1, position, id) -> {
                productionLineId = productionLines.get(position).id;
            });
            spinner.setDatas(productionLines);
        }
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    protected void firstTimeLoadData() {
        super.firstTimeLoadData();
        getAndroidCameraList();
    }

    //获取安卓监控列表
    private void getAndroidCameraList() {
        params.clear();
        params.put(Global.type, id);
        if (productionLineId != null) {
            params.put(Global.productionLineId, productionLineId);
        }
        MyOkHttpUtils.post(Global.GET_ANDROID_CAMERA_LIST, params, new BaseCallback1<BaseListInfo<GetAndroidCameraListInfo>>(this) {
            @Override
            public void onOk(@NonNull BaseListInfo<GetAndroidCameraListInfo> info, int id) {
                dismissLoadingDialog();
                if (info.isSuccess()) {
                    List<GetAndroidCameraListInfo> data = info.data;
                    mAdapter.setNewData(data);
                    if (data != null && !data.isEmpty()) {
                        GetAndroidCameraListInfo item = data.get(0);
                        if (item != null) {
                            startPlay(item.id, item.number);
                        }
                    }
                } else {
                    toast(info.message);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (gsyVideoPlayer != null) {
            boolean inPlayingState = gsyVideoPlayer.isInPlayingState();
            //如果Fragment显示
            if (isVisibleToUser) {
                //如果没播放
                if (!inPlayingState) {
                    onResume();
                }
            } else {
                //如果播放
                if (inPlayingState) {
                    onPause();
                }
            }
        }
    }

    //开始播放
    private void startPlay(int id, String number) {
        gsyVideoPlayer.setUp(number, false, null);
        gsyVideoPlayer.startPlayLogic();
    }

    @Override
    public void onResume() {
        super.onResume();
        gsyVideoPlayer.onVideoResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        gsyVideoPlayer.onVideoPause();
    }

    public boolean onBackPressed() {
//        return !Jzvd.backPress();
        return !gsyVideoPlayer.isIfCurrentIsFullscreen();
    }

    @Override
    public void onDestroyView() {
        //释放所有
        gsyVideoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
        super.onDestroyView();
        unbinder.unbind();
    }
}