
/**
 * 修改门牌类型
 */
public class EditDoorTypeActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;
    private ArrayAdapter<String> adapter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_door_type);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        position = intent.getIntExtra(Global.POSTION,0);
        // 经典的adndroid.R的item
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        // 多选item
        //adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_multichoice);
        // 自己写的item 使用CheckedTextView
        adapter = new ArrayAdapter<String>(this,R.layout.item_edit_door_type);
        // 单选item （也可以当多选用）
        //adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice);
        // 勾选item
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked);
        // 小字体item
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_gallery_item);

        //adapter = new ArrayAdapter<String>(this,R.layout.text_item);
        lv.setAdapter(adapter);
        adapter.add("小区牌/路牌");
        adapter.add("楼牌");
        adapter.add("门牌");
        adapter.add("商铺牌");

        // 其中 多选、单选、勾选 可以用下面2个函数设置 多选/单选 属性
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // 多选
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 单选
        lv.setItemChecked(position,true);//设置选中状态,可单选/多选.true:选中


//        checkedTextView = (CheckedTextView) findViewById(R.layout.my_select_dialog_multichoice);
//        //checkedTextView.setCheckMarkDrawable(android.R.drawable.arrow_down_float);
//
//        //根据数组id得到数组类型
//        TypedArray ta = getBaseContext().getTheme().obtainStyledAttributes(new int[]{R.drawable.icon_box_empty});
//        //初始化绘制目标
//        Drawable indicator = ta.getDrawable(0);
//        checkedTextView = new CheckedTextView(this);
//        checkedTextView.setText("test1");
//        //得到绘制目标后放入选择框中
//        checkedTextView.setCheckMarkDrawable(indicator);

        // item点击监听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String ld = adapter.getItem(position);
            }
        });
    }
}
