private NormalListDialog listDialog;
private String[] options = new String[] {"善后处理信息", "桥梁结构受损情况统计", "应急过程资料上传", "总结评估报告与预案修订意见"};
listDialog = new NormalListDialog(activity, options).title("请选择");
listDialog.setOnOperItemClickL(new OnOperItemClickL() {
	@Override
	public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
});
listDialog.show();
