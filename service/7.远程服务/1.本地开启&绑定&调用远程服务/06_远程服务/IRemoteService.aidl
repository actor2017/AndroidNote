package com.itheima.remote;
/**
 * 暴露远程服务组件中的方法,把数据序列化到公共内存
 * 1. 把接口改成.aidl
 * 2. 去掉这个接口中的权限修饰符public
 * 3. 会在gen/package/目录下生成一个接口IRemoteService.java
 * 4. 让远程服务里的内部类继承上面接口的Stub类
 */
interface IRemoteService {
	/**
	 * 调用远程服务中的方法
	 */
	void qianYangNiu();//牵洋妞
}
