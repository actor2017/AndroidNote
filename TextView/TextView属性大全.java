https://yq.aliyun.com/articles/51235?spm=a2c4e.11154873.tagmain.6.7369392aFYBK9Z

<TextView
    android:id="@+id/tv_ysrq"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:gravity="center_vertical"
    android:hint="请选择预赎日期"			//提示文字
	android:text="日期"
    android:textColor="@color/gray_666"		//设置文本颜色
	android:textSize="17sp"
	android:textStyle=""					//设置字形[bold(加粗,粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2] 可以设置一个或多个，用“|”隔开
    android:textColorHint="@color/gray_999"	//提示文字颜色
	android:autoLink=""	//当文本为URL/email/电话/map时,文本显示为可点击的链接,可选值:1.none 2.web 3.email 4.phone 5.map 6.all
	android:maxLength="18"					//限制显示/输入的文本长度，超出部分不显示
    android:textIsSelectable="true"			//文本自由复制
	android:cursorVisible="true"			//EditText有效, 设定光标为显示/隐藏，默认显示
	android:digits="1234567890"				//设置允许输入哪些字符。如“1234567890.+-*/% ()”
　　android:editable="true"					//过时, EditText设置是否可编辑, 如果=false: 1.没光标. 2.不能双击/长按编辑
											//替代: editText.setInputType(InputType.TYPE_NULL);//属性设置android:inputType="none"无效, 是可以输入的
　　android:imeOptions=""			//需要设置 inputType, 否则右下角还是回车...
									//输入法编辑器选项，设置右下角IME动作与编辑框相关的动作，如actionDone右下角将显示一个“完成”，默认是一个回车符号
	android:inputType=""			//设置文本的类型，用于帮助输入法显示合适的键盘类型
	android:maxHeight=""			//设置最大高度
　　android:minHeight=""			//设置最小高度
　　android:maxWidth=""				//设置最大宽度
　　android:minWidth=""				//设置最小宽度
	android:password="true"			//过时. 以小点”.”显示文本, 密码形式
	android:selectAllOnFocus="true"	//EditText中, 如果文本是可选择的，当获取焦点时, 全选文本
　　android:textColorHighlight=""	//被选中文字的底色，默认为蓝色
　　android:textColorLink=""		//文字链接的颜色.
	android:typeface=""				//设置文本字体，必须是以下常量值之一：normal 0, sans 1, serif 2, monospace(等宽字体) 3
	
	//跑马灯
	android:ellipsize=""	//省略符号. 当文字过长时,该控件该如何显示。有如下值设置：
							//1."start"  —...省略号显示在开头;
							//2."end"    —...省略号显示在结尾;
							//3."middle" —...省略号显示在中间;
							//4."marquee"—以跑马灯的方式显示(动画横向移动)
	android:marqueeRepeatLimit="marquee_forever|123"//在ellipsize指定marquee的情况下，指定跑马灯滚动次数，marquee_forever:无限次
	
	//行数
	android:lines="2"		//设置文本的行数，设置两行就显示两行，即使第二行没有数据。
　　android:maxLines="2"	//设置文本的最大显示行数，与width或者layout_width结合使用，超出部分自动换行，超出行数将不显示。
　　android:minLines="2"	//设置文本的最小行数，与lines类似。
	android:singleLine="true"//设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示

	//字符宽度(layout_width=wrap_content有效)
	android:ems="5"//设置TextView的宽度为N个字符的宽度, em(equal M:和M字符一致的宽度为一个单位)是一个印刷排版的单位,表示字宽
　　android:maxEms="5"//设置TextView的宽度为最长为N个字符的宽度。与ems同时使用时覆盖ems
　　android:minEms="5"//设置TextView的宽度为最短为N个字符的宽度。与ems同时使用时覆盖ems
					
	//行/字间距, padding
	android:lineSpacingExtra=""//设置行间距，如”8dp”
	android:lineSpacingMultiplier//设置行间距倍数，如“1.5”，即为1.5倍行间距
	android:textScaleX="" //设置字间距，如“1.5”等，默认是1.0
	android:includeFontPadding="true"//设置文本是否包含顶部和底部额外空白(paddingTop&paddingBottom)，默认为true。即使设置为false还是有一点padding...
	
	//drawableLeft 文本图标. 如果指定一个颜色的话会把text的背景设为该颜色，并且同时和background使用时覆盖后者。
	android:drawableLeft="@drawable/..."//在text的左边输出一个drawable，如图片
	android:drawableRight=""//在text的右边输出一个drawable
	android:drawableTop=""//在text的正上方输出一个drawable
	android:drawableBottom=""//在text的下方输出一个drawable
	android:drawablePadding=""//设置text与drawable(图片)的间隔, 与上方4个属性一起使用,可设置为负数,单独使用没有效果.
	
	//滚动
	android:scrollHorizontally="true"//设置文本超出TextView的宽度的情况下，是否出现横拉条。
	
	//以下3个阴影, View属性
    android:fadingEdgeLength="10dp"	//阴影高度
    android:requiresFadingEdge="vertical"//阴影方向
    android:fadingEdge="vertical"	//阴影方向(从api14开始, 过时)
	android:shadowColor="@color/red"//指定文本阴影的颜色，需要与shadowRadius一起使用。
	android:shadowRadius="0.1"		//设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好。
	android:shadowDx="float"		//设置阴影横向坐标开始位置。
　　android:shadowDy="float"		//设置阴影纵向坐标开始位置。
	
	//自动缩放api26(8.0)
	android:autoSizeTextType="none|uniform"//是否支持自动改变字体大小, api26(8.0)https://www.jianshu.com/p/2fdc97ae74a8
	android:autoSizeMinTextSize="10sp"//最小字体大小，例如设置为10sp，表示文字最多只能缩小到10sp, api26(8.0)
	android:autoSizeMaxTextSize="18sp"//最大字体大小，例如设置为18sp，表示文字最多只能放大到18sp, api26(8.0)
	android:autoSizeStepGranularity="1sp"//缩放粒度，即每次字体大小变化的数值，例如设置为1sp，表示每次缩小或放大的值为1sp, api26(8.0)

	//不常用属性
	android:autoText="true"	//是否自动执行输入值的拼写纠正。在EditText显示输入法并输入的时候起作用。
	android:bufferType="editable|normal|spannable"//指定getText()方式取得的文本类别
							//editable 类似于StringBuilder可追加字符，
							//spannable 则可在给定的字符区域使用样式，参见这里1、这里2。
	android:capitalize="characters|none|sentences|words"//设置英文字母大写类型。在EditText弹出输入法才能看得到，参见EditView此属性说明。
	android:editorExtras=""	//设置文本的额外的输入数据
	android:freezesText="false"//设置保存文本的内容以及光标的位置。
	android:imeActionId=""	//设置IME动作ID，在onEditorAction中捕获判断进行逻辑操作
　　android:imeActionLabel=""//设置IME动作标签。但是不能保证一定会使用，猜想在输入法扩展的时候应该有用
	android:inputMethod=""	//为文本指定输入法，需要完全限定名(完整的包名)。例如：com.google.android.inputmethod.pinyin
	android:linksClickable="true"//设置链接是否点击连接，即使设置了autoLink
　　android:numeric="integer|decimal|signed"//过时. 限制输入数字类型，integer（正整数）、signed（带符号整数，有正负）和decimal(浮点数)
　　android:phoneNumber="true"//过时. 设置为电话号码的输入方式。
　　android:privateImeOptions=""//提供额外的输入法选项(字符串格式)。依据输入法而决定是否提供，如这里所见。自定义输入法继承
	android:textAppearance=""//设置文字外观。如“?android:attr/textAppearanceLargeInverse”这里引用的是系统自带的一个外观
							//?表示系统是否有这种外观，否则使用默认的外观。可设置的值如下：
							//textAppearanceButton/textAppearanceInverse/textAppearanceLarge/textAppearanceLargeInverse
							//textAppearanceMedium/textAppearanceMediumInverse/textAppearanceSmall/textAppearanceSmallInverse
	android:height=""		//设置文本区域的高度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)
