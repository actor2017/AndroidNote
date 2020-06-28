    ArrayList<Fragment> fragmentsList = FragmentFactory.getFragment();//��ʼ��

    @Override
    protected void onCreate(Bundle savedInstanceState) {		//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //��ʼ��
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentsList.size(); i++) {
                fragmentTransaction.add(R.id.fl_fram,fragmentsList.get(i));
        }
        fragmentTransaction.commit();

        //Ĭ���л�����0��
        switchToPosition(0);
}

    /**
     * �л���ĳһ��fragment,Ϊ�˱���Fragment��onCreateView�ظ�ִ�еķ���
     * 1����ÿһ��Fragment��view�����ɳ�Ա����  ��onCreateView�н��зǿյ��ж�
     * 2����ʹ��replace  ������add ��hide������Fragment
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
