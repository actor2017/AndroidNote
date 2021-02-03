http://www.jianshu.com/p/c14f4d97b845

<EditText
	android:inputType=��none��
	android:inputType=��text��
	android:inputType=��textCapCharacters�� ��ĸ��д
	android:inputType=��textCapWords�� ����ĸ��д
	android:inputType=��textCapSentences�� ����һ����ĸ��д
	android:inputType=��textAutoCorrect�� �Զ����
	android:inputType=��textAutoComplete�� �Զ����
	android:inputType=��textMultiLine�� ��������
	android:inputType=��textImeMultiLine�� ���뷨���У����֧�֣�
	android:inputType=��textNoSuggestions�� ����ʾ
	android:inputType=��textUri�� ��ַ
	android:inputType=��textEmailAddress�� �����ʼ���ַ
	android:inputType=��textEmailSubject�� �ʼ�����
	android:inputType=��textShortMessage�� ��Ѷ
	android:inputType=��textLongMessage�� ����Ϣ
	android:inputType=��textPersonName�� ����
	android:inputType=��textPostalAddress�� ��ַ
	android:inputType=��textPassword�� ����
	android:inputType=��textVisiblePassword�� �ɼ�����(�������ֻ����������,��ĸ...,�����ġ��)
	android:inputType=��textWebEditText�� ��Ϊ��ҳ�����ı�
	android:inputType=��textFilter�� �ı�ɸѡ����
	android:inputType=��textPhonetic�� ƴ������(ûʲô����)

	//��ֵ����
	android:inputType=��number�� ����	 editText.setInputType(InputType.TYPE_CLASS_NUMBER | ...);
	android:inputType=��numberSigned�� ���������ָ�ʽ
	android:inputType=��numberDecimal�� ��С����ĸ����ʽ
	android:inputType=��phone�� ���ż���
	android:inputType=��datetime�� ʱ������
	android:inputType=��date�� ���ڼ���
	android:inputType=��time�� ʱ�����
	
    <requestFocus />		//��ȡ���
</EditText>


//���������ȡ���ƫ����
int offsetForPosition = editText.getOffsetForPosition(x, y);
editText.setSelection(offsetForPosition);


//�������򽹵�(KeyboardInputEditText)
public void clearFocus() {
	parent.setFocusableInTouchMode(true);//���ø���focusableInTouchMode
	parent.setFocusable(true);//���ø���focusable
	parent.requestFocus();//���ø����ȡfocus
}


/**
 * �����Ƿ������(false��ʱ��,���Ե���TextViewչʾ)(GridTableEditText)
 * @param enable
 */
public void setInputEnable(boolean enable) {
//        et1.setEnabled(enable);//�������ܱ༭,�������������뷨,����EditText�ĵ���¼��޷�Ӧ,����������¼�
	et1.setFocusable(enable);
	et1.setClickable(!enable);
	et1.setFocusableInTouchMode(enable);
//        if (enable) et1.requestFocus();//�ѹ���ƶ�����һ��et1,���ǲ���������
//        et1.setCursorVisible(false);
}

et1.append(CharSequence text);//����ں���


android:digits="0123456789xX" �������뷶Χ������������������֤��,����:et.setKeyListener(DigitsKeyListener.getInstance(digits));
���֤ʾ��:
<EditText
    android:id="@+id/et_sfz"
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:layout_marginLeft="5dp"
    android:background="@color/white"
    android:digits="0123456789xX"        //digitsʾ���÷���
    android:hint="�����뷿�����֤"
    android:imeOptions="actionNext"
    android:inputType="number"           //����������digits����,Ҳ����˵��ʹ������number,Ҳ������xX. ���ǻ�ȡ���������������9������
    android:maxLength="18"
    android:singleLine="true"            //�������ʾ"��һ��",������singleLine�Ϳ��ԡ�
    android:textColor="@color/gray_666"
    android:textColorHint="@color/gray_999"
    android:textSize="17sp"/>


//------------------------------imeOptions--------------------------------------
	Constant				Value		Mean
