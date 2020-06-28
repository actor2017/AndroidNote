ѧϰ����
https://github.com/nanchen2251/PracticeDraw1
http://hencoder.com/ui-1-1/


1.�̳�ViewGroup ���� View.
2.��д3�����췽��,��3�����췽����������.
3.�ڵ�3�����췽���п��Ի�ȡ�ؼ��� "����" ��Ϣ.
��:
public class RatioLayout extends FrameLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private float ratio;        //��߱���

    //new�����ʱ�����
    public RatioLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    //���ز��ֵ�ʱ��
    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //�����ļ�����style��ʱ��
    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        /**
         * ����ǰ�յĲ����������
         * ��3:A view group that will be the parent.
         * ��null��ʾ��ǰ����û�и��ؼ�,�󲿷ֶ���null
         * ��this��ʾ�ѵ�ǰ��Բ���Ϊ������ֵĸ��ؼ�,���������Ժ�,��ǰ�յĲ��־���������
         */
        //View view = View.inflate(context, R.layout.item_grid_table_spinner, this);

        //��ֹ�û�δ����atrr
        if(attrs != null) {
            //�����﷽ʽ1, ��bug. ��ȡ@string���͵�ֵʱ, ��ȡ������"@2131755078"�����������
            //float ratio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 0.0f);//ratio:�Զ�������
            // System.out.println("ratio=" + ratio);

            //�����﷽ʽ2������ �����е����Լ����У���ȡ�Զ������Եļ���,ƥ���±�����,Ч�ʸ���
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRatioLayoutAttrs);
			
			typedArray.getColor(R.styleable.QuickSearchBar_qsbTextColorNormal, defaultValue);//������һ����ֵ, ����Ĭ��ֵ��Ҫд��-1(#FFF)

            if(!isInEditMode()){//��ɴ���Ĵ����(xmlԤ��ʱ����, ��סԤ������)
                //����ϵͳ���������ɵ�һ���±�����   �Զ������Լ�������_�Զ������Ե�����    ����Զ�������λ���Զ������Լ����е��±�����
                ratio = typedArray.getFloat(R.styleable.MyRatioLayoutAttrs_ratio, 0.0f);
            }
            typedArray.recycle();//��Լ�ڴ�
        }
    }
}


5.������д��3������    //onMeasure--onLayout--onDraw	����--��λ--����

