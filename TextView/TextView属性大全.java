https://yq.aliyun.com/articles/51235?spm=a2c4e.11154873.tagmain.6.7369392aFYBK9Z

<TextView
    android:id="@+id/tv_ysrq"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:gravity="center_vertical"
    android:hint="��ѡ��Ԥ������"			//��ʾ����
	android:text="����"
    android:textColor="@color/gray_666"		//�����ı���ɫ
	android:textSize="17sp"
	android:textStyle=""					//��������[bold(�Ӵ�,����) 0, italic(б��) 1, bolditalic(�ִ���б) 2] ��������һ���������á�|������
    android:textColorHint="@color/gray_999"	//��ʾ������ɫ
	android:autoLink=""	//���ı�ΪURL/email/�绰/mapʱ,�ı���ʾΪ�ɵ��������,��ѡֵ:1.none 2.web 3.email 4.phone 5.map 6.all
	android:maxLength="18"					//������ʾ/������ı����ȣ��������ֲ���ʾ
    android:textIsSelectable="true"			//�ı����ɸ���
	android:cursorVisible="true"			//EditText��Ч, �趨���Ϊ��ʾ/���أ�Ĭ����ʾ
	android:digits="1234567890"				//��������������Щ�ַ����硰1234567890.+-*/% ()��
����android:editable="true"					//��ʱ, EditText�����Ƿ�ɱ༭, ���=false: 1.û���. 2.����˫��/�����༭
											//���: editText.setInputType(InputType.TYPE_NULL);//��������android:inputType="none"��Ч, �ǿ��������
����android:imeOptions=""			//��Ҫ���� inputType, �������½ǻ��ǻس�...
									//���뷨�༭��ѡ��������½�IME������༭����صĶ�������actionDone���½ǽ���ʾһ������ɡ���Ĭ����һ���س�����
	android:inputType=""			//�����ı������ͣ����ڰ������뷨��ʾ���ʵļ�������
	android:maxHeight=""			//�������߶�
����android:minHeight=""			//������С�߶�
����android:maxWidth=""				//���������
����android:minWidth=""				//������С���
	android:password="true"			//��ʱ. ��С�㡱.����ʾ�ı�, ������ʽ
	android:selectAllOnFocus="true"	//EditText��, ����ı��ǿ�ѡ��ģ�����ȡ����ʱ, ȫѡ�ı�
����android:textColorHighlight=""	//��ѡ�����ֵĵ�ɫ��Ĭ��Ϊ��ɫ
����android:textColorLink=""		//�������ӵ���ɫ.
	android:typeface=""				//�����ı����壬���������³���ֵ֮һ��normal 0, sans 1, serif 2, monospace(�ȿ�����) 3
	
	//�����
	android:ellipsize=""	//ʡ�Է���. �����ֹ���ʱ,�ÿؼ��������ʾ��������ֵ���ã�
							//1."start"  ��...ʡ�Ժ���ʾ�ڿ�ͷ;
							//2."end"    ��...ʡ�Ժ���ʾ�ڽ�β;
							//3."middle" ��...ʡ�Ժ���ʾ���м�;
							//4."marquee"��������Ƶķ�ʽ��ʾ(���������ƶ�)
	android:marqueeRepeatLimit="marquee_forever|123"//��ellipsizeָ��marquee������£�ָ������ƹ���������marquee_forever:���޴�
	
	//����
	android:lines="2"		//�����ı����������������о���ʾ���У���ʹ�ڶ���û�����ݡ�
����android:maxLines="2"	//�����ı��������ʾ��������width����layout_width���ʹ�ã����������Զ����У���������������ʾ��
����android:minLines="2"	//�����ı�����С��������lines���ơ�
	android:singleLine="true"//���õ�����ʾ�������layout_widthһ��ʹ�ã����ı�����ȫ����ʾʱ�������á���������ʾ

	//�ַ����(layout_width=wrap_content��Ч)
	android:ems="5"//����TextView�Ŀ��ΪN���ַ��Ŀ��, em(equal M:��M�ַ�һ�µĿ��Ϊһ����λ)��һ��ӡˢ�Ű�ĵ�λ,��ʾ�ֿ�
