
/**
 * 商品详情页,展示viewpager,描述,评分...
 */
public class ProductDetailPagerActivity extends BaseActivity {

    ArrayList<ProductDetailBean> options1Items = new ArrayList<>();
    ArrayList<ProductDetailBean> options2Items = new ArrayList<>();
    private OptionsPickerView CustomOptionsSize;    //这是"规格"的pv.注意不要加.builder,调试了半天.
    private OptionsPickerView CustomOptionsMaterial;//这是"材质"的PickerView,有数据之后才能初始化

    private void parseJson(String response) {
        initCustomOptionsSize();                                        //初始化规格选择
    }

    @OnClick({R.id.tv_select_size, R.id.tv_select_material})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_size:
                if (CustomOptionsSize != null) {
                    CustomOptionsSize.show();     //点击"选择规格"后显示dialog
                }
                break;
            case R.id.tv_select_material:
                if (CustomOptionsMaterial != null) {
                    CustomOptionsMaterial.show(); //点击"选择材质"后显示dialog
                }
                break;
        }
    }

    private void initCustomOptionsSize() {//条件选择器初始化，自定义布局
        CustomOptionsSize = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_select_size.setText(options1Items.get(options1).getPickerViewText());
                LogUtils.Error("options1:"+options1+"   option2:"+option2+"     options3:"+options3);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //中间的标题
                final TextView tvMiddle = (TextView) v.findViewById(R.id.tv_middle);
                tvMiddle.setText(productDetailInfo.product.extras.get(0).name);
                //点击"完成"的监听
                v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomOptionsSize.returnData(v);
                    }
                });
                //点击"取消"的监听
                v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomOptionsSize.dismiss();
                    }
                });
            }
        }).build();
        CustomOptionsSize.setPicker(options1Items);//把集合中的数据添加到显示区域
    }

    private void initCustomOptionsMaterial() {//条件选择器初始化，自定义布局
        CustomOptionsMaterial = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
              //返回的分别是三个级别的选中位置
                tv_select_material.setText(options2Items.get(options1).getPickerViewText());
                LogUtils.Error("options1:"+options1+"   option2:"+option2+"     options3:"+options3);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //中间的标题
                final TextView tvMiddle = (TextView) v.findViewById(R.id.tv_middle);
                tvMiddle.setText(productDetailInfo.product.extras.get(1).name);
                //点击"完成"的监听
                v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomOptionsMaterial.returnData(v);
                    }
                });
                //点击"取消"的监听
                v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomOptionsMaterial.dismiss();
                    }
                });
            }
        }).build();
        CustomOptionsMaterial.setPicker(options2Items);//把集合中的数据添加到显示区域
    }

    //重写返回键
    @Override
    public void onBackPressed() {
        if (CustomOptionsSize != null && CustomOptionsSize.isShowing()) {
            CustomOptionsSize.dismiss();
            return;
        }
        if (CustomOptionsMaterial != null && CustomOptionsMaterial.isShowing()) {
            CustomOptionsMaterial.dismiss();
            return;
        }
        super.onBackPressed();
    }
}
