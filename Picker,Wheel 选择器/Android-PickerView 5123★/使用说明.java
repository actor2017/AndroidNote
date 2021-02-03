https://github.com/Bigkoo/Android-PickerView
1.�������
compile 'com.contrarywind:Android-PickerView:4.1.8'

2.�����ļ�����д,����textview���õ���¼�,show����

3.ʾ������
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

        //����ѡ����
        OptionsPickerView.Builder pvOptions =  new OptionsPickerView.Builder(this, new
                OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                /**
                 * ���صķֱ������������ѡ��λ��
                 * options1Items-->options3Items��3��list����
                 */
                String tx = options1Items.get(0)[options1]//(ʾ��)
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3)
                        .getPickerViewText();
                //tvOptions:��Ҫ��ʵ��textview
                tvOptions.setText(tx);
            }
        });
        pvOptions.build();
        /**
         * setPicker(List<T> options1Items, List<List<T>> options2Items) {
         * 3�������list����
         */
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }
}