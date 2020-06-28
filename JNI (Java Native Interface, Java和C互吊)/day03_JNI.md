#JNI
C代码回调Java代码

#C代码回调Java
------
* C代码调用Java代码应用场景
	* 复用已经存在的java代码
	* c语言需要给java一些通知
	* c代码不方便实现的逻辑(界面)
	
* 反射

		//1.加载类字节码
		Class clazz = Demo.class.getClassLoader().loadClass("com.example.Dialog");
		//2.获取方法
		Method method = clazz.getDeclaredMethod("showDialog",String.class);
		//3.调用方法
		method.invoke(clazz.newInstance(), "德玛西亚");

* C代码调Java代码步骤
		
		//1. 加载字节码
		//jclass      (*FindClass)(JNIEnv*, const char*);
		jclass clazz = (*env)->FindClass(env, "cn/itcast/ccalljava/MainActivity");
	
		//2. 获取方法id
		//方法签名：唯一确定一个方法  javap -s 包名.类名
		//在bin/class目录中调用命令行
		//jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
		jmethodID methodId = (*env)->GetMethodID(env, clazz, "showDialog", "(Ljava/lang/String;)V");
	
		//3. 调用方法
		//void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
		(*env)->CallVoidMethod(env, thiz, methodId, (*env)->NewStringUTF(env, "德玛西亚，人在塔在"));

#锅炉压力监测器
-----
	1. 需求
	2. 业务逻辑
		* 锅炉压力V1
			1. C代码中生成一个随机的压力值 rand()%101					
			2. 创建Jni工程
		    3. 在Java中拿到C代码压力返回值

	3. 界面展示进度条	    
		* 锅炉压力V2
			1. C代码回调Java代码显示进度条进度
			2. MainActivity中，写设置进度条进度的方法
			3. C代码中，每隔1秒钟检测压力情况
				flag = 1;
				while(flag){
					int pressure = getPressure();
					jclass  clazz = (*env)->FindClass(env,"com/itheima/monitor/MainActivity");
					jmethodID  methodid = (*env)->GetMethodID(env,clazz,"setPb","(I)V");
					(*env)->CallVoidMethod(env,obj,methodid,pressure);
					sleep(1);
				}

				小细节：C代码是执行在主线程中的，不能阻塞
		
#自定义竖型进度条显示锅炉压力
-----
	4. 锅炉压力V3	
		1. 继承View，写出三个构造方法
		2. 在onDraw方法中绘制矩形
	
				paint = new Paint();
				if (progress < 50) {
					paint.setColor(Color.GREEN);
				}else if(progress >= 50 &&progress < 80){
					paint.setColor(Color.YELLOW);
				}else if (progress >= 80 && progress < 95) {
					paint.setColor(Color.RED);
				}
				canvas.drawRect(50, 50-progress+max, 80, 50+max, paint);
				

		3. 刷新界面
			 invalidate：使视图无效，如果视图可见，那么会重新调用onDraw方法
				 		 该方法只能用于UI线程
			 postInvalidate：用于非UI线程
