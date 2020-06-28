https://github.com/CymChad/BaseRecyclerViewAdapterHelper
����:https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E9%A6%96%E9%A1%B5
����:http://www.jianshu.com/p/b343fcff51b0
����:http://www.recyclerview.org/

���Ŵ��RecyclerViewӦ�ò���İ��,�����������Ӧ�ö�ʹ�������ˣ���Ҳ��google�Ƽ��滻ListView�Ŀؼ��������ù�����ͬѧӦ�ö�֪������ĳЩ���沢û��ListViewʹ���������㣬��Ҫ���Ƕ���ı�д���룬����͸���ҽ���һ����Դ��BaseRecyclerViewAdapterHelper������������ʹ��RecyclerView��ʱ�򣬺�ListViewһ���ĺ��ã� 
��ô��Ҫ���ˣ�BaseRecyclerViewAdapterHelper����ʲô�� 

������ʲô��
1.�Ż�Adapter����
  ��ԭʼ��adapter��ԣ�����70%�Ĵ�������
2.���Item�¼�
  Item�ĵ���¼�
  Item�ĳ����¼�
  Item�ӿؼ��ĵ���¼�
  Item�ӿؼ��ĳ����¼�
3.����б���ض���
  һ�д��������л�5��Ĭ�϶�����
4.���ͷ����β��
  һ�д���㶨���о��ֻص�ListViewʱ����
5.�Զ�����
  ��������������������¼�,���Զ�����ز��֣���ʾ�쳣��ʾ���Զ����쳣��ʾ��
6.��ӷ���
  ���Ķ������ͷ����
7.�Զ��岻ͬ��item����
  �����á�������д���ⷽ����
8.���ÿղ���
  ��Listview��setEmptyView��Ҫ���á�
9.�����ק������ɾ��
  �������������ɣ�������ô�򵥡�
10.�����������
  ��ExpandableListView��Ҫǿ��֧��������
11.�Զ���ViewHolder
  ֧���Զ���ViewHolder���ÿ���������������


1.���� build.gradle(Project:XXXX) �� repositories ���:
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }//��ҪŪ����,����ᱨ���벻��ȥ
		}
	}

2.Ȼ���� build.gradle(Module:app) �� dependencies ���:
	dependencies {
	        compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'//27
	}
VERSION_CODE�滻������İ汾:https://github.com/CymChad/BaseRecyclerViewAdapterHelper/releases

3.**ע�⣺**����������Ҫд��Ҫ��Ȼ�޷����سɹ���

Ϊʲô��10�����ݣ�ֻ��ʾ1����
����һ��item�Ĳ���������Layout�ǲ���layout_height������match_parent.


4.�п��ܳ��ֵ�bug,�����а�ҳ��,һ��SwipeRefreshLayoutǶ����2��RecyclerView://Ӧ��д2��Fragment!
    //
    @Override
    public void onDestroy() {
        super.onDestroy();
//        myAppAdapter = null;//��ƽ�����˳�app֮���ٴ�,adapter�п���!=null
//        myGameAdapter = null;//����Ҫô������=null,Ҫô�˳�app��ʱ�����System.exit(0);
    }


5.ע�����������:
	mContext//ע�����������
	helper.itemView;
	myAdapter.remove(position);//�Ƴ�item
	myAdapter.getItem(position);//��ȡItem
	myAdapter.getViewByPosition(position, viewId);//
	myAdapter.addData(item/*s*/);//�������
	myAdapter.setNewData(item/null);//����������
	myAdapter.replaceData(@NonNull items);//�滻


6.�ֲ�ˢ��(2019.7.14���)
	myAdapter.refreshNotifyItemChanged(1);//�� + header ������, �ٵ���ԭ��������
	myAdapter.notifyItemChanged(1);//ԭ������
	myAdapter.notifyItemChanged(1, object);//item�ڵľֲ�ˢ��

DiffUtil
//BaseQuickAdapter��ʵ��convertPayloads()���������ھֲ����£�
/**
 *
 * ���� payload info ʱ��ֻ��ִ�д˷���
 *
 * @param helper   A fully initialized helper.
 * @param item     The item that needs to be displayed.
 * @param payloads payload info.
 */
@Override
protected void convertPayloads(BaseViewHolder helper, Item item, @NonNull List<Object> payloads) {
	for (Object p : payloads) {
����
	}
}


�������:
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


�������:ClassCastException ViewHolder,�����:??????
-keep class com.chad.library.adapter.** {
   *;
}


//------------------------------------------ʾ��д��:���� ɾ��item--------------------------
private MyAdapter myAdapter;
protected void onCreate(Bundle savedInstanceState) {
	myAdapter = new MyAdapter(R.layout.item_emp_info, items);//MyAdapter extends BaseQuickAdapter
	myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {//child�ĵ���¼�
		 switch (view.getId()) {}
	}
	recyclerview.setAdapter(empInfoListAdapter);
	request(page = 1);//��������
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
