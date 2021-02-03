https://github.com/Bigkoo/Android-PickerView
1.添加依赖
compile 'com.contrarywind:Android-PickerView:4.1.8'

2.布局文件不用写,例给textview设置点击事件,show出来

3.示例代码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail_pager);

        final ArrayList<String[]> options1Items = new ArrayList();
        final ArrayList<String[]> options2Items = new ArrayList();
        final ArrayList<String[]> options3Items = new ArrayList();
        String[] arr1 = new String[]{""};
        String[] arr2 = new String[]{""};
        String[] arr3 = new String[]{""};
        options1Items.add(arr1);
        options2Items.add(arr2);
        options3Items.add(arr3);

        //条件选择器
        OptionsPickerView.Builder pvOptions =  new OptionsPickerView.Builder(this, new
                OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                /**
                 * 返回的分别是三个级别的选中位置
                 * options1Items-->options3Items是3个list集合
                 */
                String tx = options1Items.get(0)[options1]//(示例)
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3)
                        .getPickerViewText();
                //tvOptions:你要现实的textview
                tvOptions.setText(tx);
            }
        });
        pvOptions.build();
        /**
         * setPicker(List<T> options1Items, List<List<T>> options2Items) {
         * 3个级别的list集合
         */
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }
}