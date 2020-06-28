
    //设置密码的对话框
    private void ShowSetDialog() {
        //1.创建对话框
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //2.加载弹窗的布局文件对象
        View view = View.inflate(this, R.layout.dialog_set_pwd, null);
        //3.给弹窗设置布局对象
        builder.setView(view);
        //4.通过设置好的builder创建一个dialog对象
        final AlertDialog dialog = builder.create();
        //5.显示对话框   (老师说这个写在前面会空指针,然而并没有)

        //下载过程中,当点击对话框的外面/返回键的时候,设置不能取消对话框★★★★★★★★★★★★
        dialog.setCancelable(false);
        //下面这种方法的意思:按返回键可以关闭dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
		
        //找到控件
        final EditText et_home_item0_input_pwd1 = (EditText) view.findViewById(R.id.et_home_item0_input_pwd1);
        final EditText et_home_item0_input_pwd2 = (EditText) view.findViewById(R.id.et_home_item0_input_pwd2);
        Button btn_positive = (Button) view.findViewById(R.id.btn_home_item0_positive);
        Button btn_negative = (Button) view.findViewById(R.id.btn_home_item0_negative);
        //设置监听
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取控件的值
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
        android:text="设置密码"
        android:textColor="@color/black"
        android:textSize="25sp"/>

    <EditText
        android:id="@+id/et_home_item0_input_pwd1"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_marginTop="10dp"
        android:gravity="center_vertical"
        android:hint="请输入密码"
        android:inputType="textPassword"
        android:textSize="20sp"/>

    <EditText
        android:id="@+id/et_home_item0_input_pwd2"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_marginTop="10dp"
        android:gravity="center_vertical"
        android:hint="请再次输入密码"
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
            android:text="确定"/>

        <Button
            android:id="@+id/btn_home_item0_negative"
            android:layout_width="match_parent"
            android:layout_height="35dp"
            android:layout_weight="1"
            android:background="@drawable/btn_home_item0_negative_selector"
            android:text="取消"/>
    </LinearLayout>
</LinearLayout>
