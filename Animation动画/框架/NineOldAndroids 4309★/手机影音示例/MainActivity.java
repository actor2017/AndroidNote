package com.itheima.mobileplayer.ui.activity;

public class MainActivity extends BaseActivity {

    private ViewPager vpViewPager;
    private TextView tvMusic;
    private TextView tvVideo;
    private View indicator;
	
    indicator.getLayoutParams().width = width / list.size();//初始化指示器

    @Override
    public void initListener() {
        vpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //触发onOntouchEvent
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            LogUtils.e(TAG, "当前页面划过屏幕百分比[0, 1)：" + positionOffset);
//                LogUtils.e(TAG, "当前页面偏移像素[0, 屏幕宽度):" + positionOffsetPixels);

                int width = indicator.getWidth();
                int startX = position * width;//2. 起始的位置 = 页面位置 * 指示器的宽度
                float moveX = positionOffset * width;//3. 移动的位置 = 划过屏幕的百分比 * 指示器的宽度
                int finalX = (int) (startX + moveX);//1. 最终的位置 = 起始位置 + 移动位置

                /**
				 * indicator 平移
                 * view.setTranslationX(finalX);
                 */
                ViewHelper.setTranslationX(indicator, finalX);
            }
        });
    }
	
    /**
     * 修改标题字体大小、颜色
     */
    private void updateTabs(@Position int position) {
        switch (position) {
            case TABS_VIDEO://视频播放列表
                ViewPropertyAnimator.animate(tvVideo).scaleX(1.2f).scaleY(1.2f);//TextView "视频" 放大
                ViewPropertyAnimator.animate(tvMusic).scaleX(1f).scaleY(1f);//TextView "音乐" 还原
                break;
            case TABS_AUDIO://音乐播放列表
                ViewPropertyAnimator.animate(tvMusic).scaleX(1.2f).scaleY(1.2f);//TextView "音乐" 放大
                ViewPropertyAnimator.animate(tvVideo).scaleX(1f).scaleY(1f);//TextView "视频" 还原
                break;
        }
    }
}
