//1��ͨ��Intent���Դ�������
//	a�����ݻ�������������
//	b�����ݶ��󣨶������ʵ��Serializable����ʵ��Parcelable��
//Serializable  : Java��׼�Ļ������Ѷ������һ���ļ��л�������˵�Ѷ������л���һ���ļ���
//Parcelable��Android�Ŵ��еĶ������Ѷ���洢��һ�鹫���ڴ���
//2�����췽������������ô��ݵķ�ʽ
//3��view.setTag  ʹ�ü���
//4��ApplicationҲ�ǿ��Դ�������MyApplication.appInfo; MyApplication.screenInfo;��

1.Intent���÷���
//=========================================================intent��������========
���ͷ�:
Intent intent = new Intent(this,ProductDetailContent.class);
intent.putExtra("content", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
startActivity(intent);

���շ�:
String content = getIntent().getStringExtra("content");
        TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(content);

//=========================================================intent����bundle������========

Intent intent = new Intent(ProductDetailPager.this,BigPhotosActivity.class);

                    Bundle bundle = new Bundle();				//bundle��
                    bundle.putInt("position",vp_viewpager.getCurrentItem());
                    bundle.putStringArrayList("bigImgsList",bigImgsList);
                    bundle.putInt("productId",proId);
                    bundle.putString("uId",uId);
                    String extraMsg = pickView1+"-"+pickView2;
                    bundle.putString("extraMsg",extraMsg);

                    intent.putExtra("bundle",bundle);				//intent����bundle
                    startActivity(intent);

//----------------------------------------------------intent��������,����------------
1.���ͷ�
intent = new Intent(this,SelectActivity.class);
                intent.putExtra("item",2);
                startActivityForResult(intent,0);		//ע������д��

    //���շ�����������
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result;
	    result = data.getStringExtra("result");
            tv_PayWay.setText(result);
            tv_PayWay.setVisibility(View.VISIBLE);


2.���շ����������������������
int item = getIntent().getIntExtra("item", 0);

		//���շ����÷������ݡ�����������
                intent.putExtra("result", "֧����");
                setResult(2, intent);
                finish();

//-----------------------------------intent����һ��ҳ��finish------------------
���ͷ�:
startActivityForResult(new Intent(this, CarRegisterActivity.class), 4);//4������
onActivityResult:
            case 4://����������Ϣ�Ǽ�ҳ�淵��
                if (data != null) {
                    finish();
                }
                break;

���շ�:
        if (uploadSuccess) {
            setResult(1,new Intent());//1,�����.����ϴ��ɹ�,������һ��ҳ��finish
            finish();
        }

//###################################################intent��������,����###################

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


��1��Action��ת

1�� ʹ��Action��ת��������AndroidManifest.xml��ĳһ�� Activity��IntentFilter�����˰���Action�����ǡ����Ŀ��Actionƥ�䣬����IntentFilter��û�ж���������Type��Category������������ô������ƥ���ˡ�����ֻ������������ϵ�Action����ƥ�䣬��ô�ͻᵯ��һ���Ի��ɿ�����ʾ˵���������һ����ַ��������ѡ�Ի���
//------------------------------------------
1.4 �����ť��绰��ͼ
public void onClick(View v) {
				System.out.println("������˰�ť��");
				// �� ��  �� �
				// 1. ������ͼ����
				Intent intent = new Intent();
				// 2. ���ö���
				intent.setAction(Intent.ACTION_CALL);//��ͼ.���ö���(�㲥)
				// 3. ��������
				Uri data = Uri.parse("tel://110");
				intent.setData(data);
				// 4. ������ͼ
				startActivity(intent);
			}


                //�������Ž���
                Intent intent = new Intent(Intent.ACTION_DIAL);//ACTION_CALLֱ�Ӵ�绰
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
//----------------------------------------
1.5\�Զ�����ͼ
java������д:
public void open(View v){	//��������(������ʦ����д��)
		// 1. ������ͼ
		Intent intent = new Intent();
		// 2. ���ö���(����д,��д��"asdfasdfsfd",����һ��д��:����+������,��:com.itheima.dt.EAT)
		intent.setAction("com.itheima.dt.EAT");
		//�������ݣ���д�ɲ�д
		intent.setData(Uri.parse("lujing://buzhidao"));
		// 3. �򿪵ڶ���ҳ��
		startActivity(intent);

�嵥�ļ���:
<!-- ��дactivity,��дname,lable(app����������),��д��ͼ������ -->
            <!-- ���������ע��:����ڶ��а���Ҫд����,��ʦ�����˺ܾá����������� -->
        <activity
            android:name="com.heima.intents.SecondActivity"
            android:label="day10_03_��ͼ���ö����������2" >
            <intent-filter>
              			<!-- ����������õ���ͼ�Ķ��� -->
                <action android:name="secondintent" />
										<!-- ���������д��DEFAULT -->
                <category android:name="android.intent.category.DEFAULT" />
                <!-- Java������д��,����ͱ���дscheme:�ƻ� -->
                <data android:scheme="suibian"/>
            </intent-filter>
        </activity>
//==========================================================================
2. ����Intent���÷�
android ��intent�Ǿ���Ҫ�õ��ġ�������ҳ��ǣת�����Ǵ������ݣ����ǵ����ⲿ����ϵͳ���ܶ�Ҫ�õ�intent��������һЩ����intentʾ����
 
1.��google�������� 
Intent intent = new Intent(); 
intent.setAction(Intent.ACTION_WEB_SEARCH); 
intent.putExtra(SearchManager.QUERY,"searchString") 
startActivity(intent); 

2.�����ҳ 
Uri uri = Uri.parse("http://www.google.com"); 
Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
startActivity(it); 

3.��ʾ��ͼ 
Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
Intent it = new Intent(Intent.Action_VIEW,uri); 
startActivity(it); 

4.·���滮 
Uri uri = Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en"); 
Intent it = new Intent(Intent.ACTION_VIEW,URI); 
startActivity(it); 

5.����绰 
Uri uri = Uri.parse("tel:xxxxxx"); 
Intent it = new Intent(Intent.ACTION_DIAL, uri);   
startActivity(it); 

6.���÷����ŵĳ��� 
Intent it = new Intent(Intent.ACTION_VIEW);    
it.putExtra("sms_body", "The SMS text");    
it.setType("vnd.android-dir/mms-sms");    
startActivity(it); 

7.���Ͷ��� 
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
//3.1  ��ȡ���Ź�����				//������ʦ���ķ������õ����ַ���
		SmsManager sm = SmsManager.getDefault();
		//3.2  �ö��Ź��������Ͷ���
		/*
		 * destinationAddress	�������ߵ��ֻ�����
		 * scAddress		�������� null
		 * text			��Ҫ���Ͷ��ŵ�����
		 * sentIntent		�����ŷ��ͳɹ�����
		 * deliveryIntent	���Է����ܶ��ųɹ�
		 */
		sm.sendTextMessage(phoneNum, null, smsBody, null, null);
//-----------------------------
8.���Ͳ��� 
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

9.����Email 
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

10.���Ŷ�ý��   
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

13. ������� 
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

14.��galleryѡȡͼƬ 
  Intent i = new Intent(); 
            i.setType("image/*"); 
            i.setAction(Intent.ACTION_GET_CONTENT); 
            startActivityForResult(i, 11); 

15. ��¼���� 
   Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
            startActivity(mi); 

16.��ʾӦ����ϸ�б�       
Uri uri = Uri.parse("market://details?id=app_id");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it);         
//where app_id is the application ID, find the ID          
//by clicking on your application on Market home          
//page, and notice the ID from the address bar      

�ղ���app idδ�������������package nameҲ���� 
Uri uri = Uri.parse("market://details?id=<packagename>"); 
����򵥶��� 

17Ѱ��Ӧ��       
Uri uri = Uri.parse("market://search?q=pname:pkg_name");         
Intent it = new Intent(Intent.ACTION_VIEW, uri);         
startActivity(it); 
//where pkg_name is the full package path for an application       

18����ϵ���б� 
            <1>            
           Intent i = new Intent(); 
           i.setAction(Intent.ACTION_GET_CONTENT); 
           i.setType("vnd.android.cursor.item/phone"); 
           startActivityForResult(i, REQUEST_TEXT); 

            <2> 
            Uri uri = Uri.parse("content://contacts/people"); 
            Intent it = new Intent(Intent.ACTION_PICK, uri); 
            startActivityForResult(it, REQUEST_TEXT); 

19 ����һ���� 
Intent i = new Intent(); 
            ComponentName cn = new ComponentName("com.yellowbook.android2", 
                    "com.yellowbook.android2.AndroidSearch"); 
            i.setComponent(cn); 
            i.setAction("android.intent.action.MAIN"); 
            startActivityForResult(i, RESULT_OK); 

20.����ϵͳ�༭�����ϵ�ˣ��߰汾SDK��Ч����
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
 
21.����ϵͳ�༭�����ϵ�ˣ�ȫ��Ч����
Intent intent = newIntent(Intent.ACTION_INSERT_OR_EDIT);
           intent.setType(People.CONTENT_ITEM_TYPE);
           intent.putExtra(Contacts.Intents.Insert.NAME, "My Name");
           intent.putExtra(Contacts.Intents.Insert.PHONE, "+1234567890");
           intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE,Contacts.PhonesColumns.TYPE_MOBILE);
           intent.putExtra(Contacts.Intents.Insert.EMAIL, "com@com.com");
           intent.putExtra(Contacts.Intents.Insert.EMAIL_TYPE,                    Contacts.ContactMethodsColumns.TYPE_WORK);
           startActivity(intent);

