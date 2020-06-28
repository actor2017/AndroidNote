
public class CaseDetailActivity extends BaseActivity {

    private String id;
    private AlertDialog checkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        ButterKnife.bind(this);

        initCheckDialog();//审核对话框
    }

    private void initCheckDialog() {
        View checkDialogView = getLayoutInflater().inflate(R.layout.dialog_check, null);
        final TextInputLayout tilYijian = (TextInputLayout) checkDialogView.findViewById(R.id.til_yijian);
        Button btnOk = (Button) checkDialogView.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) checkDialogView.findViewById(R.id.btn_cancel);
        Button btnOuterPermission = (Button) checkDialogView.findViewById(R.id.btn_outerpermission);
		
        AlertDialog.Builder uploadBuilder = new AlertDialog.Builder(this);
        uploadBuilder.setTitle("请填写审核意见");
        uploadBuilder.setView(checkDialogView);
        checkDialog = uploadBuilder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog.dismiss();
            }
        });
        btnOuterPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}


dialog_check.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    <!--审核dialog的xml-->

    <android.support.design.widget.TextInputLayout
        android:id="@+id/til_yijian"
        android:layout_width="match_parent"
        android:layout_height="140dp"
        android:textColorHint="@color/deep_green"
        app:errorTextAppearance="@style/counterOverflowTextColor"
        app:hintTextAppearance="@style/HintAppearance">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="start"/>
    </android.support.design.widget.TextInputLayout>

    <LinearLayout
        android:id="@+id/ll"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:orientation="horizontal">

        <android.support.v7.widget.CardView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="5dp"
            app:cardCornerRadius="5dp"
            app:cardElevation="5dp"
            app:cardUseCompatPadding="true">

            <Button
                android:id="@+id/btn_ok"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@drawable/selector_button"
                android:padding="5dp"
                android:text="同意"
                android:textColor="@color/white"
                android:textSize="16sp"/>
        </android.support.v7.widget.CardView>

        <android.support.v7.widget.CardView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="5dp"
            app:cardCornerRadius="5dp"
            app:cardElevation="5dp"
            app:cardUseCompatPadding="true">

            <Button
                android:id="@+id/btn_cancel"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@drawable/selector_button_gray"
                android:padding="5dp"
                android:text="不同意"
                android:textColor="@color/white"
                android:textSize="16sp"/>
        </android.support.v7.widget.CardView>
    </LinearLayout>

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        app:cardCornerRadius="5dp"
        app:cardElevation="5dp"
        app:cardUseCompatPadding="true">

        <Button
            android:id="@+id/btn_outerpermission"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/selector_button"
            android:text="超过我的权限"
            android:textColor="@color/white"
            android:textSize="16sp"/>
    </android.support.v7.widget.CardView>
</LinearLayout>
