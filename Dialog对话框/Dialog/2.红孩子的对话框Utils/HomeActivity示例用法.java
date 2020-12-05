

public class HomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = DialogUtils.createDialog(HomeActivity.this, "标题",
						"内容...", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								boolean isLeft = (boolean) v.getTag();
								if (isLeft) {
									ToastUtils.show(HomeActivity.this,"点击了左键(确定)");
								} else {
									ToastUtils.show(HomeActivity.this, "点击了右键(取消)");
								}
							}
						});
				dialog.show();
			}
		});
	}
}
