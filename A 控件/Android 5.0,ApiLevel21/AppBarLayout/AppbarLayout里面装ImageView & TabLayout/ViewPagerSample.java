public class ViewPagerSample extends AppCompatActivity {

    ViewPager mViewPager;
    List<Fragment> mFragments;

    String[] mTitles = new String[]{
            "��ҳ", "΢��", "���"
    };
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        // ��һ������ʼ��ViewPager��TabLayout
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPager();
    }

    private void setupViewPager() {

        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            ListFragment listFragment = ListFragment.newInstance(mTitles[i]);
            mFragments.add(listFragment);
        }
        // �ڶ�����ΪViewPager����������
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        //  ����������ViewPager��TableLayout ����һ��
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
/**
���ߣ�xujun9411
���ӣ�http://www.jianshu.com/p/f09723b7e887
��Դ������
����Ȩ���������С���ҵת������ϵ���߻����Ȩ������ҵת����ע��������*/