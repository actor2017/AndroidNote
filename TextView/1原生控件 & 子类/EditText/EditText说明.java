http://www.jianshu.com/p/c14f4d97b845

<EditText
	android:inputType=”none”
	android:inputType=”text”
	android:inputType=”textCapCharacters” 字母大写
	android:inputType=”textCapWords” 首字母大写
	android:inputType=”textCapSentences” 仅第一个字母大写
	android:inputType=”textAutoCorrect” 自动完成
	android:inputType=”textAutoComplete” 自动完成
	android:inputType=”textMultiLine” 多行输入
	android:inputType=”textImeMultiLine” 输入法多行（如果支持）
	android:inputType=”textNoSuggestions” 不提示
	android:inputType=”textUri” 网址
	android:inputType=”textEmailAddress” 电子邮件地址
	android:inputType=”textEmailSubject” 邮件主题
	android:inputType=”textShortMessage” 短讯
	android:inputType=”textLongMessage” 长信息
	android:inputType=”textPersonName” 人名
	android:inputType=”textPostalAddress” 地址
	android:inputType=”textPassword” 密码
	android:inputType=”textVisiblePassword” 可见密码(★★用于只能输入数字,字母...,非中文★★)
	android:inputType=”textWebEditText” 作为网页表单的文本
	android:inputType=”textFilter” 文本筛选过滤
	android:inputType=”textPhonetic” 拼音输入(没什么卵用)

	//数值类型
	android:inputType=”number” 数字	 editText.setInputType(InputType.TYPE_CLASS_NUMBER | ...);
	android:inputType=”numberSigned” 带符号数字格式
	android:inputType=”numberDecimal” 带小数点的浮点格式
	android:inputType=”phone” 拨号键盘
	android:inputType=”datetime” 时间日期
	android:inputType=”date” 日期键盘
	android:inputType=”time” 时间键盘
	
    <requestFocus />		//获取光标
</EditText>


//根据坐标获取光标偏移量
int offsetForPosition = editText.getOffsetForPosition(x, y);
editText.setSelection(offsetForPosition);


//清空输入框焦点(KeyboardInputEditText)
public void clearFocus() {
	parent.setFocusableInTouchMode(true);//设置父类focusableInTouchMode
	parent.setFocusable(true);//设置父类focusable
	parent.requestFocus();//设置父类获取focus
}


/**
 * 设置是否可输入(false的时候,可以当做TextView展示)(GridTableEditText)
 * @param enable
 */
public void setInputEnable(boolean enable) {
//        et1.setEnabled(enable);//这样不能编辑,可用于隐藏输入法,但是EditText的点击事件无反应,不能做点击事件
	et1.setFocusable(enable);
	et1.setClickable(!enable);
	et1.setFocusableInTouchMode(enable);
//        if (enable) et1.requestFocus();//把光标移动到这一个et1,但是不弹出键盘
//        et1.setCursorVisible(false);
}

et1.append(CharSequence text);//添加在后面


android:digits="0123456789xX" 限制输入范围★这个可用于输入身份证★,代码:et.setKeyListener(DigitsKeyListener.getInstance(digits));
身份证示例:
<EditText
    android:id="@+id/et_sfz"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:layout_marginLeft="5dp"
    android:background="@color/white"
    android:digits="0123456789xX"        //digits示例用法★
    android:hint="请输入房主身份证"
    android:imeOptions="actionNext"
    android:inputType="number"           //输入类型由digits决定,也就是说即使设置了number,也能输入xX. 但是获取焦点后是数字输入9宫格★★
    android:maxLength="18"
    android:singleLine="true"            //如果不显示"下一步",再设置singleLine就可以★
    android:textColor="@color/gray_666"
    android:textColorHint="@color/gray_999"
    android:textSize="17sp"/>


//------------------------------imeOptions--------------------------------------
	Constant				Value		Mean