����android:maxEms="5"//����TextView�Ŀ��Ϊ�ΪN���ַ��Ŀ�ȡ���emsͬʱʹ��ʱ����ems
����android:minEms="5"//����TextView�Ŀ��Ϊ���ΪN���ַ��Ŀ�ȡ���emsͬʱʹ��ʱ����ems
					
	//��/�ּ��, padding
	android:lineSpacingExtra=""//�����м�࣬�硱8dp��
	android:lineSpacingMultiplier//�����м�౶�����硰1.5������Ϊ1.5���м��
	android:textScaleX="" //�����ּ�࣬�硰1.5���ȣ�Ĭ����1.0
	android:includeFontPadding="true"//�����ı��Ƿ���������͵ײ�����հ�(paddingTop&paddingBottom)��Ĭ��Ϊtrue����ʹ����Ϊfalse������һ��padding...
	
	//drawableLeft �ı�ͼ��. ���ָ��һ����ɫ�Ļ����text�ı�����Ϊ����ɫ������ͬʱ��backgroundʹ��ʱ���Ǻ��ߡ�
	android:drawableLeft="@drawable/..."//��text��������һ��drawable����ͼƬ
	android:drawableRight=""//��text���ұ����һ��drawable
	android:drawableTop=""//��text�����Ϸ����һ��drawable
	android:drawableBottom=""//��text���·����һ��drawable
	android:drawablePadding=""//����text��drawable(ͼƬ)�ļ��, ���Ϸ�4������һ��ʹ��,������Ϊ����,����ʹ��û��Ч��.
	
	//����
	android:scrollHorizontally="true"//�����ı�����TextView�Ŀ�ȵ�����£��Ƿ���ֺ�������
	
	//����3����Ӱ, View����
    android:fadingEdgeLength="10dp"	//��Ӱ�߶�
    android:requiresFadingEdge="vertical"//��Ӱ����
    android:fadingEdge="vertical"	//��Ӱ����(��api14��ʼ, ��ʱ)
	android:shadowColor="@color/red"//ָ���ı���Ӱ����ɫ����Ҫ��shadowRadiusһ��ʹ�á�
	android:shadowRadius="0.1"		//������Ӱ�İ뾶������Ϊ0.1�ͱ���������ɫ�ˣ�һ������Ϊ3.0��Ч���ȽϺá�
	android:shadowDx="float"		//������Ӱ�������꿪ʼλ�á�
����android:shadowDy="float"		//������Ӱ�������꿪ʼλ�á�
	
	//�Զ�����api26(8.0)
	android:autoSizeTextType="none|uniform"//�Ƿ�֧���Զ��ı������С, api26(8.0)https://www.jianshu.com/p/2fdc97ae74a8
	android:autoSizeMinTextSize="10sp"//��С�����С����������Ϊ10sp����ʾ�������ֻ����С��10sp, api26(8.0)
	android:autoSizeMaxTextSize="18sp"//��������С����������Ϊ18sp����ʾ�������ֻ�ܷŴ�18sp, api26(8.0)
	android:autoSizeStepGranularity="1sp"//�������ȣ���ÿ�������С�仯����ֵ����������Ϊ1sp����ʾÿ����С��Ŵ��ֵΪ1sp, api26(8.0)

	//����������
	android:autoText="true"	//�Ƿ��Զ�ִ������ֵ��ƴд��������EditText��ʾ���뷨�������ʱ�������á�
	android:bufferType="editable|normal|spannable"//ָ��getText()��ʽȡ�õ��ı����
							//editable ������StringBuilder��׷���ַ���
							//spannable ����ڸ������ַ�����ʹ����ʽ���μ�����1������2��
	android:capitalize="characters|none|sentences|words"//����Ӣ����ĸ��д���͡���EditText�������뷨���ܿ��õ����μ�EditView������˵����
	android:editorExtras=""	//�����ı��Ķ������������
	android:freezesText="false"//���ñ����ı��������Լ�����λ�á�
	android:imeActionId=""	//����IME����ID����onEditorAction�в����жϽ����߼�����
����android:imeActionLabel=""//����IME������ǩ�����ǲ��ܱ�֤һ����ʹ�ã����������뷨��չ��ʱ��Ӧ������
	android:inputMethod=""	//Ϊ�ı�ָ�����뷨����Ҫ��ȫ�޶���(�����İ���)�����磺com.google.android.inputmethod.pinyin
	android:linksClickable="true"//���������Ƿ������ӣ���ʹ������autoLink
����android:numeric="integer|decimal|signed"//��ʱ. ���������������ͣ�integer������������signed������������������������decimal(������)
����android:phoneNumber="true"//��ʱ. ����Ϊ�绰��������뷽ʽ��
����android:privateImeOptions=""//�ṩ��������뷨ѡ��(�ַ�����ʽ)���������뷨�������Ƿ��ṩ���������������Զ������뷨�̳�
	android:textAppearance=""//����������ۡ��硰?android:attr/textAppearanceLargeInverse���������õ���ϵͳ�Դ���һ�����
							//?��ʾϵͳ�Ƿ���������ۣ�����ʹ��Ĭ�ϵ���ۡ������õ�ֵ���£�
							//textAppearanceButton/textAppearanceInverse/textAppearanceLarge/textAppearanceLargeInverse
							//textAppearanceMedium/textAppearanceMediumInverse/textAppearanceSmall/textAppearanceSmallInverse
	android:height=""		//�����ı�����ĸ߶ȣ�֧�ֶ�����λ��px(����)/dp/sp/in/mm(����)
����android:width=""		//�����ı�����Ŀ�ȣ�֧�ֶ�����λ��px(����)/dp/sp/in/mm(����)����layout_width���������

	//tools
	tools:text="��С:18.2MB"//tools:Ԥ����ʾ����������tools
	tools:showIn="@layout/activity_scrolling"//���������ʾ���ĸ�����,������item��Ĳ���.
/>

1.������췽��
textView.setText("CharSequence");
/**
 * flag: FROM_HTML_MODE_COMPACT: html��Ԫ��֮��ʹ��һ�����з��ָ�. FROM_HTML_MODE_LEGACY: html��Ԫ��֮��ʹ���������з��ָ�
 * imageGetter: ��д����,����Drawable,���·���д��,https://blog.csdn.net/hoooooozzz/article/details/52468556
 * tagHandler: ���������ܽ�����HTML���ʱ,���������ص�(Դ������mTagHandler.handleTag)
 */
textView.setText(Html.fromHtml(String source, int flag, ImageGetter imageGetter, TagHandler tagHandler));//��ʾͼ��,������ҳһ��
textView.setText("CharSequence", BufferType.NORMAL);//Ĭ��BufferType.NORMAL
textView.setText(new char[]{}, 0, 0);//char[] text, int start, int len
textView.setText(R.string.text_ack_msg);//@StringRes int resid
textView.setText(span, BufferType.SPANNABLE);//CharSequence text, BufferType type,һ�����ڸ��ı�������ʾ

textView.setText("�Ƶ���ס\n��Ϣ�Ǽ�");//����

textview.setText("��\u3000��");		//�ո�
<TextView
        android:id="@+id/textview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/dp_10"
		android:text="��\u3000\u3000��"
        android:text="��&#160;&#160;&#160;&#160;��"/>//xml������ո�ʹ�� (&#160;) �� ( \u3000 ) ����Ч
<string name="str">��\u3000\u3000\u3000��</string>	//string.xml �ļ���ո�ʹ�� ( \u3000 ) ��Ч

textView.append(CharSequence text);//����ں���
textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //�»���
textView.getPaint().setAntiAlias(true);//�����

int lines = textView.getLineCount();//��ȡ����
if (lines <= 1) {
    textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);//����
} else textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);//����



2.����drawableleftͼƬ��С(xmlû����������drawableleftͼƬ��С,�Ӵ��������)
Drawable drawable=getResources().getDrawable(R.drawable.ic_phone);
drawable.setBounds(0,0,30,35);//��һ0�Ǿ���߾��룬�ڶ�0�Ǿ��ϱ߾��룬30��35�ֱ��ǳ���
tv_phone.setCompoundDrawables(drawable,null,null,null);//ֻ�����


3.�ܻ�����textview
TextView �����android:scrollbars="vertical"
�����textview.setMovementMethod(ScrollingMovementMethod.getInstance());
Ϊʲô��������֮����ֻtextview��ֵ֮�󡣱���Ҫ�Ȼ���һ��textview�ؼ���������ʾtextview�����ݣ�����Ȼ���ǿհ�һƬ��
mText.scrollTo(0, 0);

<string name="about_github"><a href="https://github.com/DreaminginCodeZH/MaterialRatingBar">View on GitHub</a></string>
tv.setMovementMethod(LinkMovementMethod.getInstance());//�������ת����
int selectionStart = editText.getSelectionStart();//���λ��[0,+max],��-1?
int selectionEnd = editText.getSelectionEnd();//���ѡ�����λ��
editText.getText().replace(Math.min(selectionStart, selectionEnd),//��ѡ�е�ȫ����Χ�滻Ϊ[�ɰ�]
                    Math.max(selectionStart, selectionEnd), "[�ɰ�]", 0,
                    "[�ɰ�]".length());


