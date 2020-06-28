package cn.itcast.popupdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //显示小弹窗 showPopup:一个按钮
    //参数View v, 就是当前被点击的Button对象
    public void showPopup(View v) {
        TextView view = new TextView(this);
        view.setText("我是小弹窗哦!!!");
        view.setTextSize(30);
        view.setBackgroundColor(Color.RED);

        //参1:弹窗布局; 参2,3:宽高; 参4:是否有焦点,一般是true
        PopupWindow popup = new PopupWindow(view, 200, 200, true);

        //设置背景,全透明; 只有设置了背景, 点击返回键和窗口外侧, 弹窗才能够消失
        popup.setBackgroundDrawable(new ColorDrawable());

        //显示在某个位置, 参1:布局对象;参2:重心位置; 参3,4:基于重心位置的偏移
        //popup.showAtLocation(rlRoot, Gravity.CENTER, 0, 0);

        //显示在某个控件正下方(推荐)
        //参1:哪个控件; 参2,3:偏移
        popup.showAsDropDown(v, 0, 0);
    }
}
