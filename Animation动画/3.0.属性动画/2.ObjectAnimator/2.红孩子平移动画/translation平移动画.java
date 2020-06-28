	/**
	 * 初始化动画
	 */
	private void initAnimation() {
		animationSet = new AnimatorSet();
		ObjectAnimator translationY1 = ObjectAnimator.ofFloat(ivBaby, "translationY", -800,0);
		ObjectAnimator translationX1 = ObjectAnimator.ofFloat(ivBaby, "translationX", -200,0,200,0,-200,0,200,0);
		ObjectAnimator translationY2 = ObjectAnimator.ofFloat(ivLogo, "translationY",-800,0);
		ObjectAnimator translationX2 = ObjectAnimator.ofFloat(ivLogo, "translationX", 200,0,-200,0,200,0,-200,0);
//		ObjectAnimator translationY = ObjectAnimator.ofFloat(ivLogo, "rotation", 0,45,0-45,0,45,0-45,0);
//		ObjectAnimator translationX = ObjectAnimator.ofFloat(ivLogo, "translationX",0,-200,0,200,0,-200,0,200,0);
		animationSet.setDuration(2000);
		animationSet.playTogether(translationX1,translationY1,translationY2,translationX2);
		animationSet.start();
		
		animationSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				//动画结束后跳转到主页面
				startActivity(new Intent(SplashActivity.this, HomeActivity.class));
				finish();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
	}