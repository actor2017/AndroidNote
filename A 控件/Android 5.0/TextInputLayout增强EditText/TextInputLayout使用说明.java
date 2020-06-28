https://www.jianshu.com/p/2b0cd9e9a4d9
1.�������:compile 'com.android.support:design:25.1.1'

2.����չʾEditText����ʾ����hint��EditText�������ʱ�Ĵ�����ʾ����
TextInputLayoutֻ����һ��EditText����������쳣

3.TextInputLayout.getEditText.getText/setText����Щ�ֻ����ܻ����?�Ž�ϵͳ�ĵ�¼,����ֱ���õ�EditText.get/set text!!!


EditText������ѡ��:
android:background="@color/back_gray"<color name="back_gray">#E3E3E3</color>

            <!--hintEnabled:�Ƿ�������ʾhint-->
            <android.support.design.widget.TextInputLayout
                android:id="@+id/til_usename"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginRight="10dp"
                android:layout_weight="1"
//-------------------------------------------------------------
                app:errorTextAppearance="@style/counterOverflowTextColor"����������setError��err�������ɫ���»��ߵ���ɫ
//-------------------------------------------------------------
                android:hint="�������û���"		//��д����/����
                android:textColorHint="@color/deep_green"//δ��ȡ�����hint����ɫ,�Ѿ������ʧȥ�����hint��ɫ.ֻ��д������(������Ч)
                //app:hintEnable="false"//�رո�����ǩ(Ĭ���ǿ�����)
                //app:hintTextAppearance="@style/HintAppearance"//��ȡ���������Label����ɫ/��С��,�ɲ�����,ϵͳĬ��
//-------------------------------------------------------------
				ǰ����EditText��inputType=password
                app:passwordToggleEnabled="true"	//�����л������Ƿ������
                //app:passwordToggleDrawable="@drawable/eye"������ͼ��,�ɲ�����,��Ĭ�ϵ�
                app:passwordToggleTint="@color/deep_green">ͼ�����ɫ(ԭ����ɫ)#079481
//-------------------------------------------------------------
                app:counterEnabled="true"		//�����������
                app:counterMaxLength="11"		//����������󳤶�
                app:counterTextAppearance="@style/counterTextColor"û�г�������,�·�3/11����ɫ
                app:counterOverflowTextAppearance="@style/counterOverflowTextColor"//�����������,hint���»��ߵ���ɫ


                <EditText
                    android:id="@+id/et_username"
                    android:layout_width="match_parent"
                    //android:inputType="textPassword"
                    android:layout_height="match_parent"
                    android:textColor="@color/black"/>	//����д������
					
				<android.support.design.widget.TextInputEditText
					android:layout_width="match_parent"
					android:layout_height="wrap_content"
					android:hint="����������"
					android:inputType="numberPassword" />
            </android.support.design.widget.TextInputLayout>


    <!--���EditText�����볤��û����,�·�counter����ɫ-->
    <style name="counterTextColor"
           parent="Base.TextAppearance.AppCompat.Widget.PopupMenu.Small">
        <item name="android:textColor">@color/deep_green</item><!--#079481-->
        <item name="android:textAllCaps">false</item>
        <item name="android:textSize">16sp</item>
    </style>

    <!--���EditText�����볤�ȳ�����,hint���»��ߵ���ʾ-->
    <style name="counterOverflowTextColor"
           parent="Base.TextAppearance.AppCompat.Widget.PopupMenu.Small">
        <item name="android:textColor">@color/red</item><!--#ff0000-->
        <item name="android:textAllCaps">false</item>
        <item name="android:textSize">20sp</item>
    </style>

    <!--��ȡ�����,����Label����ɫ,��С-->
    <style name="HintAppearance" parent="TextAppearance.AppCompat">
        <item name="android:textSize">14sp</item>
        <item name="android:textColor">@color/deep_green</item>
    </style>




3.�»��ߵ���ɫ������������colorAccent����ɫ������Ҫ���»�����ɫ�Ļ�����Theme���潫colorAccent��һ�¼���
�ɸĳ�����ɫ:<color name="deep_green">#079481</color>


4.ȡ���ײ���
���Ҫȡ��TextInputEditText�ײ��ߣ���EditText����������background=@null��
����TextInputEditText��ͬ��������Ҫ��TextInputLayout��TextInputEdit��backgound������Ϊ@null

//===============================��ʾ������Ϣ======================

        //���������������
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
                    tilComment.setError("�������" + tilComment.getCounterMaxLength() + "����");//��ʾ��TextInputLayout�·�
                } else {
                    tilComment.setErrorEnabled(false);//���������ʾ��
                }
            }
        });

//-------------------------------------------------------------

//��ʾ��EditText���·�(�����ɫ�����岻�ܸ���,���������ͨ��html...����),���鲻��
et_username.setError(getString(R.string.password_error));

//����Drawable����Ҫ��API level��21���������ݵİ汾��21���µľͲ�����
et_username.setError(getString(R.string.password_error),getDrawable(R.mipmap.ic_launcher));//drawable����=null

