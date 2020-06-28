1��ScrollView��HorizontalScrollView��Ϊ�ؼ����߲�����ӹ�����
2�����������ؼ�ֻ����һ�����ӣ������������Ǵ�ͳ�����ϵ�����
3�����������ؼ����Ի���Ƕ��
4����������λ�����ڵ�ʵ�����ǣ�������layout_width��layout_height�趨
5��ScrollView�������ô�ֱ��������HorizontalScrollView��������ˮƽ��������
   ��Ҫע����ǣ���һ��������    scrollbars �������ù������ķ���:����ScrollView���ó�horizontal�Ǻ����ó�none��Ч��ͬ��HorizontalScrollView���ó�vertical��none��Ч��ͬ��

<?xml version="1.0" encoding="utf-8"?>
<HorizontalScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" >

    <ScrollView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content" >

        <LinearLayout
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical" >

            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="@string/hello"
                android:textSize="90sp" />

		... ...���textview

scrollEnabled �Ƿ���Թ���

int i = nsv.computeVerticalScrollRange();//scrollviewȫ���߶�
scrollview.fullScroll(View.FOCUS_DOWN);//scroll�������ײ�
scrollview.pageScroll(View.FOCUS_DOWN);//���»���һҳ

Nestedscrollview Ƕ�� Recyclerview,��RecyclerView�������Ӻ��ܻ������ײ�����:
ThreadUtils.handler.postDelayed(new Runnable() {//Ҫ��ʱ�»�, ���һ�����ݲ�����ʾ����...
    @Override
    public void run() {
        nsv.fullScroll(View.FOCUS_DOWN);
    }
}, 500);


contentSize �������ݵĴ�С�������Թ����Ĵ�С��Ĭ����0��û�й���Ч��
showsHorizontalScrollIndicator ����ʱ�Ƿ���ʾˮƽ������
showsVerticalScrollIndicator ����ʱ�Ƿ���ʾ��ֱ������
bounces Ĭ����YES�����ǹ��������߽�ᷴ�������з���������Ч��������NO�����������߽������ֹͣ

directionalLockEnabled Ĭ����NO�������ڴ�ֱ��ˮƽ����ͬʱ�˶�����ֵ��YESʱ�����ĸ�����ʼ����������һ������Ĺ�����

indicatorStyle ����������ʽ���ܹ�3ɫ��Ĭ�ϡ��ڡ���
scrollIndicatorInsets ���ù�����λ��
decelerating ����������ָ�ſ������ڼ��������С���ʱ��YES������ʱ����NO
decelerationRate ������ָ�ſ���ļ�����


���Ի���
�༭
android:scrollbars
���ù�������ʾ��none�����أ���horizontal��ˮƽ����vertical����ֱ����

android:scrollbarFadeDuration
���ù���������Ч�������е������ı䵭ֱ����ʧ��ʱ�䣬�Ժ���Ϊ��λ��Android2.2�й�����������֮�����ʧ���ٹ����ֻ��������1.5��1.6�汾�����һֱ��ʾ�š�

android:scrollbarSize
���ù������Ŀ�ȡ�

android:scrollbarStyle
���ù������ķ���λ�á�����ֵ��insideOverlay��insideInset��outsideOverlay��outsideInset

android:scrollbarThumbHorizontal
����ˮƽ��������drawable��

android:scrollbarThumbVertical
���ô�ֱ��������drawable.

android:scrollbarTrackHorizontal
����ˮƽ�������������켣����ɫdrawable

android:soundEffectsEnabled
���õ������ʱ�Ƿ�������Ч��


��android���������У����Ƕ���ᷢ�ֵ�����ScrollView�а�����EditText��ͼ��ʱ�򣬳���ҳ����ص�ʱ�򣬻��Զ��������༭�����ڵ�λ�á���ô��ô���׽�����������أ�
ScrollView view = (ScrollView)rootView.findViewById(R.id.home_scrollview);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.requestFocusFromTouch();
                return false;
            }           
        });

���������������

���䣺
��ʹ�ڲ�û��EditText��Ҳ����������������˷���ͬ�����á�

//��������
nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
            oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) {
            LogUtils.error("���»�");
        }
        if (scrollY < oldScrollY) {
            LogUtils.error("���ϻ�");
        }
        if (scrollY == 0) {
            LogUtils.error("����");
        }
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            LogUtils.error("�������˵ײ�");
        }
    }
});

