/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\xian12\\day08_服务     （第六剑）\\code\\06_远程服务\\src\\com\\itheima\\remote\\IRemoteService.aidl
 */
package com.itheima.remote;
/**
 * 暴露远程服务组件中的方法
 */
public interface IRemoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.itheima.remote.IRemoteService
{
private static final java.lang.String DESCRIPTOR = "com.itheima.remote.IRemoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.itheima.remote.IRemoteService interface,
 * generating a proxy if needed.
 */
public static com.itheima.remote.IRemoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.itheima.remote.IRemoteService))) {
return ((com.itheima.remote.IRemoteService)iin);
}
return new com.itheima.remote.IRemoteService.Stub.Proxy(obj);
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
case TRANSACTION_qianYangNiu:
{
data.enforceInterface(DESCRIPTOR);
this.qianYangNiu();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.itheima.remote.IRemoteService
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
	 * 调用远程服务中的方法
	 */
@Override public void qianYangNiu() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_qianYangNiu, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_qianYangNiu = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
	 * 调用远程服务中的方法
	 */
public void qianYangNiu() throws android.os.RemoteException;
}
