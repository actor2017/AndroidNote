package cn.itcast.viewpager12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    MyViewPager mViewPager;

    int[] mImageIds = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R
            .drawable.a5, R.drawable.a6};

    private RadioGroup rgGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (MyViewPager) findViewById(R.id.my_viewpager);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);

        //给ViewPager添加图片
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mViewPager.addView(view);
        }

        //添加测试页面
        View view = View.inflate(this, R.layout.layout_test, null);
        mViewPager.addView(view, 2);//插入到第三个位置上

        //根据页面个数添加RadioButton
        for (int i = 0; i <= mImageIds.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setId(i);//设置id
            rgGroup.addView(rb);
        }

        rgGroup.check(0);//默认第一个选中

        //监听RaidoButton切换事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = checkedId;

                mViewPager.setCurrentItem(position);
            }
        });

        //监听ViewPager,切换RaidoButton
        mViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int id = position;
                rgGroup.check(id);
            }
        });

    }
}
