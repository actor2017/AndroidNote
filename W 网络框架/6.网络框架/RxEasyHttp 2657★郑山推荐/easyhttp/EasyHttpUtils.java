package com.actor.myandroidframework.utils.easyhttp;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.cookie.CookieManger;
import com.zhouyou.http.cookie.PersistentCookieStore;
import com.zhouyou.http.request.BaseRequest;
import com.zhouyou.http.request.CustomRequest;
import com.zhouyou.http.request.DeleteRequest;
import com.zhouyou.http.request.DownloadRequest;
import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;
import com.zhouyou.http.request.PutRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description: 对 EasyHttp 的简单封装: https://github.com/zhou-you/RxEasyHttp
 * 槽点:
 * 1.参数是Map<String, String>
 * 2.不能自定义解析的 Gson
 * 3.有个感觉没啥用的 ProgressDialogCallBack
 * 4.解析泛型不能传 List<Object>, 必须调用: .execute(new TypeToken<List<SectionItem>>() {}.getType())//Type类型
 * 5.由于写死了 ApiResult<T>, 当调用如下接口时报错, 自定义ApiResult写法感觉麻烦.(我想解析成什么就解析成什么, 需要你写死 ApiResult???)
 *     BaseSubscriber.onError(L:69): -->http is onError
 *     BaseSubscriber.onError(L:71): --> e instanceof ApiException err:com.zhouyou.http.exception.ApiException: NullPointerException
 * EasyHttp.get("https://api.github.com").execute(new SimpleCallBack<GithubInfo>() {
 *     @Override
 *     public void onError(ApiException e) {
 *         e.printStackTrace();
 *         toast(e.getMessage());
 *     }
 *     @Override
 *     public void onSuccess(GithubInfo githubInfo) {
 *         toast(githubInfo.hub_url);
 *     }
 * });
 * public class GithubInfo {
 *     public String hub_url;//即使写成 private & get/set方法, 也会报错
 * }
 * 6.minSdkVersion 19
 * 7.所以,不推荐这个库
 *
 *
 * 如果实在想用这个库:
 * 1.添加依赖
 * //https://github.com/zhou-you/RxEasyHttp minSdkVersion 19
 * implementation ('com.zhouyou:rxeasyhttp:2.1.7') {
 *     //https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor 排除官方拦截器, 没看见哪儿在使用
 *     exclude group: 'com.squareup.okhttp3', module: 'logging-interceptor'//3.12.2
 *     //exclude group: 'com.squareup.okhttp3', module: 'okhttp'//3.12.2
 *     //exclude group: 'com.jakewharton', module: 'disklrucache'//2.0.2
 *     //exclude group: 'io.reactivex.rxjava2', module: 'rxjava'//2.2.10
 *     //exclude group: 'io.reactivex.rxjava2', module: 'rxandroid'//2.1.1
 *     //https://github.com/square/retrofit
 *     //exclude group: 'com.squareup.retrofit2', module: 'retrofit'//2.5.0
 *     //https://github.com/square/retrofit/tree/master/retrofit-converters/gson
 *     //exclude group: 'com.squareup.retrofit2', module: 'converter-gson'//2.5.0
 *     //https://github.com/google/gson
 *     //exclude group: 'com.google.code.gson', module: 'gson'//2.8.5
 *     //exclude group: 'com.squareup.retrofit2', module: 'adapter-rxjava2'//2.5.0
 * }
 *
 * 2.在Application中配置
 * //RxEasyHttp 默认初始化,必须调用
 * EasyHttp.init(this);
 * EasyHttp easyHttp = EasyHttp.getInstance()
 *         .setBaseUrl(ConfigUtils.baseUrl)//设置全局URL  url只能是域名 或者域名+端口号
 *         .setCookieStore(new CookieManger(this));//cookie持久化存储，如果cookie不过期，则一直有效
 * if (isDebugMode) {
 *     easyHttp.debug("EasyHttp", true);//true表示是否打印内部异常，一般打开方便调试错误
 * } else {
 *     easyHttp.setOkproxy(Proxy.NO_PROXY);
 * }
 * configEasyHttp(easyHttp);//其余自定义配置
 *
 * 3.在Activity/Fragment/Dialog/Popup 的onDestroy的时候:
 *   EasyHttpUtils.cancelSubscription(this);//取消网络请求
 *
 * 4.添加混淆
 *
 * @author    : 李大发
 * date       : 2020/4/17 on 16:23
 *
 * @version 1.0
 */
