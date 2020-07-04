https://github.com/zhou-you/RxEasyHttp
������һ�����RxJava2+Retrofit2ʵ�ּ����õ����������ܣ����androidƽ̨���Ե������װ��,����api��ʽ����һ�㵽��,����cookie����,���ֻ���ģʽ,����https����,�ϴ����ؽ�����ʾ,��������Զ�����,����Я��token��ʱ�����ǩ��sign��̬����,�Զ���¼�ɹ��������ط�����,3�ֲ�εĲ�������Ĭ��ȫ�־ֲ�,Ĭ�ϱ�׼ApiResultͬʱ����֧���Զ�������ݽṹ���Ѿ����������ڵĴ󲿷���������

�۵�:
1.������Map<String, String>
2.�����Զ��������Gson
3.�и��о�ûɶ�õ� ProgressDialogCallBack
4.�������Ͳ��ܴ�<List<Object>>, �������: .execute(new TypeToken<List<SectionItem>>() {}.getType())//Type����
5.����д���� ApiResult<T>, ���������½ӿ�ʱ����, �Զ���ApiResultд���о��鷳.(���������ʲô�ͽ�����ʲô, ��Ҫ��д�� ApiResult???)
    BaseSubscriber.onError(L:69): -->http is onError
    BaseSubscriber.onError(L:71): --> e instanceof ApiException err:com.zhouyou.http.exception.ApiException: NullPointerException
EasyHttp.get("https://api.github.com").execute(new SimpleCallBack<GithubInfo>() {
    @Override
    public void onError(ApiException e) {
        e.printStackTrace();
        toast(e.getMessage());
    }
    @Override
    public void onSuccess(GithubInfo githubInfo) {
        toast(githubInfo.getHub_url());
    }
});
public class GithubInfo {
	public String hub_url;//��ʹд�� private & get/set����, Ҳ�ᱨ��
}
6.minSdkVersion 19
7.����,���Ƽ������


1.�ص�
��Retrofitʹ�ø��򵥡������á�
������ʽ����һ�㵽��
�������ApiService������Api����
֧�ֶ�̬���ú��Զ���ײ���Okhttpclient��Retrofit.
֧�ֶ��ַ�ʽ��������GET��POST��PUT��DELETE������Э��
֧�����绺��,���ֻ�����Կ�ѡ,���Ǵ����ҵ�񳡾�
֧�̶ֹ����header�Ͷ�̬���header
֧�����ȫ�ֲ����Ͷ�̬��Ӿֲ�����
֧���ļ����ء����ļ��ϴ��ͱ��ύ����
֧���ļ������ϴ������صĽ��Ȼص�������ص���Ҳ�����Զ���ص�
֧��Ĭ�ϡ�ȫ�֡��ֲ�������ε����ù���
֧���������ݽṹ���Զ�����
֧����Ӷ�̬��������timeStampʱ�����token��ǩ��sign
֧���Զ������չAPI
֧�ֶ������ϲ�
֧��Cookie����
֧���첽��ͬ������
֧��Https����ǩ����վHttps�ķ��ʡ�˫����֤
֧��ʧ�����Ի��ƣ�����ָ�����Դ��������Լ��ʱ��
֧�ָ���kyɾ�����绺���������绺��
�ṩĬ�ϵı�׼ApiResult�����ͻص������ҿ��Զ���ApiResult
֧��ȡ����������ȡ�����ģ����жԻ����������Ҫ�ֶ�ȡ�����󣬶Ի�����ʧ���Զ�ȡ������
֧���������ݽ�����ûص��Ͷ������ַ�ʽ
api����Ͻ��httpЭ���androidƽ̨�ص���ʵ��,loading�Ի���,ʵʱ��������ʾ
���ؽ�����쳣ͳһ����
���RxJava2���߳����ܿ���

2.RxEasyHttp�������Rxjava2��ϳ���ʹ�ó�������
http://blog.csdn.net/zhouy478319399/article/details/78550248

3.������־
https://github.com/zhou-you/RxEasyHttp/blob/master/update.md

4.��鿴���а汾
https://jcenter.bintray.com/com/zhouyou/rxeasyhttp/

5.�������, minSdkVersion 19//4.4
dependencies {
    implementation 'com.zhouyou:rxeasyhttp:2.1.7'
}
5.1.�Ѿ���ӵĵ����������� https://github.com/zhou-you/RxEasyHttp/blob/master/rxeasyhttp/build.gradle
    //������������
    compile 'com.squareup.okhttp3:logging-interceptor:3.12.2'
    compile 'com.squareup.okhttp3:okhttp:3.12.2'
    compile 'com.jakewharton:disklrucache:2.0.2'
    compile 'io.reactivex.rxjava2:rxjava:2.2.10'
    compile 'io.reactivex.rxjava2:rxandroid:2.1.1'
    compile 'com.squareup.retrofit2:retrofit:2.5.0'
    compile 'com.squareup.retrofit2:converter-gson:2.5.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'


6.Ĭ�ϳ�ʼ��
//RxEasyHttp Ĭ�ϳ�ʼ��,�������
EasyHttp.init(this);

7.�߼���ʼ��
	//ȫ����������ͷ
	HttpHeaders headers = new HttpHeaders();
	headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
	//ȫ�������������
	HttpParams params = new HttpParams();
	params.put("appId", AppConstant.APPID);

	//�������õ����в�����ȫ�ֲ���,ͬ���Ĳ��������������ʱ��������һ��,��ô���ڸ���������,�����еĲ����Ḳ��ȫ�ֲ���
	EasyHttp.getInstance()
	
			//����ȫ��ͳһ����ȫ��URL
			.setBaseUrl(Url)//����ȫ��URL  urlֻ�������� ��������+�˿ں� 

			// �򿪸õ��Կ��ز�����TAG,����Ҫ�Ͳ�Ҫ�������
			// ����true��ʾ�Ƿ��ӡ�ڲ��쳣��һ��򿪷�����Դ���
			.debug("EasyHttp", true)
			
			//���ʹ��Ĭ�ϵ�60��,��������Ҳ����Ҫ����
			.setReadTimeOut(60 * 1000)
			.setWriteTimeOut(60 * 100)
			.setConnectTimeout(60 * 100)
			
			//����ȫ��ͳһ���ó�ʱ��������,Ĭ��Ϊ3��,��ô�������������4��(һ��ԭʼ����,������������),
			//����Ҫ��������Ϊ0
			.setRetryCount(3)//���粻���Զ�����3��
			//����ȫ��ͳһ���ó�ʱ���Լ��ʱ��,Ĭ��Ϊ500ms,����Ҫ��������Ϊ0
			.setRetryDelay(500)//ÿ����ʱ500ms����
			//����ȫ��ͳһ���ó�ʱ���Լ������ʱ��,Ĭ��Ϊ0ms������
			.setRetryIncreaseDelay(500)//ÿ����ʱ����500ms
			
			//����ȫ��ͳһ���û���ģʽ,Ĭ���ǲ�ʹ�û���,���Բ���,�����뿴CacheMode
			.setCacheMode(CacheMode.NO_CACHE)
			//����ȫ��ͳһ���û���ʱ��,Ĭ����������
			.setCacheTime(-1)//-1��ʾ���û���,��λ:�� ��Okhttp���Զ���RxCache���涼������
			//ȫ�������Զ��建�汣��ת��������Ҫ����Զ���RxCache����
			.setCacheDiskConverter(new SerializableDiskConverter())//Ĭ�ϻ���ʹ�����л�ת��
			//ȫ�������Զ��建���С��Ĭ��50M
			.setCacheMaxSize(100 * 1024 * 1024)//���û����СΪ100M
			//���û���汾����������б仯���޸İ汾�󣬻���Ͳ��ᱻ���ء��ر������ڰ汾�ش�����ʱ���治��ʹ�õ����
			.setCacheVersion(1)//����汾Ϊ1
			//.setHttpCache(new Cache())//����Okhttp���棬�ڻ���ģʽΪDEFAULT��������
			
			//��������https��֤��,���¼��ַ���������Ҫ�Լ�����
			.setCertificates()                                  //����һ����������֤��,����ȫ�з���
			//.setCertificates(new SafeTrustManager())            //���������Զ������ι���У������֤��
			//����https������ƥ����򣬲���Ҫ�Ͳ�Ҫ���룬ʹ�ò����ᵼ��https����ʧ��
			//.setHostnameVerifier(new SafeHostnameVerifier())
			//.addConverterFactory(GsonConverterFactory.create(gson))//�����û�в���Retrofit��Gsonת�������Բ�������
			.addCommonHeaders(headers)//����ȫ�ֹ���ͷ
			.addCommonParams(params)//����ȫ�ֹ�������
			//.addNetworkInterceptor(new NoCacheInterceptor())//��������������
			//.setCallFactory()//������Retrofit����Factory
			
														//��������ñ������cookie,���²���Ҫ
			//.setCookieStore(new CookieManger(this)) //cookie�־û��洢�����cookie�����ڣ���һֱ��Ч
			//.setOkproxy()//����ȫ�ִ���
			//.setOkconnectionPool()//�����������ӳ�
			//.setCallbackExecutor()//ȫ������Retrofit callbackExecutor
			//�������ȫ��������������Ҫ�Ͳ�Ҫ���룬����д��ֱ�ӵ����κλص���ִ��
			//.addInterceptor(new GzipRequestInterceptor())//����post���ݽ���gzip���͸�������
			.addInterceptor(new CustomSignInterceptor());//��Ӳ���ǩ��������





