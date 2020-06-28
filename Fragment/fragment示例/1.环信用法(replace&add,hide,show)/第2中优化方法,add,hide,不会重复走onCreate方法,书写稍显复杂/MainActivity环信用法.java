    ArrayList<Fragment> fragmentsList = FragmentFactory.getFragment();//初始化

    @Override
    protected void onCreate(Bundle savedInstanceState) {		//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentsList.size(); i++) {
                fragmentTransaction.add(R.id.fl_fram,fragmentsList.get(i));
        }
        fragmentTransaction.commit();

        //默认切换到第0条
        switchToPosition(0);
}

    /**
     * 切换到某一个fragment,为了避免Fragment的onCreateView重复执行的方法
     * 1、将每一个Fragment的view对象变成成员变量  在onCreateView中进行非空的判断
     * 2、不使用replace  而是用add 和hide来操作Fragment
     */
    public void switchToPosition(int position) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentsList.size(); i++) {
            if (i == position) {
                fragmentTransaction.show(fragmentsList.get(i));
            } else {
                fragmentTransaction.hide(fragmentsList.get(i));
            }
        }
        fragmentTransaction.commit();
    }
}
