package cn.itheima.redboy.activity;

/**
 * 商品详情页,展示viewpager,描述,评分...
 */
public class ProductDetailPager extends BaseActivity {

    @BindView(R.id.rb_score)
    RatingBar rb_score;            //商品评分AppCompat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_pager);
        ButterKnife.bind(this);

        rb_score.setMax(100);           //1.商品评分最大100

        OkHttpUtils
                .get()
                .url(GlobalConstants.PREF_NET_URL_PREFIX+"product/product_getProductById.html")
                .addParams("proId",""+ proId)
                .addParams("uId",uId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            //在获取最新数据的时候，写，保证sp中的缓存不会太旧
                            //以url为key，保证数据和key一一对应即可
                            parseJson(response);        //http://www.loghare.com/...
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void parseJson(String response) {
        Gson gson = new Gson();
        productDetailInfo = gson.fromJson(response, ProductDetailPagerBean.class);
        //float score = Float.parseFloat(productDetailInfo.product.score);
        //rb_score.setRating( score*5/100);
        rb_score.setProgress(Integer.parseInt(productDetailInfo.product.score));//2.商品评分
    }
}
