�������project��gradle �� module��gradle.

Error:Failed to resolve: com.android.support:appcompat-v7:27.0.1
�޸�project��gradle,ʾ��:
allprojects {
    repositories {
        google()	//https://dl.google.com/dl/android/maven2/
        jcenter()	//https://jcenter.bintray.com/
				����:implementation "io.reactivex.rxjava3:rxjava:3.0.0-RC2"
					 https://jcenter.bintray.com/io/reactivex/rxjava2/rxjava/2.2.6/rxjava-2.2.6.jar
        maven { url "https://jitpack.io"}
//        mavenCentral()//https://oss.sonatype.org/content/repositories/releases/�ϰ汾StudioĬ��
//        maven { url "https://maven.google.com" }
//        maven { url "http://maven.aliyun.com/nexus/content/groups/public" }//�����Ʋֿ�
		maven { url 'http://maven.aliyun.com/nexus/content/repositories/google' }
		maven { url 'http://maven.aliyun.com/nexus/content/repositories/jcenter'}
//        maven { url 'http://maven.oschina.net/content/groups/public/'}//oschian�ľ���,�Ѿ�������
}


����Ӧ��������ĳЩ���������26�汾,�������������ʱ��,��ò�Ҫ��+����汾��.
Error:(15, 21) No resource found that matches the given name: attr ��android:keyboardNavigationCluster��.
�޸�module��gradle 
1.��compileSdkVersion��26 
2.buildToolsVersion�ġ�26.0.1�� 
3.compile ��com.android.support:appcompat-v7:26.0.1���滻֮ǰ�İ汾


Error:Unable to find method 'com.android.build.gradle.tasks.factory.AndroidJavaCompile.setDependencyCacheDir(Ljava/io/File;)V'.
Re-download dependencies and sync project (requires network)
Stop Gradle build processes (requires restart)
�ҵ���Ŀ\gradle\wrapper\gradle-wrapper.properties, �޸�����gradle-x.x-all.zip�汾


Ī�������bug, AndroidStudio��Terminal������:
gradlew compileDebugJavaWithJavac

�鿴 AndroidManifest.xml �ϲ����:
1.�鿴"�嵥�ļ��ϲ����.jpg"
  ���ߵ�� �嵥�ļ��·���"Merged Manifest", �鿴���Ͻ�

�鿴���������:
1.�鿴"2.�鿴��Ŀ������(����Ҳ�û����,��AllModules��appģ�����л�����).jpg"
  ���� gradlew -q app:dependencies