public class EasyHttpUtils {

    protected static final Map<String, List<Disposable>> TAGS = new HashMap<>();

    public static Disposable get(String url, Map<String, String> params, BaseCallBack6 callBack6) {
        return get(url, null, params, callBack6);
    }

    /**
     * get 请求
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param callBack6 回调, 泛型类型:
     *                  支持回调的类型可以是Bean、String、CacheResult<Bean>、CacheResult<String>、List<Bean>
     */
    public static Disposable get(String url, Map<String, String> headers, Map<String, String> params, BaseCallBack6 callBack6) {
        GetRequest getRequest = EasyHttp.get(url).params(params)
//                .addCookie()
//                .addInterceptor()
//                .accessToken(false)//是否需要追加token, 默认false
//                .baseUrl()
//                .cacheKey(getClass().getSimpleName()+"test")
//                .cacheMode(CacheMode.CACHEANDREMOTE)
//                .cacheTime(5 * 60)//缓存时间300s，默认-1永久缓存  okhttp和自定义缓存都起作用
                //.okCache(new Cache());//okhttp缓存，模式为默认模式（CacheMode.DEFAULT）才生效
                //.cacheDiskConverter(new GsonDiskConverter())//默认使用的是 new SerializableDiskConverter();
//                .removeParam("appId")
//                .retryCount(5)//重试次数

//                .readTimeOut(30 * 1000)//局部定义读超时
//                .writeTimeOut(30 * 1000)
//                .connectTimeout(30 * 1000)

//                .sign(true)//是否需要签名, 默认false
//                .syncRequest(false)//设置同步请求, 默认false
//                .timeStamp(false)//是否需要追加时间戳, 默认false
                ;
        addHeaders(getRequest, headers);
        Disposable disposable = getRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    public static Disposable post(String url, Map<String, String> params, BaseCallBack6 callBack6) {
        return post(url, null, params, callBack6);
    }

    /**
     * post 请求
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param callBack6 回调
     */
    public static Disposable post(String url, Map<String, String> headers, Map<String, String> params, BaseCallBack6 callBack6) {
        PostRequest postRequest = EasyHttp.post(url).params(params);
        addHeaders(postRequest, headers);
        Disposable disposable = postRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 将 string 传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param string 字符串
     * @param callBack6 回调
     */
    public static Disposable postString(String url, Map<String, String> headers, String string, BaseCallBack6 callBack6) {
        //默认类型：MediaType.parse("text/plain")
        PostRequest postRequest = EasyHttp.post(url).upString(string/*, String mediaType*/);
        addHeaders(postRequest, headers);
        Disposable disposable = postRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 将 json 传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param json json
     * @param callBack6 回调
     */
    public static Disposable postJson(String url, Map<String, String> headers, String json, BaseCallBack6 callBack6) {
        PostRequest postRequest = EasyHttp.post(url).upJson(json);
        addHeaders(postRequest, headers);
        Disposable disposable = postRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 将 object 传到服务器
     * 必须要增加.addConverterFactory(GsonConverterFactory.create())设置,
     * 本质就是把object转成json给到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param obj 对象
     * @param callBack6 回调
     */
    public static Disposable postObject(String url, Map<String, String> headers, Object obj, BaseCallBack6 callBack6) {
        PostRequest postRequest = EasyHttp.post(url).upObject(obj)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()));
        addHeaders(postRequest, headers);
        Disposable disposable = postRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 将文件以流的方式传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param filePath 文件路径
     * @param callBack6 回调
     */
    public static Disposable postFile(String url, Map<String, String> headers, String filePath, BaseCallBack6 callBack6) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                byte[] bytes = FileIOUtils.readFile2BytesByStream(file);
                PostRequest postRequest = EasyHttp.post(url).upBytes(bytes);
                addHeaders(postRequest, headers);
                Disposable disposable = postRequest.execute(callBack6);
                if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
                return disposable;
            }
        }
        return null;
    }


