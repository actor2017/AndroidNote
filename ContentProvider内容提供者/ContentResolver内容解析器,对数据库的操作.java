��Android Ӧ�ó���֮�����ݹ���-ContentResolver�У��Ѿ�˵����Android�����ʵ��Ӧ�ó���֮�����ݹ���ģ�����ϸ��������λ�ȡ����Ӧ�ó���������ݡ�ContentProviders�洢�ͼ������ݣ�ͨ�������������е�Ӧ�ó�����ʵ�����Ҳ��Ӧ�ó���֮��Ψһ�������ݵķ�������ô��ν�Ӧ�ó�������ݱ�¶��ȥ��

ͨ����ǰ���µ�ѧϰ��֪��ContentResolver��ͨ��ContentProvider����ȡ������Ӧ�ó���������ݣ���ôContentResolver��ContentProvider�Ľӿ�Ӧ�ò��ġ�����ContentProvider������֯Ӧ�ó�������ݣ�������Ӧ�ó����ṩ���ݣ�ContentResolver�����ȡContentProvider�ṩ�����ݣ��޸�/���/ɾ���������ݵȣ�
ContentProvider �����������ṩ���ݵģ�
Android�ṩ��ContentProvider��һ���������ͨ��ʵ��һ��ContentProvider�ĳ���ӿڽ��Լ���������ȫ��¶��ȥ������ContentProviders�����������ݿ��б�ķ�ʽ�����ݱ�¶��Ҳ����˵ContentProvider����һ�������ݿ⡱����ô����ȡ���ṩ�����ݣ�Ҳ��Ӧ��������ݿ��л�ȡ���ݵĲ�������һ����ֻ�����ǲ���URI����ʾ�����Ҫ���ʵġ����ݿ⡱��������δ�URI��ʶ��������Ҫ�����ĸ������ݿ⡱�������Android�ײ���Ҫ���������ˣ����ڴ���ϸ˵����Ҫ������ContentProvider������ṩ���ݲ����Ľӿڣ�
query(Uri, String[], String, String[], String)
insert(Uri, ContentValues)
update(Uri, ContentValues, String, String[])
delete(Uri, String, String[])

��Щ���������ݿ�Ĳ�����������ȫһ�����ڴ˲���ϸ˵����Ҫ����˵���ĵط���URI��
��URI��D���ֿ��ܰ���һ��_ID �����Ӧ�ó�����SQL����еģ�������������ķ�ʽ���֣����Ҫ���������ṩ���ݵ�ʱ����Ҫ�������ע����������Ϣ��Android  SDK�Ƽ��ķ����ǣ����ṩ���ݱ��ֶ��а���һ��ID���ڴ�����ʱINTEGER PRIMARY KEY AUTOINCREMENT��ʶ��ID�ֶ�
 
