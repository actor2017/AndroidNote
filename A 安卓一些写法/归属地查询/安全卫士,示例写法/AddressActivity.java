package com.heima.mobilesafe_work.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.heima.mobilesafe_work.R;
import com.heima.mobilesafe_work.db.dao.AddressDao;
import com.heima.mobilesafe_work.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 归属地查询原理:
 * 1. 在线查询, 将电话号码提交给服务器, 服务器查询后返回归属地
 * 2. 本地查询, 归属地数据库 只保存号码前7位
 * <p>
 * 小米数据库address.db用SQLite Expert Professional示例查询:
 * data1+data2:手机库    data2:座机库
 * 示例查询手机库:
 * select outkey from data1 where id=1351234    ==>930
 * select location from data2 where id=930
 * sql嵌套:select location from data2 where id=(select outkey from data1 where id=1351234)
 * <p>
 * 安卓写代码顺序★★★★★★★★★★★★★★★★★:
 * 1.在main目录下新建assets目录(建成后有图标)
 * 2.把address.db放入assets目录里面
 * 3.把assets目录的address.db用流写入到/data/data/files目录下,因为在assets目录程序无法读取
 *      (见本文件的写法,注意输入流写法!!!★★★★★★★★★★★)
 * 4.在activity同级目录新建文件夹db.dao(两层目录),在这里面写查询类AddressDao
 * 5.调用查询类的自定义方法查询地址:
 *  AddressDao.getAddress(getApplicationContext(), number);
 */

public class AddressActivity extends AppCompatActivity {

    //这是butterKnife声明的2个控件
    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.tv_result)
    TextView tvResult;

    //在初始化过程中,对EditText进行监听
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //下面这句是butterKnife生成
        ButterKnife.bind(this);

        //对etPhoneNum进行输入监听
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("文本变化前回调");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("文本变化中回调");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("文本变化后回调");
                //把结果显示出来etPhoneNum.getText().toString(),可改成:s
                String address = AddressDao.getAddress(getApplicationContext(),s.toString());
                tvResult.setText(address);
            }
        });
    }

    //这个按钮是butterKnife生成的点击事件
    @OnClick(R.id.btn_query)
    public void onClick() {
        if (TextUtils.isEmpty(etPhoneNum.getText().toString())) {
            ToastUtils.show(this,"请输入号码");
            //执行EditText左右晃动和手机振动
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPhoneNum.startAnimation(animation);

            //让手机振动
            vibrate();
        }
    }

    //手机振动需添加权限
    //<uses-permission android:name="android.permission.VIBRATE"/>
    //振动的方法
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);//振动0.5秒
        /**
         * 按照一定节奏来震动; 如果从1开始数数, 数组的奇数位表示停留时间, 偶数为是震动时间
         * 参2:表示循环方式, -1表示不循环, 0代表下次循环从第0个位置开始,如果写9:数组索引越界异常
         */
        //vibrator.vibrate(new long[]{1000,2000,1000,4000,1000,5000},0);
        //vibrator.cancel();//停止振动(感觉没卵用)
    }
}
