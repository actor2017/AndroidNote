
        // ��ʼ���ؼ�
        Spinner spinner2  = (Spinner) findViewById(R.id.spinner2);
        // ��������Դ
        List<Person>  persons = new ArrayList<Person>();
        persons.add(new Person("����", "�Ϻ� "));
        persons.add(new Person("����", "�Ϻ� "));
        persons.add(new Person("����", "����" ));
        persons.add(new Person("����", "���� "));
        //  ����Adapter������Դ
        MyAdapter myAdapter = new MyAdapter(this, persons);
        //��Adapter
        spinner2.setAdapter(myAdapter);


public class Person {
    private String personName;
    private String personAddress;
    public Person(String personName, String personAddress) {
        super();
        this.personName = personName;
        this.personAddress = personAddress;
    }
}

public class MyAdapter extends BaseAdapter {
    private List<Person> mList;
    private Context mContext;
 
    public MyAdapter(Context pContext, List<Person> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }
 
    @Override
    public int getCount() {
        return mList.size();
    }
 
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * ��������Ҫ����
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_custom, null);
		}
        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
		TextView _TextView1=(TextView)convertView.findViewById(R.id.textView1);
		TextView _TextView2=(TextView)convertView.findViewById(R.id.textView2);
		imageView.setImageResource(R.drawable.ic_launcher);
		_TextView1.setText(mList.get(position).getPersonName());
		_TextView2.setText(mList.get(position).getPersonAddress());
        return convertView;
    }
}