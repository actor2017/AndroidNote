https://github.com/laobie/StatusBarUtil
http://www.jcodecraeer.com/a/opensource/2016/0328/4095.html
http://jaeger.itscoder.com/android/2016/03/27/statusbar-util.html(���İ���ϸ˵��)
Demo���ص�ַ:https://fir.im/5mnp


4.4֮�ϲſ����޸�'״̬����ɫ'
Android6.0����ϵͳ����С�׵�MIUI V6���ϰ汾���ߣ������Flyme4.0���ϰ汾�ſ�������'״̬��Ϊ����'��


ʹ��
1.�� build.gradle �ļ����������, StatusBarUtil �Ѿ������� JCenter:
compile 'com.jaeger.statusbarutil:library:1.4.0'

2.�� setContentView() ֮���������Ҫ�ķ���������:
setContentView(R.layout.main_activity);
ButterKnife.bind(this);//�󶨺����, ����viewû�г�ʼ��
StatusBarUtil.setColor(MainActivity.this, mColor);
ʾ��:StatusBarUtil.setColor(this, getResources().getColor(R.color.deep_green));

3.�������һ������ DrawerLayout �Ľ�����ʹ��,
����Ҫ�ڲ����ļ���Ϊ DrawerLayout ��� android:fitsSystemWindows="true" ����:



1.����״̬����ɫ
StatusBarUtil.setColor(Activity activity, int color);//Status��ɫ�и���ɫ
StatusBarUtil.setColor(this, getResources().getColor(R.color.deep_green),0);//Status�������õ���ɫ,0:͸����

2.����״̬����͸��
StatusBarUtil.setTranslucent(Activity activity, int statusBarAlpha);

3.����״̬��ȫ͸��
StatusBarUtil.setTransparent(Activity activity);

4.Ϊ���� DrawerLayout �Ľ�������״̬����ɫ��Ҳ�������ð�͸����ȫ͸����
StatusBarUtil.setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color);

5.Ϊʹ�� ImageView ��Ϊͷ���Ľ�������״̬��͸��.ע��:�������android:scaleType="centerCrop",���������ʾЧ������
StatusBarUtil.setTranslucentForImageView(Activity activity, int statusBarAlpha, View needOffsetView);
ʾ��:StatusBarUtil.setTranslucentForImageView(this, 30, null);//����͸����

6.�� Fragment ��ʹ��
��activity����дsetStatusBar����(������дonCreateһ��)
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(UseInFragmentActivity.this, null);
    }

7.Ϊ�������ؽ�������״̬����ɫ
�Ƽ���� bingoogolapple/BGASwipeBackLayout-Android: Android Activity �������� �����һ��ʹ�á�
������ַ:https://github.com/bingoogolapple/BGASwipeBackLayout-Android
StatusBarUtil.setColorForSwipeBack(Activity activity, @ColorInt int color, int statusBarAlpha);

8.StatusBarUtil.setTranslucentForCoordinatorLayout(this,0);//��ʹ��CollapsingToolbarLayout��ʱ��,��bug!!!

9.StatusBarUtil.hideFakeStatusBarView(this);//����α״̬�� View

10.����StatusBarUtil.setLightModeΪ��ɫ����(��������Ч, ����6.0+����)
����CoordinatorLayout���ֵ�ʱ��������ɫ������Ч��(�ڸ�����ConstraintLayout������Ƕ��һ��LinearLayout ����RelativeLayout��Ӧ�þͿ�����)
StatusBarUtil.setLightMode(this);

11.ͨ������ statusBarAlpha ���������Ըı�״̬����͸����ֵ��Ĭ��ֵ��112����ֵ��Ҫ�� 0 ~ 255 ֮��
