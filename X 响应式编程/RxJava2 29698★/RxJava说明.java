https://github.com/ReactiveX/RxJava
�汾�б�:https://github.com/ReactiveX/RxJava/releases

1.�������
implementation 'io.reactivex.rxjava2:rxjava:2.1.16'

2.��ӻ���
##------Begin: proguard configuration for Rxjava2 Rxandroid------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent
##------End: proguard configuration for Rxjava2 Rxandroid------



RxJava ������ʲô
һ���ʣ��첽��
RxJava �� GitHub ��ҳ�ϵ����ҽ����� "a library for composing asynchronous and event-based programs using observable sequences for the Java VM"��һ���� Java VM ��ʹ�ÿɹ۲������������첽�ġ������¼��ĳ���Ŀ⣩��

������һ��ʵ���첽�����Ŀ⣬����Ķ��ﶼ�ǻ�����֮�ϵġ�
RxJava ���첽ʵ�֣���ͨ��һ����չ�Ĺ۲���ģʽ��ʵ�ֵġ�

RxJava ���ĸ��������
Observable (�ɹ۲��ߣ������۲���)
Observer (�۲���)
subscribe (����)���¼�
Observable �� Observer ͨ�� subscribe() ����ʵ�ֶ��Ĺ�ϵ���Ӷ� Observable ��������Ҫ��ʱ�򷢳��¼���֪ͨ Observer��

onCompleted():	����ֻ��һ��,�����¼������е����һ��,�¼�������ᡣRxJava ������ÿ���¼�����������������ǿ���һ�����С�
		RxJava �涨�������������µ� onNext() ����ʱ����Ҫ���� onCompleted() ������Ϊ��־��
onError():	����ֻ��һ��,�����¼������е����һ��,�¼������쳣��
		���¼���������г��쳣ʱ��onError() �ᱻ������ͬʱ�����Զ���ֹ�������������¼�������

�봫ͳ�۲���ģʽ��ͬ�� RxJava ���¼��ص�����������ͨ�¼� onNext() ���൱�� onClick() / onEvent()��֮�⣬������������������¼���onCompleted() �� onError()��

onCompleted() �� onError() ����Ҳ�ǻ���ģ����ڶ����е���������һ�����Ͳ�Ӧ���ٵ�����һ����

RxJava ������
���仰˵����ͬ�������첽��Ϊʲô�����������������ֳɵ� AsyncTask / Handler / XXX / ... ����
һ���ʣ���ࡣ
�첽�����ܹؼ���һ���ǳ���ļ���ԣ���Ϊ�ڵ��ȹ��̱Ƚϸ��ӵ�����£��첽���뾭�������дҲ�ѱ������� Android ����� AsyncTask ��Handler ����ʵ����Ϊ�����첽������Ӽ�ࡣRxJava ������Ҳ�Ǽ�࣬�����ļ������ڲ�֮ͬ�����ڣ����ų����߼����Խ��Խ���ӣ�����Ȼ�ܹ����ּ�ࡣ


�ٸ�����:
����������һ�����󣺽�������һ���Զ������ͼ imageCollectorView ��������������ʾ����ͼƬ������ʹ�� addImage(Bitmap) ����������������ʾ��ͼƬ��������Ҫ����һ��������Ŀ¼���� File[] folders ��ÿ��Ŀ¼�µ� png ͼƬ�����س�������ʾ�� imageCollectorView �С���Ҫע����ǣ����ڶ�ȡͼƬ����һ���̽�Ϊ��ʱ����Ҫ���ں�ִ̨�У���ͼƬ����ʾ������� UI �߳�ִ�С����õ�ʵ�ַ�ʽ�ж��֣���������������һ�֣�

new Thread() {
    @Override
    public void run() {
        super.run();
        for (File folder : folders) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".png")) {
                    final Bitmap bitmap = getBitmapFromFile(file);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageCollectorView.addImage(bitmap);
                        }
                    });
                }
            }
        }
    }
}.start();

�����ʹ�� RxJava ��ʵ�ַ�ʽ�������ģ�
Observable.from(folders)
    .flatMap(new Func1<File, Observable<File>>() {
        @Override
        public Observable<File> call(File file) {
            return Observable.from(file.listFiles());
        }
    })
    .filter(new Func1<File, Boolean>() {
        @Override
        public Boolean call(File file) {
            return file.getName().endsWith(".png");
        }
    })
    .map(new Func1<File, Bitmap>() {
        @Override
        public Bitmap call(File file) {
            return getBitmapFromFile(file);
        }
    })
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) {
            imageCollectorView.addImage(bitmap);
        }
    });


RxJava �Ļ���ʵ����Ҫ�����㣺

1) ���� Observer
Observer ���۲��ߣ��������¼�������ʱ������������Ϊ�� RxJava �е� Observer �ӿڵ�ʵ�ַ�ʽ��
Observer<String> observer = new Observer<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};

���� Observer �ӿ�֮�⣬RxJava ��������һ��ʵ���� Observer �ĳ����ࣺSubscriber�� Subscriber �� Observer �ӿڽ�����һЩ��չ�������ǵĻ���ʹ�÷�ʽ����ȫһ���ģ�
Subscriber<String> subscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};

��������ʹ�÷�ʽһ����ʵ���ϣ��� RxJava �� subscribe �����У�Observer Ҳ���ǻ��ȱ�ת����һ�� Subscriber ��ʹ�á����������ֻ��ʹ�û������ܣ�ѡ�� Observer �� Subscriber ����ȫһ���ġ����ǵ��������ʹ������˵��Ҫ�����㣺

onStart(): ���� Subscriber ���ӵķ����������� subscribe �տ�ʼ�����¼���δ����֮ǰ�����ã�����������һЩ׼���������������ݵ���������á�����һ����ѡ������Ĭ�����������ʵ��Ϊ�ա���Ҫע����ǣ������׼���������߳���Ҫ�����絯��һ����ʾ���ȵĶԻ�������������߳�ִ�У��� onStart() �Ͳ������ˣ���Ϊ�������� subscribe ���������̱߳����ã�������ָ���̡߳�Ҫ��ָ�����߳�����׼������������ʹ�� doOnSubscribe() ��������������ں�������п�����

unsubscribe(): ���� Subscriber ��ʵ�ֵ���һ���ӿ� Subscription �ķ���������ȡ�����ġ���������������ú�Subscriber �����ٽ����¼���һ���������������ǰ������ʹ�� isUnsubscribed() ���ж�һ��״̬�� unsubscribe() �����������Ҫ����Ϊ�� subscribe() ֮�� Observable ����� Subscriber �����ã��������������ܼ�ʱ���ͷţ������ڴ�й¶�ķ��ա�������ñ���һ��ԭ��Ҫ�ڲ���ʹ�õ�ʱ�򾡿��ں��ʵĵط������� onPause() onStop() �ȷ����У����� unsubscribe() ��������ù�ϵ���Ա����ڴ�й¶�ķ�����


���� Observable

Observable �����۲��ߣ�������ʲôʱ�򴥷��¼��Լ������������¼��� RxJava ʹ�� create() ����������һ�� Observable ����Ϊ�������¼���������
Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
    @Override
    public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello");
        subscriber.onNext("Hi");
        subscriber.onNext("Aloha");
        subscriber.onCompleted();
    }
});


create() ������ RxJava ������Ĵ����¼����еķ������������������ RxJava ���ṩ��һЩ����������ݴ����¼����У����磺
just(T...): ������Ĳ������η��ͳ�����

Observable observable = Observable.just("Hello", "Hi", "Aloha");
// �������ε��ã�
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();


from(T[]) / from(Iterable<? extends T>) : ������������ Iterable ��ֳɾ����������η��ͳ�����
String[] words = {"Hello", "Hi", "Aloha"};
Observable observable = Observable.from(words);
// �������ε��ã�
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();

���� just(T...) �����Ӻ� from(T[]) �����ӣ�����֮ǰ�� create(OnSubscribe) �������ǵȼ۵ġ�


Subscribe (����)
������ Observable �� Observer ֮������ subscribe() ���������������������������ӾͿ��Թ����ˡ�������ʽ�ܼ򵥣�

observable.subscribe(observer);
// ���ߣ�
observable.subscribe(subscriber);


