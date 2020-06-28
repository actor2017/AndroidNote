    //写动画旋转
    private void startLogoAnim() {
        ImageView iv_icon = (ImageView) findViewById(R.id.iv_icon);
        //设置属性动画:一直旋转
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_icon, "rotationY", 0, 360);
        //设置每次旋转所用时间
        objectAnimator.setDuration(2000);
        //设置重复的模式
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //重复次数
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//infinite无限的,无穷的
        //不要忘了写这句,不然不旋转
        objectAnimator.start();
    }