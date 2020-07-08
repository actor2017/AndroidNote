
FragmentUtils.add(getSupportFragmentManager(), areaPublicFragment, R.id.ll_container);

FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.ll_container);

FragmentUtils.removeAll(getSupportFragmentManager());//移除所有的fragment

Fragment fragment1 = FragmentUtils.findFragment(getSupportFragmentManager(), ReCommonFragment.class);//查找
