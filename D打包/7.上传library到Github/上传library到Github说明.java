1.ע��Github�˺Ų���https://jitpack.io/��github�˺ŵ�¼(ͼ1)

2.����Github Desktop(ͼ2),������ignore:
Repository-->Repository settings...-->Ignored files,ʾ��:
.gradle/
.idea/
.svn/
app/build/generated/
app/build/intermediates/
app/build/tmp/
*.iml
local.properties

��app/.gitignore��(Ҫ��ʾ��apk, �������û����output�ļ���):
/build/generated/
/build/intermediates/
/build/tmp/

������ϴ�����Ӻ���, ��ô�޸�.gitignore�ļ���Ч, �ɱ�������ɾ��, ���ύ �Ϳ�����.


3.��library(�ɰ���sample)�ϴ���Github��ַ,�����½��ļ���captures,��Ч��ͼƬ&�����ļ�������
  README.md����Դ��ַ:
    ͼƬ:<img src="captures/chatlayout.png(��д���ص�ַ)" width=35%></img>
    ����:<a href="https://github.com/actor20170211030627/ChatLayout/raw/master/app/build/outputs/apk/debug/app-debug.apk">download apk</a> or scan qrcode:  <br/>
<img src="captures/qrcode.png" width=35%></img>

4.�����Լ�library����ҳ,���"0 release"(ͼ4)

5.���create a new release,��дһ����Ϣ:(ͼ5)
  1.Tag version		ʾ��:1.0
  2.Release title		ʾ��:1.0
  3.Describe this release

6.����https://jitpack.io/,��¼��ˢ��,
  ����Լ���library,Ȼ����Git it,���Զ����(ͼ6),���������־, ��־����д���ĵ�ַ.
��������ص�ַʾ��:
https://jitpack.io/com/github/actor20170211030627/MyAndroidFrameWork/1.0/MyAndroidFrameWork-1.0.pom
������pom�ļ�Ϊ��, ˵�����ʧ��...


7.��Ҫע�����:��Ҫ��library��build.gradle��������´���,����������������Ŀ��ûע��
android {
    //����Ϊ����libraryע���ڴ��jar����:https://www.jianshu.com/p/b04ef4029b90
    // ���Դ��jar
    task sourcesJar(type: Jar) {
        from android.sourceSets.main.java.srcDirs
        classifier = 'sources'
    }
    task javadoc(type: Javadoc) {
        failOnError false
        source = android.sourceSets.main.java.sourceFiles
        classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
        classpath += configurations.compile
    }
    // ����ĵ�jar
    task javadocJar(type: Jar, dependsOn: javadoc) {
        classifier = 'javadoc'
        from javadoc.destinationDir
    }
	//����� xxx.jar
    task makeJar(type: Jar) {
        baseName 'sdk'//ָ�����ɵ�jar��
        from('build/intermediates/classes/debug/org/cmdmac/cloud/pluginsdk/')//��������class�ļ�
        into('org/cmdmac/cloud/pluginsdk/')//�����jar���Ŀ¼�ṹ
        //ȥ������Ҫ�����Ŀ¼���ļ�('test/', 'BuildConfig.class', 'R.class')
        exclude('DaoMaster.class', 'DaoSession.class')
        exclude{ it.name.startsWith('R$');}//ȥ��R$��ͷ���ļ�
    }
    artifacts {
        archives sourcesJar
        archives javadocJar
        //archives makeJar
    }
}