　　android:width=""		//设置文本区域的宽度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)，与layout_width的区别看这里。

	//tools
	tools:text="大小:18.2MB"//tools:预览提示，但不编译tools
	tools:showIn="@layout/activity_scrolling"//这个布局显示在哪个界面,常用语item类的布局.
/>

1.五个构造方法
textView.setText("CharSequence");
/**
 * flag: FROM_HTML_MODE_COMPACT: html块元素之间使用一个换行符分隔. FROM_HTML_MODE_LEGACY: html块元素之间使用两个换行符分隔
 * imageGetter: 重写方法,返回Drawable,见下方有写法,https://blog.csdn.net/hoooooozzz/article/details/52468556
 * tagHandler: 当遇到不能解析的HTML标记时,这个方法会回调(源码搜索mTagHandler.handleTag)
 */
textView.setText(Html.fromHtml(String source, int flag, ImageGetter imageGetter, TagHandler tagHandler));//显示图文,就像网页一样
textView.setText("CharSequence", BufferType.NORMAL);//默认BufferType.NORMAL
textView.setText(new char[]{}, 0, 0);//char[] text, int start, int len
textView.setText(R.string.text_ack_msg);//@StringRes int resid
textView.setText(span, BufferType.SPANNABLE);//CharSequence text, BufferType type,一般用于富文本表情显示

textView.setText("酒店入住\n信息登记");//换行

textview.setText("你\u3000好");		//空格
<TextView
        android:id="@+id/textview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/dp_10"
		android:text="你\u3000\u3000好"
        android:text="你&#160;&#160;&#160;&#160;好"/>//xml布局里，空格使用 (&#160;) 和 ( \u3000 ) 都生效
<string name="str">你\u3000\u3000\u3000好</string>	//string.xml 文件里，空格使用 ( \u3000 ) 生效

textView.append(CharSequence text);//添加在后面
textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
textView.getPaint().setAntiAlias(true);//抗锯齿

int lines = textView.getLineCount();//获取行数
if (lines <= 1) {
    textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);//靠右
} else textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);//靠左



2.设置drawableleft图片大小(xml没有属性设置drawableleft图片大小,从代码里可以)
Drawable drawable=getResources().getDrawable(R.drawable.ic_phone);
drawable.setBounds(0,0,30,35);//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
tv_phone.setCompoundDrawables(drawable,null,null,null);//只放左边


3.能滑动的textview
TextView 布局里：android:scrollbars="vertical"
代码里：textview.setMovementMethod(ScrollingMovementMethod.getInstance());
为什么我这样做之后。这只textview的值之后。必须要先滑动一下textview控件，才能显示textview的内容，不必然就是空白一片？
mText.scrollTo(0, 0);

<string name="about_github"><a href="https://github.com/DreaminginCodeZH/MaterialRatingBar">View on GitHub</a></string>
tv.setMovementMethod(LinkMovementMethod.getInstance());//点击后跳转链接
int selectionStart = editText.getSelectionStart();//光标位置[0,+max],有-1?
int selectionEnd = editText.getSelectionEnd();//光标选择结束位置
editText.getText().replace(Math.min(selectionStart, selectionEnd),//把选中的全部范围替换为[可爱]
                    Math.max(selectionStart, selectionEnd), "[可爱]", 0,
                    "[可爱]".length());


4.删除
//删除1
KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
editText.dispatchKeyEvent(event);
//删除2(可用)
public void delClick(EditText editText) {
	KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
	editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
}
//删除3
int start = editText.getSelectionStart();
int end = editText.getSelectionEnd();
if (end > start) {//选择了一个区域
	editText.getText().delete(start, end);
} else if (start > 0) {
	editText.getText().delete(start - 1, end);
}
//清空
editText.getText().clear();


5.setFilters
//对输入的文字进行过滤
//public void setFilters(InputFilter[] filters)
editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength)});//限制最大输入长度
editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(16)});//只能输入16位大写字母

