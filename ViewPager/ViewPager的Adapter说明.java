1.如果没有用Fragment,ViewPager用PagerAdapter.

2.如果用Fragment,https://www.imooc.com/article/details/id/22021

1.FragmentPagerAdapter:
1.1.每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种(对视图进行attach和detach)

1.2.在ViewPager左右滑动过程中, 会重复至少执行2个生命周期:
onCreateView -> onDestroyView
即, 没有执行onDestroy, Fragment并没有被销毁
几个页面的时候, 推荐使用. viewpager可以设置:setOffscreenPageLimit(int limit)


2.FragmentStatePagerAdapter:
2.1.如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用(对fragment进行完全的添加和删除操)
2.2.在ViewPager左右滑动过程中, 会重复至少执行4个生命周期:
onCreate -> onCreateView -> onDestoryView -> onDestroy
即, Fragment在滑动过程中被销毁了
很多页面的时候, 推荐使用


3.几个页面Fragment的初始化
不能一次性new 多个Fragment, 因为 屏幕旋转/系统恢复页面 后, Activity会重写走onCreate, 再重写new 了多个Fragment,
但是viewpager的adapter的getItem()方法不会重新调用, 多new的几个Fragment没有使用,
此时在Activity中调用fragment的方法其实是调用的新的fragment的方法, 页面会没反应(调用不到原来fragment的方法)
原因:
恢复页面时, 是由viewpager和FragmentPagerAdapter完成.

正确写法:
//SparseArray<E> implements Cloneable, 相当于一个Map, android特有, 效率比Map高很多
private SparseArray<Fragment> fragments = new SparseArray<>();