ContentProvider �������֯���ݵģ�
��֯������Ҫ�������洢���ݣ���ȡ���ݣ������ݿ�ķ�ʽ��¶���ݡ����ݵĴ洢��Ҫ������Ƶ�����ѡ����ʵĴ洢�ṹ����ѡ���ݿ⣬��ȻҲ����ѡ�񱾵������ļ������������������ϵ����ݡ����ݵĶ�ȡ�������ݿ�ķ�ʽ��¶�������Ҫ��������������δ洢�ģ����������������ݵķ�ʽ���ʡ�
���ܻ���2�����⣬����Ҫ��ע�ġ�
ContentProvider��ʲôʱ�򴴽��ģ���˭�����ģ�����ĳ��Ӧ�ó���������ݣ��Ƿ���Ҫ�������Ӧ�ó������������Android SDK��û����ȷ˵�������Ǵ����ݹ���ĽǶȳ�����ContentProviderӦ����Android��ϵͳ����ʱ�ʹ����ˣ������̸�������ݹ����ˡ����Ҫ����AndroidManifest.XML��ʹ��Ԫ����ȷ���塣
���ܻ��ж������ͬʱͨ��ContentResolver����һ��ContentProvider���᲻�ᵼ�������ݿ������ġ������ݡ����������һ������Ҫ���ݿ���ʵ�ͬ��������������д���ͬ������AndroidManifest.XML�ж���ContentProvider��ʱ����Ҫ������Ԫ��multiprocess���Ե�ֵ������һ����Android��ContentResolver���ṩ��notifyChange()�ӿڣ������ݸı�ʱ��֪ͨ����ContentObserver������ط�Ӧ��ʹ���˹۲���ģʽ����ContentResolver��Ӧ����һЩ����register��unregister�Ľӿڡ�
Android�����ʵ��Ӧ�ó���֮�����ݹ���ģ�������ǰ̸�����ĳ������ͨ��ContentResolver�ӿڷ���ContentProvider�ṩ�����ݣ�����������̸����δ����Լ���ContentProvider��ʵ��Ӧ�ó���֮������ݹ���
һ��Ӧ�ó�����Դ����Լ������ݣ�������ݶԸ�Ӧ�ó�����˵��˽�еģ���������������Ҳ��֪����������� �洢�ģ�������ʹ�����ݿ⻹��ʹ���ļ�������ͨ�����ϻ�ã���Щһ�ж�����Ҫ����Ҫ����������ͨ����һ�ױ�׼��ͳһ�Ľӿں��������������ݴ򽻵����� �磺���(insert)��ɾ��(delete)����ѯ(query)���޸�(update)��
AndroidΪ�����ṩ��ContentProvider��ʵ�����ݵĹ���һ������������ñ�ĳ�����Բ����Լ������ݣ��Ͷ����Լ��� ContentProvider��Ȼ����AndroidManifest.xml��ע�ᣬ����application����ͨ����ȡ ContentResolverͨ��Uri��������һ��������ݡ�
Android�еĵ绰�������ݾ���ͨ��ContentProviderʵ�����ݹ���ģ�ϵͳ���кܶ��Ѿ����ڵĹ���Uri�����ǿ���ʹ��ContentResolverͨ��Uri��������ͬ������ݣ���Contacts.People.CONTENT_URI��
��ѯContent Provider�ķ�����������ContentResolver��query() �� Activity ����� managedQuery(),���߽��յĲ�������ͬ�����صĶ���Cursor ����Ψһ��ͬ���� ʹ��managedQuery ����������Activity ������ Cursor ���������ڡ� 
�������Cursor ���� Activity������ͣ״̬��ʱ������Լ��� deactivate ��������ж�أ�����Activity�ص�����״̬ʱ������Լ���requery �������²�ѯ���ɵ�Cursor�������һ��δ�������Cursor�����뱻Activity�������Ե���Activity�� startManagingCursor������ʵ�֡�
ContentProvider ʲô��URI��
�����ΪA��B��C��D 4�����֣�
A����׼ǰ׺������˵��һ��Content Provider������Щ���ݣ��޷��ı�ģ�"content://"
B��URI�ı�ʶ�������������ĸ�Content Provider�ṩ��Щ���ݡ����ڵ�����Ӧ�ó���Ϊ�˱�֤URI��ʶ��Ψһ�ԣ���      ������һ�������ġ�Сд�� �����������ʶ�� Ԫ�ص� authorities������˵����һ���Ƕ����ContentProvider�İ�.      ������� ;"content://com.android.calendar" ��ϵͳ������URI��
C��·����URI�µ�ĳһ��Item��������վһ��������ҳ�°����ܶ�С��ҳ������ͨ�׵Ľ�������Ҫ���������ݿ��б����      �֣�������Ҳ�����Լ����壬�ǵ���ʹ�õ�ʱ�򱣳�һ�¾�ok�ˣ�"content://com.android.calendar/calendars"
D�����URI�а�����ʾ��Ҫ��ȡ�ļ�¼��ID����ͷ��ظ�id��Ӧ�����ݣ����û��ID���ͱ�ʾ����ȫ����
"content://com.android.calendar/calendars/#" #��ʾ����id��#�����������֣� "content://com.android.calendar/calendars/*" *��ƥ�������ı�
UriMatcher������ƥ��Uri�������÷����£�
1.���Ȱ�����Ҫƥ��Uri·��ȫ����ע���ϡ�
//1.����UriMatcher.NO_MATCH��ʾ��ƥ���κ�·���ķ�����(-1)��
UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//2.���match()����ƥcontent://com.android.calendar/calendars·��������ƥ����Ϊ1
uriMatcher.addURI(��content://com.android.calendar��, ��calendars��, 1);
//3.�����Ҫƥ��uri�����ƥ��ͻ᷵��ƥ���� //���match()����ƥ�� 
content://com.android.calendar/calendars/23·��������ƥ����Ϊ2 uriMatcher.addURI(��content://com.android.calendar��, ��calendars/#��, 2);

2.ע������Ҫƥ���Uri�󣬾Ϳ���ʹ��uriMatcher.match(uri)�����������Uri����ƥ�䣬���ƥ ��ͷ���ƥ���룬ƥ�����ǵ��� addURI()��������ĵ���������������ƥ�� 
content://com.android.calendar/calendars·�������ص�ƥ����Ϊ1��
ContentUris�����ڻ�ȡUri·�������ID���֣����������Ƚ�ʵ�õķ�����
withAppendedId(uri, id)����Ϊ·������ID����
parseId(uri)�������ڴ�·���л�ȡID����
������һ�����ӵļ�˵����
Xml���� 
Java���� �ղش���
    private static final String URI_AUTHORITY = "com.calendarwidget.provider";  
    public static final String URI_PATH2 = "RecordSet/#";//ֻ����䣬û������  
    public static final Uri CONTENT_URI = Uri.parse("content://"  
    private static final UriMatcher sMatcher;  
    public static final int ALL_EVENT_RECORDS = 0;  
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);  
        sMatcher.addURI(URI_AUTHORITY, URI_PATH, ALL_EVENT_RECORDS);  
        sMatcher.addURI(URI_AUTHORITY, URI_PATH2, ALL_EVENT_RECORDS);  
    public boolean onCreate()  
        if (mContext == null)  
            mContext = getContext();  
    }  
    @Override  
    public Cursor query(Uri uri, String[] projection, String selection,  
        //ƥ����  
                int match = sMatcher.match(uri);  
        switch (match)  
            case ALL_EVENT_RECORDS:  
                cur = loadAllCalendarEvent(this);  
            default:  
break;  
return cur;  
private MatrixCursor loadAllCalendarEvent(CalendarProvider calendarProvider)  
MatrixCursor mc = new MatrixCursor(CalendarConstants.PROJECTION);  
calendarCursor = calendarProvider  
.getContentResolver().query("content://com.android.calendar/calendars",  
while (calendarCursor.moveToNext())  
//TODO  
.....  
 
mc.addRow(rowObject);  
} finally  
calendarCursor.close();  
@Override  
public String getType(Uri uri)  
 
}  
 
