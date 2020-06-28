//BaseActivity中重写下面3个方法,实现跳转的动画效果:

//退出动画:

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.from_right_in,R.anim.to_left_out);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.from_left_in,R.anim.to_right_out);
	}


//参见安全卫士新手引导页