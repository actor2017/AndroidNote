compile 'com.android.support:gridlayout-v7:25.3.1'//ȷ����v7���汾����һ��,�������v7�����,�ᵼ�¿�ȷ��䲻��)(��/�ұ���߽���벻һ��)
ÿ���ؼ��Ŀ��һ��!!(ʾ��:����������)

GridLayout����Android4.0���������²��֣�ʹ������������������
1�����ٲ���Ƕ�ײ�δӶ�������ܣ�
2������Ҫ���Ӷ���Ĳ��֣�����Ī����
3.���ַ���:��Ϊˮƽ�ʹ�ֱ���ַ�ʽ��Ĭ����ˮƽ���֣�һ���ؼ�����һ���ؼ��������������У�����ͨ��ָ��android:columnCount�������������Ժ󣬿ؼ����Զ����н������С�
�������Ϊ��ֱ���򲼾֣��ؼ����Ǵ��ϵ����������У�����ͨ��ָ��android:rowCount�������������Ժ󣬿ؼ����Զ����н������С�

��ʵ��ʹ���У�ǿ�ҽ���ÿһ����View����ȷ����android:layout_row��android:layout_column����Ҫʹ��GridLayout���Զ�ȷ����View���깦�ܡ�

GridLayoutȷ���Ŀ�ߵ��㷨�ǣ��Ե�1�к͵�1��Ϊ������


��1�еĸߣ�
������1����������View�ĸߣ�ȡ���ֵ��Ϊ���еĸߣ��������û����View���и���Ϊ0��


��1�еĿ�
������1����������View�Ŀ�ȡ���ֵ��Ϊ���еĿ��������û����View���п���Ϊ0��

GridLayout����View����Ҫ����layout_width��layout_height���ԣ���ΪGridLayout������е���View����������������ΪWRAP_CONTENT��������������Ҳû���á�?????�ӿؼ��������ÿ�߲�����Ч?????

    <android.support.v7.widget.GridLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"

        //app:useDefaultMargins="true"//���˿�Ȳ���ƽ������
        app:columnCount="4"//ռ��4�С�
        app:rowCount="3">//ռ��3�С�


        <TextView
            android:text="First Name:"
            //android:layout_row="0"//ָ���õ�Ԫ���ڵ�һ����ʾ��
            //app:layout_column="0"//ָ���õ�Ԫ���ڵ�һ����ʾ��
            app:layout_gravity="right" />

        <EditText
            android:ems="10"
            //app:layout_rowSpan="1"//ָ���õ�Ԫ��ռ�ݵ�����(���=1)��
            app:layout_columnSpan="2"//ָ���õ�Ԫ��ռ�ݵ�����(���=2)��
            //android:layout_gravity="fill_horizontal"//��˼�ǰ����е�ʣ��ռ䶼������ҡ�
            android:layout_rowWeight="1"//��API21(5.0)���룩��Ȩ��,�����е�ʣ��ռ䶼������ҡ�
            android:layout_columnWeight="1"/>//��API21(5.0)���룩��Ȩ��,�����е�ʣ��ռ䶼������ҡ�
            
    </android.support.v7.widget.GridLayout>