@Override  
 
public Uri insert(Uri uri, ContentValues values)  
 
@Override  
 
public int delete(Uri uri, String selection, String[] selectionArgs)  
 
public int update(Uri uri, ContentValues values, String selection,  
 
} 
 
} 
public class CalendarProvider extends ContentProvider
public static final String URI_PATH = "RecordSet"; //ֻ����䣬û������  

{  
}  
private Context mContext;  
{  
}  
String[] selectionArgs, String sortOrder)  
break;  
}  
{  
Cursor calendarCursor = null;  
 .getContext()  
 null, null,   
null, null); /  
}  
return mc;  
}  
{  
return null;  
return null;  
{  
}  
@Override  
String[] selectionArgs)  
return 0;  
{  
 
 
 
+ URI_AUTHORITY + "/" + URI_PATH);  
 
static 
       
 
@Override 
{  
return true;  
 
{  
Cursor cur = null;  
{  
 
}  
try 
{  
{  
 
{  
}  
 
  
{  
 }  
       
 return 0;  
       
 {  
����getTypeʹ����ʾ�� 
            <category android:name="android.intent.category.DEFAULT">
            <data android:mimetype="vnd.android.cursor.item/vnd.google.note">
</data></category></action></action></action>
�҂������׿���action��category�Ǻ�����ƥ��ģ����҂�����Uri�Ĕ������Nƥ���أ��@�rϵ�y�͕�ȥ�{���㶨�x��ContentProvider�е�getType��ȡ�����P�ķ���ֵ��������data���M��ƥ�䣬��ȻgetType�ķ��ؽY��������Ҫ�Լ�ȥ���x�ġ�

���ڳ�������Ҳ�����Լ�֪��data����ͣ���ֱ��ƥ���ˣ�intent.setType(type);
