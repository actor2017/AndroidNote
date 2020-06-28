
    //0~300
    //10,20,30......300
    //中间值
    private void open(View view) {
        //我们创建了一个值的产生器，这个值的产生器可以帮助我们产生0~300的中间值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getMinHeight(),getMaxHeight());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //当产生中间值的时候，会不断的调用此方法,并且此方法运行在主线程
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int temp = (int) animation.getAnimatedValue();
                //获取布局参数
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = temp;
                //设置布局参数
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();//开始产生中间值
    }

    //300~0
    private void close(View view) {
        //我们创建了一个值的产生器，这个值的产生器可以帮助我们产生0~300的中间值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getMaxHeight(),getMinHeight());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //当产生中间值的时候，会不断的调用此方法,并且此方法运行在主线程
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int temp = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = temp;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();//开始产生中间值
    }

    private int getMinHeight() {
        return 0;
    }

    private int getMaxHeight() {
        //我们可以对ll_footer进行手动测量
        int size = UiUtils.getScreenWidth() - UiUtils.dp2px(5);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        ll_footer.measure(widthMeasureSpec, 0);
        return ll_footer.getMeasuredHeight();
        //return ll_footer.getHeight();
    }