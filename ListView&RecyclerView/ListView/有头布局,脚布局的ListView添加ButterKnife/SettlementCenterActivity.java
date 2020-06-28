package cn.itheima.redboy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itheima.redboy.R;
import cn.itheima.redboy.bean.SettlementCenterInfo;
import cn.itheima.redboy.global.GlobalConstants;
import cn.itheima.redboy.utils.DialogUtils;
import cn.itheima.redboy.utils.LogUtils;
import cn.itheima.redboy.utils.MyOkHttpUtils;
import cn.itheima.redboy.utils.PrefUtils;
import cn.itheima.redboy.utils.ToastUtils;

import static cn.itheima.redboy.R.id.tv_address;
import static cn.itheima.redboy.R.id.tv_ask_invoice;
import static cn.itheima.redboy.R.id.tv_count;
import static cn.itheima.redboy.R.id.tv_deliver_goods_time;
import static cn.itheima.redboy.R.id.tv_deliver_goods_way;
import static cn.itheima.redboy.R.id.tv_discountMsg;
import static cn.itheima.redboy.R.id.tv_money;
import static cn.itheima.redboy.R.id.tv_name;
import static cn.itheima.redboy.R.id.tv_pay_money;
import static cn.itheima.redboy.R.id.tv_pay_way;
import static cn.itheima.redboy.R.id.tv_phonenum;
import static cn.itheima.redboy.R.id.tv_vip_money;
import static cn.itheima.redboy.R.id.tv_way_money;

/**
 * 结算中心
 */
public class SettlementCenterActivity extends BaseActivity {

    @BindView(tv_name)              //收货人姓名
    TextView tv_Name;
    @BindView(tv_phonenum)          //手机号码
    TextView tv_Phonenum;
    @BindView(tv_address)           //地址
    TextView tv_Address;
    @BindView(tv_pay_way)           //支付方式
    TextView tv_PayWay;
    @BindView(tv_deliver_goods_time)//送货时间
    TextView tv_DeliverGoodsTime;
    @BindView(tv_deliver_goods_way) //送货方式
    TextView tv_DeliverGoodsWay;
    @BindView(tv_ask_invoice)       //索取发票
    TextView tv_AskInvoice;

    @BindView(tv_count)             //数量总计
    TextView tv_Count;
    @BindView(tv_money)             //原始金额
    TextView tv_Money;
    @BindView(tv_way_money)         //运费
    TextView tv_WayMoney;
    @BindView(tv_vip_money)         //促销优惠金额
    TextView tv_VipMoney;
    @BindView(tv_pay_money)         //应支付金额
    TextView tv_PayMoney;
    @BindView(tv_discountMsg)       //优惠信息,例:"满300减100"
    TextView tv_DiscountMsg;

    private String uid;                                         //用户id
    private Dialog dialog;                                      //提示登录的dialog
    private ListView lv_listview;                               //listview
    private SettlementCenterInfo settlementCenterInfo;          //结算中心返回的购物车数据
    private SettlementCenterActivity.mListAdapter mListAdapter; //适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_center);

