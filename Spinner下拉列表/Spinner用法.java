android:Theme.Light�ܳ�,�� "1.Spinner����Դ��xml����" ͼƬ.
�����Ҳ�Ǻܶ��˲���ʹ��Spinner��ԭ���˰ɡ���������2.3����ֻ������������Ч����

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="1dp"
        android:background="@color/white">
        <!--���������һ���Ǹ�Spinner���ñ���ɫ,��ΪSpinner�ı���ɫ��͸���ġ��-->
     
        <Spinner
            android:id="@+id/spinner1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
			android:entries="@array/languages"	//dropdown ģʽʱ����д, ������Դ,  arrays.xml
            android:prompt="@string/please_choose_sex"//(��ѡ���Ա�)��dialogģʽ�²���Ч,����ֱ��д����������
            android:spinnerMode="dropdown|dialog"//�˵���ʾ��ʽ:�����˵�,������(��android2.3��û���������,ϵͳĬ�Ͻ�Spinner�����˵���ʾ��dialog)
            android:dropDownVerticalOffset="25.5dp"//spinnerMode=��dropdown��ʱ����������Ŀѡ�񴰿��ڴ�ֱ���������Spinner���ڵ�ƫ������
            android:popupBackground="@drawable/"//��spinner=��dropdown��ʱ��ʹ��������������������б�ı�����
            //android:backgroundTint="@color/gray_333"//��ͷ����ɫ,����api14(android 4.0)
            //android:background="@color/white"//������ɫ(������֮�󿴲�����ͷ,��Ҫ��������)
            //android:gravity="left|top"//���뷽ʽ, ��ʱ����Ч...
            android:textAlignment="center|textStart|textEnd|viewStart|viewEnd"//API 17 �Ժ������
            //android:dropDownHorizontalOffset="1.5dp"//spinnerMode=��dropdown��ʱ����������Ŀѡ�񴰿���ˮƽ���������Spinner���ڵ�ƫ������
            //android:dropDownSelector="@drawable/spinner_selector"//ѡ��/δѡ�е�selector
            //android:dropDownWidth="50dp"//��spinnerMode=��dropdown��ʱ���趨������Ŀ�ȡ�
			/>
    </LinearLayout>
<string-array name="please_choose_sex">//values/arrays����strings???.xml
    <item>��</item>
    <item>Ů</item>
</string-array>


--------------------------------����----------------------------------
spinner.setPopupBackgroundResource(int);//��spinner=��dropdown��ʱ��ʹ��������������������б�ı�����
spinner.setDropDownHorizontalOffset(int);//spinnerMode=��dropdown��ʱ����������Ŀѡ�񴰿���ˮƽ���������Spinner���ڵ�ƫ������
spinner.setDropDownVerticalOffset(int);//spinnerMode=��dropdown��ʱ����������Ŀѡ�񴰿��ڴ�ֱ���������Spinner���ڵ�ƫ������
spinner.setDropDownWidth(int);//��spinnerMode=��dropdown��ʱ���趨������Ŀ�ȡ�


spinner.getSelectedItemPosition();//��ȡѡ�����һ��
spinner.setSelection(0);//����ѡ����һ��
spinner.getCount();//��ȡitem����


��ô��ȡֵ?
1.spinner.getSelectedItem().toString();//ֱ�ӷ��ظ���Ŀ��ֵ.(itemֻ��TextView��ʱ����Ч,���򷵻ظ�item�ĵ�ֵַ)

2.getResources().getStringArray(R.array.language)[spinner.getSelectedItemPosition()];//���ֵ�!


3.ѡ�м���:
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
           
                String language = getResources().getStringArray(R.array.languages)[pos];
                Toast.makeText(MainActivity.this, "��������:"+language, 2000).show();
            }
			
			//adapterΪ�յ�ʱ��ͻ���õ��������
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });


