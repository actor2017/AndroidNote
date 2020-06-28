## 1.线程生命周期
1.新建（new Thread）
当创建Thread类的一个实例（对象）时，此线程进入新建状态（未被启动）。
例如：Thread  t1=new Thread();

2.就绪（runnable）
线程已经被启动，正在等待被分配给CPU时间片，也就是说此时线程正在就绪队列中排队等候得到CPU资源。
例如：t1.start();

3.运行（running）
线程获得CPU资源正在执行任务（run()方法），此时除非此线程自动放弃CPU资源或者有优先级更高的线程进入，线程将一直运行到结束。

4.死亡（dead）
当线程执行完毕或被其它线程杀死，线程就进入死亡状态，这时线程不可能再进入就绪状态等待执行。
  自然终止：正常运行run()方法后终止
  异常终止：调用stop()方法让一个线程终止运行

5.堵塞（blocked）
由于某种原因导致正在运行的线程让出CPU并暂停自己的执行，即进入堵塞状态。
  正在睡眠：用sleep(long t) 方法可使线程进入睡眠方式。一个睡眠着的线程在指定的时间过去可进入就绪状态。
  正在等待：调用wait()方法。（调用notify()方法回到就绪状态）
  被另一个线程所阻塞：调用suspend(/sə'spɛnd/)方法。（调用resume()方法恢复）

## 2.常用方法
void run()   创建该类的子类时必须实现的方法
void start() 开启线程的方法
static void sleep(long t);//释放CPU的执行权，不释放锁
static void sleep(long millis,int nanos);//
final void wait();	//释放CPU的执行权，释放锁
final void notify();	//回到就绪状态
static void yied();	//可以对当前线程进行临时暂停（让线程将资源释放出来）
public final void join();//让线程加入执行，执行某一线程join方法的线程会被冻结，等待某一线程执行结束，该线程才会恢复到可运行状态

synchronized:用来与对象的互斥锁联系,在任一时刻只能由一个线程访问，即使该线程出现堵塞，该对象的被锁定状态也不会解除，其他线程任不能访问该对象


