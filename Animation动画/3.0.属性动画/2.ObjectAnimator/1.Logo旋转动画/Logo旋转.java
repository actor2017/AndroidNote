    //д������ת
    private void startLogoAnim() {
        ImageView iv_icon = (ImageView) findViewById(R.id.iv_icon);
        //�������Զ���:һֱ��ת
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_icon, "rotationY", 0, 360);
        //����ÿ����ת����ʱ��
        objectAnimator.setDuration(2000);
        //�����ظ���ģʽ
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //�ظ�����
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//infinite���޵�,�����
        //��Ҫ����д���,��Ȼ����ת
        objectAnimator.start();
    }