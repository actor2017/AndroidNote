
public class MainActivity extends BaseActivity {

    @BindView(R.id.pt_pagertab)
    PagerTab ptPagertab;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;
	
    private String[] titles = {"推荐", "分类", "排行", "管理"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vpViewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), FragmentFactory.getFragments()));
        ptPagertab.setViewPager(vpViewpager);
    }

    //ViewPager的适配器
    private class MyViewPagerAdapter extends BaseFragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm, fragments);
        }

        @Override
        public String getPagesTitle(int position) {
            return titles[position];
        }
    }
}
