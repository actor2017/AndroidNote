package com.itheima.alipay;

/**
 * ���ưְֵ�֧������Ľӿ�
 */
 interface IALiPayService {

	/**
	 * ֧������Ǯ
	 * 
	 * @param name
	 *            �û���
	 * @param pwd
	 *            ����
	 * @param money
	 *            ֧�����
	 * @param time
	 *            ʱ���
	 */
void pay(String name, String pwd, float money, long time);

}
