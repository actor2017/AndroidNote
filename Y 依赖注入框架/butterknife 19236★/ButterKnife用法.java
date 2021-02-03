https://github.com/JakeWharton/butterknife
 * ButterKnife: 奶油小刀, 通过注解方式自动findviewbyid和绑定事件
bufferKnife加点击事件,只针对Activity,对小弹窗等无效,会和Activity的搞混??


2.安装插件
Settings-->Plugins-->搜索Android ButterKnife Zelezny-->安装

3.添加依赖:
  implementation 'com.jakewharton:butterknife:8.8.1'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'

Kotlin:
  compile 'com.jakewharton:butterknife:8.8.1'
  kapt 'com.jakewharton:butterknife-compiler:8.8.1'

//androidx, 10.+
implementation 'com.jakewharton:butterknife:10.2.3'
annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.3'


4.添加混淆
##---------Begin: proguard configuration for ButterKnife---------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
##----------End: proguard configuration for ButterKnife----------


##------------------------------5.View事件------------------------------
1.@BindView(R2.id.button)			//绑定View

  @BindViews({ R2.id.button1, R2.id.button2,  R2.id.button3})//绑定布局内多个控件id 注解
  public List<Button> buttonList;

2.@BindBool(R2.bool.is_a)			boolean isA;	//绑定boolean
  @BindColor(R.color.red)			int red;		//绑定颜色, int or ColorStateList field
  @BindDimen(R.dimen.spacer)		float spacer;	//int (for pixel size) or float (for exact value) field
  @BindDrawable(R.drawable.graphic)	Drawable graphic;//绑定Drawable
  @BindBitmap(R2.mipmap.bm)			Bitmap bitmap;	//绑定Bitmap
  @BindInt
  @BindString(R.string.title)		String title;	//绑定String
  @BindArray(R2.array.city)			String[] citys;	//绑定string里面array数组

3.@OnClick(R2.id.button1)			//点击事件
4.@OnClick({R.id.btn1, R.id.btn2})	//多个点击
5.@OnLongClick( R2.id.button1)		//长按事件
5.@OnPageChange				//页面改变事件
6.@OnTextChanged			//EditText里面的文本变化事件
7.@OnFocusChange			//焦点改变
8.@OnTouch					//触摸事件
9.@Optional					//选择性注入(目标View存在,则注入.不存在,则什么事情都不做)
10.@OnEditorAction			//软键盘的功能键

11.列表Item点击,下面2行是可以用这个注解的控件
   Spinner, StackView, AdapterViewFlipper, Gallery, ListView, GridView, ExpandedMenuView, DropDownListView,
   RecycleListView, ExpandableListView, MenuPopupWindow(@hide), MenuDropDownListView(@hide)
@OnItemClick(R.id.example_list)     	—->被点击(如果item里有Button等有点击事件的控件,需要设置这些控件属性focusable=false)
   public void onItemClick(int position) {}
   public void onItemClick(AdapterView parent, View view, int position, long id);

12.列表Item长按
@OnItemLongClick(R.id.example_list) 	—->长按(返回true可以拦截onItemClick)
   public boolean onItemLongClick(int position) {return true;}
   public boolean onItemLongClick(AdapterView parent, View view, int position, long id);

13.列表Item被选中
@OnItemSelected(R.id.sp_spiner)
  public void onItemSelected(int position) {}
  public void onItemSelected(android.widget.AdapterView, View, int position, long id)
@OnItemSelected(value = R.id.example_list, callback = NOTHING_SELECTED)//什么都没选中
  void onNothingSelected() {}


14.开关选中状态,下面一行是可以用这个注解的控件
   //CheckBox, RadioButton, Switch,SwitchCompat, ToggleButton
@OnCheckedChanged({R.id.radioBtn1,R.id.radioBtn2})
  public void onChecked(boolean checked){}
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){}


使用findById：
Butter Knife仍然包含了findById()方法，用于仍需从一个view ，Activity，或者 Dialog 上初始化view的时候，并且它可以自动转换类型。
View view = LayoutInflater.from(context).inflate(R.layout.thing, null);  
TextView firstName = ButterKnife.findById(view, R.id.first_name);  
TextView lastName = ButterKnife.findById(view, R.id.last_name);  
ImageView iv = ButterKnife.findById(view, R.id.iv);  
  public static <T extends View> T findById(@NonNull View view, @IdRes int id) {//他的写法
    return (T) view.findViewById(id);
  }


15.在 Dialog 中使用:
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	//ButterKnife.bind(this, view);//或者↓
    ButterKnife.bind(this, window.getDecorView());
}


//自定义一个特定类型，它将自动被转换
@OnClick(R.id.submit)
    public void sayHi(Button button) {//看括号内参数的变化就明白了  
      button.setText("Hello!");  
    }  

//自定义View使用绑定事件
public class MyButton extends Button {  
  @OnClick  
  public void onClick() {}  
}

//在Activity的onCreate方法中绑定.
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_address);
	//下面这句是butterKnife生成
	ButterKnife.bind(this);

}

