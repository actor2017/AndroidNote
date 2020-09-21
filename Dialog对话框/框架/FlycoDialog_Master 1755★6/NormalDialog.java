private NormalDialog normalDialog;

normalDialog = new NormalDialog(activity)
            .title("领取事件")//不是setTitle, 默认"温馨提示"
            .content("确认领取事件吗?")
			.btnText("用得不爽反馈", "去好评");

normalDialog.setOnBtnClickL(null, new OnBtnClickL() {//param1 取消, param2 确认
				@Override
				public void onBtnClick() {
					toast("领取");
				}
			});
normalDialog.show();
