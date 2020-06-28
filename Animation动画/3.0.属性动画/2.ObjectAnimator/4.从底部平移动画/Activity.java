
    private float          translationY;
	//显示
    private void showContent(View view) {
        translationY = view.getTranslationY();
        int height = view.getHeight() - ConvertUtils.dp2px(30);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", translationY, -height);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }
    //隐藏
    private void hideContent(View view) {
        float translation = view.getTranslationY();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", translation, translationY);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }
	