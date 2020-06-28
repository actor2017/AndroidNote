package com.ly.bridgeemergency.fragment;

import com.ly.bridgeemergency.info.BannerFindallInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Description: 首页
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/8 on 14:42
 
 * <com.youth.banner.Banner
 *     android:id="@+id/banner"
 *     android:layout_width="match_parent"
 *     android:layout_height="0dp"
 *     app:layout_constraintDimensionRatio="2.5"
 *     app:layout_constraintTop_toTopOf="parent">
 * </com.youth.banner.Banner>
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    private List<String> titles = new ArrayList<>();//标题

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner.setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        toast(String.valueOf(position));
                    }
                })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置banner样式
//                .setIndicatorGravity(BannerConfig.CENTER)

        getBanner();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    //获取轮播提
    private synchronized void getBanner() {
        MyOkHttpUtils.post(getUrl(Global.BANNER_FINDALL), null, new BaseCallback1<BannerFindallInfo>(this) {
            @Override
            public void onOk(@NonNull BannerFindallInfo info, int id) {
                if (checkCode(info.code)) {
                    List<BannerFindallInfo.DataBean> data = info.data;
                    if (data != null && data.size() > 0) {
                        titles.clear();
                        for (BannerFindallInfo.DataBean datum : data) {
                            titles.add(datum.title);
                        }
                        banner.setBannerTitles(titles)
                                .setImages(data)//设置图片集合
                                .start();//banner设置方法全部调用完毕时最后调用
                    }
                } else toast(info.message);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
