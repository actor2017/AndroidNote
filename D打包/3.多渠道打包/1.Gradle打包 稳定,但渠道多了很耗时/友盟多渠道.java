һ����Ŀ��α�������ͬǩ������������Դ�ȣ���apk��.html
https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650826652&idx=1&sn=8bb07e42bcfde541d3477ce07a4f41a2&chksm=80b7b302b7c03a14b95e8ebddd929e5dfd7706292ce62c211c6c6e70f71538889954a1b18443&mpshare=1&scene=24&srcid=1115ZNwqc2f1FThe8oCCEhUu#rd


//ȱ�㣺
1)ÿ����һ������������Ҫ��һ��ִ��һ�鹹�����̣�Ч��̫�ͣ������������������ٵĳ�����
2)Gradle��Ϊÿһ������������һ����ͬ��BuildConfig.java�࣬��¼������Ϣ������ÿһ����������DEX��CRCֵ����ͬ����ͨ����£�����û��Ӱ��ġ����Ǽ�����ʹ����΢�ŵ�Tinker�Ȳ�����������ô����ҪΪ��ͬ����������ͬ�Ĳ�������ȫȻ�ǲ��ܹ����ܵġ�������Tinker��ͨ�����ջ�����APK���°�APK���ɲ�ֲ�����Ȼ���ٰѲ����ͻ�����APKһ��ϳ��°�APK�����Ҫ���������ɲ�ֲ����Ļ�����DEX�����ںϳ��°��Ļ�����DEX��ȫȻһ�µġ�����ÿһ��������������DEX�ļ���ȫȻһ�µģ���Ȼ�ͻ�ϳ�ʧ�ܣ�

<!--1.AndroidManifest��<Application>��,���˶��������-->
<!--@mipmap/ic_launcher,@string/app_name-->
    <application
        android:icon="${app_icon}"
        android:label="${app_name}">

        <!--���˶��������-->
        <meta-data
            android:name="UMENG_CHANNEL"
            android:value="${UMENG_CHANNEL_VALUE}"/><!--baidu, xiaomi...-->

