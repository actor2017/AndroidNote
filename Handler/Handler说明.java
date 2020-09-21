Handler在哪个线程创建,handler就会把消息发送到这个线程的消息队列.
当App启动的时候，就启动了一条线程来操作UI，称为UI线程或者主线程,
在主线程启动的时候，Looper就调用了 looper.prepareMainLooper(), 为当前的线程绑定了一个looper对象,
因为每个looper对象都有自己的消息队列，因此主线程也间接拥有的了自己的消息队列,
之后 looper.loop();开始轮询消息队列的消息

Handler在发送消息进入消息队列之前，Message会保存一个对Handler的引用target。
然后当轮询到这个消息的时候，就可以通过target来获取发送自己的handler,然后把消息处理掉。

Hanlder,Message,MessageQueue,Looper之间的关系
在子线程中发消息给MessageQueue消息队列，所有的消息Message都在消息队列中排队，
按照先来后到的顺序排队，系统里的Looper轮询器，不断的从消息队列中取出消息，给在主线程中的Handler，在handleMessage()方法中修改UI。
1.Handler构造方法 ==========================================================
public Handler();
public Handler(Callback callback);
public Handler(Looper looper);					//new Handler(Looper.getMainLooper());
public Handler(Looper looper, Callback callback);
public Handler(boolean async);
public Handler(Callback callback, boolean async);
public Handler(Looper looper, Callback callback, boolean async);

2.Handler的方法 ==========================================================
String getMessageName(Message message);

Message obtainMessage();//从消息池获取msg.Message msg = handler.obtainMessage();msg.obj = "obj";msg.sendToTarget();这样也能发消息
Message obtainMessage(int what);
Message obtainMessage(int what, Object obj);//★★★注意这个方法和new Message()的区别★★★
Message obtainMessage(int what, int arg1, int arg2);
Message obtainMessage(int what, int arg1, int arg2, Object obj);

boolean post(Runnable r);//发一个任务过去
boolean postAtTime(Runnable r, long uptimeMillis);
boolean postAtTime(Runnable r, Object token, long uptimeMillis);
boolean postDelayed(Runnable r, long delayMillis);//延时消息new Handler().postDelayed(new Runnable() {void run(){}},1000);
boolean postAtFrontOfQueue(Runnable r);

void removeMessages(int what);//移除消息 onDestroy()1
void removeMessages(int what, Object object);

void removeCallbacks(Runnable r);
void removeCallbacks(Runnable r, Object token);
void removeCallbacksAndMessages(Object token);//onDestroy()2

void handleMessage(Message msg);
void dispatchMessage(Message msg);

boolean sendEmptyMessage(int what);
boolean sendEmptyMessageDelayed(int what, long delayMillis);//发送延时消息, 0, 2000
boolean sendEmptyMessageAtTime(int what, long uptimeMillis);
boolean sendMessage(Message msg);							//发送普通消息
boolean sendMessageDelayed(Message msg, long delayMillis);
boolean sendMessageAtTime(Message msg, long uptimeMillis);
boolean sendMessageAtFrontOfQueue(Message msg);

boolean hasMessages(int what);
boolean hasMessages(int what, Object object);

Looper getLooper();

void dump(Printer pw, String prefix);

3.Handler示例写法 ==========================================================
3.1.子线程给主线程发消息
//空参构造写法,在主线程中创建handler对象
Handler handler = new Handler(){
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		tv.setText(String.valueOf(msg.obj));
	}
};

//有参构造写法
Handler handler1 = new Handler(new Handler.Callback() {
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
});

//子线程给主线程发送消息
new Thread(){
    @Override
    public void run() {
        super.run();
        Message message = new Message();
        message.obj = "From SubThread!";
        handler.sendMessage(message);
    }
}.start();

3.2.主线程给子线程发消息
public class BaseActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.obj = "董小雷";
                handler.sendMessage(msg);
            }
        });

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();           //初始化Looper
                handler = new Handler(){    //子线程的handler
                    @Override
                    public void handleMessage(Message msg) {
                        System.out.println(msg);
                        //super.handleMessage(msg);//空实现,在这里没意义
                    }
                };
                Looper.loop();              //启动消息循环
                //super.run();
            }
        }.start();
    }
}

3.3延时
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
},2000);
