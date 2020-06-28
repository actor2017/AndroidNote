public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivStyle;

    //��������ʽͼƬ
    private int[] mStylePics = new int[]{R.drawable.shape_toast_normal, R.drawable
            .shape_toast_orange, R.drawable.shape_toast_blue, R.drawable.shape_toast_gray, R
            .drawable.shape_toast_green};

    private String[] mStyleNames = new String[]{"��͸��", "������", "��ʿ��", "������", "ƻ����"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

	//�����ط������
	sivStyle = (SettingItemView) findViewById(R.id.siv_style);

	//��ʼ��
        initAddressStyle();
    }

    //�����ط������
    private void initAddressStyle() {
        sivStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //����dialog,�˴�context��Ҫ����activity����
                final AddressDialog dialog = new AddressDialog(SettingActivity.this);

                //�����Զ���dialog�е�setAdapter(),��dialog�е�listview��������
                dialog.setAdapter(new AddressAdapter());

                //�����Զ���dialog�е�setOnItemClickListener(),��dialog�е�listview������Ŀ����¼�
                dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long
                            id) {
                        //����ǰ�������λ�ü�¼��sp��
                        PrefUtils.putInt(getApplicationContext(), GlobalConstants
                                .PREF_ADDRESS_STYLE_POS, position);

                        //���ص�ǰ��dialog
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }


    //�����ط�������������
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

            //ȷ���Թ���ʾ��������
            int pos = PrefUtils.getInt(getApplicationContext(), GlobalConstants
                    .PREF_ADDRESS_STYLE_POS, 0);//��sp��ȡ����ǰѡ�е���ʽλ��, Ĭ���ǵ�һ��

            if (pos == position) {
                ivSelect.setVisibility(View.VISIBLE);
            } else {
                ivSelect.setVisibility(View.GONE);
            }

            return convertView;
        }
    }