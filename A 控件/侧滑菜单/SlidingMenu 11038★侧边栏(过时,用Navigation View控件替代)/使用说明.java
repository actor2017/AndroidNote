https://github.com/jfeinstein10/SlidingMenu

1.example��һ��ʾ��,���Կ�ExampleListActivity.apk��ʵ��Ч��,�ɲ��õ��빤�̲鿴

2.��SlidingMenu-master����ģ��Module���뵽����

3.�����ʱ��,�����ָĳ�SlidingMenuLibrary

4.�޸Ĵ���,��build.gradle�е�һЩ����ĵط��޸ĵ�:
3	��:	mavenCentral()			�ĳ�:	jcenter()
6	��:classpath 'com.android.tools.build:gradle:0.4.+'
      �ĳ�:classpath 'com.android.tools.build:gradle:2.2.2'()�����Լ���Ŀ

9	��:	apply plugin: 'android-library'	�ĳ�:	apply plugin: 'com.android.library'
16	��:	compileSdkVersion 17	�ĳ��Լ���Ŀ�İ汾,��:24
17	��:buildToolsVersion "17.0.0"	�ĳ��Լ���Ŀ�İ汾,��:buildToolsVersion "25.0.2"
21	��:targetSdkVersion 16	�ĳ��Լ���Ŀ�İ汾,��:targetSdkVersion 24

5.�����Ŀ����

6.����,�ᱨ��,��FloatMath.sin(f);	�ĳ�	Math.sin(f);

7.���ʹ��SlidingMenu
	1�����Activity�̳�SlidingActivity����SlidingFragmentActivity(�����Activity��û��fragment)

	2����onCreate�ķ���Ȩ�޸ĳ�public

	3����ʼ����SlidingMenu��ص�һЩapi
	setBehindContentView(����������,���򱨴�)

8.һЩAPI:����������������������������������������
����ʾ��д��:
public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
        //����û�б�����
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1.����һ��ʼ����ʾ�����Ĳ���
        setContentView(R.layout.layout_content);
        //2.����һ��ʼ�����������Ĳ���
        setBehindContentView(R.layout.layout_left);
        //3.��ȡ�����ؼ�
        SlidingMenu slidingMenu = getSlidingMenu();
        //4.���ô�����ģʽ(ȫ��,��Ե,���ܻ���)TOUCHMODE_MARGIN,TOUCHMODE_NONE
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //5.���������Ĳ���(����û���������)
        //slidingMenu.setSecondaryMenu(R.layout.layout_right);
        //6.����SlidingMenu������ģʽ�������������Ǵ�������
        slidingMenu.setMode(SlidingMenu.LEFT);
		//7.�����������֮����Ļ��ʣ��ռ�
        slidingMenu.setBehindOffset(200);
		
        initFragment();
    }

	//8.��SlidingMenu�ر�(�ر���߲����)
    mainActivity.getSlidingMenu().toggle();

    //��Fragment��ʾ����(LeftMenuFragmentΪ�β���ListView???��ΪFragment����������,���ÿ����������ӵ�...)
    //��Fragment�����������ļ��еĿյ���Բ��������
    //Fragment������   android.app.Fragment
    //v4����Ҳ��һ��Fragment  android.support.v4.app.Fragment(�����,����)
    private void initFragment() {
		//��ȡ���ݵ�Fragment������
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //��ʼ����  //����һ��getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //��ContentFragment����װ��View����TextView��,�ŵ�rlContent��
        /**
         * ע��:����R.id.layout_content,����д��R.layout.layout_content���������������
         */
        fragmentTransaction.add(R.id.layout_content, new ContentFragment(),"ContentFragment");
        //��LeftMenuFragment����װ��View����TextView��,�ŵ�rlLeft��
        fragmentTransaction.add(R.id.layout_left, new LeftMenuFragment(),"LeftMenuFragment");
        fragmentTransaction.commit();
    }

	//������ṩLeftMenuFragment�Ķ���	ע������д��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public LeftMenuFragment getLeftMenuFragment(){
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag("LeftMenuFragment");
    }
}

