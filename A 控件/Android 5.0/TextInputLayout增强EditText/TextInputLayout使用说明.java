https://www.jianshu.com/p/2b0cd9e9a4d9
1.添加依赖:compile 'com.android.support:design:25.1.1'

2.可以展示EditText的提示文字hint和EditText输入错误时的错误提示文字
TextInputLayout只能套一个EditText，否则会抛异常

3.TextInputLayout.getEditText.getText/setText在有些手机可能会出错?门禁系统的登录,后来直接用的EditText.get/set text!!!


EditText背景可选择:
android:background="@color/back_gray"<color name="back_gray">#E3E3E3</color>

            <!--hintEnabled:是否允许显示hint-->
            <android.support.design.widget.TextInputLayout
                android:id="@+id/til_usename"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginRight="10dp"
                android:layout_weight="1"
//-------------------------------------------------------------
                app:errorTextAppearance="@style/counterOverflowTextColor"代码中设置setError后err字体的颜色和下划线的颜色
//-------------------------------------------------------------
                android:hint="请输入用户名"		//可写在上/下面
                android:textColorHint="@color/deep_green"//未获取焦点的hint的颜色,已经输入后失去焦点的hint颜色.只能写在上面(下面无效)
                //app:hintEnable="false"//关闭浮动标签(默认是开启的)
                //app:hintTextAppearance="@style/HintAppearance"//获取焦点后，上面Label的颜色/大小等,可不设置,系统默认
//-------------------------------------------------------------
				前提是EditText的inputType=password
                app:passwordToggleEnabled="true"	//密码切换开关是否可以用
                //app:passwordToggleDrawable="@drawable/eye"密码眼图标,可不设置,有默认的
                app:passwordToggleTint="@color/deep_green">图标的颜色(原本黑色)#079481
//-------------------------------------------------------------
                app:counterEnabled="true"		//开启输入计数
                app:counterMaxLength="11"		//设置输入最大长度
                app:counterTextAppearance="@style/counterTextColor"没有超过长度,下方3/11的颜色
                app:counterOverflowTextAppearance="@style/counterOverflowTextColor"//如果超过长度,hint和下划线的颜色


                <EditText
                    android:id="@+id/et_username"
                    android:layout_width="match_parent"
                    //android:inputType="textPassword"
                    android:layout_height="match_parent"
                    android:textColor="@color/black"/>	//不能写在上面
					
				<android.support.design.widget.TextInputEditText
					android:layout_width="match_parent"
					android:layout_height="wrap_content"
					android:hint="请输入密码"
					android:inputType="numberPassword" />
            </android.support.design.widget.TextInputLayout>


    <!--如果EditText中输入长度没超过,下方counter的颜色-->
    <style name="counterTextColor"
           parent="Base.TextAppearance.AppCompat.Widget.PopupMenu.Small">
        <item name="android:textColor">@color/deep_green</item><!--#079481-->
        <item name="android:textAllCaps">false</item>
        <item name="android:textSize">16sp</item>
    </style>

    <!--如果EditText中输入长度超过了,hint和下划线的提示-->
    <style name="counterOverflowTextColor"
           parent="Base.TextAppearance.AppCompat.Widget.PopupMenu.Small">
        <item name="android:textColor">@color/red</item><!--#ff0000-->
        <item name="android:textAllCaps">false</item>
        <item name="android:textSize">20sp</item>
    </style>

    <!--获取焦点后,上面Label的颜色,大小-->
    <style name="HintAppearance" parent="TextAppearance.AppCompat">
        <item name="android:textSize">14sp</item>
        <item name="android:textColor">@color/deep_green</item>
    </style>




3.下划线的颜色，就是主题中colorAccent的颜色，所以要改下划线颜色的话，在Theme里面将colorAccent改一下即可
可改成深绿色:<color name="deep_green">#079481</color>


4.取消底部线
如果要取消TextInputEditText底部线，在EditText中我们设置background=@null，
但是TextInputEditText不同，我们需要将TextInputLayout和TextInputEdit的backgound都设置为@null

//===============================提示错误信息======================

        //监听文字输入个数
        tilComment.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > tilComment.getCounterMaxLength()) {
                    tilComment.setError("最多输入" + tilComment.getCounterMaxLength() + "个字");//显示在TextInputLayout下方
                } else {
                    tilComment.setErrorEnabled(false);//清除错误提示。
                }
            }
        });

//-------------------------------------------------------------

//显示在EditText右下方(框的颜色和字体不能更改,字体好像能通过html...更改),体验不好
et_username.setError(getString(R.string.password_error));

//带有Drawable的需要的API level是21，如果你兼容的版本是21以下的就不能用
et_username.setError(getString(R.string.password_error),getDrawable(R.mipmap.ic_launcher));//drawable可以=null