4.ɾ��
//ɾ��1
KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
editText.dispatchKeyEvent(event);
//ɾ��2(����)
public void delClick(EditText editText) {
	KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
	editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
}
//ɾ��3
int start = editText.getSelectionStart();
int end = editText.getSelectionEnd();
if (end > start) {//ѡ����һ������
	editText.getText().delete(start, end);
} else if (start > 0) {
	editText.getText().delete(start - 1, end);
}
//���
editText.getText().clear();


5.setFilters
//����������ֽ��й���
//public void setFilters(InputFilter[] filters)
editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength)});//����������볤��
editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(16)});//ֻ������16λ��д��ĸ

//ʵ��InputFilter����������Ҫ����һ����filter�ķ���
public interface InputFilter {
	/**
	 * CharSequence source,  //��������� 
     * int start,  //��ʼλ�� 
     * int end,  //����λ�� 
     * Spanned dest, //��ǰ��ʾ������ 
     * int dstart,  //��ǰ��ʼλ�� 
     * int dend //��ǰ����λ�� 
	 */
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend);
}
//ʵ��һ������Сдת��д��filter
InputFilter switchFilter = new InputFilter() {
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        return source.toString().toUpperCase();
    }
};
//���������
private class RegexFilter implements InputFilter {

	private String regex;//����

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
//��������̰����ļ���
public void setKeyListener(KeyListener input);

DigitsKeyListener.getInstance();//= ��
//boolean sign(����), boolean decimal(С��)     ��: DigitsKeyListener.COMPATIBILITY_CHARACTERS
DigitsKeyListener.getInstance(false, false);//ֻ������������
DigitsKeyListener.getInstance(true, false);//�����з�����������
DigitsKeyListener.getInstance(false, true);//����С������������
DigitsKeyListener.getInstance(true, true);//�����з�������/С������
DigitsKeyListener.getInstance(digits);//digits=0123456789, ���������������

//���ַ����������
NumberKeyListener keyListener = new NumberKeyListener() {
    public int getInputType() {
        return InputType.TYPE_CLASS_TEXT;//ָ����������
    }
    protected char[] getAcceptedChars() {//ָ���������ܵ��ַ�
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".toCharArray();
    }
};


7.�������ָı����
tv_phone.addTextChangedListener(textWatcher);
textWatcher = new TextWatcher() {
	
	//���ı��仯ǰ����
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//s: �޸�֮ǰ������
		//start: ��ʼ�޸ĵ�λ��
		//count: �仯���ַ�����
		//after: �仯���λ���ַ�����(����1����ĸ, after=1)
		System.out.printf("s=%s, start=%d, count=%d, after=%d", s, start,after, count);
	}

	//���ı��仯ʱ����,��ʱs�������ѷ����ı�
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//s: �޸ĺ������
		//start: ��ʼ�޸ĵ�λ��
		//before: �仯ǰ�ַ�����Ϊ(0)
		//count: �仯�ַ�����Ϊ(1)
		System.out.printf("s=%s, start=%d, before=%d, count=%d", s, start,before, count);
	}

	//���ı��仯�����,s��Ϊ�仯����ı����
	@Override
	public void afterTextChanged(Editable s) {//�޸ĺ������
		System.out.println(s);
	}
};


//////////////////////////////////////////setKeyListener//////////////////////////////////////////
//ͼƬ���100%
String headStr = "<html><head><meta charset='UTF-8'/><meta name='viewport' content='width=device-width, initial-scale=1.0' /><style>img{width:100%;display:block;}</style></head><body>";
String html = "<font color=\"#ff2929\">" + nickname + "</font>" +//������ɫ
                            "<font color=\"#454545\">�ظ�</font>" +
							"<font color=\"#ff2929\">" + nickname2 +
							"��</font>" + "<font color=\"#454545\">" + content + "</font>";
String time4Order = "<font color='#9f38f7'>�ѵ�</font>ԤԼʱ��";
String html = "";
html = html.concat(String.format("<font><b>%s </b>�ظ� </font>", item.replyUser.nickname));
html = html.concat(String.format("<font><b>%s: </b>%s</font>", item.discussUser.nickname, item.content));
//����: <br />



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

