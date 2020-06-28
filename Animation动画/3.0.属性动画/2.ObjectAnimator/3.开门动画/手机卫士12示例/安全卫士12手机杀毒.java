            //��ȡ��ǰ���Ȳ��ֵĽ�ͼ
            llProgress.setDrawingCacheEnabled(true);//�������ƻ���
            llProgress.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);//����ͼƬ��������
            Bitmap srcBitmap = llProgress.getDrawingCache();//��ȡ��ǰ����Ľ�ͼ

            //��������������ͼƬ,ivLeft�ڽ��Ⱥͽ����������,ռ��ߵ�λ��
            leftBitmap = getLeftBitmap(srcBitmap);
            ivLeft.setImageBitmap(leftBitmap);

            //ivRight�ڽ��Ⱥͽ����������,ռ�ұߵ�λ��
            rightBitmap = getRightBitmap(srcBitmap);
            ivRight.setImageBitmap(rightBitmap);

            //�������Ŷ���
            startOpenAnim();



//------------------------------------------------------------------
    //�������Ŷ���
    private void startOpenAnim() {
        //1. ��ͼ����
        //2. ��ͼ����
        //3. ��ͼ������ʧ
        //4. ��ͼ������ʧ
        //5. ɨ����������ʾ

        //���Զ���
        //ivLeft.setTranslationX(100);
        //ivLeft.setAlpha();
//        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivLeft, "translationX", 0,
//                -leftBitmap.getWidth());
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivRight, "translationX", 0,
//                rightBitmap.getWidth());

        //���Զ�������
        AnimatorSet set = new AnimatorSet();

        //�������ͬʱ����
        set.playTogether(ObjectAnimator.ofFloat(ivLeft, "translationX", 0,
                -leftBitmap.getWidth()), ObjectAnimator.ofFloat(ivRight, "translationX", 0,
                rightBitmap.getWidth()), ObjectAnimator.ofFloat(ivLeft, "alpha", 1, 0),
                ObjectAnimator.ofFloat(ivRight, "alpha", 1, 0), ObjectAnimator.ofFloat(llResult,
                        "alpha", 0, 1));

        set.setDuration(3000);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnScan.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        //��������
        set.start();
    }


    //��ȡ����ͼ
    private Bitmap getLeftBitmap(Bitmap srcBitmap) {
        //����һ����ͼ, ���Ϊԭͼһ��, �߶Ⱥ�ͼƬ���ú�ԭͼһ��
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap.getWidth() / 2, srcBitmap.getHeight(),
                srcBitmap.getConfig());

        //����, ���ڿ�ͼ��������, �ǽ������Ƶ����ݾͻ����ڿ�ͼ��
        Canvas canvas = new Canvas(bitmap);

        //�ڻ����ϻ���ͼƬ, ���ڿ�ͼ���ֻ��ԭͼһ��, ��ô�ڿ�ͼ�ϻ���ԭͼʱ, ֻ�Ὣԭͼ���������ڿ�ͼ֮��
        //�Ӷ�ʵ�ֽ�ȡ����ͼƬ�Ĺ���
        canvas.drawBitmap(srcBitmap, new Matrix(), null);//import android.graphics.Matrix;

        return bitmap;
    }

    //��ȡ�Ҳ��ͼ
    private Bitmap getRightBitmap(Bitmap srcBitmap) {
        //����һ����ͼ, ���Ϊԭͼһ��, �߶Ⱥ�ͼƬ���ú�ԭͼһ��
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap.getWidth() / 2, srcBitmap.getHeight(),
                srcBitmap.getConfig());

        //����, ���ڿ�ͼ��������, �ǽ������Ƶ����ݾͻ����ڿ�ͼ��
        Canvas canvas = new Canvas(bitmap);

        //�ڻ����ϻ���ͼƬ
        //�ڻ���ʱ, �����ƶ����ԭͼ�Ŀ��, ��ԭͼ�Ҳ�պ����ڻ�����
        Matrix matrix = new Matrix();
        matrix.postTranslate(-srcBitmap.getWidth() / 2, 0);

        canvas.drawBitmap(srcBitmap, matrix, null);

        return bitmap;
    }

