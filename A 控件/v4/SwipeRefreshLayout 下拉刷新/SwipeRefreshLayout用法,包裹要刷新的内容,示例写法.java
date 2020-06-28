SwipeRefreshLayout��ͬʱ��������ؼ�
1.SwipeRefreshLayout Ƕ���� NestedScrollView ����.


xml:
    <!--���Ի�����һ�����ؿؼ�v4��,�����ж����ͬ���͵Ŀؼ�,
        ��2��scrollview��bug(����app������ҳ��,��һ��rv�ϻ���,�ڶ���rv�����»�)-->
ע��:�����scrollview,Ӧ�ð�scrollview����������
    <android.support.v4.widget.SwipeRefreshLayout
        android:id="@+id/srl"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView	//RecyclerView����ˢ�¹���,ˢ���߼�Ҫ�Լ�ʵ��
            android:id="@+id/rv_recuclerview"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </android.support.v7.widget.RecyclerView>
    </android.support.v4.widget.SwipeRefreshLayout>


������:
SwipeRefreshLayout srl= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
srl.setColorSchemeColors(Color.RED,Color.BLUE,Color.CYAN,Color.YELLOW);//תһȦ��һ����ɫ
srl.setProgressBackgroundColor(R.color.refresh_bg);//����ȦȦ������ɫ

        int[] colors = new int[100];//100����ɫ
        for (int i = 0; i < colors.length; i++) {
            colors[i] = UiUtils.getRandomColor();
        }
        srl.setColorSchemeColors(colors);


srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//��������ˢ�¼���
    @Override
    public void onRefresh() {}
});

srl.postDelayed(new Runnable() {//�ӳ�3��ִ��run����
    @Override
    public void run() {
        srl.setRefreshing(false);
    }
},3000);

srl.setRefreshing(true/false);//����ˢ��Ч��

srl.isRefreshing();//����Ƿ�����ˢ��

srl.setEnabled(false);//���ò���ˢ��
