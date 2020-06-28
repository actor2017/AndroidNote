
    //��������ĶԻ���
    private void ShowSetDialog() {
        //1.�����Ի���
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //2.���ص����Ĳ����ļ�����
        View view = View.inflate(this, R.layout.dialog_set_pwd, null);
        //3.���������ò��ֶ���
        builder.setView(view);
        //4.ͨ�����úõ�builder����һ��dialog����
        final AlertDialog dialog = builder.create();
        //5.��ʾ�Ի���   (��ʦ˵���д��ǰ����ָ��,Ȼ����û��)

        //���ع�����,������Ի��������/���ؼ���ʱ��,���ò���ȡ���Ի���������������
        dialog.setCancelable(false);
        //�������ַ�������˼:�����ؼ����Թر�dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
		
        //�ҵ��ؼ�
        final EditText et_home_item0_input_pwd1 = (EditText) view.findViewById(R.id.et_home_item0_input_pwd1);
        final EditText et_home_item0_input_pwd2 = (EditText) view.findViewById(R.id.et_home_item0_input_pwd2);
        Button btn_positive = (Button) view.findViewById(R.id.btn_home_item0_positive);
        Button btn_negative = (Button) view.findViewById(R.id.btn_home_item0_negative);
        //���ü���
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //��ȡ�ؼ���ֵ
                String s1 = et_home_item0_input_pwd1.getText().toString().trim();
                String s2 = et_home_item0_input_pwd2.getText().toString().trim();
            }
        });
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

dialog_set_pwd.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="vertical"
              android:padding="10dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="#EFEFEF"
        android:drawableLeft="@drawable/dialog_title_default_icon"
        android:drawablePadding="5dp"
        android:gravity="center_vertical"
        android:text="��������"
        android:textColor="@color/black"
        android:textSize="25sp"/>

    <EditText
        android:id="@+id/et_home_item0_input_pwd1"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_marginTop="10dp"
        android:gravity="center_vertical"
        android:hint="����������"
        android:inputType="textPassword"
        android:textSize="20sp"/>

    <EditText
        android:id="@+id/et_home_item0_input_pwd2"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_marginTop="10dp"
        android:gravity="center_vertical"
        android:hint="���ٴ���������"
        android:inputType="textPassword"
        android:textSize="20sp"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:gravity="center_vertical"
        android:orientation="horizontal">

        <Button
            android:id="@+id/btn_home_item0_positive"
            android:layout_width="match_parent"
            android:layout_height="35dp"
            android:layout_marginRight="20dp"
            android:layout_weight="1"
            android:background="@drawable/btn_home_item0_positive_selector"
            android:text="ȷ��"/>

        <Button
            android:id="@+id/btn_home_item0_negative"
            android:layout_width="match_parent"
            android:layout_height="35dp"
            android:layout_weight="1"
            android:background="@drawable/btn_home_item0_negative_selector"
            android:text="ȡ��"/>
    </LinearLayout>
</LinearLayout>
