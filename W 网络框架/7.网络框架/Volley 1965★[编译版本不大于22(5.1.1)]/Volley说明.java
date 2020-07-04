https://github.com/google/volley
1.volley��һ���򵥵��첽http�⣬���˶��ѡ�ȱ���ǲ�֧��ͬ�����������ƿ���ģʽ������post�����ݣ������OOM,���Բ��ʺ������ϴ��ļ���
Volley���������󲿷�Ĭ��������Apache HttpClient����Apache HttpClient��API 23��ʼ�Ѿ���Android�б��Ƴ��������ˡ�
�����Ϊʲô�ܶ࿪���߻���ΪVolley�Ѿ���ʱ�ˣ���ΪVolley��û��Ǩ�Ƶ��µ�δ�����Ĵ��롣

����Լ�����Volley�Ļ�,compileSdkVersion��Ҫ>=22,������Ϊ��Android6.0(�汾9)��Google�Ƴ���httpClient��ص�API
���ɵ�jar�ļ���ַ:\build\intermediates\bundles\release\classes.jar
���ɵ�aar�ļ���ַ:\build\outputs\aar

Ĭ�������Volley����DefaultRetryPolicy�лὫ��ȡ�����ӵĳ�ʱʱ������Ϊ2.5s�����Ҷ�ÿ������ʧ�ܻ��߳�ʱ����һ���Զ����ԡ�
 ���Զ���һЩ��������Ӧ���ܻᳬ��2s�����󣬿�������Ҫ�����С���¡�Retrofit��Ĭ�ϳ�ʱʱ����10s����������ʧ�ܻ��߳�ʱ�Ĳ��������Զ����ԡ�

�ܶ࿪���߶���˵Retrofit���Volley���졣��Ϊ����ר��ȥ���Թ�����ʵ�����ǲ��Ͻ��ġ�
��ΪVolley���Խ��ʹ��HttpUrlConnection��HttpClient��OkHttp����ʹ�ã���Retrofit����OkHttpһ��
�����������Volley���OkHttp֮������������ͻᷢ��������˵��ʵ���ǲ������¡�


1.�ʺ�������С,������Ƶ���Ĺ���,�ʺϴ����������Ƭ��������(��������ͼƬ)
	����String,JsonObject,jsonArray,xml,image,ImageLoder
2.����������,���Ժ�activity,fragment��
3.android6.0,API 23�Ƴ���httpClient,�����޷�����VolleyԴ��,���:useLibrary...
4.�ڴ滺��
5.��������ʧ�ܺ�᳢��������һ��
6.��֧���ļ��ϴ�/�����ļ�

7.������volleyLib����Ŀ,Ҫ���HttpUrlConnection��jar��,
  ��Ϊgoogle��version9(2.3�汾)�Ƴ���apache��HttpClient

1.����jar����libs	(github�Ϻ���û�йٷ�volley)
�����������:compile 'com.mcxiaoke.volley:library:1.0.19'

2.дһ����,���ڻ�ȡ������еĵ���(���ļ���bean)


2.5.�ڴ�����д:(��ʦ����onCreat��д��)

2.6.��ÿ��Activity/Fragment��д:    private static final String TAG = "��:MainActivity";//������ڱ�ҳ��ر�ʱֹͣ����������

