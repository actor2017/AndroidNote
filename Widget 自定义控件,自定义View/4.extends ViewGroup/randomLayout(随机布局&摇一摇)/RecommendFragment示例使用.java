package cn.itcast.googleplay12.fragment;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.googleplay12.http.protocol.RecommendProtocol;
import cn.itcast.googleplay12.utils.UiUtils;
import cn.itcast.googleplay12.widget.LoadingPage;
import cn.itcast.googleplay12.widget.random.ShakeListener;
import cn.itcast.googleplay12.widget.random.StellarMap;

/**
 * Created by zhengping on 2017/4/2,11:24.
 */

public class RecommendFragment extends BaseFragment {

    //data = [QQ, 视频, 电子书, 酒店, 单机, 小说, 放开那三国, 斗地主, 优酷, 网游, WIFI万能钥匙, 播放器,
    //捕鱼达人2, 机票, 游戏, 熊出没之熊大快跑, 美图秀秀, 浏览器, 单机游戏, 我的世界, 电影电视, QQ空间,
    //旅游, 免费游戏, 2048, 刀塔传奇, 壁纸, 节奏大师, 锁屏, 装机必备, 天天动听, 备份, 网盘]
    private ArrayList<String> data;
    private StellarMap stellarMap;
    private MyAdapter myAdapter;

    @Override
    public View fragmentCreateSuccessView() {
        stellarMap = new StellarMap(UiUtils.getContext());
        int padding = UiUtils.dp2px(5);
        stellarMap.setRegularity(9, 9);
        stellarMap.setPadding(padding,padding,padding,padding);
        myAdapter = new MyAdapter(data, 3);
        stellarMap.setAdapter(myAdapter);
        stellarMap.setCurrentGroup(0,true);
        ShakeListener shakeListener = new ShakeListener(UiUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.setNextGroup(true);
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPage.ResultState fragmentLoadData() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        data = recommendProtocol.getData();
        System.out.println(data.toString());
        return checkData(data);
    }

    public class MyAdapter extends StellarMap.StellarAdapter<String> {

        public MyAdapter(List<String> data, int groupCount) {
            super(data, groupCount);
        }

        @Override
        public View getView(int groupPosition, int positionInGroup, int positionInAllData, View convertView) {
            if(convertView == null) {
                convertView = new TextView(UiUtils.getContext());
            }
            TextView tv = (TextView) convertView;
            tv.setText(data.get(positionInAllData));
            tv.setTextColor(UiUtils.getRandomColor());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,UiUtils.getRandomTextSize());
            tv.setOnClickListener(onClickListener);//使用同一个对象,没有过多的创建对象
            return convertView;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            String msg = tv.getText().toString();
            UiUtils.toast(msg);
        }
    };
}
