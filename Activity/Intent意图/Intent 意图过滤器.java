//1、通过Intent可以传递数据
//	a、传递基本的数据类型
//	b、传递对象（对象必须实现Serializable或者实现Parcelable）
//Serializable  : Java标准的基础，把对象存在一个文件中或者我们说把对象序列化到一个文件中
//Parcelable：Android才带有的东西，把对象存储在一块公共内存中
//2、构造方法，对象的引用传递的方式
//3、view.setTag  使用技巧
//4、Application也是可以传递数据MyApplication.appInfo; MyApplication.screenInfo;等

1.Intent的用法：
//=========================================================intent传递数据========
发送方:
Intent intent = new Intent(this,ProductDetailContent.class);
intent.putExtra("content", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
startActivity(intent);

接收方:
String content = getIntent().getStringExtra("content");
        TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(content);

//=========================================================intent传递bundle数据捆========

Intent intent = new Intent(ProductDetailPager.this,BigPhotosActivity.class);

                    Bundle bundle = new Bundle();				//bundle区
                    bundle.putInt("position",vp_viewpager.getCurrentItem());
                    bundle.putStringArrayList("bigImgsList",bigImgsList);
                    bundle.putInt("productId",proId);
                    bundle.putString("uId",uId);
                    String extraMsg = pickView1+"-"+pickView2;
                    bundle.putString("extraMsg",extraMsg);

                    intent.putExtra("bundle",bundle);				//intent传递bundle
                    startActivity(intent);

//----------------------------------------------------intent传递数据,回显------------
1.发送方
intent = new Intent(this,SelectActivity.class);
                intent.putExtra("item",2);
                startActivityForResult(intent,0);		//注意这种写法

    //接收返回来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result;
	    result = data.getStringExtra("result");
            tv_PayWay.setText(result);
            tv_PayWay.setVisibility(View.VISIBLE);


2.接收方★★★★★★★★★★★★★★★★★★★★
int item = getIntent().getIntExtra("item", 0);

		//接收方设置返回数据★★★★★★★★★★★
                intent.putExtra("result", "支付宝");
                setResult(2, intent);
                finish();

//-----------------------------------intent让上一个页面finish------------------
发送方:
startActivityForResult(new Intent(this, CarRegisterActivity.class), 4);//4请求码
onActivityResult:
            case 4://汽车租赁信息登记页面返回
                if (data != null) {
                    finish();
                }
                break;

接收方:
        if (uploadSuccess) {
            setResult(1,new Intent());//1,结果码.如果上传成功,就让上一个页面finish
            finish();
        }

//###################################################intent传递数据,回显###################

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


（1）Action跳转

1、 使用Action跳转，当程序AndroidManifest.xml中某一个 Activity的IntentFilter定义了包含Action，如果恰好与目标Action匹配，且其IntentFilter中没有定义其它的Type或Category过滤条件，那么就正好匹配了。如果手机中有两个以上的Action程序匹配，那么就会弹出一个对话可框来提示说明。例如打开一个网址，弹出可选对话框：
//------------------------------------------
1.4 点击按钮打电话意图
public void onClick(View v) {
				System.out.println("哥哥点击了按钮！");
				// 打 人  泡 妞
				// 1. 创建意图对象
				Intent intent = new Intent();
				// 2. 设置动作
				intent.setAction(Intent.ACTION_CALL);//意图.设置动作(广播)
				// 3. 设置数据
				Uri data = Uri.parse("tel://110");
				intent.setData(data);
				// 4. 激活意图
				startActivity(intent);
			}


                //跳到拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL);//ACTION_CALL直接打电话
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
//----------------------------------------
1.5\自定义意图
java代码中写:
public void open(View v){	//动作监听(王超老师反射写法)
		// 1. 创建意图
		Intent intent = new Intent();
		// 2. 设置动作(随意写,可写成"asdfasdfsfd",但是一般写成:包名+功能名,例:com.itheima.dt.EAT)
		intent.setAction("com.itheima.dt.EAT");
		//设置数据，可写可不写
		intent.setData(Uri.parse("lujing://buzhidao"));
		// 3. 打开第二个页面
		startActivity(intent);

清单文件中:
<!-- 重写activity,重写name,lable(app的桌面名字),重写意图过滤器 -->
            <!-- ★★★★★★★★注意:下面第二行包不要写错了,老师调试了很久★★★★★★★★★★★ -->
        <activity
            android:name="com.heima.intents.SecondActivity"
            android:label="day10_03_意图设置动作激活界面2" >
            <intent-filter>
              			<!-- 这儿就是设置的意图的动作 -->
                <action android:name="secondintent" />
										<!-- 这儿必须重写成DEFAULT -->
                <category android:name="android.intent.category.DEFAULT" />
                <!-- Java代码中写了,这里就必须写scheme:计划 -->
                <data android:scheme="suibian"/>
            </intent-filter>
        </activity>
//==========================================================================
2. 几种Intent的用法
android 中intent是经常要用到的。不管是页面牵转，还是传递数据，或是调用外部程序，系统功能都要用到intent，下面是一些常用intent示例：
 
1.从google搜索内容 
Intent intent = new Intent(); 
intent.setAction(Intent.ACTION_WEB_SEARCH); 
intent.putExtra(SearchManager.QUERY,"searchString") 
startActivity(intent); 

2.浏览网页 
Uri uri = Uri.parse("http://www.google.com"); 
Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
startActivity(it); 

3.显示地图 
Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
Intent it = new Intent(Intent.Action_VIEW,uri); 
startActivity(it); 

4.路径规划 
Uri uri = Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en"); 
Intent it = new Intent(Intent.ACTION_VIEW,URI); 
startActivity(it); 

5.拨打电话 
Uri uri = Uri.parse("tel:xxxxxx"); 
Intent it = new Intent(Intent.ACTION_DIAL, uri);   
startActivity(it); 

6.调用发短信的程序 
Intent it = new Intent(Intent.ACTION_VIEW);    
it.putExtra("sms_body", "The SMS text");    
it.setType("vnd.android-dir/mms-sms");    
startActivity(it); 

7.发送短信 
Uri uri = Uri.parse("smsto:0800000123");    
Intent it = new Intent(Intent.ACTION_SENDTO, uri);    
it.putExtra("sms_body", "The SMS text");    
startActivity(it); 
String body="this is sms demo"; 
Intent mmsintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", number, null)); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, true); 
mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, true); 
startActivity(mmsintent); 
//-----------------------------
//3.1  获取短信管理器				//王超老师讲的发短信用的这种方法
		SmsManager sm = SmsManager.getDefault();
		//3.2  用短信管理器发送短信
		/*
		 * destinationAddress	：接收者的手机号码
		 * scAddress		：呼叫中 null
		 * text			：要发送短信的内容
		 * sentIntent		：短信发送成功报告
		 * deliveryIntent	：对方接受短信成功
		 */
		sm.sendTextMessage(phoneNum, null, smsBody, null, null);
//-----------------------------
8.发送彩信 
Uri uri = Uri.parse("content://media/external/images/media/23");    
Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra("sms_body", "some text");    
it.putExtra(Intent.EXTRA_STREAM, uri);    
it.setType("image/png");    
startActivity(it); 
StringBuilder sb = new StringBuilder(); 
sb.append("file://"); 
sb.append(fd.getAbsoluteFile()); 
Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mmsto", number, null)); 
// Below extra datas are all optional. 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_SUBJECT, subject); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY, body); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_CONTENT_URI, sb.toString()); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE, composeMode); 
intent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT, exitOnSent); 
startActivity(intent); 