    /**
     * 将文件以表单的形式传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param filePath 文件路径
     * @param progressCallback 进度回调, 可以刷新UI
     * @param callBack6 回调
     */
    public static Disposable postFile(String url, Map<String, String> headers,
                                      Map<String, String> params, String key, String filePath,
                                      UIProgressResponseCallBack progressCallback,
                                      BaseCallBack6 callBack6) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                PostRequest postRequest = EasyHttp.post(url)
                        .params(params)
                        .params(key, file, progressCallback);
                addHeaders(postRequest, headers);
                Disposable disposable = postRequest.execute(callBack6);
                if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
                return disposable;
            }
        }
        return null;
    }


    /**
     * 将文件以表单的形式传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param filePaths 文件路径集合
     * @param callBack6 回调
     */
    public static Disposable postFiles(String url, Map<String, String> headers,
                                      Map<String, String> params, String key, Collection<String> filePaths,
                                      UIProgressResponseCallBack progressCallback,
                                      BaseCallBack6 callBack6) {
        if (filePaths != null && !filePaths.isEmpty()) {
            List<File> files = new ArrayList<>(filePaths.size());
            for (String filePath : filePaths) {
                if (filePath != null) files.add(new File(filePath));
            }
            return postFiles(url, headers, params, key, files, progressCallback, callBack6);
        }
        return null;
    }


    /**
     * 将文件以表单的形式传到服务器
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param files 文件集合
     * @param callBack6 回调
     */
    public static Disposable postFiles(String url, Map<String, String> headers,
                                       Map<String, String> params, String key, List<File> files,
                                       UIProgressResponseCallBack progressCallback,
                                       BaseCallBack6 callBack6) {
        if (files != null && !files.isEmpty()) {
            PostRequest postRequest = EasyHttp.post(url).params(params);
            for (File file : files) {
                try {
                    // TODO: 2020/4/20 中文名会报错, 2.1.7后的版本可测试一下是否已修复.
                    String fileName = URLEncoder.encode(file.getName(), "UTF-8");
                    postRequest.params(key, file, fileName, progressCallback);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //addFileParams 不能传中文 https://github.com/zhou-you/RxEasyHttp/issues/214
//            postRequest.addFileParams(key, files, progressCallback);
            addHeaders(postRequest, headers);
            Disposable disposable = postRequest.execute(callBack6);
            if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
            return disposable;
        }
        return null;
    }


    public static Disposable delete(String url, Map<String, String> params, BaseCallBack6 callBack6) {
        return delete(url, null, params, callBack6);
    }

    /**
     * delete 请求
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param callBack6 回调
     */
    public static Disposable delete(String url, Map<String, String> headers, Map<String, String> params, BaseCallBack6 callBack6) {
        DeleteRequest deleteRequest = EasyHttp.delete(url).params(params)
//                .upJson("json")//可传json
                ;
        addHeaders(deleteRequest, headers);
        Disposable disposable = deleteRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    public static Disposable put(String url, Map<String, String> params, BaseCallBack6 callBack6) {
        return put(url, null, params, callBack6);
    }

    /**
     * put 请求
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param callBack6 回调
     */
    public static Disposable put(String url, Map<String, String> headers, Map<String, String> params, BaseCallBack6 callBack6) {
        PutRequest putRequest = EasyHttp.put(url).params(params);
        addHeaders(putRequest, headers);
        Disposable disposable = putRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 下载文件
     * @param url url
     * @param headers 请求头 , 可传null
     * @param params 参数, 可传null
     * @param callBack6 回调
     */
    public static Disposable download(String url, Map<String, String> headers, Map<String, String> params, BaseCallBack6 callBack6) {
        DownloadRequest downloadRequest = EasyHttp.downLoad(url).params(params)
                //默认下载的目录: /storage/emulated/0/Android/data/包名/files
//                .savePath("")
                //1.首先检查用户是否传入了文件名,如果传入,将以用户传入的文件名命名
                //2.如果没有传入文件名，默认名字是时间戳生成的。
                //3.如果传入了文件名但是没有后缀，程序会自动解析类型追加后缀名
//                .saveName("")
                ;
        addHeaders(downloadRequest, headers);
        Disposable disposable = downloadRequest.execute(callBack6);
        if (callBack6 != null && callBack6.tag != null) putDisposable(callBack6.tag, disposable);
        return disposable;
    }


    /**
     * 自定义请求示例
     */
    public static CustomRequest custom(String url, Map<String, String> headers, Map<String, String> params) {
        CustomRequest customRequest = EasyHttp.custom()
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .params(params)
                .build();
        addHeaders(customRequest, headers);
        return customRequest;
    }


    //添加请求头
    protected static void addHeaders(BaseRequest baseRequest, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                baseRequest.headers(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 移除缓存
     */
    public static void removeCache(String key) {
        if (key != null) EasyHttp.removeCache(key);
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        EasyHttp.clearCache();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Cookie 区
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 根据 url 获取 Cookie 列表
     * @param url url
     */
    public static List<Cookie> getCookies(String url) {
        CookieManger cookieManger = EasyHttp.getCookieJar();
        return cookieManger.loadForRequest(HttpUrl.get(url));
    }

    /**
     * 查看CookieManger所有cookie
     */
    public static List<Cookie> getAllCookies() {
        CookieManger cookieManger = EasyHttp.getCookieJar();
        PersistentCookieStore cookieStore = cookieManger.getCookieStore();
        return cookieStore.getCookies();
    }

    /**
     * 添加cookie
     */
    public static void addCookie(String url, String name, String value) {
        CookieManger cookieManger = EasyHttp.getCookieJar();
        HttpUrl httpUrl = HttpUrl.get(url);
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name(name).value(value).domain(httpUrl.host()).build();
        cookieManger.saveFromResponse(httpUrl, cookie/*List<Cookie> cookies*/);
    }

    /**
     * 移除cookie
     */
    public static void removeCookie(String url, String name, String value) {
        CookieManger cookieManger = EasyHttp.getCookieJar();
        HttpUrl httpUrl = HttpUrl.get(url);
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name(name).value(value).domain(httpUrl.host()).build();
        cookieManger.remove(httpUrl,cookie);
    }

    /**
     * 清空cookie
     */
    public static void removeAllCookies() {
        CookieManger cookieManger = EasyHttp.getCookieJar();
        cookieManger.removeAll();
    }


   /**
     * 将 Disposable 存起来, 页面销毁的时候取消网络请求
     * @param tag
     * @param disposable
     */
    public static void putDisposable(Object tag, Disposable disposable) {
        if (tag == null) return;
        List<Disposable> list = TAGS.get(tag.getClass().getName());
        if (list == null) list = new ArrayList<>();
        list.add(disposable);
    }

    /**
     * 取消订阅
     * @param tag 传this(activity or fragment or others),在onDestroy的时候:MyOkHttpUtils.cancelTag(this);
     */
    public static void cancelSubscription(Object tag) {
        if (tag != null) {
            String key = tag.getClass().getName();
            List<Disposable> list = TAGS.get(key);
            if (list != null) {
                for (Disposable disposable : list) {
                    //取消请求
                    EasyHttp.cancelSubscription(disposable);
                }
            }
            //移除 tag
            TAGS.remove(key);
        }
        Collection<List<Disposable>> values = TAGS.values();
        Iterator<List<Disposable>> iterator = values.iterator();
        while (iterator.hasNext()) {
            List<Disposable> list = iterator.next();
            Iterator<Disposable> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                Disposable disposable = iterator2.next();
                //移除无用的 Disposable
                if (disposable == null || disposable.isDisposed()) iterator2.remove();
            }
            //移除空 List
            if (list.isEmpty()) iterator.remove();
        }
    }
}
