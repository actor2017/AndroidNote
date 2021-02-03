https://github.com/JakeWharton/butterknife
 * ButterKnife: ����С��, ͨ��ע�ⷽʽ�Զ�findviewbyid�Ͱ��¼�
bufferKnife�ӵ���¼�,ֻ���Activity,��С��������Ч,���Activity�ĸ��??


2.��װ���
Settings-->Plugins-->����Android ButterKnife Zelezny-->��װ

3.�������:
  implementation 'com.jakewharton:butterknife:8.8.1'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'

Kotlin:
  compile 'com.jakewharton:butterknife:8.8.1'
  kapt 'com.jakewharton:butterknife-compiler:8.8.1'

//androidx, 10.+
implementation 'com.jakewharton:butterknife:10.2.3'
annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.3'


4.��ӻ���
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


##------------------------------5.View�¼�------------------------------
1.@BindView(R2.id.button)			//��View

  @BindViews({ R2.id.button1, R2.id.button2,  R2.id.button3})//�󶨲����ڶ���ؼ�id ע��
  public List<Button> buttonList;

2.@BindBool(R2.bool.is_a)			boolean isA;	//��boolean
  @BindColor(R.color.red)			int red;		//����ɫ, int or ColorStateList field
  @BindDimen(R.dimen.spacer)		float spacer;	//int (for pixel size) or float (for exact value) field
  @BindDrawable(R.drawable.graphic)	Drawable graphic;//��Drawable
  @BindBitmap(R2.mipmap.bm)			Bitmap bitmap;	//��Bitmap
  @BindInt
  @BindString(R.string.title)		String title;	//��String
  @BindArray(R2.array.city)			String[] citys;	//��string����array����

3.@OnClick(R2.id.button1)			//����¼�
4.@OnClick({R.id.btn1, R.id.btn2})	//������
5.@OnLongClick( R2.id.button1)		//�����¼�
5.@OnPageChange				//ҳ��ı��¼�
6.@OnTextChanged			//EditText������ı��仯�¼�
7.@OnFocusChange			//����ı�
8.@OnTouch					//�����¼�
9.@Optional					//ѡ����ע��(Ŀ��View����,��ע��.������,��ʲô���鶼����)
10.@OnEditorAction			//����̵Ĺ��ܼ�

11.�б�Item���,����2���ǿ��������ע��Ŀؼ�
   Spinner, StackView, AdapterViewFlipper, Gallery, ListView, GridView, ExpandedMenuView, DropDownListView,
   RecycleListView, ExpandableListView, MenuPopupWindow(@hide), MenuDropDownListView(@hide)
@OnItemClick(R.id.example_list)     	��->�����(���item����Button���е���¼��Ŀؼ�,��Ҫ������Щ�ؼ�����focusable=false)
   public void onItemClick(int position) {}
   public void onItemClick(AdapterView parent, View view, int position, long id);

12.�б�Item����
@OnItemLongClick(R.id.example_list) 	��->����(����true��������onItemClick)
   public boolean onItemLongClick(int position) {return true;}
   public boolean onItemLongClick(AdapterView parent, View view, int position, long id);

13.�б�Item��ѡ��
@OnItemSelected(R.id.sp_spiner)
  public void onItemSelected(int position) {}
  public void onItemSelected(android.widget.AdapterView, View, int position, long id)
@OnItemSelected(value = R.id.example_list, callback = NOTHING_SELECTED)//ʲô��ûѡ��
  void onNothingSelected() {}


14.����ѡ��״̬,����һ���ǿ��������ע��Ŀؼ�
   //CheckBox, RadioButton, Switch,SwitchCompat, ToggleButton
@OnCheckedChanged({R.id.radioBtn1,R.id.radioBtn2})
  public void onChecked(boolean checked){}
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){}


ʹ��findById��
Butter Knife��Ȼ������findById()���������������һ��view ��Activity������ Dialog �ϳ�ʼ��view��ʱ�򣬲����������Զ�ת�����͡�
View view = LayoutInflater.from(context).inflate(R.layout.thing, null);  
TextView firstName = ButterKnife.findById(view, R.id.first_name);  
TextView lastName = ButterKnife.findById(view, R.id.last_name);  
ImageView iv = ButterKnife.findById(view, R.id.iv);  
  public static <T extends View> T findById(@NonNull View view, @IdRes int id) {//����д��
    return (T) view.findViewById(id);
  }


15.�� Dialog ��ʹ��:
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	//ButterKnife.bind(this, view);//���ߡ�
    ButterKnife.bind(this, window.getDecorView());
}


//�Զ���һ���ض����ͣ������Զ���ת��
@OnClick(R.id.submit)
    public void sayHi(Button button) {//�������ڲ����ı仯��������  
      button.setText("Hello!");  
    }  

//�Զ���Viewʹ�ð��¼�
public class MyButton extends Button {  
  @OnClick  
  public void onClick() {}  
}

//��Activity��onCreate�����а�.
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_address);
	//���������butterKnife����
	ButterKnife.bind(this);

}

