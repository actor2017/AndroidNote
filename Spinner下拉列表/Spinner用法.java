android:Theme.Light很丑,见 "1.Spinner数据源于xml数组" 图片.
想必这也是很多人不想使用Spinner的原因了吧。如果想兼容2.3，则只能忍受这样的效果。

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="1dp"
        android:background="@color/white">
        <!--★★上面这一条是给Spinner设置背景色,因为Spinner的背景色是透明的★★-->
     
        <Spinner
            android:id="@+id/spinner1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
			android:entries="@array/languages"	//dropdown 模式时这样写, 绑定数据源,  arrays.xml
            android:prompt="@string/please_choose_sex"//(请选择性别)在dialog模式下才有效,不能直接写汉字在这里
            android:spinnerMode="dropdown|dialog"//菜单显示方式:下拉菜单,弹出框(在android2.3上没有这个属性,系统默认将Spinner弹出菜单显示成dialog)
            android:dropDownVerticalOffset="25.5dp"//spinnerMode=”dropdown”时，下拉的项目选择窗口在垂直方向相对于Spinner窗口的偏移量。
            android:popupBackground="@drawable/"//在spinner=”dropdown”时，使用这个属性来设置下拉列表的背景。
            //android:backgroundTint="@color/gray_333"//箭头的颜色,至少api14(android 4.0)
            //android:background="@color/white"//背景颜色(设置了之后看不见箭头,不要这样设置)
            //android:gravity="left|top"//对齐方式, 有时候无效...
            android:textAlignment="center|textStart|textEnd|viewStart|viewEnd"//API 17 以后才启用
            //android:dropDownHorizontalOffset="1.5dp"//spinnerMode=”dropdown”时，下拉的项目选择窗口在水平方向相对于Spinner窗口的偏移量。
            //android:dropDownSelector="@drawable/spinner_selector"//选中/未选中的selector
            //android:dropDownWidth="50dp"//在spinnerMode=”dropdown”时，设定下拉框的宽度。
			/>
    </LinearLayout>
<string-array name="please_choose_sex">//values/arrays还是strings???.xml
    <item>男</item>
    <item>女</item>
</string-array>


--------------------------------代码----------------------------------
spinner.setPopupBackgroundResource(int);//在spinner=”dropdown”时，使用这个属性来设置下拉列表的背景。
spinner.setDropDownHorizontalOffset(int);//spinnerMode=”dropdown”时，下拉的项目选择窗口在水平方向相对于Spinner窗口的偏移量。
spinner.setDropDownVerticalOffset(int);//spinnerMode=”dropdown”时，下拉的项目选择窗口在垂直方向相对于Spinner窗口的偏移量。
spinner.setDropDownWidth(int);//在spinnerMode=”dropdown”时，设定下拉框的宽度。


spinner.getSelectedItemPosition();//获取选择的哪一项
spinner.setSelection(0);//设置选中哪一项
spinner.getCount();//获取item数量


怎么获取值?
1.spinner.getSelectedItem().toString();//直接返回该条目的值.(item只有TextView的时候有效,否则返回该item的地址值)

2.getResources().getStringArray(R.array.language)[spinner.getSelectedItemPosition()];//这种吊!


3.选中监听:
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
           
                String language = getResources().getStringArray(R.array.languages)[pos];
                Toast.makeText(MainActivity.this, "你点击的是:"+language, 2000).show();
            }
			
			//adapter为空的时候就会调用到这个方法
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });


