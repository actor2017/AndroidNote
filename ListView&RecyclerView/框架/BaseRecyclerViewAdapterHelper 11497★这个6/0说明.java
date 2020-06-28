https://github.com/CymChad/BaseRecyclerViewAdapterHelper
中文:https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5
简书:http://www.jianshu.com/p/b343fcff51b0
官网:http://www.recyclerview.org/

相信大家RecyclerView应该不会陌生,大多数开发者应该都使用上它了，它也是google推荐替换ListView的控件，但是用过它的同学应该都知道它在某些方面并没有ListView使用起来方便，需要我们额外的编写代码，今天就给大家介绍一个开源库BaseRecyclerViewAdapterHelper，有了它让你使用RecyclerView的时候，和ListView一样的好用！ 
那么你要问了，BaseRecyclerViewAdapterHelper能做什么？ 

它能做什么？
1.优化Adapter代码
  和原始的adapter相对，减少70%的代码量。
2.添加Item事件
  Item的点击事件
  Item的长按事件
  Item子控件的点击事件
  Item子控件的长按事件
3.添加列表加载动画
  一行代码轻松切换5种默认动画。
4.添加头部、尾部
  一行代码搞定，感觉又回到ListView时代。
5.自动加载
  上拉加载无需监听滑动事件,可自定义加载布局，显示异常提示，自定义异常提示。
6.添加分组
  随心定义分组头部。
7.自定义不同的item类型
  简单配置、无需重写额外方法。
8.设置空布局
  比Listview的setEmptyView还要好用。
9.添加拖拽、滑动删除
  开启，监听即可，就是这么简单。
10.分组的伸缩栏
  比ExpandableListView还要强大，支持两级。
11.自定义ViewHolder
  支持自定义ViewHolder，让开发者随心所欲。


1.先在 build.gradle(Project:XXXX) 的 repositories 添加:
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }//不要弄错了,否则会报错导入不进去
		}
	}

2.然后在 build.gradle(Module:app) 的 dependencies 添加:
	dependencies {
	        compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'//27
	}
VERSION_CODE替换成这儿的版本:https://github.com/CymChad/BaseRecyclerViewAdapterHelper/releases

3.**注意：**两个都必须要写，要不然无法加载成功。

为什么有10条数据，只显示1条？
请检查一下item的布局最外层的Layout是不是layout_height设置了match_parent.


4.有可能出现的bug,在排行榜页面,一个SwipeRefreshLayout嵌套了2个RecyclerView://应该写2个Fragment!
    //
    @Override
    public void onDestroy() {
        super.onDestroy();
//        myAppAdapter = null;//在平板上退出app之后再打开,adapter有可能!=null
//        myGameAdapter = null;//所以要么在这里=null,要么退出app的时候加上System.exit(0);
    }


5.注意有这个方法:
	mContext//注意有这个变量
	helper.itemView;
	myAdapter.remove(position);//移除item
	myAdapter.getItem(position);//获取Item
	myAdapter.getViewByPosition(position, viewId);//
	myAdapter.addData(item/*s*/);//添加数据
	myAdapter.setNewData(item/null);//设置新数据
	myAdapter.replaceData(@NonNull items);//替换


6.局部刷新(2019.7.14添加)
	myAdapter.refreshNotifyItemChanged(1);//会 + header 的数量, 再调用原生方法↓
	myAdapter.notifyItemChanged(1);//原生方法
	myAdapter.notifyItemChanged(1, object);//item内的局部刷新

DiffUtil
//BaseQuickAdapter中实现convertPayloads()方法，用于局部更新：
/**
 *
 * 当有 payload info 时，只会执行此方法
 *
 * @param helper   A fully initialized helper.
 * @param item     The item that needs to be displayed.
 * @param payloads payload info.
 */
@Override
protected void convertPayloads(BaseViewHolder helper, Item item, @NonNull List<Object> payloads) {
	for (Object p : payloads) {
……
	}
}


添加依赖:
##-----------------BaseRecyclerViewAdapterHelper-----------------
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
##-----------------BaseRecyclerViewAdapterHelper-----------------


如果报错:ClassCastException ViewHolder,再添加:??????
-keep class com.chad.library.adapter.** {
   *;
}


//------------------------------------------示例写法:包括 删除item--------------------------
private MyAdapter myAdapter;
protected void onCreate(Bundle savedInstanceState) {
	myAdapter = new MyAdapter(R.layout.item_emp_info, items);//MyAdapter extends BaseQuickAdapter
	myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {//child的点击事件
		 switch (view.getId()) {}
	}
	recyclerview.setAdapter(empInfoListAdapter);
	request(page = 1);//请求网络
}

private void request(int page) {
	initZHMPMap(params);
	params.put(Global.id, innerId);
	MyOkHttpUtils.get(Global.URL, params, new BaseCallBack<CheckResultInfo>(this) {
		@Override
		public void onOk(CheckResultInfo info, int id) {
			if(page == 1) items.clear();
			myAdapter.addData(info.list);
		}
	});
}
