https://github.com/google/volley
1.volley是一个简单的异步http库，仅此而已。缺点是不支持同步，这点会限制开发模式；不能post大数据，否则会OOM,所以不适合用来上传文件。
Volley在网络请求部分默认依赖于Apache HttpClient。而Apache HttpClient从API 23开始已经在Android中被移除并废弃了。
这就是为什么很多开发者会认为Volley已经过时了，因为Volley并没有迁移到新的未废弃的代码。

如果自己编译Volley的话,compileSdkVersion需要>=22,这是因为在Android6.0(版本9)中Google移除了httpClient相关的API
生成的jar文件地址:\build\intermediates\bundles\release\classes.jar
生成的aar文件地址:\build\outputs\aar

默认情况下Volley会在DefaultRetryPolicy中会将读取和连接的超时时间设置为2.5s，并且对每次请求失败或者超时都有一次自动重试。
 所以对于一些服务器响应可能会超过2s的请求，开发者需要格外的小心下。Retrofit的默认超时时间是10s，而且它对失败或者超时的操作不会自动重试。

很多开发者都会说Retrofit会比Volley更快。因为有人专门去测试过，其实这里是不严谨的。
因为Volley可以结合使用HttpUrlConnection、HttpClient、OkHttp等来使用，而Retrofit是用OkHttp一起，
所以如果你让Volley结合OkHttp之后再来测试你就会发现总体来说其实他们不相上下。


1.适合数据量小,但请求频繁的工作,适合处理大量的碎片网络请求(批量加载图片)
	请求String,JsonObject,jsonArray,xml,image,ImageLoder
2.有生命周期,可以和activity,fragment绑定
3.android6.0,API 23移除了httpClient,导致无法编译Volley源码,解决:useLibrary...
4.内存缓存
5.网络请求失败后会尝试在请求一次
6.不支持文件上传/下载文件

7.如果添加volleyLib到项目,要添加HttpUrlConnection的jar包,
  因为google在version9(2.3版本)移除了apache的HttpClient

1.导入jar包到libs	(github上好像没有官方volley)
或者添加依赖:compile 'com.mcxiaoke.volley:library:1.0.19'

2.写一个类,用于获取请求队列的单例(见文件夹bean)


2.5.在代码中写:(老师是在onCreat中写的)

2.6.在每个Activity/Fragment中写:    private static final String TAG = "例:MainActivity";//★★用于本页面关闭时停止网络请求★★

