ѧϰ����:http://www.jianshu.com/p/f8bf9e8b0a39

������ע��:ViewPager�ĸ߶ȱ���д�ɶ�ֵ,���д��match_parent�ǳ��п��ܻ����!!!!!!�������

vpViewPager.setCurrentItem(0,true);//�ڶ�������SmoothScroll:�Ƿ�ƽ���Ļ����л�����ҳ��?
vpViewpager.setCurrentItem(vp_viewpager.getCurrentItem()+1);//��ViewPager��������һҳ(��Ȼ����Խ���ж�...)

vp_viewpager.getCurrentItem();//��ȡ�����ǵڼ���item

viewPager.getAdapter().getCount();//���ֲ��ǻ�ȡviewpager��size
viewPager.getChildCount();//��ʾ��ǰ�ɼ�ҳsize,��0--3�б仯

vp_viewpager.setOffscreenPageLimit(123); //viewpager������Ԥ������(>0,������Ч)����ע��:���Ƕ��һ��Ҫ����,�����п��ܲ���ʾ���ݡ���
viewPager.setPageMargin(35);//����page���(BannerЧ��)
viewPager.setPageMarginDrawable();//����drawableId | Drawable, û�Թ�
/**
 * ����ҳ���л�����
 * @param reverseDrawingOrder ����View�ڻ�����viewʱ��˳��,true child����,false child˳��
 * @param ViewPager.PageTransformer transformer,һ���ӿ�,�ص���ʵ�ʶ�������
 * @param pageLayerType https://www.jianshu.com/p/d4a9326543fa
 *                      Ĭ�Ͽ���Ӳ������:ViewCompat.LAYER_TYPE_HARDWARE, ��ʱ���������
 */
viewPager.setPageTransformer(true, new xxxTransformer(xxx));

//========ע��:���ViewPager��Indicatorҳǩ,���ҳ��ı�ļ�����дindicator.addOnPageChangeListener========
indicator.addOnPageChangeListener(�ϱ�˵��,û����֤��)

//ҳ��ı�ļ���
vp_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener {
	//����onOntouchEvent����
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		LogUtils.e(TAG, "��ǰҳ�滮����Ļ�ٷֱ�[0, 1)��" + positionOffset);
		LogUtils.e(TAG, "��ǰҳ��ƫ������[0, ��Ļ���):" + positionOffsetPixels);
    }

	//ѡ��ĳ��ҳ�����
    @Override
    public void onPageSelected(int position) {
        setSlidingMenuEnable(position == 0 || position == mPagers.size()-1);//�Զ��巽��
    }

	//ҳ�滬��״̬�ı�ʱ����
    @Override
    public void onPageScrollStateChanged(int state) {
	    switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://����, ��ȫͣ����
                mHandler.removeMessages(0); //�����ж����Ϣ
                mHandler.sendEmptyMessageDelayed(0,2000);//��д��ʼ�ֲ�
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://ǣ��,��ק, ����������
            case ViewPager.SCROLL_STATE_SETTLING://����,����,�ַſ���ʱ��
            default:
                mHandler.removeMessages(0);
            }
    }
});

    //1.������viewpager��������
    class mAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return COUNT;
        }

        //���������д���ǳ��̶�
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //����ViewPager��ĳһ��λ���ϵ�view����,�Ƚ�������getView
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);//Ҫɾ��,���ȥ�ῴ�����쳣
            //1������View����
            ImageView imageView = new ImageView(GuideActivity.this);
            //imageView.setImageResource(mImgs[position]);      //ͼƬ������,��������
            imageView.setBackgroundResource(mImgs[position]);
            //2����iv��ӵ�container
            container.addView(imageView);
            //3����iv����
            return imageView;       //ע��:������ܷ���container�������������
        }


        //����������䷽ʽ������
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //View view = View.inflate(BigPhotosActivity.this,R.layout.item_bigphoto_pic,null);//����
            View view = LayoutInflater.from(BigPhotosActivity.this).inflate(R.layout//������䷽ʽҲ����
                    .item_bigphoto_pic, container,false);

            ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            //1������View����
            //imageView.setImageResource(mImgs[position]);      //ͼƬ������,��������
            loadImage(BigPhotosActivity.this, pics.get(position).imgUrl, ivPic, null, null);
            //2����iv��ӵ�container
            container.addView(view);
            //3����iv����
            return view;
        }


        //���������д���ǳ��̶�
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);//Ҫɾ��,���ȥ�ῴ�����쳣
            container.removeView((View) object);
        }
		
		@Override
        public CharSequence getPageTitle(int position) {//����д�������,���ر���
        	return super.getPageTitle(position);
    	}
		
		@Override
        public float getPageWidth(int position) {//��ȡpager���?
            return super.getPageWidth(position);
        }
    }


//==========================================================�ֲ���2��ʵ�ַ�ʽ,����============
1.��Ӵ�������	2.���ҳ��ı�ļ���(���������������,��֪��Ϊʲôԭ��)

//===========================================================1.��ViewPager��Ӵ�������==============
    private Handler mHandler;               //ͼƬ�ֲ�

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_pager);

        mHandler = new Handler() {				//����onCreate�г�ʼ��Handler
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int currentItem = vp_viewpager.getCurrentItem();
                int next = currentItem + 1;
                if (next == smallImgsList.size()) {
                    next = 0;
                }
                vp_viewpager.setCurrentItem(next);

                sendEmptyMessageDelayed(0, 2000);
            }
        };
    

        //���ô�������
        vp_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:	//�����µ�ʱ��//ע��,���viewpager�����е���¼�,��ô�������down����.���Ҫд�����͵���¼�,������up�¼����Լ�дʱ���ж�
                        mHandler.removeMessages(0);	//ֹͣ�ֲ�
			mHandler.removeCallbacksAndMessages(null);//����дҲ��
			Toast.makeText(MainActivity.this, "δ���ǵ����˫���¼�", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:		//�����ֲ�
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeMessages(0);	//���Ƴ���Ϣ
                        mHandler.sendEmptyMessageDelayed(0, 2000);
                        break;	//����true��ʾȫȨ���ѵ����¼�,�ᵼ��viewpager�޷���Ӧ����; ���Դ˴�Ҫ����false
                }
                return false;
            }
        });

	//������������
	private void parseJson(String response) {
		Gson gson = new Gson();
		HomeAdInfo homeAdInfo = gson.fromJson(response, HomeAdInfo.class);
		home_images = homeAdInfo.home_images;

		viewPager.setAdapter(new MyPagerAdapter());
		indicator.setViewPager(viewPager);

		//						3.�״ν����ֲ�
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

//==============================================================================================
public boolean dispatchTouchEvent(MotionEvent ev){
	int action = ev.getAction();
	if(action == MotionEvent.ACTION_DOWN){
		//���󸸿ؼ�,���ڿؼ��������¼�
		��ν���󸸿ؼ����������¼�,��˼:���󸸿ؼ������ؽ�������move�¼�
		//���ڵ�ǰ���¼�,�ӿؼ����޷����Ƹ��ؼ��Ƿ�����
		getParent().requestDisallowInterceptTouchEvent(true);
	}
}
