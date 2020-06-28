https://github.com/zyyoona7/EasyPopup

PopupWindow Wrapper. �� PopupWindow �ķ�װ����ָ������� anchor view ������λ���������ñ����䰵��ָ�� ViewGroup �����䰵�����ԡ�

��Ŀ����
��ʽ���ã������ڴ�ͳ�� PopupWindow ʹ�÷���֮�⻹�����˸���ķ���
��������� AnchorView �ĸ�����λ�����ķ��������� PopupWindow �����ɡ�����
֧�� PopupWindow ����ʱ�����䰵��ָ�� ViewGroup �����䰵�����ñ䰵��ɫ�� (API>=18)
�����˼򵥵��������ڷ������Զ��� PopupWindow�������߼������㡢������

maven { url 'https://jitpack.io' }
//���µ�VERSION_CODE: https://github.com/zyyoona7/EasyPopup/releases
implementation 'com.github.zyyoona7:EasyPopup:VERSION_CODE'//1.1.2//popup,������dialog��ʽ

1.���� EasyPopup ����
private EasyPopup easyPopup;
easyPopup = EasyPopup.create()
        .setContentView(this, R.layout.layout_circle_comment)
        .setAnimationStyle(R.style.RightPopAnim)
  	    //�Ƿ�������PopupWindow֮��ĵط���ʧ,Ĭ��true.�������Ϊfalse���֮��ĵط�������ʧ�����ǻ���Ӧ���ذ�ť�¼�
        .setFocusAndOutsideEnable(true)
		//�Ƿ��������䰵,Ĭ��false,ֻ֧�� 4.2 ���ϵİ汾
		.setBackgroundDimEnable(false)
		//�䰵��͸����(0-1),Ĭ��0.7. 0Ϊ��ȫ͸��
		.setDimValue(0.7f)
		//�䰵�ı�����ɫ
		.setDimColor(Color.YELLOW)
		//ָ���ĸ�ViewGroup�����䰵,�ɲ�����
		.setDimView(viewGroup)
        .apply();

2.��ʼ��View
TextView tvZan=easyPopup.findViewById(R.id.tv_zan);
TextView tvComment=easyPopup.findViewById(R.id.tv_comment);
tvZan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showShort("��");
        easyPopup.dismiss();
    }
});
tvComment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showShort("����");
        easyPopup.dismiss();
    }
});

3.��ʾ
/**
 * ���anchor view��ʾ������ ��߲�Ϊmatch_parent
 * @param view ������ĸ�view��ʾ
 * @param anchor
 * @param yGravity  ��ֱ����Ķ��뷽ʽ
 * @param xGravity  ˮƽ����Ķ��뷽ʽ
 * @param x            ˮƽ�����ƫ��
 * @param y            ��ֱ�����ƫ��
 */
easyPopup.showAtAnchorView(view, YGravity.CENTER, XGravity.LEFT, 0, 0);
easyPopup.showAsDropDown();
easyPopup.showAtLocation();

4.��λע�����
4.1.��ֱ������룺YGravity
YGravity.CENTER,//��ֱ����
YGravity.ABOVE,//anchor view֮��
YGravity.BELOW,//anchor view֮��
YGravity.ALIGN_TOP,//��anchor view��������
YGravity.ALIGN_BOTTOM,//pop�ĵײ���anchor view�ײ�����

4.2.ˮƽ������룺XGravity
XGravity.CENTER,//ˮƽ����
XGravity.LEFT,//anchor view���
XGravity.RIGHT,//anchor view�Ҳ�
XGravity.ALIGN_LEFT,//��anchor view��߶���
XGravity.ALIGN_RIGHT,//��anchor view�ұ߶���

5.�Զ��� PopupWindow
EasyPopup���Զ����������������ڣ�

6.������������
������														����						��ע
setContentView(View contentView)							���� contentView
setContentView(@LayoutRes int layoutId)
setContentView(Context context, @LayoutRes int layoutId)
setContentView(View contentView, int width, int height)
setContentView(@LayoutRes int layoutId, int width, int height)
setContentView(Context context, @LayoutRes int layoutId, int width, int height)//�����
setWidth(int width)											���ÿ�	
setHeight(int height)										���ø�	
setAnchorView(View view)									����Ŀ�� view	
setYGravity(@YGravity int yGravity)							���ô�ֱ�������	
setXGravity(@XGravity int xGravity)							����ˮƽ�������	
setOffsetX(int offsetX)										����ˮƽƫ��	
setOffsetY(int offsetY)										���ô�ֱ	
setAnimationStyle(@StyleRes int animationStyle)				���ö������	
getContentView()											��ȡPopupWindow�м��ص�view	@Nullable
getContext()												��ȡcontext					@Nullable
getPopupWindow()											��ȡPopupWindow����			@Nullable
dismiss()	��ʧ	