android:imeOptions="actionUnspecified"		  0		未指定(如果是中间,是"下一步".如果是最后一个,是"完成")
android:imeOptions="normal"			  0		普通(如果是中间,是"下一步".如果是最后一个,是"完成")
android:imeOptions="actionNone"			  1		None,不带任何提示(如果是中间,作用是下一步,但是不现实下一步,如果是末尾,点击无反应)
android:imeOptions="actionGo"			  2		Go,开始,去往 ,前往
android:imeOptions="actionSearch"		  3		放大镜图片,搜索 mEditText.setImeOptions(3);
android:imeOptions="actionSend"			  4		发送
android:imeOptions="actionNext"			  5		下一步,下一项
android:imeOptions="actionDone"			  6		完成 mEditText.setImeOptions(6);EditorInfo.IME_ACTION_DONE
android:imeOptions="actionPrevious"		  7		上一步,上一项
android:imeOptions="flagNoPersonalizedLearning"	 1000000	输入法不会记住输入历史
android:imeOptions="flagNoFullscreen"		 2000000	输入法不会全屏
android:imeOptions="flagNavigatePrevious"	 4000000	导航到上一步
android:imeOptions="flagNavigateNext"		 8000000	导航到下一步
android:imeOptions="flagNoExtractUi"		 10000000	尽可能不占用屏幕
android:imeOptions="flagNoAccessoryAction"	 20000000	该次输入事件优先级最高
android:imeOptions="flagNoEnterAction"		 40000000	enter键不可用
android:imeOptions="flagForceAscii"		 80000000	强制输入ASCII

注意:如果inputTyle和imeOptions都设置了,但是没有imeOptions的效果,试一下:singleLine

//针对键盘的监听者，例如手机键盘、外设键盘
mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchContactsActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    search();//进行搜索的操作
                    return true;
                }
                return false;
            }
        });
// 事件监听(完成, 搜索, Go, 发送...)
  mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

       @Override
       public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
			//ACTION_UP和ACTION_DOWN时都会触发这个方法
			if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (actionId) {
				  case EditorInfo.IME_ACTION_SEARCH:
					  Toast.makeText(getApplicationContext(), "点击了搜索按钮", Toast.LENGTH_SHORT).show();
					  break;
			   }
			}
            return false;
       }
  });

//动态显示/隐藏密码
etPassword.setInputType(257 - etPassword.getInputType());//128+129
viewEye.setSelected(!view.isSelected());//眼睛, 显示/隐藏密码
//int inputType = etPassword.getInputType();
//if (inputType == 129) {
//    etPassword.setInputType(128);
//    ivShowPassword.setImageResource(R.mipmap.img_register_hide_password);
//} else {
//    etPassword.setInputType(129);
//    ivShowPassword.setImageResource(R.mipmap.img_register_show_password);
//}


//警用端示例隐藏:
    private InputMethodManager imm;//虚拟键盘(输入法)
    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//虚拟键盘(输入法)
	//隐藏虚拟键盘
	if (imm.isActive()) {
		imm.hideSoftInputFromWindow(etMsg.getApplicationWindowToken(), 0);
	}


android:maxLength="1"设置输入最大长度,代码:et1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1)});
android:numeric="integer" 设置只能输入整数，如果是小数则是：decimal 
android:singleLine="true" 设置单行输入，一旦设置为true，则文字不会自动换行。
android:password="true" 设置只能输入密码
android:textColor = "#ff8c00" 字体颜色
android:textStyle="bold" 字体，bold, italic, bolditalic 
android:textSize="20dip" 大小
android:capitalize = "characters" 以大写字母写
android:textAlign="center" EditText没有这个属性，但TextView有 android:textColorHighlight="#cccccc" 被选中文字的底色，默认为蓝色
android:textColorHint="#ffff00" 设置提示信息文字的颜色，默认为灰色
android:textScaleX="1.5" 控制字与字之间的间距
android:typeface="monospace" 字型，normal, sans, serif, monospace
android:background="@null" 空间背景，这里没有，指透明
android:layout_weight="1" 权重，控制控件之间的地位,在控制控件显示的大小时蛮有用的。
android:textAppearance="?android:attr/textAppearanceLargeInverse" 文字外观，这里引用的是系统自带的一个外观，？表示系统是否有这种外观，否则使用默认的外观。不知道这样理解对不对？
 通过EditText的layout xml文件中的相关属性来实现:
1. 密码框属性 android:password="true" 这条可以让EditText显示的内容自动为星号，输入时内容会在1秒内变成*字样。
2. 纯数字 android:numeric="true" 这条可以让输入法自动变为数字输入键盘，同时仅允许0-9的数字输入
3. 仅允许 android:capitalize="cwj1987" 这样仅允许接受输入cwj1987，一般用于密码验证 下面是一些扩展的风格属性
android:editable="false" 设置EditText不可编辑
android:singleLine="true" 强制输入的内容在单行
android:ellipsize="end" 自动隐藏尾部溢出数据，一般用于文字内容过长一行无法全部显示时

