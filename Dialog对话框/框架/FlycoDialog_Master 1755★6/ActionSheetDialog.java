private ActionSheetDialog sheetDialog;
sheetDialog = new ActionSheetDialog(this, options, null).title("选择文件");
sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
	@Override
	public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
});
sheetDialog.show();