import android.app.AlertDialog;

AlertDialog.Builder builder = new AlertDialog.Builder(activity);
builder.setTitle("����");				//�ɲ����ñ���
builder.setMessage("����");				//�ɲ���������
builder.setIcon(R.mipmap.ic_launcher);	//����ͼ�꣬ͼƬid����
builder.setView(view);					//ͨ�������Զ���view(�м����ݲ���)
    view.findViewById();				//ͨ����������view
builder.setCancelable(cancelAble);
builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		toast("�����ȷ��");
	}
});
builder.setNegativeButton("ȡ��", /*null*/new DialogInterface.OnClickListener() {//���ȡ����ʲô������Ҫ��,ֱ�Ӵ�null
	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}
});
builder.setNeutralButton("�м䰴ť", new DialogInterface.OnClickListener() {//Neutral ����,����,����ɫ��
	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		Toast.makeText(MainActivity.this, "����" + which, Toast.LENGTH_SHORT).show();
	}
});

AlertDialog alertDialog = builder.create();

//����͸��, ��Ȼ�Զ���view�������һ����ɫ���
alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
alertDialog.getWindow().setDimAmount(0.2f);//���ô��ں����ɫ�󱳾�������[0-1]

//Ĭ���� setCancelable()һ����ֵ
//��� setCancelable = true, setCanceledOnTouchOutside = true/false, ���ö���Ч
//��� setCancelable = false, setCanceledOnTouchOutside = true, ��� '����'&'�ⲿ' ����ȡ��!!!
alertDialog.setCanceledOnTouchOutside(true);//�ⲿ����Ƿ���ȡ��


2.Service�д�Dialog(����С���ֻ����ܻ����, ���дһ��Dialog�����Activity)
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

AlertDialog alertDialog = new AlertDialog.Builder(XxxService.this).create();
alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//ϵͳ����dialog����ȫ�����ʵ�dialog
alertDialog.show();



