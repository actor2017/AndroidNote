package com.itheima.alipay;

/**
 * 马云爸爸的支付服务的接口
 */
 interface IALiPayService {

	/**
	 * 支付，扣钱
	 * 
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param money
	 *            支付金额
	 * @param time
	 *            时间戳
	 */
void pay(String name, String pwd, float money, long time);

}
