
private TranslateAnimation animRight;	//左移动画
private TranslateAnimation animLeft;	//右移动画

//右移
//x方向从0平移到1(宽度1倍)
//(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)
//x轴:相对自己的x轴,从0.相对自己,到1. y轴:相对自己,从0.相对自己,到1.
animRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
		Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
animRight.setDuration(500);

//左移动画
animLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
		Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation
		.RELATIVE_TO_SELF, 0);
animLeft.setDuration(500);


if (isLock) {
	//左移动画, 此方法不会阻塞, 启动动画后,马上会走到下面代码
	view.startAnimation(animLeft);

	//动画结束之后再更新数据
	animLeft.setAnimationListener(new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			//动画开始
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			//动画结束
			//解锁
			//1. 从数据库移除
			//2. 从已加锁集合中移除
			//3. 给未加锁集合添加对象
			//4. 刷新ListView
			mDao.delete(info.packageName);
			mLockList.remove(info);
			mUnlockList.add(info);

			mUnlockAdapter.notifyDataSetChanged();
			mLockAdapter.notifyDataSetChanged();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			//动画重复
		}
	});
} else {
	//右移动画
	view.startAnimation(animRight);

	animRight.setAnimationListener(new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			//加锁
			//1. 给数据库添加
			//2. 从未加锁中移除
			//3. 给已加锁集合添加
			//4. 刷新ListView
			mDao.add(info.packageName);
			mUnlockList.remove(info);
			mLockList.add(info);

			mUnlockAdapter.notifyDataSetChanged();
			mLockAdapter.notifyDataSetChanged();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	});
}