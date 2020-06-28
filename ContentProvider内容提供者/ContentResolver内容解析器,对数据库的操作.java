在Android 应用程序之间数据共享—-ContentResolver中，已经说明了Android是如何实现应用程序之间数据共享的，并详细解析了如何获取其他应用程序共享的数据。ContentProviders存储和检索数据，通过它可以让所有的应用程序访问到，这也是应用程序之间唯一共享数据的方法。那么如何将应用程序的数据暴露出去？

通过以前文章的学习，知道ContentResolver是通过ContentProvider来获取其他与应用程序共享的数据，那么ContentResolver与ContentProvider的接口应该差不多的。其中ContentProvider负责组织应用程序的数据；向其他应用程序提供数据；ContentResolver则负责获取ContentProvider提供的数据；修改/添加/删除更新数据等；
ContentProvider 是如何向外界提供数据的？
Android提供了ContentProvider，一个程序可以通过实现一个ContentProvider的抽象接口将自己的数据完全暴露出去，而且ContentProviders是以类似数据库中表的方式将数据暴露，也就是说ContentProvider就像一个“数据库”。那么外界获取其提供的数据，也就应该与从数据库中获取数据的操作基本一样，只不过是采用URI来表示外界需要访问的“数据库”。至于如何从URI中识别出外界需要的是哪个“数据库”，这就是Android底层需要做的事情了，不在此详细说。简要分析下ContentProvider向外界提供数据操作的接口：
query(Uri, String[], String, String[], String)
insert(Uri, ContentValues)
update(Uri, ContentValues, String, String[])
delete(Uri, String, String[])

这些操作与数据库的操作基本上完全一样，在此不详细说，需要特殊说明的地方是URI：
在URI的D部分可能包含一个_ID ，这个应该出现在SQL语句中的，可以以种特殊的方式出现，这就要求我们在提供数据的时候，需要来额外关注这个特殊的信息。Android  SDK推荐的方法是：在提供数据表字段中包含一个ID，在创建表时INTEGER PRIMARY KEY AUTOINCREMENT标识此ID字段
 