android:imeOptions="actionUnspecified"		  0		δָ��(������м�,��"��һ��".��������һ��,��"���")
android:imeOptions="normal"			  0		��ͨ(������м�,��"��һ��".��������һ��,��"���")
android:imeOptions="actionNone"			  1		None,�����κ���ʾ(������м�,��������һ��,���ǲ���ʵ��һ��,�����ĩβ,����޷�Ӧ)
android:imeOptions="actionGo"			  2		Go,��ʼ,ȥ�� ,ǰ��
android:imeOptions="actionSearch"		  3		�Ŵ�ͼƬ,���� mEditText.setImeOptions(3);
android:imeOptions="actionSend"			  4		����
android:imeOptions="actionNext"			  5		��һ��,��һ��
android:imeOptions="actionDone"			  6		��� mEditText.setImeOptions(6);EditorInfo.IME_ACTION_DONE
android:imeOptions="actionPrevious"		  7		��һ��,��һ��
android:imeOptions="flagNoPersonalizedLearning"	 1000000	���뷨�����ס������ʷ
android:imeOptions="flagNoFullscreen"		 2000000	���뷨����ȫ��
android:imeOptions="flagNavigatePrevious"	 4000000	��������һ��
android:imeOptions="flagNavigateNext"		 8000000	��������һ��
android:imeOptions="flagNoExtractUi"		 10000000	�����ܲ�ռ����Ļ
android:imeOptions="flagNoAccessoryAction"	 20000000	�ô������¼����ȼ����
android:imeOptions="flagNoEnterAction"		 40000000	enter��������
android:imeOptions="flagForceAscii"		 80000000	ǿ������ASCII

ע��:���inputTyle��imeOptions��������,����û��imeOptions��Ч��,��һ��:singleLine

//��Լ��̵ļ����ߣ������ֻ����̡��������
mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    // �����ؼ���
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchContactsActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    search();//���������Ĳ���
                    return true;
                }
                return false;
            }
        });
// �¼�����(���, ����, Go, ����...)
  mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

       @Override
       public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
			//ACTION_UP��ACTION_DOWNʱ���ᴥ���������
			if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (actionId) {
				  case EditorInfo.IME_ACTION_SEARCH:
					  Toast.makeText(getApplicationContext(), "�����������ť", Toast.LENGTH_SHORT).show();
					  break;
			   }
			}
            return false;
       }
  });

//��̬��ʾ/��������
etPassword.setInputType(257 - etPassword.getInputType());//128+129
viewEye.setSelected(!view.isSelected());//�۾�, ��ʾ/��������
//int inputType = etPassword.getInputType();
//if (inputType == 129) {
//    etPassword.setInputType(128);
//    ivShowPassword.setImageResource(R.mipmap.img_register_hide_password);
//} else {
//    etPassword.setInputType(129);
//    ivShowPassword.setImageResource(R.mipmap.img_register_show_password);
//}


//���ö�ʾ������:
    private InputMethodManager imm;//�������(���뷨)
    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//�������(���뷨)
	//�����������
	if (imm.isActive()) {
		imm.hideSoftInputFromWindow(etMsg.getApplicationWindowToken(), 0);
	}


android:maxLength="1"����������󳤶�,����:et1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1)});
android:numeric="integer" ����ֻ�����������������С�����ǣ�decimal 
android:singleLine="true" ���õ������룬һ������Ϊtrue�������ֲ����Զ����С�
android:password="true" ����ֻ����������
android:textColor = "#ff8c00" ������ɫ
android:textStyle="bold" ���壬bold, italic, bolditalic 
android:textSize="20dip" ��С
android:capitalize = "characters" �Դ�д��ĸд
android:textAlign="center" EditTextû��������ԣ���TextView�� android:textColorHighlight="#cccccc" ��ѡ�����ֵĵ�ɫ��Ĭ��Ϊ��ɫ
android:textColorHint="#ffff00" ������ʾ��Ϣ���ֵ���ɫ��Ĭ��Ϊ��ɫ
android:textScaleX="1.5" ����������֮��ļ��
android:typeface="monospace" ���ͣ�normal, sans, serif, monospace
android:background="@null" �ռ䱳��������û�У�ָ͸��
android:layout_weight="1" Ȩ�أ����ƿؼ�֮��ĵ�λ,�ڿ��ƿؼ���ʾ�Ĵ�Сʱ�����õġ�
android:textAppearance="?android:attr/textAppearanceLargeInverse" ������ۣ��������õ���ϵͳ�Դ���һ����ۣ�����ʾϵͳ�Ƿ���������ۣ�����ʹ��Ĭ�ϵ���ۡ���֪���������Բ��ԣ�
 ͨ��EditText��layout xml�ļ��е����������ʵ��:
1. ��������� android:password="true" ����������EditText��ʾ�������Զ�Ϊ�Ǻţ�����ʱ���ݻ���1���ڱ��*������
2. ������ android:numeric="true" �������������뷨�Զ���Ϊ����������̣�ͬʱ������0-9����������
3. ������ android:capitalize="cwj1987" �����������������cwj1987��һ������������֤ ������һЩ��չ�ķ������
android:editable="false" ����EditText���ɱ༭
android:singleLine="true" ǿ������������ڵ���
android:ellipsize="end" �Զ�����β��������ݣ�һ�������������ݹ���һ���޷�ȫ����ʾʱ

