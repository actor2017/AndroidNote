
    private ArrayList<Fragment> fragments;
    private int position = -1;//��¼���ڵ��������һ��

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragments = FragmentFactory.getFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            int fragmentNum = fragments.size();//��¼����
            fragments.clear();//˵��fragment�б����ֵ
            Fragment fragment;
            for (int i = 0; i < fragmentNum; i++) {
                fragment = getSupportFragmentManager().findFragmentByTag("tag"+i);
                fragmentTransaction.add(R.id.fl_framelayout,fragment,"tag"+i);
                fragments.add(fragment);
            }
        }else {
            //��ʼ��
            for (int i = 0; i < fragments.size(); i++) {
                fragmentTransaction.add(R.id.fl_framelayout, fragments.get(i),"tag"+i);//���ϱ��
            }
        }
        fragmentTransaction.commit();
        switchToPosition(0);//Ĭ���л�����0��
    }

    /**
     * �л���ĳһ��fragment,Ϊ�˱���Fragment��onCreateView�ظ�ִ�еķ���
     * 1����ÿһ��Fragment��view�����ɳ�Ա����  ��onCreateView�н��зǿյ��ж�
     * 2����ʹ��replace  ������add ��hide������Fragment
     */
    private void switchToPosition(int i) {
        if (i != position) {
            position = i;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            for (int j = 0; j < fragments.size(); j++) {
                if (j == i) {
                    fragmentTransaction.show(fragments.get(j));
                } else {
                    fragmentTransaction.hide(fragments.get(j));
                }
            }
            ivCollection.setImageResource(i == 0 ? pressedIcons[0] : normalIcons[0]);
            ivWarn.setImageResource(i == 1 ? pressedIcons[1] : normalIcons[1]);
            ivTask.setImageResource(i == 2 ? pressedIcons[2] : normalIcons[2]);
            ivMine.setImageResource(i == 3 ? pressedIcons[3] : normalIcons[3]);
            tvCollection.setTextColor(i == 0 ? Color.parseColor(colors[0]) : Color.parseColor
                    (colors[1]));
            tvWarn.setTextColor(i == 1 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            tvTask.setTextColor(i == 2 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            tvMine.setTextColor(i == 3 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            fragmentTransaction.commit();
        }
    }