//�ǵ��������Ȩ��

        //1.�����������(ֻ��Ҫһ�����ɡ�����������)
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
	RequestQueue requestQueue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

        //int GET = 0;  int POST = 1;
        //alt + R -->���Convert Anonymous to Inner...�ɳ�ȡ�ڲ���
        //2.��������
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://cn.bing.com",
                new Response.Listener<String>() {   //����ɹ�
                    @Override
                    public void onResponse(String s) {
                        tv_text.setText(s);
                    }
                },
                new Response.ErrorListener() {      //����ʧ��
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "��������ʧ��", Toast.LENGTH_SHORT).show();
                    }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//��д�˷������ڴ��ݲ���,�������Ҫ�Ļ�,[һ�㲻��д]
                //֧��POST��GET

                //Ҫ�����в�����װ��map��, volley�ײ���map���б���,��ƴ�ӳ���Ӧ�Ĳ���
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "����");
                map.put("age", "18");

                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {//��д�˷���,���Զ�������ͷ,�������Ҫ�Ļ�,[һ�㲻��д]
									     //��������:ͳ���ֻ����͵�
                //�û�����, UA, ������ʾ��ǰ�豸,�ͻ����ͺ�,�汾����Ϣ
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
	request.setTag(TAG);						//������ڱ�ҳ��ر�ʱֹͣ����������

        //3.��������ӵ����������
        requestQueue.add(stringRequest);

        //4.�������Ҫ��������,ֻ��Ҫ��д��2��(д�Լ�����������),Ȼ��д��3�������������м���

	//5.ȡ����������:
        @Override
        protected void onStop() {
            super.onStop();
            VolleyInstance.getInstance(this).getQueue().cancelAll(TAG);		//���ȡ�����д���ĳ��ǵ�����
        }

//--------------------------------------------------������������--------------
    //����jsonObject
    public void jsonObjectRequest(View view) {
        //��ʼ���������
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

        //http://www.baidu.com?name=xxx&age=18;
        //key-value

        //���������ʽ��һ��json {name:"xxx", age=18}

        //��1�����󷽷��� ��2�������ַ����3���������(�ύ��ʽ��json(��:{name=xxx&age=18})�� JSONObject)
					      ֱ������json��StringRequest���,��������,�ɴ�null
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://192.168.21" +
                ".158:8080/update.json", null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            String des = response.getString("description");//�ӷ�������ȡ����,Ҫtryc
//                            System.out.println("des:" + des);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        //���json��������ֶΣ��ͷ��أ� ���û�У�Ҳ�������쳣
                        String des = response.optString("des");//����tryc,û�����ݷ���""
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
����jsonArray
    public void jsonArrayRequest(View view) {
        //��ʼ���������
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

        //http://www.baidu.com?name=xxx&age=18;
        //key-value

        //���������ʽ��һ��json {name:"xxx", age=18}

        //��1�����󷽷��� ��2�������ַ����3���������(�ύ��ʽ��json�� JSONObject)
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
����Gson(���Զ���)
    public void gsonRequest(View view) {
        //��ʼ���������
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

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
������ͼƬ(���������)
    public void imageRequest(View view) {
        //��ʼ���������
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

        //��2: �ɹ��Ļص�; ��3:�����; ��4:���߶�; ��5:���ŷ�ʽ;��6:ͼƬ��ʽ
        //ARGB_8888: ÿ��ɫֵռ8λ, 4���ֽ�
        //ARGB_4444: 2���ֽ�
        //RGB_565: û��͸������Ϣ, 2���ֽ�
        //ALPHA_8: ֻ��͸������Ϣ, 1���ֽ�(�Ҷ�ͼƬ,�ڰ�)
        ImageRequest request = new ImageRequest("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1491188264&di" +
                "=20ca0a8460b60569002a93f59317a6e7&imgtype=jpg&er=1&src=http%3A%2F%2Fh.hiphotos" +
                ".baidu.com%2Fimage%2Fpic%2Fitem%2F91ef76c6a7efce1b620971c3ad51f3deb48f659d.jpg",
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        ivImage.setImageBitmap(response);//����ͼƬ
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
ͼƬ����(�ɼ��ض���ͼƬ,���Զ��建��)
ImageLoader loader = new ImageLoader(queue, new VolleyCache());//(Volley�ķ����ѷ�װ)

    public void imageLoaderRequest(View view) {
        //��ʼ���������
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������

        //����ͼƬ;��1:ͼƬ��ַ;��2:����ص�
        loader.get("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1490593550070&di" +
                "=a03b0fe20d96c1f5c1763182cf42bbf3&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu" +
                ".com%2Fimage%2Fpic%2Fitem%2Ff703738da9773912ec931761fa198618367ae27d.jpg", new
                ImageLoader.ImageListener() {

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean
                            isImmediate) {
                        //����ɹ�
                        Bitmap bitmap = response.getBitmap();
                        ivImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //����ʧ��
                        error.printStackTrace();
                    }
                });

        //queue.add(request);	//����Ҫadd
    }

//================================================================
�Զ���ؼ�����ͼƬ
<NetworkImageView
	android:id="@+id/niv_image"

public void networkImageViewRequest(View view) {	//Button��ť
        //RequestQueue queue = Volley.newRequestQueue(this);
	RequestQueue queue = VolleyInstance.getInstance(this).getQueue();//��ȡ��������
        ImageLoader loader = new ImageLoader(queue, new VolleyCache());

        nivImage.setImageUrl("https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1490593550070&di" +
                "=fade128f203026f666b098abef9f621e&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu" +
                ".com%2Fimage%2Fpic%2Fitem%2Fb999a9014c086e068d8c874b00087bf40ad1cb8e.jpg", loader);
    }
