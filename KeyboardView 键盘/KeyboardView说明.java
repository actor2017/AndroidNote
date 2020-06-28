https://blog.csdn.net/flueky/article/details/80088255
1.xml布局中
<android.inputmethodservice.KeyboardView
    android:id="@+id/key_board_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#ececec"
    android:keyBackground="@drawable/shape_rec_border_for_key_preview"//键盘keys的背景, 默认theme里取色, 灰黑色#272727
    android:keyPreviewHeight="35dp"		//按键按下时弹出的预览框的高度, 默认240px?
    android:keyPreviewLayout="@layout/layout_for_key_preview"//按键按下时预览框的布局, 是一个TextView
    android:keyPreviewOffset="-36px"	//按键按下时预览框的偏移, 默认-36px?
    android:keyTextColor="#515151"		//键盘字体颜色, 默认黑色
    android:keyTextSize="18sp"			//键盘字体大小, 默认18px
	android:labelTextSize="18sp"		//标签文字大小,keylabel有多个字符且keycodes只有一个值时，该属性生效, 默认?
    android:popupLayout="@layout/layout_for_pupop_layout"//备选小键盘, 长按W的时候, 上方弹出的"W 2 w"
    android:shadowColor="#515151"		//键盘文字笔画阴影, 可屏蔽调键盘字体模糊问题
    android:shadowRadius="0.5"			//键盘文字笔画阴影粗细, =0时可屏蔽调键盘字体模糊问题
    android:verticalCorrection="0dp"	//键盘实际位置和按下位置的偏移量，用于偏差矫正
	android:paddingTop="5dp"
	android:paddingBottom="5dp"
	//android:paddingLeft="5dp"//会把布局往右挤压...
	/>

2.KeyboardView方法
keyboardView.setPreviewEnabled(true);
Keyboard keyboard = keyboardView.getKeyboard();
List<Keyboard.Key> keys = keyboard.getKeys();//不包括小键盘

// 设置shift状态然后刷新页面(26个字母大小写转换)
boolean shifted = keyboardView.isShifted();
keyboardView.setShifted(!shifted);
keyboardView.invalidateAllKeys();



3.键盘布局, 放在res/xml目录下, R.xml.xxx
<?xml version="1.0" encoding="utf-8"?>
<Keyboard xmlns:android="http://schemas.android.com/apk/res/android"
    android:horizontalGap="2%p"	//Key按键水平间隙，区分精确值(dp、px等) 和 相对值(%、%p)
    android:keyWidth="8%p"	//Key宽度, 同上
    android:keyHeight="40dp"	//Key高度, 同上
    android:verticalGap="1%p">	//Key按键垂直间隙, 同上
	
	//计算方法, 假设 horizontalGap="2%p"
	//则keys剩余宽度 =         1 - horizontalGap * 每行key个数(10个)        = 80%p
	//keyWidth = 80%p / 10    = 8%p
	
	
	
	<!--属性见: {@link Keyboard#Row}-->
	<Row
	    //android:horizontalGap="2%p"
        //android:keyWidth="8%p"
        //android:keyHeight="40dp"
		android:keyboardMode=""				//键盘类型，如果该行的类型不符合键盘的类型，将跳过该行???
		android:rowEdgeFlags="top|bottom"	//行边界标记，top/bottom，键盘顶（底）部锚点???
        //android:verticalGap="1%p"
		>

		<!--属性见: {@link Keyboard#Key}-->
		<Key
			//android:keyWidth="10%p"
			//android:keyHeight="40dp"
			android:horizontalGap="1%p"					//第一个按钮 horizontalGap 是其余按钮的一半
			//android:codes="65"						//Key输出符号对应的Unicode值，可以省略，默认使用keyLabel字符的Unicode值('A'=65)
			//android:keyIcon="@drawable/delete"		//替换label显示在按键上的icon
			android:keyLabel="W"						//显示在Key上的标签
			//android:iconPreview						//弹出回显的icon
			//android:isSticky							//是否是开关键
			//android:isRepeatable						//是否允许重复。true表示长按时重复执行
			//android:isModifier						//是否功能修饰键，如：Alt/Shift
			//android:popupKeyboard="@xml/keyboard_pupop_for_w"//按键候选小键盘的keyboard布局
			//android:popupCharacters="W2w"				//小键盘显示的字符，用于显示Key候选项(长按W显示"W2w")
														//如果只声明了 popupCharacters，没有声明 popupLayout 和 popupKeyboard,将会使用默认布局
			//android:keyOutputText="W"					//Key按下时输出的字符或字符串, 可省略(如果是小键盘, 设置了没用)
			android:keyEdgeFlags="left/right"	//Key边缘位置标记，left/right，键盘左/右边锚点???
			/>
	</Row>
</Keyboard>



4.codes取值范围, 还可自定义
Keyboard.KEYCODE_SHIFT = -1;		//Shift 键需要设置 isSticky 和 isModifier 值为true， codes值为-1
Keyboard.KEYCODE_MODE_CHANGE = -2;
Keyboard.KEYCODE_CANCEL = -3;
Keyboard.KEYCODE_DONE = -4;
Keyboard.KEYCODE_DELETE = -5;		//Delete 键需要设置 isRepeatable 和 isModifier 值为true，codes值为-5
Keyboard.KEYCODE_ALT = -6;


5.KeyboardView中attr属性英文注释
<attr name="keyboardViewStyle" format="reference" />
//Image for the key. This image needs to be a StateListDrawable, with the following
//possible states: normal, pressed, checkable, checkable+pressed, checkable+checked,
//checkable+checked+pressed.
keyBackground

//Size of the text for custom keys with some text and no icon.
//自定义键的文本大小，包含一些文本，但没有图标。
labelTextSize

//Layout resource for key press feedback
keyPreviewLayout

//Vertical offset of the key press feedback from the key.
keyPreviewOffset

//Height of the key press feedback popup
keyPreviewHeight

// Amount to offset the touch Y coordinate by, for bias correction
//对触摸Y坐标进行偏移量，进行偏置校正
<attr name="verticalCorrection" format="dimension" />

//Layout resource for popup keyboards
popupLayout

<attr name="shadowColor" format="color"/>
<attr name="shadowRadius" format="float"/>

