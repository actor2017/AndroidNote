/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\xian12\\day08_服务     （第六剑）\\code\\08_支付宝\\src\\com\\itheima\\alipay\\IALiPayService.aidl
 */
package com.itheima.alipay;
/**
 * 马云爸爸的支付服务的接口
 */
public interface IALiPayService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.itheima.alipay.IALiPayService
{
private static final java.lang.String DESCRIPTOR = "com.itheima.alipay.IALiPayService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.itheima.alipay.IALiPayService interface,
 * generating a proxy if needed.
 */
public static com.itheima.alipay.IALiPayService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.itheima.alipay.IALiPayService))) {
return ((com.itheima.alipay.IALiPayService)iin);
}
return new com.itheima.alipay.IALiPayService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_pay:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
float _arg2;
_arg2 = data.readFloat();
long _arg3;
_arg3 = data.readLong();
this.pay(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.itheima.alipay.IALiPayService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
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
@Override public void pay(java.lang.String name, java.lang.String pwd, float money, long time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(pwd);
_data.writeFloat(money);
_data.writeLong(time);
mRemote.transact(Stub.TRANSACTION_pay, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_pay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
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
public void pay(java.lang.String name, java.lang.String pwd, float money, long time) throws android.os.RemoteException;
}
