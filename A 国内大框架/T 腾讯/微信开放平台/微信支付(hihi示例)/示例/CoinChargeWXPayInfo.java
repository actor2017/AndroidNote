package com.ly.hihifriend.info;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Description: 微信支付——获取服务器订单号等
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/29 on 17:22
 */
public class CoinChargeWXPayInfo {

    /**
     * code : 1000
     * msg : 成功
     * data : {"package":"Sign=WXPay","appid":"wx8aee7894414e5f5a",
     * "sign":"01C9BE8ABA3EF5D370A4A56FA2DD68A43187A9988344BD9A034AF5A11393D683",
     * "partnerid":"1529503581","prepayid":"wx291746413505145c3b167fa62375728300",
     * "noncestr":"4c23253f9eb44523ab920c740a5f8419","timestamp":"1553852799"}
     */

    public int code;
    public String   msg;
    public DataBean data;

    public static class DataBean {
        /**
         * package : Sign=WXPay
         * appid : wx8aee7894414e5f5a
         * sign : 01C9BE8ABA3EF5D370A4A56FA2DD68A43187A9988344BD9A034AF5A11393D683
         * partnerid : 1529503581
         * prepayid : wx291746413505145c3b167fa62375728300
         * noncestr : 4c23253f9eb44523ab920c740a5f8419
         * timestamp : 1553852799
         */

        @JSONField(name = "package")
        public String packageX;
        public String appid;
        public String sign;
        public String partnerid;
        public String prepayid;
        public String noncestr;
        public String timestamp;
    }
}
