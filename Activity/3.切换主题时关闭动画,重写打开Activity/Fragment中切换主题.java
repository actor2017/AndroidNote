//效果见Theme
    /**
	 * 关闭当前activity重新开启，没有动画
	 */
	protected void reload() {
		//关闭activity的转场动画
		Intent intent = getActivity().getIntent();
		getActivity().overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		//关闭fragment依附的MainActivity
		getActivity().finish();

		//重新开启MainActivity，加载fragment，关闭activity动画
		FragmentFactory.clear();
		getActivity().overridePendingTransition(0, 0);
		startActivity(intent);
	}