android{

    /**
     * 2.1.module/build.gradle/android{}��,productFlavors:��android�ڵ��һ���Խڵ㡣
     * ����Ҫ��ʲô�����İ�,�������ﰴumeng��Ҫ������������"�嵥�ļ�"��UMENG_CHANNEL_VALUE��ֵ
     */
    productFlavors {//android{ �ڵ���, ��Ʒ������
        baidu{
            applicationId 'com.google.package.baidu'             //�Զ������
			versionCode 32										//�ɲ�����
			versionName '1.3.2'									//�ɲ�����
            manifestPlaceholders = [app_name: "@string/app_name_baidu",//ÿ����������Ӧ��������һ��
                                    app_icon: "@mipmap/icon_baidu",//ÿ����������ͼ�궼��һ��
                                    UMENG_CHANNEL_VALUE: "baidu"]//�滻�嵥�ļ��е�ֵ
			buildConfigField("String", "APP_DOMAIN", '"http://183.64.5.22:8546"')//����
			buildConfigField("String", "APP_DOMAIN", "com.blankj.utilcode.util.Utils.getApp().getString(R.string.app_domain_inner)")
        }
        xiaomi{
            manifestPlaceholders=[UMENG_CHANNEL_VALUE: "xiaomi"]
        }
		
		//�ۺϼ��(���Ժ,������������) ʾ��
		inner {
            manifestPlaceholders = [app_name: "@string/app_name_inner"]//�滻�嵥�ļ��е�ֵ, app����: �ۺϼ��
            //����
            buildConfigField("String", "APP_DOMAIN", "com.blankj.utilcode.util.Utils.getApp().getString(R.string.app_domain_inner)")//url = ����url
        }
        outer {
            manifestPlaceholders = [app_name: "@string/app_name_outer"]//�滻�嵥�ļ��е�ֵ, app����: �ۺϼ��(��)
            //����
            buildConfigField("String", "APP_DOMAIN", "com.blankj.utilcode.util.Utils.getApp().getString(R.string.app_domain_outer)")//url = ����url
        }
	}

    //2.2.����д��̫�鷳,ÿ����������flavor�����Ʋ�ͬ,�Ż�����:
    productFlavors {//android{ �ڵ���, ��Ʒ������
        baidu{}
        xiaomi{}
    }
    //productFlavors.allд���������ǩ productFlavors{}��Ҳ�ǿ��Ե�
    productFlavors.all { flavor ->
        flavor.manifestPlaceholders = [app_name: "@string/app_name_${name}",//app_name_baidu
                                       app_icon: "@mipmap/icon_${name}",//icon_baidu
                                       UMENG_CHANNEL_VALUE: name]
    }

    //3.1.�޸�apk��������(û��Ҫ�ɲ�д)
    //Gradle�汾3.0���¿�����������,�����ɵ�apk���ϰ汾��:app-xiaomi-release.apk --> app-xiaomi-release-1.0.apk
    applicationVariants.all { variant ->
        variant.outputs.each { output ->
            def outputFile = output.outputFile
            if (outputFile != null && outputFile.name.endsWith('.apk')) {
                def fileName = outputFile.name.replace(".apk", "-${defaultConfig.versionName}.apk")
                output.outputFile = new File(outputFile.parent, fileName)
            }
        }
    }

    //3.2.���Gradle�汾��3������(û��Ҫ�ɲ�д)
    //���������:Error:All flavors must now belong to a named flavor dimension.Learn more at https://d.android.com/r/tools/flavorDimensions-missing-error-message.html,��ô��defaultConfig {}��ǩ�����:flavorDimensions "versionCode"//flavor dimension����ά�Ⱦ��Ǹð汾��,����ά�Ⱦ��Ƕ���ͳһ����
    /*android.����Ҳ����, ����?*/applicationVariants.all { variant ->    //�����޸�Apk����
        variant.outputs.all { output ->
            if (!variant.buildType.isDebuggable()) {
                //��ȡǩ�������� variant.signingConfig.name
                //Ҫ���滻��Դ�ַ���
//                def sourceFile = "-${variant.flavorName}-${variant.buildType.name}"//baidu/xiaomi-release
				//def date = new Date().format("yyyyMMdd" , TimeZone.getTimeZone("GMT+08"))//20200326
                def sourceFile = ".apk"
                //�滻���ַ��� //���apk����Ϊ��������_�汾��_ʱ��.apk
//                def replaceFile = "${variant.productFlavors[0].name}_V${variant.versionName}_${variant.flavorName}_${variant.buildType.name}"
                def replaceFile = "${variant.flavorName}_${variant.versionName}_${versionName}_${releaseTime()}.apk"
                outputFileName = output.outputFile.name.replace(sourceFile, replaceFile)
            }
			
			output.outputFile = new File('C:\\Users\\54225\\Desktop\\my.apk')  
			variant.getPackageApplication().outputDirectory = new File("D:/MyAppName/apk")//�޸����·��, ������
			
			if (variant.flavorName == 'baidu') {
				buildConfigField("String", "DNS", "\"http://119.29.29.29\"")
			} else if (variant.flavorName == 'xiaomi') {
				buildConfigField("String", "DNS", "\"http://8.8.8.8\"")
			}
			
			//�ۺϼ��(���Ժ,������������) ʾ��
			if (variant.flavorName == 'inner') {
				outputFileName = "${variant.flavorName}_${versionName}.apk"//inner_1.0.0.apk ������
				outputFileName = "�ۺϼ��_${versionName}.apk"//�ۺϼ��_1.0.0.apk
			} else if (variant.flavorName == 'outer') {
				outputFileName = "${variant.flavorName}_${versionName}.apk"//outer_1.0.0.apk ������
				outputFileName = "�ۺϼ��(��)_${versionName}.apk"//�ۺϼ��(��)_1.0.0.apk
			}
        }
    }
	
	//�ǻۻ��� ʾ��
	android.applicationVariants.all { variant ->
		variant.outputs.all {
			if (variant.buildType.name.equals('release')) {
				outputFileName = "zhjg_${defaultConfig.versionName}��ʽ.apk"//zhjg_1.1��ʽ.apk
			} else if (variant.buildType.name.equals('debug')) {
				outputFileName = "zhjg_${defaultConfig.versionName}����.apk"//zhjg_1.1����.apk
			}
		}
	}
}
//��ȡ����(û��Ҫ�ɲ�д)
def releaseTime() {
    return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
}

4.���������ǿ���ͨ����ȡmate-data��Ϣ����ȡ����
    private String getChannel() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }
