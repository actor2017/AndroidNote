//BaseActivity����д����3������,ʵ����ת�Ķ���Ч��:

//�˳�����:

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


//�μ���ȫ��ʿ��������ҳ