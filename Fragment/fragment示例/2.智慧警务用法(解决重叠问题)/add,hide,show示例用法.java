
    private ArrayList<Fragment> fragments;
    private int position = -1;//记录现在点击的是哪一个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragments = FragmentFactory.getFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            int fragmentNum = fragments.size();//记录个数
            fragments.clear();//说明fragment有保存的值
            Fragment fragment;
            for (int i = 0; i < fragmentNum; i++) {
                fragment = getSupportFragmentManager().findFragmentByTag("tag"+i);
                fragmentTransaction.add(R.id.fl_framelayout,fragment,"tag"+i);
                fragments.add(fragment);
            }
        }else {
            //初始化
            for (int i = 0; i < fragments.size(); i++) {
                fragmentTransaction.add(R.id.fl_framelayout, fragments.get(i),"tag"+i);//加上标记
            }
        }
        fragmentTransaction.commit();
        switchToPosition(0);//默认切换到第0条
    }

    /**
     * 切换到某一个fragment,为了避免Fragment的onCreateView重复执行的方法
     * 1、将每一个Fragment的view对象变成成员变量  在onCreateView中进行非空的判断
     * 2、不使用replace  而是用add 和hide来操作Fragment
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