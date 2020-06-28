public class SplashActivity extends AppCompatActivity {

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            enterHomeActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
		mhandler.sendEmptyMessageDelayed(0,2000);
    }
}

//==============================—” ±2√ÎÃ¯“≥√Ê==============
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        },2000);
    }