public class MainActivity extends Activity {
private AutoCompleteTextView autotext;
private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        autotext =(AutoCompleteTextView) findViewById(R.id.autotext);
        String [] arr={"aa","aab","aac"};
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        autotext.setAdapter(arrayAdapter);
    }

	/*
	<AutoCompleteTextView
		android:id="@+id/autotext"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:completionHint			设置出现在下拉菜单中的提示标题
		android:completionThreshold		设置用户至少输入多少个字符才会显示提示
		android:dropDownHorizontalOffset下拉菜单于文本框之间的水平偏移。默认与文本框左对齐
		android:dropDownHeight			下拉菜单的高度
		android:dropDownWidth			下拉菜单的宽度
		android:singleLine				单行显示
		android:dropDownVerticalOffset	垂直偏移量
		/>
	*/
}