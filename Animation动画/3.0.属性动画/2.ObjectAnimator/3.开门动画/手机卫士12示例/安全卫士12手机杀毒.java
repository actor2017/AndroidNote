            //获取当前进度布局的截图
            llProgress.setDrawingCacheEnabled(true);//开启绘制缓存
            llProgress.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);//设置图片缓存质量
            Bitmap srcBitmap = llProgress.getDrawingCache();//获取当前缓存的截图

            //给左右两侧设置图片,ivLeft在进度和结果布局上面,占左边的位置
            leftBitmap = getLeftBitmap(srcBitmap);
            ivLeft.setImageBitmap(leftBitmap);

            //ivRight在进度和结果布局上面,占右边的位置
            rightBitmap = getRightBitmap(srcBitmap);
            ivRight.setImageBitmap(rightBitmap);

            //启动开门动画
            startOpenAnim();



//------------------------------------------------------------------
    //启动开门动画
    private void startOpenAnim() {
        //1. 左图左移
        //2. 右图右移
        //3. 左图渐变消失
        //4. 右图渐变消失
        //5. 扫描结果渐变显示

        //属性动画
        //ivLeft.setTranslationX(100);
        //ivLeft.setAlpha();
//        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivLeft, "translationX", 0,
//                -leftBitmap.getWidth());
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivRight, "translationX", 0,
//                rightBitmap.getWidth());

        //属性动画集合
        AnimatorSet set = new AnimatorSet();

        //多个动画同时运行
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

        //启动动画
        set.start();
    }


    //获取左侧截图
    private Bitmap getLeftBitmap(Bitmap srcBitmap) {
        //创建一个空图, 宽度为原图一半, 高度和图片配置和原图一致
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap.getWidth() / 2, srcBitmap.getHeight(),
                srcBitmap.getConfig());

        //画布, 基于空图创建画布, 那将来绘制的内容就会落在空图上
        Canvas canvas = new Canvas(bitmap);

        //在画布上绘制图片, 由于空图宽度只有原图一半, 那么在空图上绘制原图时, 只会将原图的左半边落在空图之上
        //从而实现截取左半边图片的功能
        canvas.drawBitmap(srcBitmap, new Matrix(), null);//import android.graphics.Matrix;

        return bitmap;
    }

    //获取右侧截图
    private Bitmap getRightBitmap(Bitmap srcBitmap) {
        //创建一个空图, 宽度为原图一半, 高度和图片配置和原图一致
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap.getWidth() / 2, srcBitmap.getHeight(),
                srcBitmap.getConfig());

        //画布, 基于空图创建画布, 那将来绘制的内容就会落在空图上
        Canvas canvas = new Canvas(bitmap);

        //在画布上绘制图片
        //在绘制时, 向左移动半个原图的宽度, 让原图右侧刚好落在画布上
        Matrix matrix = new Matrix();
        matrix.postTranslate(-srcBitmap.getWidth() / 2, 0);

        canvas.drawBitmap(srcBitmap, matrix, null);

        return bitmap;
    }

