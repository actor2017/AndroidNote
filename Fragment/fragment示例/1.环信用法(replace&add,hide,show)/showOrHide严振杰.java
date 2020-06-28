经过上面的封装，其实已经变的很好用了： 
Activity中：
showHideFragment(null, new SignInFragment());

Fragment中：
showHideFragment(this, new RegisterFragment());

当需要退出的时候：
finish(this);

结合注释看应该是看得懂的，调用show()和hide()方法可以保存fragment的状态，但是逻辑复杂，同样存在以下缺点：
1.没有startFragmetnForResult()用法。
2.没有Activity的standard模式，也就是同一个Fragment启动多个实例。
3.因此我结合support包中的Fragment开发了NoFragment

/**
 * 显示一个新的fragment，并且隐藏旧的fragment。
 *
 * @param outFragment display fragment。
 * @param inFragment  hide fragment。
 * @param <T>         {@link Fragment}.
 */
public <T extends Fragment> void showHideFragment(T outFragment, T inFragment) {
    FragmentManager fManager = getSupportFragmentManager();
    // 如果要隐藏的fragment非空，隐藏。
    if (outFragment != null) {
        fManager
            .beginTransaction()
            .hide(outFragment)
            .commit();
    }
    // 先从栈中看是否存在要显示的fagment。
    String tag = inFragment.getClass().getClass().getSimpleName();
    Fragment tempFragment = fManager.findFragmentByTag(tag);
    if(tempFragment != null) { // 存在则直接显示。
        fManager
            .beginTransaction()
            .show(inFragment)
            .commitAllowingStateLoss();
    } else { // 不存在就添加并显示。
        fManager
            .beginTransaction()
            .add(R.id.fragment_root, inFragment, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss();
    }
}

/**
 * Destroy self.
 */
public <T extends Fragment> void finish(T fragment) {
    FragmentManager fManager = getSupportFragmentManager();
    fManager
        .beginTransaction()
        .remove(fragment)
        .commitAllowingStateLoss();
    }