9.发送Email 
Uri uri = Uri.parse("mailto:xxx@abc.com"); 
Intent it = new Intent(Intent.ACTION_SENDTO, uri); 
startActivity(it); 
Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");    
it.putExtra(Intent.EXTRA_TEXT, "The email body text");    
it.setType("text/plain");    
startActivity(Intent.createChooser(it, "Choose Email Client")); 
Intent it=new Intent(Intent.ACTION_SEND);      
String[] tos={"me@abc.com"};      
String[] ccs={"you@abc.com"};      
it.putExtra(Intent.EXTRA_EMAIL, tos);      
it.putExtra(Intent.EXTRA_CC, ccs);      
it.putExtra(Intent.EXTRA_TEXT, "The email body text");      
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");      
it.setType("message/rfc822");      
startActivity(Intent.createChooser(it, "Choose Email Client"));    

Intent it = new Intent(Intent.ACTION_SEND);    
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");    
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");    
sendIntent.setType("audio/mp3");    
startActivity(Intent.createChooser(it, "Choose Email Client")); 

10.播放多媒体   
Intent it = new Intent(Intent.ACTION_VIEW); 
Uri uri = Uri.parse("file:///sdcard/song.mp3"); 
it.setDataAndType(uri, "audio/mp3"); 
startActivity(it); 
Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");    
Intent it = new Intent(Intent.ACTION_VIEW, uri);    
startActivity(it); 

