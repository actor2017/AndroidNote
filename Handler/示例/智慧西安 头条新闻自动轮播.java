 //ͷ�������Զ��ֲ�
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            int nextItemId = vp_viewpager.getCurrentItem() + 1;
			
            if (nextItemId > vp_viewpager.getAdapter().getCount()-1) {//Ӧ��д������
                nextItemId = 0;
            }
            vp_viewpager.setCurrentItem(nextItemId);//������һҳ
            mHandler.removeMessages(0);             //����ڷ���ǰ���
            mHandler.sendEmptyMessageDelayed(0,2000);//�γ���ѭ��
        }
    };

    @Override
    protected void onDestroy() {//һ��Ҫ����,����fragment�лᱨ��
        super.onDestroy();
        bannerHandler.removeMessages(0);
        bannerHandler.removeCallbacksAndMessages(null);
    }


//----------------------------------------------------------------------------------------
        //����ҳ��ı仯
        vp_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                //�ı�tv_title��ֵ(��һҳ��ֵ��parseJson()��ʱ���ֶ���ֵ)
                tv_title.setText(viewPagerTopnews.get(position).title);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
					case ViewPager.SCROLL_STATE_IDLE://����
						mHandler.removeMessages(0); //�����ж����Ϣ
						mHandler.sendEmptyMessageDelayed(0,2000);//��д��ʼ�ֲ�
						break;
					case ViewPager.SCROLL_STATE_DRAGGING://ǣ��,��ק
					case ViewPager.SCROLL_STATE_SETTLING://����,����,Ӧ���ǰ��µ���˼
					default:
						mHandler.removeMessages(0);
                }
            }
        });

    //����json
    private void parseJson(String response) {
        Gson gson = new Gson();
        TabInfo tabInfo =  gson.fromJson(response, TabInfo.class);
        viewPagerTopnews = tabInfo.data.topnews;     //ͷ������viewpager��ͼƬ��Ϣ
        listViewNews = tabInfo.data.news;           //�·�listview����Ŀ��Ϣ

        tv_title.setText(viewPagerTopnews.get(0).title);//�ֶ��Ľ�tvTitle����Ϊ��0��Ԫ�صı���
        vp_viewpager.setAdapter(new mPagerAdapter());
        cpi_indicator.setViewPager(vp_viewpager);   //���½ǵ�СԲ��,��viewpager
        //��һ��������Ϣ,�����ӳ���Ϣ��ʱ����һ���Լ�������ӳ���Ϣ�ķ��ͻ᲻���ж��
        //ÿ�����ӳ���Ϣ��ʱ�򣬽���Ϣ�����е��ӳ���Ϣȫ���Ƴ�֮���ٷ����µ��ӳ���Ϣ
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,2000);

        refreshListViewItem.setAdapter(new mListViewAdapter());//listView����������
    }