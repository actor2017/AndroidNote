1.���û����Fragment,ViewPager��PagerAdapter.

2.�����Fragment,https://www.imooc.com/article/details/id/22021

1.FragmentPagerAdapter:
1.1.ÿһ�����ɵ� Fragment �����������ڴ�֮�У������������Щ��Ծ�̬��ҳ������Ҳ�Ƚ��ٵ�����(����ͼ����attach��detach)

1.2.��ViewPager���һ���������, ���ظ�����ִ��2����������:
onCreateView -> onDestroyView
��, û��ִ��onDestroy, Fragment��û�б�����
����ҳ���ʱ��, �Ƽ�ʹ��. viewpager��������:setOffscreenPageLimit(int limit)


2.FragmentStatePagerAdapter:
2.1.�����Ҫ�����кܶ�ҳ���������ݶ�̬�Խϴ�ռ���ڴ�϶�������Ӧ��ʹ��(��fragment������ȫ����Ӻ�ɾ����)
2.2.��ViewPager���һ���������, ���ظ�����ִ��4����������:
onCreate -> onCreateView -> onDestoryView -> onDestroy
��, Fragment�ڻ��������б�������
�ܶ�ҳ���ʱ��, �Ƽ�ʹ��


3.����ҳ��Fragment�ĳ�ʼ��
����һ����new ���Fragment, ��Ϊ ��Ļ��ת/ϵͳ�ָ�ҳ�� ��, Activity����д��onCreate, ����дnew �˶��Fragment,
����viewpager��adapter��getItem()�����������µ���, ��new�ļ���Fragmentû��ʹ��,
��ʱ��Activity�е���fragment�ķ�����ʵ�ǵ��õ��µ�fragment�ķ���, ҳ���û��Ӧ(���ò���ԭ��fragment�ķ���)
ԭ��:
�ָ�ҳ��ʱ, ����viewpager��FragmentPagerAdapter���.

��ȷд��:
//SparseArray<E> implements Cloneable, �൱��һ��Map, android����, Ч�ʱ�Map�ߺܶ�
private SparseArray<Fragment> fragments = new SparseArray<>();