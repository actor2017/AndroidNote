
public class EnterPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pwd);

        //获取AppLockService.java发过来的包名
        packageName = getIntent().getStringExtra("package");
    }
}