11.uninstall apk 
Uri uri = Uri.fromParts("package", strPackageName, null);    
Intent it = new Intent(Intent.ACTION_DELETE, uri);    
startActivity(it); 

12.install apk 
Uri installUri = Uri.fromParts("package", "xxx", null); 
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri); 

13. 打开照相机 
    <1>Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null); 
           this.sendBroadcast(i); 
     <2>long dateTaken = System.currentTimeMillis(); 
            String name = createName(dateTaken) + ".jpg"; 
            fileName = folder + name; 
            ContentValues values = new ContentValues(); 
            values.put(Images.Media.TITLE, fileName); 
            values.put("_data", fileName); 
            values.put(Images.Media.PICASA_ID, fileName); 
            values.put(Images.Media.DISPLAY_NAME, fileName); 
            values.put(Images.Media.DESCRIPTION, fileName); 
            values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName); 
            Uri photoUri = getContentResolver().insert( 
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
             
            Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
            inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); 
            startActivityForResult(inttPhoto, 10); 

14.从gallery选取图片 
  Intent i = new Intent(); 
            i.setType("image/*"); 
            i.setAction(Intent.ACTION_GET_CONTENT); 
            startActivityForResult(i, 11); 

15. 打开录音机 
   Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
            startActivity(mi); 

16.显示应用详细列表       
Uri uri = Uri.parse("market://details?id=app_id");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it);         
//where app_id is the application ID, find the ID          
//by clicking on your application on Market home          
//page, and notice the ID from the address bar      

刚才找app id未果，结果发现用package name也可以 
Uri uri = Uri.parse("market://details?id=<packagename>"); 
这个简单多了 

17寻找应用       
Uri uri = Uri.parse("market://search?q=pname:pkg_name");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it); 
//where pkg_name is the full package path for an application       

18打开联系人列表 
            <1>            
           Intent i = new Intent(); 
           i.setAction(Intent.ACTION_GET_CONTENT); 
           i.setType("vnd.android.cursor.item/phone"); 
           startActivityForResult(i, REQUEST_TEXT); 

            <2> 
            Uri uri = Uri.parse("content://contacts/people"); 
            Intent it = new Intent(Intent.ACTION_PICK, uri); 
            startActivityForResult(it, REQUEST_TEXT); 

19 打开另一程序 
Intent i = new Intent(); 
            ComponentName cn = new ComponentName("com.yellowbook.android2", 
                    "com.yellowbook.android2.AndroidSearch"); 
            i.setComponent(cn); 
            i.setAction("android.intent.action.MAIN"); 
            startActivityForResult(i, RESULT_OK); 

20.调用系统编辑添加联系人（高版本SDK有效）：
Intent it = newIntent(Intent.ACTION_INSERT_OR_EDIT);
               it.setType("vnd.android.cursor.item/contact");
                //it.setType(Contacts.CONTENT_ITEM_TYPE);
                it.putExtra("name","myName");
               it.putExtra(android.provider.Contacts.Intents.Insert.COMPANY,  "organization");
               it.putExtra(android.provider.Contacts.Intents.Insert.EMAIL,"email");
                it.putExtra(android.provider.Contacts.Intents.Insert.PHONE,"homePhone");
                it.putExtra(android.provider.Contacts.Intents.Insert.SECONDARY_PHONE,
                               "mobilePhone");
                it.putExtra(  android.provider.Contacts.Intents.Insert.TERTIARY_PHONE,
                               "workPhone");
               it.putExtra(android.provider.Contacts.Intents.Insert.JOB_TITLE,"title");
                startActivity(it);
 
21.调用系统编辑添加联系人（全有效）：
Intent intent = newIntent(Intent.ACTION_INSERT_OR_EDIT);
           intent.setType(People.CONTENT_ITEM_TYPE);
           intent.putExtra(Contacts.Intents.Insert.NAME, "My Name");
           intent.putExtra(Contacts.Intents.Insert.PHONE, "+1234567890");
           intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE,Contacts.PhonesColumns.TYPE_MOBILE);
           intent.putExtra(Contacts.Intents.Insert.EMAIL, "com@com.com");
           intent.putExtra(Contacts.Intents.Insert.EMAIL_TYPE,                    Contacts.ContactMethodsColumns.TYPE_WORK);
           startActivity(intent);

