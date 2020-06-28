
private AlertDialog editNumAlertDialog = null;
protected AlertDialog getEditNumAlertDialog(String title, String message, String hint) {
	if (editNumAlertDialog == null) {
		final EditText editText = new EditText(this);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);//ֻ��������
		editText.setImeOptions(EditorInfo.IME_ACTION_DONE);//���뷨���½�"���"
		editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));//ֻ����������
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (title != null) builder.setTitle(title);
		if (message != null) builder.setMessage(message);
		if (hint != null) editText.setHint(hint);
		builder.setView(editText);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String content = getText(editText);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		editNumAlertDialog = builder.create();
	}
	return editNumAlertDialog;
}
