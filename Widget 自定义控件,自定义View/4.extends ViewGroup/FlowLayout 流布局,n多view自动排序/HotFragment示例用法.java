package cn.itcast.googleplay12.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.itcast.googleplay12.http.protocol.HotProtocol;
import cn.itcast.googleplay12.utils.UiUtils;
import cn.itcast.googleplay12.widget.LoadingPage;
import cn.itcast.googleplay12.widget.MyFlowLayout;

/**
 * Created by zhengping on 2017/4/2,11:24.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public View fragmentCreateSuccessView() {

        //1.把new ScrollView,把MyFlowLayout装进ScrollView(可要可不要,视情况而定)
        ScrollView sc = new ScrollView(UiUtils.getContext());

        //2.把MyFlowLayout new出来
        MyFlowLayout flowLayout = new MyFlowLayout(UiUtils.getContext());
        int padding = UiUtils.dp2px(5);
        //测试sc有padding的情况会否出错,本行没实际意义
        sc.setPadding(padding,padding,padding,padding);
        //测试MyFlowLayout有padding的时候是否会出错,本行没实际意义
        flowLayout.setPadding(padding,padding,padding,padding);

        //遍历集合,把控件全部添加进入MyFlowLayout中
        for (int i = 0; i < data.size(); i++) {
            TextView tv = new TextView(UiUtils.getContext());
            if(i == 11) {
                //这一行是为了测试如果textView有2行的表现,没实际意义
                tv.setText(data.get(i) + "\n"  + data.get(i));
            } else {
                tv.setText(data.get(i));
            }

            //tv在代码中只能设置一个背景,不能设置selector
            //tv.setBackgroundColor(UiUtils.getRandomColor());
            //tv.setBackgroundDrawable(UiUtils.getShape(UiUtils.dp2px(5),UiUtils.getRandomColor()));

            //这是selector的实现类,因为textView的背景有selector,不能直接设置,所有用到了这个类
            StateListDrawable selector = UiUtils.getSelector(UiUtils.getShape(UiUtils.dp2px(5),
                    Color.rgb(200, 200, 200)),
                    UiUtils.getShape(UiUtils.dp2px(5), UiUtils.getRandomColor()));
            //设置背景selector
            tv.setBackgroundDrawable(selector);
            tv.setTextColor(Color.WHITE);
            //设置内间距
            tv.setPadding(padding,padding,padding,padding);
            //设置字体中心显示
            tv.setGravity(Gravity.CENTER);
            //设置点击事件
            tv.setOnClickListener(onClickListener);
            flowLayout.addView(tv);
        }
        sc.addView(flowLayout);
        return sc;
    }

    //点击事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            UiUtils.toast(tv.getText().toString());
        }
    };

    //获取数据
    @Override
    public LoadingPage.ResultState fragmentLoadData() {
        HotProtocol hotProtocol = new HotProtocol();
        data = hotProtocol.getData();
        return checkData(data);
    }
}
