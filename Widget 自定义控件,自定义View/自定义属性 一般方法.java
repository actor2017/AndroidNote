学习资料
https://github.com/nanchen2251/PracticeDraw1
http://hencoder.com/ui-1-1/


1.继承ViewGroup 或者 View.
2.重写3个构造方法,把3个构造方法串联起来.
3.在第3个构造方法中可以获取控件的 "属性" 信息.
例:
public class RatioLayout extends FrameLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private float ratio;        //宽高比例

    //new对象的时候调用
    public RatioLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    //加载布局的时候
    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //布局文件中有style的时候
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
         * 给当前空的布局填充内容
         * 参3:A view group that will be the parent.
         * 传null表示当前布局没有父控件,大部分都传null
         * 传this表示已当前相对布局为这个布局的父控件,这样做了以后,当前空的布局就有内容了
         */
        //View view = View.inflate(context, R.layout.item_grid_table_spinner, this);

        //防止用户未定义atrr
        if(attrs != null) {
            //★★★★★方式1, 有bug. 获取@string类型的值时, 获取到的是"@2131755078"的问题★★★★★
            //float ratio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 0.0f);//ratio:自定义属性
            // System.out.println("ratio=" + ratio);

            //★★★★★方式2★★★★★ 在所有的属性集合中，获取自定义属性的集合,匹配下标索引,效率更高
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRatioLayoutAttrs);
			
			typedArray.getColor(R.styleable.QuickSearchBar_qsbTextColorNormal, defaultValue);//返回是一个赋值, 所以默认值不要写成-1(#FFF)

            if(!isInEditMode()){//造成错误的代码段(xml预览时报错, 挡住预览界面)
                //利用系统帮我们生成的一个下标索引   自定义属性集合名称_自定义属性的名称    这个自定义属性位于自定义属性集合中的下标索引
                ratio = typedArray.getFloat(R.styleable.MyRatioLayoutAttrs_ratio, 0.0f);
            }
            typedArray.recycle();//节约内存
        }
    }
}


5.常见重写的3个方法    //onMeasure--onLayout--onDraw	测量--定位--绘制

