package cn.itcast.mobilesafe12.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cn.itcast.mobilesafe12.R;

/**
 * Created by Kevin.
 * 自定义归属地风格弹窗
 * <p>
 * 开发流程:
 * 1. 写一个类继承Dialog, 然后在设置页面new一个dialog, 并进行展示 show
 * 2. 给dialog设置布局:setContentView
 * 3. 去掉标题栏, 在values/styles中创建dialog的样式, <item name="windowNoTitle">true</item>
 * 4. 去掉背景, 在values/styles中创建dialog的样式, <item name="android:windowBackground">@color/white</item>
 * 5. 弹窗靠下显示, 原理: 获取弹窗所在的窗口对象, 只要修改了窗口的位置, 弹窗的位置也会相应发生变化
 * 6. 修改弹窗进入和退出动画-->样式
 */

public class AddressDialog extends Dialog {

    private ListView lvList;

    public AddressDialog(Context context) {
        //给dialog设置样式, 去掉标题栏
        super(context, R.style.AddressDialogStyle);

        //给dialog设置布局, 和activity的方法类似
        setContentView(R.layout.dialog_address);

        Window window = getWindow();//获取当前dialog所在的窗口对象

        WindowManager.LayoutParams params = window.getAttributes();//获取当前窗口的属性, 布局参数

        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;//重心位置改为靠下居中显示

        window.setAttributes(params);//将修改后的布局参数作用到窗口上

        lvList = (ListView) findViewById(R.id.lv_list);

//        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    //为了提高扩展性, 解耦, 此处不把adapter写死, 而是由外部调用者传递进来
    public void setAdapter(BaseAdapter adapter) {
        lvList.setAdapter(adapter);
    }

    //由外部调用者实现条目的点击事件
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        lvList.setOnItemClickListener(listener);
    }

}
