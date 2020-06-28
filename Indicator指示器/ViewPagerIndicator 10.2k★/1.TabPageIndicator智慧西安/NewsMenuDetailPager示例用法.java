
import com.heima.wisdomxian.R;
import com.heima.wisdomxian.bean.NewsData;
import com.heima.wisdomxian.pager.BaseMenuDetailPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import static com.heima.wisdomxian.R.id.indicator;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/25 on 19:14.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {
    ArrayList<NewsData.NewsChildrenData> childrenData;
    private ViewPager vp_viewpager;
    private TabPageIndicator tpi_indicator;

    /**
     * 数据是在NewsCenterPager传递过来
     */
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsChildrenData> childrenData) {
        super(activity);
        this.childrenData = childrenData;
    }

    @Override
    public View initView() {
        //1.加载布局
        View view = View.inflate(activity, R.layout.pager_news_menu_detail, null);
        //2.找到viewPager
        vp_viewpager = (ViewPager) view.findViewById(R.id.vp_viewpager);

        //3.找到    页签
        tpi_indicator = (TabPageIndicator) view.findViewById(indicator);
        //4.页签绑定ViewPager
        //tpi_indicator.setViewPager(vp_viewpager); //在ViewPager设置Adapter之后才能绑定,不然报错

        //找到页签最右边下一页的箭头
        view.findViewById(R.id.ivNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让ViewPager滑动到下一页(居然不做越界判断...)
                vp_viewpager.setCurrentItem(vp_viewpager.getCurrentItem()+1);
            }
        });
        return view;
    }

    //ViewPagerIndicator的使用
    //1、布局文件配置
    //2、初始化控件findViewById
    //3、和ViewPager绑定在一起  注意事项
    //4、重写ViewPager中adapter中的getPageTitle
    @Override
    public void initData() {
        //必须在这里面写,否则会先走父类的super(activity);-->先走initView(),而现在childrenData还未赋值
        vp_viewpager.setAdapter(new mViewPagerAdapter());
        //4.页签绑定ViewPager   在ViewPager设置Adapter之后才能绑定,不然报错
        tpi_indicator.setViewPager(vp_viewpager);
    }

    //ViewPager的适配器
    class mViewPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return childrenData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            TextView textView = new TextView(activity);
            textView.setText("asdfasdf");
            container.addView(textView);            //注意这一步不要忘了,不然没显示
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //设置页签的显示标题
            return childrenData.get(position).title;
        }
    }
}