//记得添加网络权限

        //1.创建请求队列(只需要一个即可★★★★★★★★★★★)
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
	RequestQueue requestQueue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        //int GET = 0;  int POST = 1;
        //alt + R -->点击Convert Anonymous to Inner...可抽取内部类
        //2.创建请求
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://cn.bing.com",
                new Response.Listener<String>() {   //请求成功
                    @Override
                    public void onResponse(String s) {
                        tv_text.setText(s);
                    }
                },
                new Response.ErrorListener() {      //请求失败
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//重写此方法用于传递参数,如果你需要的话,[一般不用写]
                //支持POST和GET

                //要将所有参数封装在map中, volley底层会对map进行遍历,来拼接出对应的参数
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "张三");
                map.put("age", "18");

                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {//重写此方法,来自定义请求头,如果你需要的话,[一般不用写]
									     //例如用来:统计手机类型等
                //用户代理, UA, 用来表示当前设备,客户端型号,版本等信息
                //User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML,
                // like Gecko) Chrome/54.0.2840.99 Safari/537.36

                HashMap<String, String> map = new HashMap<>();
                map.put("ItcastHeader", "itcast");
                try {
                    map.put("User-Agent", "Android/" + Build.VERSION.RELEASE + " " + android
                            .os.Build.MANUFACTURER + "/" +
                            android.os.Build.MODEL + " Itcast/" + getPackageManager().getPackageInfo
                            (getPackageName(), 0).versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };
	request.setTag(TAG);						//★★用于本页面关闭时停止网络请求★★

        //3.把请求添加到请求队列中
        requestQueue.add(stringRequest);

        //4.如果还需要请求其它,只需要重写第2步(写自己的请求类型),然后写第3步添加请求进队列即可

	//5.取消请求网络:
        @Override
        protected void onStop() {
            super.onStop();
            VolleyInstance.getInstance(this).getQueue().cancelAll(TAG);		//★★取消所有带有某标记的请求
        }

//--------------------------------------------------请求其它类型--------------
    //请求jsonObject
    public void jsonObjectRequest(View view) {
        //初始化请求队列
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        //http://www.baidu.com?name=xxx&age=18;
        //key-value

        //请求参数格式是一段json {name:"xxx", age=18}

        //参1：请求方法； 参2：请求地址；参3：请求参数(提交格式是json(例:{name=xxx&age=18})， JSONObject)
					      直接请求json和StringRequest差不多,但更方便,可传null
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://192.168.21" +
                ".158:8080/update.json", null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            String des = response.getString("description");//从服务器获取数据,要tryc
//                            System.out.println("des:" + des);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        //如果json中有这个字段，就返回， 如果没有，也不会抛异常
                        String des = response.optString("des");//不用tryc,没有数据返回""
                        System.out.println("des:" + des);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

//====================================================================
请求jsonArray
    public void jsonArrayRequest(View view) {
        //初始化请求队列
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        //http://www.baidu.com?name=xxx&age=18;
        //key-value

        //请求参数格式是一段json {name:"xxx", age=18}

        //参1：请求方法； 参2：请求地址；参3：请求参数(提交格式是json， JSONObject)
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.21" +
                ".158:8080/array.json", null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int length = response.length();
                        System.out.println("array:" + length);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

//=============================================================================
请求Gson(需自定义)
    public void gsonRequest(View view) {
        //初始化请求队列
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        GsonRequest request = new GsonRequest(Request.Method.GET, "http://192.168.21" +
                ".158:8080/update.json", UpdateInfo.class, new Response.Listener<UpdateInfo>() {

            @Override
            public void onResponse(UpdateInfo response) {
                System.out.println("result:" + response.description);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }


//==================================================================================
请求单张图片(好像带缓存)
    public void imageRequest(View view) {
        //初始化请求队列
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        //参2: 成功的回调; 参3:最大宽度; 参4:最大高度; 参5:缩放方式;参6:图片格式
        //ARGB_8888: 每个色值占8位, 4个字节
        //ARGB_4444: 2个字节
        //RGB_565: 没有透明度信息, 2个字节
        //ALPHA_8: 只有透明度信息, 1个字节(灰度图片,黑白)
        ImageRequest request = new ImageRequest("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1491188264&di" +
                "=20ca0a8460b60569002a93f59317a6e7&imgtype=jpg&er=1&src=http%3A%2F%2Fh.hiphotos" +
                ".baidu.com%2Fimage%2Fpic%2Fitem%2F91ef76c6a7efce1b620971c3ad51f3deb48f659d.jpg",
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        ivImage.setImageBitmap(response);//设置图片
                    }
                }, 500, 500, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response
                .ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

//==================================================================
图片加载(可加载多张图片,能自定义缓存)
ImageLoader loader = new ImageLoader(queue, new VolleyCache());//(Volley的方法已封装)

    public void imageLoaderRequest(View view) {
        //初始化请求队列
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★

        //请求图片;参1:图片地址;参2:请求回调
        loader.get("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1490593550070&di" +
                "=a03b0fe20d96c1f5c1763182cf42bbf3&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu" +
                ".com%2Fimage%2Fpic%2Fitem%2Ff703738da9773912ec931761fa198618367ae27d.jpg", new
                ImageLoader.ImageListener() {

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean
                            isImmediate) {
                        //请求成功
                        Bitmap bitmap = response.getBitmap();
                        ivImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //请求失败
                        error.printStackTrace();
                    }
                });

        //queue.add(request);	//不需要add
    }

//================================================================
自定义控件加载图片
<NetworkImageView
	android:id="@+id/niv_image"

public void networkImageViewRequest(View view) {	//Button按钮
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//获取单例★★★
        ImageLoader loader = new ImageLoader(queue, new VolleyCache());

        nivImage.setImageUrl("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1490593550070&di" +
                "=fade128f203026f666b098abef9f621e&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu" +
                ".com%2Fimage%2Fpic%2Fitem%2Fb999a9014c086e068d8c874b00087bf40ad1cb8e.jpg", loader);
    }
