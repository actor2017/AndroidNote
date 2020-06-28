public class MainActivity extends Activity {
    private MultiAutoCompleteTextView mct;
    private static final String[] words={"abc","acd","ade"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        mct=(MultiAutoCompleteTextView) findViewById(R.id.ed);
    ArrayAdapter<String> aa=new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line,words);
    mct.setAdapter(aa);
    mct.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }