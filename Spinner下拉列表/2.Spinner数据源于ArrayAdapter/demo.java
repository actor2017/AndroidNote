// ��ʼ���ؼ�
Spinner spinner = (Spinner) findViewById(R.id.spinner1);
// ��������Դ
String[] mItems = getResources().getStringArray(R.array.languages);
// ����Adapter���Ұ�����Դ
//�ڶ���������Spinnerδչ���˵�ʱSpinner��Ĭ����ʽ��android.R.layout.simple_spinner_item, android.R.layout.simple_list_item_1 ��ϵͳ�Դ������ò��֡�
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,/* android.R.id.text1,*/ mItems);

//���õ���չ����ʱ�������˵�����ʽ��ͬ�� android.R.layout.simple_spinner_dropdown_item Ҳ�����ò��֡�
//������setDropDownViewResource, Spinnerδչ����չ�������õ�һ���Ĳ���(android.R.layout.simple_spinner_item)
//�������Զ�������: R.layout.item_textview
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
adapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);//v7
//�� Adapter���ؼ�
spinner.setAdapter(adapter);

arrayAdapter.notifyDataSetChanged();//����������֮��, ��notify

spinner.setOnItemSelectedListener(new OnItemSelectedListener() {//һ��ҳ��ͻ����
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
   
        String[] languages = getResources().getStringArray(R.array.languages);
        Toast.makeText(MainActivity.this, "��������:"+languages[pos], 2000).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
});