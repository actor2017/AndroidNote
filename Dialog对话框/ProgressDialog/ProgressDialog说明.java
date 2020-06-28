ProgressDialog extends AlertDialog extends Dialog implements DialogInterface
 
progressDialog = new ProgressDialog(this);
progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆形进度条(默认), STYLE_HORIZONTAL(水平)
progressDialog.setTitle("标题");//getResources().getDrawable()
progressDialog.setIcon(R.drawable.ic_empty);//AlertDialog方法, 设置标题图标
progressDialog.setMessage("提示信息");
progressDialog.setIndeterminate(false);//设置进度是否为不明确
progressDialog.setCancelable(true);//按返回键是否可以取消
progressDialog.setCanceledOnTouchOutside(true);//点击对话框外不消失
progressDialog.setMax(100);//设置最大进度, 默认100
progressDialog.setMim(0);//设置最小进度, 默认0
progressDialog.setProgress(36);
progressDialog.incrementProgressBy(5);//增加进度条的进度
progressDialog.incrementSecondaryProgressBy(12);
progressDialog.setProgressDrawable(drawable);//设置progress发生变化时的进度指示条的背景图

progressDialog.setView();//AlertDialog方法, 设置ProgressDialog的view

//按钮
progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "确定", new Message());
progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
});


//静态方法, 创建的Dialog只能是圆形进度条:
public static ProgressDialog show(Context context, CharSequence title, CharSequence message);
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate);
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable);
//context, 标题, 内容, 是否不明确进度, 按返回键是否可以取消, CancelListener
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener);


progressDialog.cancel();//会回调OnCancelListener
progressDialog.setCancelMessage(new Message());
progressDialog.dismiss();
progressDialog.setDismissMessage(new Message());

progressDialog.show();//Dialog方法

progressDialog.setContentView(progressBar);//Dialog方法, 在show()之后调用




