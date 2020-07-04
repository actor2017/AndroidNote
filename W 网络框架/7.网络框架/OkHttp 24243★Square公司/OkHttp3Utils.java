package com.ly.bridgeemergency.fragment;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 本工具依赖的jar包:okhttp-3.10.0.jar okio-1.14.1.jar
 * 同步上传json见 {@link #postJsonSync(String, String, Class)}
 *
 * @author actor
 * @date 2018年5月30日下午18:24:37
 */
public class OkHttp3Utils {

    private static OkHttpClient okHttpClient;
    private static final boolean isDebugMode = true;//是否是debug模式

    private OkHttp3Utils() {
        throw new RuntimeException("can not be new!!");
    }

    // 创建OkHttpClient请求对象
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttp3Utils.class) {
                if (okHttpClient == null) {
                    /**
                     * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，而是通过OkHttpClient.Builder来设置，
                     * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
                     * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
                     */
                    // File sdcache = getExternalCacheDir();
                    // File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
                    // int cacheSize = 10 * 1024 * 1024;
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    if (isDebugMode) {
                        // Log信息拦截器, 打印Log 日志
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
                        builder.addInterceptor(loggingInterceptor);
                    }
                    okHttpClient = builder.connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                    // .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize)).build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * get异步请求数据
     *
     * @param url
     * @param params 参数
     * @param callback 回调
     */
    public static void getAsync(String url, Map<String, String> params, Callback callback) {
        StringBuffer stringBuffer = new StringBuffer(url);
        OkHttpClient okHttpClient = getInstance();
        if (params != null && params.size() > 0) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    if (!url.contains("?")) {
                        stringBuffer.append('?');
                    }
                    i++;
                } else {
                    stringBuffer.append('&');
                }
                stringBuffer.append(key);
                stringBuffer.append('=');
                stringBuffer.append(params.get(key));
            }
        }
        println(stringBuffer.toString());
        Request request = new Request.Builder().url(stringBuffer.toString()).build();// 创建Request
        Call call = okHttpClient.newCall(request);// 得到Call对象
        call.enqueue(callback);// 执行异步请求
    }

    /**
     * get同步请求数据
     *
     * @param url
     * @param params 参数
     * @param entity 解析成什么实体类
     */
    public static <T> T getSync(String url, Map<String, String> params, Class<T> entity) {
        StringBuffer stringBuffer = new StringBuffer(url);
        OkHttpClient okHttpClient = getInstance();
        if (params != null && params.size() > 0) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    if (!url.contains("?")) {
                        stringBuffer.append('?');
                    }
                    i++;
                } else {
                    stringBuffer.append('&');
                }
                stringBuffer.append(key);
                stringBuffer.append('=');
                stringBuffer.append(params.get(key));
            }
        }
        println(stringBuffer.toString());
        Request request = new Request.Builder().url(stringBuffer.toString()).build();// 创建Request
        Call call = okHttpClient.newCall(request);// 得到Call对象
        try {
            Response response = call.execute();// 执行同步请求
            try {
                String body = response.body().string();
                println(body);
                return JSONObject.parseObject(body, entity);//fastjson的方法
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e1) {
            println("url请求出错:" + stringBuffer.toString());
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * post异步请求数据
     *
     * @param url
     * @param params 参数
     * @param callback 回调
     */
    public static void postAsync(String url, Map<String, String> params, Callback callback) {
        OkHttpClient okHttpClient = getInstance();
        /**
         * 3.x版本post请求换成FormBody 封装键值对参数
         */
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);// 得到Call对象
        call.enqueue(callback);// 执行异步请求
    }

    /**
     * post异步上传文件
     *
     * @param url
     * @param fileKey 文件以表单形式上传过程中的表单key名称
     * @param file 文件
     * @param callback 回调
     */
    public static void postFileAsync(String url, String fileKey, File file, Callback callback) {
        OkHttpClient okHttpClient = getInstance();
        // 创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        // 创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(fileKey, file.getName(), fileBody).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();// 创建Request
        Call call = okHttpClient.newCall(request);// 得到Call
        call.enqueue(callback);// 执行请求
    }

    /**
     * Post异步向服务器传递Json.
     * 1.设置Headers里参数:Content-Type:application/json
     *
     * @param url
     * @param json   传递的Json放入请求体(body)
     * @param entity 你想解析成什么实体类
     */
    public static <T> void postJsonAsync(final String url, final String json, final Class<T> entity,
                                         final OnPostJsonListener<T> listener) {
        //设置Headers里参数:Content-Type:application/json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                if (listener != null) {
                    try {
                        String body = response.body().string();
                        println(body);
                        listener.onSuccess(JSONObject.parseObject(body, entity));
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onSuccess(null);//解析错误
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                println("url请求出错:" + url);
                println("post的json:" + json);
                e.printStackTrace();
                if (listener != null) {
                    listener.onSuccess(null);//没有网络/火眼服务器报错
                }
            }
        });
    }

    /**
     * Post同步向服务器传递Json.
     * 1.设置Headers里参数:Content-Type:application/json
     *
     * @param url
     * @param json   传递的Json放入请求体(body)
     * @param entity 你想解析成什么实体类
     */
    public static <T> T postJsonSync(String url, String json, Class<T> entity) {
        //设置Headers里参数:Content-Type:application/json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; " +
				"charset=utf-8"), json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = getInstance().newCall(request).execute();//同步
            try {
                String body = response.body().string();
                println(body);
                return JSONObject.parseObject(body, entity);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e1) {
            println("url请求出错:" + url);
            println("post的json:" + json);
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * post异步向服务器传递String
     *
     * @param url
     * @param string
     * @param callback
     */
    public static void postStringAsync(String url, String string, Callback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), string);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 同步下载文件
     *
     * @param url
     * @param saveDir 保存路径
     * @return
     */
    public static File getFileSync(String url, String saveDir) {
        Request request = new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (response.isSuccessful()) {
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try {
                is = response.body().byteStream();
                File file = new File(saveDir);
                if (!file.exists()) file.mkdirs();
                File downloadFile = new File(file, getFileNameFromUrl(url));// 文件
                fos = new FileOutputStream(downloadFile);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                return downloadFile;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 异步下载文件
     * @param url 请求Url
     * @param saveDir 保存文件的路径
     * @param listener 下载监听回调
     */
    public static void getFileAsync(final String url, final String saveDir,
                                    final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (listener != null) listener.onNetFailure(call, e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fileOutputStream = null;
                try {
                    inputStream = response.body().byteStream();
                    File file = new File(saveDir);
                    if (!file.exists()) file.mkdirs();
//                  final String fileDir = isExistDir(saveDir);// apk保存路径
                    File downloadFile = new File(file, getFileNameFromUrl(url));// 文件
                    fileOutputStream = new FileOutputStream(downloadFile);
                    while ((len = inputStream.read(buf)) != -1) {
                        fileOutputStream.write(buf, 0, len);
                    }
                    fileOutputStream.flush();
                    if (listener != null) {
                        listener.onSuccess(call, request, downloadFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) listener.onDownloadFailure(e);
                } finally {
                    if (inputStream != null)
                        inputStream.close();
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                }
            }
        });
    }

    public interface OnResultListener {
        void onOk(File file);

        void onError(Exception e);
    }

    /**
     * 判断下载目录是否存在
     * @param saveDir
     * @return
     * @throws IOException
     */
//	public static String isExistDir(String saveDir) throws IOException {
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 下载位置
//			File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
//			if (!downloadFile.mkdirs()) {
//				downloadFile.createNewFile();
//			}
//			String savePath = downloadFile.getAbsolutePath();
//			Log.e("savePath", savePath);
//			return savePath;
//		}
//		return null;
//	}

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private static void println(String string) {
        System.out.println(string);
    }

    public interface OnPostJsonListener<T> {
        void onSuccess(T entity);
    }

    /**
     * Description: 异步下载文件监听
     * Copyright  : Copyright (c) 2018
     * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
     * Author     : 李大发
     * Date       : ${Date} on 22:06
     */
    public interface OnDownloadListener {
        void onSuccess(Call call, Request request, File downloadFile);

        void onNetFailure(Call call, IOException e);

        void onDownloadFailure(IOException e);
    }
}
