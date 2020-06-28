// 初始化控件
Spinner spinner = (Spinner) findViewById(R.id.spinner1);
// 建立数据源
String[] mItems = getResources().getStringArray(R.array.languages);
// 建立Adapter并且绑定数据源
//第二个参数是Spinner未展开菜单时Spinner的默认样式，android.R.layout.simple_spinner_item, android.R.layout.simple_list_item_1 是系统自带的内置布局。
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
//设置的是展开的时候下拉菜单的样式，同理 android.R.layout.simple_spinner_dropdown_item 也是内置布局。
//不设置setDropDownViewResource, Spinner未展开和展开都是用的一样的布局(android.R.layout.simple_spinner_item)
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
spinner.setAdapter(adapter);

arrayAdapter.notifyDataSetChanged();//可以有数据之后, 再notify

spinner.setOnItemSelectedListener(new OnItemSelectedListener() {//一进页面就会调用
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
   
        String[] languages = getResources().getStringArray(R.array.languages);
        Toast.makeText(MainActivity.this, "你点击的是:"+languages[pos], 2000).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
});