ContentProvider	:�����ṩ��,��Ӧ�ó����˽�����ݱ�¶����,��:QQ��ȡ�绰��ϵ��
1.BankProvider extends ContentProvider
2.�嵥�ļ�ע��:
        <!--authorities:ContetProviderΨһ��ʶ-->
        <!--permission:��һ��Ӧ����Ҫ���ʸ�ContentProvider��Ҫ������Ȩ��-->
        <!--process:����һ���½���-->
        <!--exported:�Ƿ�֧������Ӧ�õ��õ�ǰ���
            Ĭ��ֵ�����������intent-filter Ĭ��ֵΪtrue; û��intent-filterĬ��ֵΪfalse
        -->
        <provider
            android:name=".BankProvider"
            android:authorities="${applicationId}.provider">

�����յ�ContentResolver ����������������ṩ�߱����

androidƽ̨�ṩ��Content Providerʹһ��Ӧ�ó����ָ�����ݼ��ṩ������Ӧ�ó���
��Щ���ݿ��Դ洢���ļ�ϵͳ�С���һ��SQLite���ݿ⡢�����κ���������ķ�ʽ,
����Ӧ�ÿ���ͨ��ContentResolver��(��ContentProviderAccessApp����)
�Ӹ������ṩ���л�ȡ���������.(�൱����Ӧ�������һ���),
ֻ����Ҫ�ڶ��Ӧ�ó���乲�������ǲ���Ҫ�����ṩ�ߡ����磬ͨѶ¼���ݱ����Ӧ�ó���ʹ�ã�
�ұ���洢��һ�������ṩ����,���ĺô�:ͳһ���ݷ��ʷ�ʽ��

androidϵͳ�Դ��������ṩ��(�����ı�ʾ���ݿ���,�Ƕ����Ķ��Ǳ���)
��Щ�����ṩ����SDK�ĵ���android.provider Java���ж��н��ܡ�
����http://developer.android.com/reference/android/provider/package-summary.html

����������Browser
����������CallLog
����������Contacts
��                ����������Groups
��                ����������People
��                ����������Phones
��                ����������Photos
����������Images
��                ����������Thumbnails
����������MediaStore
��                ����������Albums
��                ����������Artists
��                ����������Audio
��                ����������Genres
��                ����������Playlists
����������Settings
����������Video

 CallLog����ַ�ͽ��յ��ĵ绰��Ϣ

 Contact.People.Phones���洢�绰����

 Setting.System��ϵͳ���ú�ƫ������

ʹ��Content Provider���⹲�����ݵĲ���
