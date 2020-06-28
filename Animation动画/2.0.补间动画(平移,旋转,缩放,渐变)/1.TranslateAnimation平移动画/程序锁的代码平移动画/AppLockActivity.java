
private TranslateAnimation animRight;	//���ƶ���
private TranslateAnimation animLeft;	//���ƶ���

//����
//x�����0ƽ�Ƶ�1(���1��)
//(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)
//x��:����Լ���x��,��0.����Լ�,��1. y��:����Լ�,��0.����Լ�,��1.
animRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
		Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
animRight.setDuration(500);

//���ƶ���
animLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
		Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation
		.RELATIVE_TO_SELF, 0);
animLeft.setDuration(500);


if (isLock) {
	//���ƶ���, �˷�����������, ����������,���ϻ��ߵ��������
	view.startAnimation(animLeft);

	//��������֮���ٸ�������
	animLeft.setAnimationListener(new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			//������ʼ
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			//��������
			//����
			//1. �����ݿ��Ƴ�
			//2. ���Ѽ����������Ƴ�
			//3. ��δ����������Ӷ���
			//4. ˢ��ListView
			mDao.delete(info.packageName);
			mLockList.remove(info);
			mUnlockList.add(info);

			mUnlockAdapter.notifyDataSetChanged();
			mLockAdapter.notifyDataSetChanged();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			//�����ظ�
		}
	});
} else {
	//���ƶ���
	view.startAnimation(animRight);

	animRight.setAnimationListener(new Animation.AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			//����
			//1. �����ݿ����
			//2. ��δ�������Ƴ�
			//3. ���Ѽ����������
			//4. ˢ��ListView
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