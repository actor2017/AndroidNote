https://github.com/gongwen/MarqueeViewLibrary
һ����������ʵ�������Ч����library��ͨ���ṩ��ͬ��MarqueeFactory�����Ʋ�ͬ�������View�� �����ṩ�˳������͵������Ч����SimpleMarqueeView

Gradle:
compile 'com.gongwen:marqueelibrary:1.1.3'


����
MarqueeView����

Attribute ����		Description ����
flipInterval		��ҳʱ����
marqueeAnimDuration	����ִ��ʱ��
inAnimation		marquee in����
outAnimation		marquee out����
autoStart		�����Ƿ��Զ���ʼ,Ĭ��true
animateFirstView	��ǰChildView��һ���Ƿ񶯻�չʾ



SimpleMarqueeView����(֧��MarqueeView�������Լ���������)

Attribute ����	Description ����
smvTextSize	���ִ�С
smvTextColor	������ɫ
smvTextGravity	����λ��
smvTextSingleLine	�����Ƿ�����ʾ
smvTextEllipsize	������ʾ����ʱ��ϵͳ�Ĵ���ʽ(��ѡ��none��start��middle��end)


�����÷���ʹ��SimpleMarqueeView��SimpleMF
XML
<com.gongwen.marqueen.SimpleMarqueeView
    android:id="@+id/simpleMarqueeView"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:flipInterval="2500"
    android:inAnimation="@anim/in_right"
    android:outAnimation="@anim/out_left"
    app:marqueeAnimDuration="2000"
    app:smvTextColor="@color/white"
    app:smvTextEllipsize="end"
    app:smvTextGravity="center_vertical"
    app:smvTextSingleLine="true"
    app:smvTextSize="15sp" />


List<String> datas = Arrays.asList("�����ù�ԭ���ͱ�", "����ԭ�ϲݣ�һ��һ���١�", "Ұ���ղ��������紵������", "Զ���ֹŵ������ӻĳǡ�", "��������ȥ�����������顣");
//SimpleMarqueeView<T>��SimpleMF<T>������Tָ���������������ͣ�����String��Spanned��
SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.marqueeView);
SimpleMF<String> marqueeFactory = new SimpleMF(this);
marqueeFactory.setData(datas);
marqueeView.setMarqueeFactory(marqueeFactory);
marqueeView.startFlipping();


���ü����¼�
marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
    @Override
    public void onItemClickListener(TextView mView, String mData, int mPosition) {
        /**
        * ע�⣺
        * ��MarqueeView����Viewʱ��mView����ǰ��ʾ����View��mData��mView���������ݣ�mPosition��mView������
        * ��MarqueeView����Viewʱ��mView��null��mData��null��mPosition����1
        */
        Toast.makeText(MainActivity.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
    }
});


��չ�÷����Զ���MarqueeFactory��������������ItemView
XML
<com.gongwen.marqueen.MarqueeView
    android:id="@+id/marqueeView"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:flipInterval="2500"
    android:inAnimation="@anim/in_right"
    android:outAnimation="@anim/out_left"
    app:marqueeAnimDuration="2000">
</com.gongwen.marqueen.MarqueeView>


�Զ���MarqueeFactory
�̳���MarqueeFactory��ͨ������ָ��ItemView�����Լ�ItemData���ͣ�֮��ʵ��generateMarqueeItemView�������ṩItemView����ΪItemView�������ݼ��ɡ�

���磺
//MarqueeFactory<T extends View, E>
//����T:ָ��ItemView������
//����E:ָ��ItemView������������
public class ComplexViewMF extends MarqueeFactory<RelativeLayout, ComplexItemEntity> {
    private LayoutInflater inflater;

    public ComplexViewMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RelativeLayout generateMarqueeItemView(ComplexItemEntity data) {
        RelativeLayout mView = (RelativeLayout) inflater.inflate(R.layout.complex_view, null);
        ((TextView) mView.findViewById(R.id.title)).setText(data.getTitle());
        ((TextView) mView.findViewById(R.id.secondTitle)).setText(data.getSecondTitle());
        ((TextView) mView.findViewById(R.id.time)).setText(data.getTime());
        return mView;
    }
}


��Ӱ����ɲο����½������(�ο�������)
@Override
public void onStart() {
    super.onStart();
    marqueeView.startFlipping();
}

@Override
public void onStop() {
    super.onStop();
    marqueeView.stopFlipping();
}