//实现InputFilter过滤器，需要覆盖一个叫filter的方法
public interface InputFilter {
	/**
	 * CharSequence source,  //输入的文字 
     * int start,  //开始位置 
     * int end,  //结束位置 
     * Spanned dest, //当前显示的内容 
     * int dstart,  //当前开始位置 
     * int dend //当前结束位置 
	 */
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend);
}
//实现一个输入小写转大写的filter
InputFilter switchFilter = new InputFilter() {
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        return source.toString().toUpperCase();
    }
};
//正则过滤器
private class RegexFilter implements InputFilter {

	private String regex;//正则

	private RegexFilter(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source.toString());
		boolean find = matcher.find();
		return find ? "" : source;
	}
}


6.setKeyListener
//对输入键盘按键的监听
public void setKeyListener(KeyListener input);

DigitsKeyListener.getInstance();//= ↓
//boolean sign(符号), boolean decimal(小数)     见: DigitsKeyListener.COMPATIBILITY_CHARACTERS
DigitsKeyListener.getInstance(false, false);//只接受整数输入
DigitsKeyListener.getInstance(true, false);//接受有符号整数输入
DigitsKeyListener.getInstance(false, true);//接受小数，整数输入
DigitsKeyListener.getInstance(true, true);//接受有符号整数/小数输入
DigitsKeyListener.getInstance(digits);//digits=0123456789, 对数字输入的限制

//对字符输入的限制
NumberKeyListener keyListener = new NumberKeyListener() {
    public int getInputType() {
        return InputType.TYPE_CLASS_TEXT;//指定键盘类型
    }
    protected char[] getAcceptedChars() {//指定你所接受的字符
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".toCharArray();
    }
};


7.设置文字改变监听
tv_phone.addTextChangedListener(textWatcher);
textWatcher = new TextWatcher() {
	
	//在文本变化前调用
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//s: 修改之前的文字
		//start: 开始修改的位置
		//count: 变化的字符长度
		//after: 变化后该位置字符数量(输入1个字母, after=1)
		System.out.printf("s=%s, start=%d, count=%d, after=%d", s, start,after, count);
	}

	//在文本变化时调用,此时s的内容已发生改变
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//s: 修改后的文字
		//start: 开始修改的位置
		//before: 变化前字符长度为(0)
		//count: 变化字符数量为(1)
		System.out.printf("s=%s, start=%d, before=%d, count=%d", s, start,before, count);
	}

	//在文本变化后调用,s即为变化后的文本结果
	@Override
	public void afterTextChanged(Editable s) {//修改后的文字
		System.out.println(s);
	}
};


//////////////////////////////////////////setKeyListener//////////////////////////////////////////
//图片宽度100%
String headStr = "<html><head><meta charset='UTF-8'/><meta name='viewport' content='width=device-width, initial-scale=1.0' /><style>img{width:100%;display:block;}</style></head><body>";
String html = "<font color=\"#ff2929\">" + nickname + "</font>" +//字体颜色
                            "<font color=\"#454545\">回复</font>" +
							"<font color=\"#ff2929\">" + nickname2 +
							"：</font>" + "<font color=\"#454545\">" + content + "</font>";
String time4Order = "<font color='#9f38f7'>已到</font>预约时间";
String html = "";
html = html.concat(String.format("<font><b>%s </b>回复 </font>", item.replyUser.nickname));
html = html.concat(String.format("<font><b>%s: </b>%s</font>", item.discussUser.nickname, item.content));
//换行: <br />



Html.ImageGetter imageGetter = new Html.ImageGetter() {
    @Override
    public Drawable getDrawable(String source) {
        final LevelListDrawable drawable = new LevelListDrawable();
        Glide.with(activity)
                .asBitmap()
                .load(source)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        if(resource != null) {
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                            drawable.addLevel(1, 1, bitmapDrawable);
                            drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                            drawable.setLevel(1);
                            CharSequence text = tvAnswer.getText();
                            tvAnswer.setText(text);
                            tvAnswer.refreshDrawableState();
                        }
                    }
                });
        return drawable;
    }
};

