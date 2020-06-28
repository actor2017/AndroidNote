Message message = new Message();
int what;
int arg1;
int arg2;
Object obj;
Messenger replyTo;
int sendingUid = -1;
void recycle();
void copyFrom(Message o);
long getWhen();
Bundle getData();
Handler getTarget();
Runnable getCallback();
void setTarget(Handler target);
void setData(Bundle data);
void setAsynchronous(boolean async);

Bundle peekData();
void sendToTarget();		//发送消息到handler.Message msg = handler.obtainMessage();msg.obj = "obj";msg.sendToTarget();这样也能发消息
boolean isAsynchronous();

int describeContents();
void writeToParcel(Parcel dest, int flags);
void readFromParcel(Parcel source);

//静态方法,直接调用
Message obtain();
Message obtain(Message orig);
Message obtain(Handler h);
Message obtain(Handler h, Runnable callback);
Message obtain(Handler h, int what);
Message obtain(Handler h, int what, Object obj);
Message obtain(Handler h, int what, int arg1, int arg2);
Message obtain(Handler h, int what, int arg1, int arg2, Object obj);

Parcelable.Creator<Message> CREATOR;


Message			//消息
	Message msg = Message.obtain();
	msg.obj="hahahha";	//handler.sendMessage(msg);
