import android.app.AlertDialog;

AlertDialog.Builder builder = new AlertDialog.Builder(activity);
builder.setTitle("标题");				//可不设置标题
builder.setMessage("内容");				//可不设置内容
builder.setIcon(R.mipmap.ic_launcher);	//设置图标，图片id即可
builder.setView(view);					//通过这样自定义view(中间内容部分)
    view.findViewById();				//通过这样查找view
builder.setCancelable(cancelAble);
builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		toast("点击了确定");
	}
});
builder.setNegativeButton("取消", /*null*/new DialogInterface.OnClickListener() {//如果取消后什么都不需要做,直接传null
	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}
});
builder.setNeutralButton("中间按钮", new DialogInterface.OnClickListener() {//Neutral 中性,中立,中性色调
	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		Toast.makeText(MainActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();
	}
});

AlertDialog alertDialog = builder.create();

//背景透明, 不然自定义view点后面有一个白色框框
alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
alertDialog.getWindow().setDimAmount(0.2f);//设置窗口后面灰色大背景的亮度[0-1]

//默认是 setCancelable()一样的值
//如果 setCancelable = true, setCanceledOnTouchOutside = true/false, 设置都有效
//如果 setCancelable = false, setCanceledOnTouchOutside = true, 点击 '返回'&'外部' 都能取消!!!
alertDialog.setCanceledOnTouchOutside(true);//外部点击是否能取消


2.Service中打开Dialog(垃圾小米手机可能会崩溃, 最好写一个Dialog主题的Activity)
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

AlertDialog alertDialog = new AlertDialog.Builder(XxxService.this).create();
alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//系统界别的dialog，即全局性质的dialog
alertDialog.show();



