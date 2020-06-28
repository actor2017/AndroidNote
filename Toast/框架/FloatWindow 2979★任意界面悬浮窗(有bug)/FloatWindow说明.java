https://github.com/yhaolpz/FloatWindow
https://www.jianshu.com/p/18cbc862ba7b

Andorid ���������������ʵ����������˼�

���ԣ�
1.֧���϶����ṩ�Զ����ߵȶ���
2.�ڲ��Զ�����Ȩ���������
3.������ָ��Ҫ��ʾ�������Ľ���
4.Ӧ���˵���̨ʱ�����������Զ�����
5.λ�ò��ɱ������������Ȩ������
6.λ�ü���߿����ðٷֱ�ֵ������������ֱ���
7.��ʽ���ã������ˬ

���ɣ�

�� 1 �����ڹ��̵� build.gradle ����ӣ�
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

�� 2 ������Ӧ�õ� build.gradle ����ӣ�
	dependencies {
	        compile 'com.github.yhaolpz:FloatWindow:1.0.7'
	}

ʹ�ã�

0.����Ȩ��
     <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
1.���������ؼ�
        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .build();
setView ���������� View ����� xml ���֡�

2.�ؼ����
        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .setWidth(100)                   //100px
              .setHeight(Screen.width,0.2f)    //��Ļ��ȵ� 20%
              .build();
�����þ�����ֵ����Ļ��/�߰ٷֱȣ�Ĭ��Ϊ wrap_content��

3.��ʾλ��
        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .setX(100)                      //100px
              .setY(Screen.height,0.3f)       //��Ļ�߶ȵ� 30%
              .build();
�����þ�����ֵ����Ļ��/�߰ٷֱȣ�Ĭ��Ϊ 0������Ļ���Ͻ�Ϊԭ�㡣

4.ָ��������ʾ
        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .setFilter(true, A_Activity.class, C_Activity.class)
              .build();
�˷�����ʾ A_Activity��C_Activity ��ʾ�������������������ء�

              .setFilter(false, B_Activity.class)
�˷�����ʾ B_Activity ����������������������ʾ��

ע�⣺setFilter ������������ʶ��� Activity ������

Ҳ����˵����� A_Activity��C_Activity �̳��� BaseActivity��������������ã�

              .setFilter(true, BaseActivity.class)

5.���϶�������
        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .setMoveType(MoveType.slide)         //���϶����ͷź��Զ�����
              .build();
���ṩ 4 �� MoveType :

MoveType.slide : ���϶����ͷź��Զ�����

MoveType.back : ���϶����ͷź��Զ��ص�ԭλ��

MoveType.active : ���϶�

MoveType.free : �����϶������ɸı�λ��

�粻���ã���Ϊ fixed ģʽ�����ɸı�λ�á�

6.����������

        FloatWindow
              .with(getApplicationContext())
              .setView(view)
              .setMoveType(MoveType.slide)
              .setMoveStyle(500, new AccelerateInterpolator())  //���߶���ʱ��Ϊ500ms�����ٲ�ֵ��
              .build();
�Զ��嶯��Ч����ֻ�� MoveType.slide �� MoveType.back ģʽ�����ô���������塣Ĭ�ϼ��ٲ�ֵ����Ĭ�϶���ʱ��Ϊ 300ms��

7.��������

        //�ֶ�����
        FloatWindow.get().show();
        FloatWindow.get().hide();//��onResume֮ǰ����ûЧ��

        //�޸���ʾλ��
        FloatWindow.get().updateX(100);
        FloatWindow.get().updateY(100);

        //����
        FloatWindow.destroy();
���ϲ���Ӧ����������ʼ������У�ע�ⲻ�ܶ� fixed ģʽ�������޸���ʾλ�á�

8.���������

        FloatWindow
                .with(getApplicationContext())
                .setView(imageView)
                .build();

        FloatWindow
                .with(getApplicationContext())
                .setView(button)
                .setTag("new")
                .build();


        FloatWindow.get("new").show();
        FloatWindow.get("new").hide();
        FloatWindow.destroy("new");
������һ������������� tag��֮���ٴ�������ָ��Ψһ tag ���Դ����֣�������к���������

