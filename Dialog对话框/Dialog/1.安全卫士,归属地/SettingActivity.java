public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivStyle;

    //归属地样式图片
    private int[] mStylePics = new int[]{R.drawable.shape_toast_normal, R.drawable
            .shape_toast_orange, R.drawable.shape_toast_blue, R.drawable.shape_toast_gray, R
            .drawable.shape_toast_green};

    private String[] mStyleNames = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

	//归属地风格设置
	sivStyle = (SettingItemView) findViewById(R.id.siv_style);

	//初始化
        initAddressStyle();
    }

    //归属地风格设置
    private void initAddressStyle() {
        sivStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建dialog,此处context需要传递activity对象
                final AddressDialog dialog = new AddressDialog(SettingActivity.this);

                //调用自定义dialog中的setAdapter(),给dialog中的listview设置数据
                dialog.setAdapter(new AddressAdapter());

                //调用自定义dialog中的setOnItemClickListener(),给dialog中的listview设置条目点击事件
                dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long
                            id) {
                        //将当前被点击的位置记录在sp中
                        PrefUtils.putInt(getApplicationContext(), GlobalConstants
                                .PREF_ADDRESS_STYLE_POS, position);

                        //隐藏当前的dialog
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }


    //归属地风格的数据适配器
    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStyleNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(SettingActivity.this, R.layout.item_address, null);
            }

            ImageView ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            ImageView ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);

            ivPic.setImageResource(mStylePics[position]);
            tvName.setText(mStyleNames[position]);

            //确定对勾显示还是隐藏
            int pos = PrefUtils.getInt(getApplicationContext(), GlobalConstants
                    .PREF_ADDRESS_STYLE_POS, 0);//从sp中取出当前选中的样式位置, 默认是第一个

            if (pos == position) {
                ivSelect.setVisibility(View.VISIBLE);
            } else {
                ivSelect.setVisibility(View.GONE);
            }

            return convertView;
        }
    }