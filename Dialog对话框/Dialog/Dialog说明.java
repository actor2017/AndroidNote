1.�޸�dialogλ����ϸ˵��
http://blog.csdn.net/fancylovejava/article/details/21617553

2.���췽��
public Dialog(@NonNull Context context, @StyleRes int themeResId);//R.style.dialog_notify

    <!--��ɫ����, û����Ӱ-->
    <style name="dialog_notify" parent="@android:style/Theme.Dialog">
        <item name="android:windowFrame">@null</item><!--�߿�-->
        <item name="android:windowFullscreen">false</item>
        <item name="android:windowIsFloating">true</item><!--�Ƿ�����activity֮��-->
        <item name="android:windowIsTranslucent">false</item><!--��͸��-->
        <item name="android:windowNoTitle">true</item><!--�ޱ���-->
        <item name="android:windowBackground">@color/transparent</item><!--����͸��-->
        <item name="android:backgroundDimEnabled">false</item><!--ģ��-->
    </style>
