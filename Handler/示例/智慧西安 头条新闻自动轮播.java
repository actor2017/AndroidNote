 //头条新闻自动轮播
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            int nextItemId = vp_viewpager.getCurrentItem() + 1;
			
            if (nextItemId > vp_viewpager.getAdapter().getCount()-1) {//应该写成这样
                nextItemId = 0;
            }
            vp_viewpager.setCurrentItem(nextItemId);//跳到下一页
            mHandler.removeMessages(0);             //最好在发送前清空
            mHandler.sendEmptyMessageDelayed(0,2000);//形成死循环
        }
    };

    @Override
    protected void onDestroy() {//一定要销毁,否则fragment中会报错
        super.onDestroy();
        bannerHandler.removeMessages(0);
        bannerHandler.removeCallbacksAndMessages(null);
    }


//----------------------------------------------------------------------------------------
        //监听页面的变化
        vp_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                //改变tv_title的值(第一页的值在parseJson()的时候手动赋值)
                tv_title.setText(viewPagerTopnews.get(position).title);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
					case ViewPager.SCROLL_STATE_IDLE://空闲
						mHandler.removeMessages(0); //避免有多个消息
						mHandler.sendEmptyMessageDelayed(0,2000);//重写开始轮播
						break;
					case ViewPager.SCROLL_STATE_DRAGGING://牵引,拖拽
					case ViewPager.SCROLL_STATE_SETTLING://安置,沉陷,应该是按下的意思
					default:
						mHandler.removeMessages(0);
                }
            }
        });

    //解析json
    private void parseJson(String response) {
        Gson gson = new Gson();
        TabInfo tabInfo =  gson.fromJson(response, TabInfo.class);
        viewPagerTopnews = tabInfo.data.topnews;     //头条新闻viewpager的图片信息
        listViewNews = tabInfo.data.news;           //下方listview的条目信息

        tv_title.setText(viewPagerTopnews.get(0).title);//手动的将tvTitle设置为第0个元素的标题
        vp_viewpager.setAdapter(new mPagerAdapter());
        cpi_indicator.setViewPager(vp_viewpager);   //右下角的小圆点,绑定viewpager
        //第一个触发消息,发送延迟消息的时候，问一问自己，这个延迟消息的发送会不会有多个
        //每发送延迟消息的时候，将消息队列中的延迟消息全部移除之后，再发送新的延迟消息
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,2000);

        refreshListViewItem.setAdapter(new mListViewAdapter());//listView设置适配器
    }