ContentProvider 是如何组织数据的？
组织数据主要包括：存储数据，读取数据，以数据库的方式暴露数据。数据的存储需要根据设计的需求，选择合适的存储结构，首选数据库，当然也可以选择本地其他文件，甚至可以是网络上的数据。数据的读取，以数据库的方式暴露数据这就要求，无论数据是如何存储的，数据最后必须以数据的方式访问。
可能还有2个问题，是需要关注的。
ContentProvider是什么时候创建的，是谁创建的？访问某个应用程序共享的数据，是否需要启动这个应用程序？这个问题在Android SDK中没有明确说明，但是从数据共享的角度出发，ContentProvider应该是Android在系统启动时就创建了，否则就谈不上数据共享了。这就要求在AndroidManifest.XML中使用元素明确定义。
可能会有多个程序同时通过ContentResolver访问一个ContentProvider，会不会导致像数据库那样的“脏数据”？这个问题一方面需要数据库访问的同步，尤其是数据写入的同步，在AndroidManifest.XML中定义ContentProvider的时候，需要考虑是元素multiprocess属性的值；另外一方面Android在ContentResolver中提供了notifyChange()接口，在数据改变时会通知其他ContentObserver，这个地方应该使用了观察者模式，在ContentResolver中应该有一些类似register，unregister的接口。
Android是如何实现应用程序之间数据共享的？我们以前谈到外界的程序可以通过ContentResolver接口访问ContentProvider提供的数据，今天我们来谈下如何创建自己的ContentProvider来实现应用程序之间的数据共享。
一个应用程序可以创建自己的数据，这个数据对该应用程序来说是私有的，外界更本看不到，也不知道数据是如何 存储的，或者是使用数据库还是使用文件，还是通过网上获得，这些一切都不重要，重要的是外界可以通过这一套标准及统一的接口和这个程序里的数据打交道，例 如：添加(insert)、删除(delete)、查询(query)、修改(update)。
Android为我们提供了ContentProvider来实现数据的共享，一个程序如果想让别的程序可以操作自己的数据，就定义自己的 ContentProvider，然后在AndroidManifest.xml中注册，其他application可以通过获取 ContentResolver通过Uri来操作上一程序的数据。
Android中的电话本等数据就是通过ContentProvider实现数据共享的，系统中有很多已经存在的共享Uri。我们可以使用ContentResolver通过Uri来操作不同表的数据；如Contacts.People.CONTENT_URI。
查询Content Provider的方法有两个：ContentResolver的query() 和 Activity 对象的 managedQuery(),二者接收的参数均相同，返回的都是Cursor 对象，唯一不同的是 使用managedQuery 方法可以让Activity 来管理 Cursor 的生命周期。 
被管理的Cursor 会在 Activity进入暂停状态的时候调用自己的 deactivate 方法自行卸载，而在Activity回到运行状态时会调用自己的requery 方法重新查询生成的Cursor对象。如果一个未被管理的Cursor对象想被Activity管理，可以调用Activity的 startManagingCursor方法来实现。
ContentProvider 什么是URI？
将其分为A，B，C，D 4个部分：
A：标准前缀，用来说明一个Content Provider控制这些数据，无法改变的；"content://"
B：URI的标识，它定义了是哪个Content Provider提供这些数据。对于第三方应用程序，为了保证URI标识的唯一性，它      必须是一个完整的、小写的 类名。这个标识在 元素的 authorities属性中说明：一般是定义该ContentProvider的包.      类的名称 ;"content://com.android.calendar" （系统日历的URI）
C：路径，URI下的某一个Item，就像网站一样，主网页下包含很多小网页。这里通俗的讲就是你要操作的数据库中表的名      字，或者你也可以自己定义，记得在使用的时候保持一致就ok了；"content://com.android.calendar/calendars"
D：如果URI中包含表示需要获取的记录的ID；则就返回该id对应的数据，如果没有ID，就表示返回全部；
"content://com.android.calendar/calendars/#" #表示数据id（#代表任意数字） "content://com.android.calendar/calendars/*" *来匹配任意文本
UriMatcher：用于匹配Uri，它的用法如下：
1.首先把你需要匹配Uri路径全部给注册上。
//1.常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码(-1)。
UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//2.如果match()方法匹content://com.android.calendar/calendars路径，返回匹配码为1
uriMatcher.addURI(“content://com.android.calendar”, “calendars”, 1);
//3.添加需要匹配uri，如果匹配就会返回匹配码 //如果match()方法匹配 
content://com.android.calendar/calendars/23路径，返回匹配码为2 uriMatcher.addURI(“content://com.android.calendar”, “calendars/#”, 2);

2.注册完需要匹配的Uri后，就可以使用uriMatcher.match(uri)方法对输入的Uri进行匹配，如果匹 配就返回匹配码，匹配码是调用 addURI()方法传入的第三个参数，假设匹配 
content://com.android.calendar/calendars路径，返回的匹配码为1。
ContentUris：用于获取Uri路径后面的ID部分，它有两个比较实用的方法：
withAppendedId(uri, id)用于为路径加上ID部分
parseId(uri)方法用于从路径中获取ID部分
以下是一个例子的简单说明：
Xml代码 
Java代码 收藏代码
    private static final String URI_AUTHORITY = "com.calendarwidget.provider";  
    public static final String URI_PATH2 = "RecordSet/#";//只是填充，没有作用  
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
        //匹配码  
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
public static final String URI_PATH = "RecordSet"; //只是填充，没有作用  

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
关于getType使用提示： 
            <category android:name="android.intent.category.DEFAULT">
            <data android:mimetype="vnd.android.cursor.item/vnd.google.note">
</data></category></action></action></action>
我們很容易看出action和category是很容易匹配的，而我們傳的Uri的數據怎麼匹配呢，這時系統就會去調用你定義的ContentProvider中的getType，取得相關的返回值來和上面的data串進行匹配，當然getType的返回結果你是需要自己去定義的。

但在程序中你也可以自己知道data的類型，就直接匹配了：intent.setType(type);