        tvCenter.setText("结算中心");
        tvRight.setText("提交订单");
        tvRight.setVisibility(View.VISIBLE);
        lv_listview = (ListView) findViewById(R.id.lv_listview);//listview不能用ButterKnife,因为是在下面绑定的ButterKnife
        View headerView = View.inflate(this, R.layout.settlement_center_header, null);
        View footerView = View.inflate(this, R.layout.settlement_center_footer, null);
        lv_listview.addHeaderView(headerView);//添加头布局
        lv_listview.addFooterView(footerView);//添加脚布局
        ButterKnife.bind(this);
        getDateFromServer();
    }

    //访问购物车,网络请求获取购物车的数据
    public void getDateFromServer() {
        HashMap<String, String> params = new HashMap<>();
        uid = PrefUtils.getInt(SettlementCenterActivity.this, GlobalConstants.PREF_UID, 0) + "";
        ToastUtils.showDefault(SettlementCenterActivity.this, uid);
        if (uid != null && !uid.equals("0")) {
            params.put("uId", uid);
            MyOkHttpUtils.getDataFromServerByPost(GlobalConstants.PREF_NET_URL_PREFIX +
                    "product/cart_getCartList.html", params, new MyOkHttpUtils
                    .OnNetResponseListener() {
                @Override
                public void onOk(String response) {
                    if (response.contains("\"购物车信息获取成功\"")) {
                        try {
                            parseJson(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showDefaultTemp(SettlementCenterActivity.this, "解析json失败," +
                                    "请检查");
                        }
                    } else {//需要登录
                        logInDialog();
                    }
                }

                @Override
                public void onErro(String erroInfo) {
                    ToastUtils.showDefault(SettlementCenterActivity.this, erroInfo);
                }
            });
        } else {//登录
            logInDialog();
        }
    }

    //提示登录的对话框
    private void logInDialog() {
        dialog = DialogUtils.createDialog(SettlementCenterActivity.this, "登录", "您还未登陆", new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean positive = (boolean) v.getTag();
                if (positive) {
                    startActivity(new Intent(SettlementCenterActivity.this, LogInActivity.class));
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void parseJson(String response) {
        LogUtils.Error("结算中心请求的json:" + response);
        Gson gson = new Gson();
        settlementCenterInfo = gson.fromJson(response, SettlementCenterInfo.class);
        if (mListAdapter == null) {
            mListAdapter = new mListAdapter();
            lv_listview.setAdapter(mListAdapter);//如果购物车里什么都没有,商品的ArrayList集合会报空指针
        } else {
            mListAdapter.notifyDataSetChanged();
        }

        ToastUtils.showDefaultTemp(SettlementCenterActivity.this, "mListAdapter" +
                ".notifyDataSetChanged();");
        tv_Count.setText(settlementCenterInfo.totalCount);//数量总计
        if (settlementCenterInfo.totalPrice != null) {
            tv_Money.setText("￥" + settlementCenterInfo.totalPrice);//原始金额
        }
        tv_WayMoney.setText("");                         //运费
        String vipmon = settlementCenterInfo.discountPrice;
        if (!vipmon.contains(".")) {
            vipmon += ".0";
        }
        tv_VipMoney.setText("￥-" + vipmon);//促销优惠金额
        float totalPrice = Float.parseFloat(settlementCenterInfo.totalPrice) - Float.parseFloat
                (settlementCenterInfo.discountPrice);
        tv_PayMoney.setText("￥" + totalPrice);
        if (settlementCenterInfo.discountMsg != null) {
            tv_DiscountMsg.setText(settlementCenterInfo.discountMsg);
            tv_DiscountMsg.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_right,R.id.rl_info, R.id.rl_pay_way, R.id.rl_deliver_goods_time, R.id
            .rl_deliver_goods_way, R.id.rl_ask_invoice,R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:                 //右上角的"提交订单"
                submitSettlement();             //自定义方法,提交结算
                break;
            case R.id.rl_info:                  //收货人信息
                ToastUtils.showDefault(this, "item1");
                break;
            case R.id.rl_pay_way:               //支付方式
                ToastUtils.showDefault(this, "item2");
                break;
            case R.id.rl_deliver_goods_time:    //送货时间
                ToastUtils.showDefault(this, "item3");
                break;
            case R.id.rl_deliver_goods_way:     //送货方式
                ToastUtils.showDefault(this, "item4");
                break;
            case R.id.rl_ask_invoice:           //索取发票
                ToastUtils.showDefault(this, "item5");
                break;
            case R.id.btn_submit:               //提交订单
                submitSettlement();             //自定义方法,提交结算
                break;
        }
    }

    private class mListAdapter extends BaseAdapter {
        ArrayList<SettlementCenterInfo.CartBean> list = settlementCenterInfo.cart;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public SettlementCenterInfo.CartBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(SettlementCenterActivity.this, R.layout
                        .item_settlement_center, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Glide
                    .with(SettlementCenterActivity.this) // 指定Context
                    .load(GlobalConstants.PREF_PIC_URL_PREFIX + list.get(position).coverImg)//
                    // 指定图片的URL
                    .placeholder(R.mipmap.ic_launcher)// 指定图片未成功加载前显示的图片,可以不设置(什么都不显示)
                    .error(R.mipmap.ic_launcher)// 指定图片加载失败显示的图片,可以不设置(什么都不显示)
                    //.override(300, 300)//指定图片的尺寸(不设置可注销)
                    .skipMemoryCache(false)// 跳过内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                    //.transform(new GlideRoundTransform(this,50))//圆形图片,圆角图片...
                    .into(viewHolder.ivImage);//指定显示图片的ImageView

            viewHolder.tvTitle.setText(list.get(position).productName);
            viewHolder.tvNum.setText("数量:" + list.get(position).amount);
            viewHolder.tvPrice.setText("价格:" + list.get(position).productMarketprice);
            viewHolder.tvCount.setText("小计:" + list.get(position).productPrice);
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvNum;
        TextView tvPrice;
        TextView tvCount;

        ViewHolder(View view) {
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvCount = (TextView) view.findViewById(tv_count);
        }
    }

    //提交结算
    private void submitSettlement() {
        if (Integer.parseInt(settlementCenterInfo.totalCount) > 0) {
            if (TextUtils.isEmpty(tv_Name.getText())) {
                ToastUtils.show(this, "亲,您还没有填写姓名哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_Phonenum.getText())) {
                ToastUtils.show(this, "亲,您还没有填写手机号码哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_Address.getText())) {
                ToastUtils.show(this, "亲,您还没有填写地址哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_PayWay.getText())) {
                ToastUtils.show(this, "亲,您还没有填写支付方式哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_DeliverGoodsTime.getText())) {
                ToastUtils.show(this, "亲,您还没有填写送货时间哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_DeliverGoodsWay.getText())) {
                ToastUtils.show(this, "亲,您还没有填写送货方式哦!");
                return;
            }
            if (TextUtils.isEmpty(tv_AskInvoice.getText())) {
                ToastUtils.show(this, "亲,您还没有填写索取发票方式哦!");
                return;
            }
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("uId", uid);             //用户id
            String addressDetail = tv_Address.getText() + " " + tv_Name.getText() + " " +
                    tv_Phonenum.getText();
            hashMap.put("addressDetail", addressDetail);             //送货信息
            hashMap.put("payway", tv_PayWay.getText().toString());  //支付方式
            hashMap.put("sendtime", tv_DeliverGoodsTime.getText().toString());//送货要求
            hashMap.put("invoiceMsg", tv_AskInvoice.getText().toString());//发票信息
            hashMap.put("sendType", tv_DeliverGoodsWay.getText().toString());//发送方式
            MyOkHttpUtils.getDataFromServerByPost(GlobalConstants.PREF_NET_URL_PREFIX +
                    "product/order_submitOrder.html", hashMap, new MyOkHttpUtils
                    .OnNetResponseListener() {
                @Override
                public void onOk(String response) {
                    if (response.contains("提交订单成功")) {
                        ToastUtils.showDefault(SettlementCenterActivity.this, "提交订单成功");
                        Intent intent = new Intent();
                        //intent.putExtra()
                        //startActivity();
                    }
                }
                @Override
                public void onErro(String erroInfo) {
                    ToastUtils.show(SettlementCenterActivity.this, erroInfo);
                }
            });
        } else {
            ToastUtils.show(this, "亲,你的购物车空空如也!╮(╯▽╰)╭");
        }
    }
}
