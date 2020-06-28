ProgressDialog extends AlertDialog extends Dialog implements DialogInterface
 
progressDialog = new ProgressDialog(this);
progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Բ�ν�����(Ĭ��), STYLE_HORIZONTAL(ˮƽ)
progressDialog.setTitle("����");//getResources().getDrawable()
progressDialog.setIcon(R.drawable.ic_empty);//AlertDialog����, ���ñ���ͼ��
progressDialog.setMessage("��ʾ��Ϣ");
progressDialog.setIndeterminate(false);//���ý����Ƿ�Ϊ����ȷ
progressDialog.setCancelable(true);//�����ؼ��Ƿ����ȡ��
progressDialog.setCanceledOnTouchOutside(true);//����Ի����ⲻ��ʧ
progressDialog.setMax(100);//����������, Ĭ��100
progressDialog.setMim(0);//������С����, Ĭ��0
progressDialog.setProgress(36);
progressDialog.incrementProgressBy(5);//���ӽ������Ľ���
progressDialog.incrementSecondaryProgressBy(12);
progressDialog.setProgressDrawable(drawable);//����progress�����仯ʱ�Ľ���ָʾ���ı���ͼ

progressDialog.setView();//AlertDialog����, ����ProgressDialog��view

//��ť
progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "ȷ��", new Message());
progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "ȷ��", new DialogInterface.OnClickListener() {
	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
});


//��̬����, ������Dialogֻ����Բ�ν�����:
public static ProgressDialog show(Context context, CharSequence title, CharSequence message);
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate);
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable);
//context, ����, ����, �Ƿ���ȷ����, �����ؼ��Ƿ����ȡ��, CancelListener
public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener);


progressDialog.cancel();//��ص�OnCancelListener
progressDialog.setCancelMessage(new Message());
progressDialog.dismiss();
progressDialog.setDismissMessage(new Message());

progressDialog.show();//Dialog����

progressDialog.setContentView(progressBar);//Dialog����, ��show()֮�����




