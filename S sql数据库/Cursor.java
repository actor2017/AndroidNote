android中数据库处理使用cursor时，游标不是放在为0的下标，而是放在为-1的下标处开始的,
如果处理不当, 会报错: CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1

//cursor.getType
Cursor.FIELD_TYPE_BLOB;//4 字节数组
Cursor.FIELD_TYPE_FLOAT;//2 float
Cursor.FIELD_TYPE_INTEGER;//1
Cursor.FIELD_TYPE_NULL;//0
Cursor.FIELD_TYPE_STRING;//3

cursor.close();//关闭游标，释放资源
cursor.copyStringToBuffer(1, (CharArrayBuffer) null);//在缓冲区中检索请求的列的文本，将将其存储
cursor.deactivate();//过时
byte[] blob = cursor.getBlob(1);//该列的值作为字节数组, 如果不是字节数组会抛异常
int columnCount = cursor.getColumnCount();//返回所有列的总数
int columnName = cursor.getColumnIndex("columnName");//返回指定名称的列，如果不存在返回-1
int columnName1 = cursor.getColumnIndexOrThrow("columnName");//返回指定名称的列，如果不存在抛出 IllegalArgumentException 异常
String columnName2 = cursor.getColumnName(1);//从给定的索引返回列名
String[] columnNames = cursor.getColumnNames();//返回一个字符串数组的列名
int count = cursor.getCount();//返回Cursor 中的行数(多少条数据)
double aDouble = cursor.getDouble(1);
Bundle extras = cursor.getExtras();
float aFloat = cursor.getFloat(1);
int anInt = cursor.getInt(1);
long aLong = cursor.getLong(1);
Uri notificationUri = cursor.getNotificationUri();
int position = cursor.getPosition();//返回当前游标所指向的行数
short aShort = cursor.getShort(1);
String string = cursor.getString(1);
int type = cursor.getType(1);//返回该列数据类型
boolean wantsAllOnMoveCalls = cursor.getWantsAllOnMoveCalls();//跨进程调用onMove()?
boolean afterLast = cursor.isAfterLast();//返回光标是否指向最后一个位置之后的位置行
boolean beforeFirst = cursor.isBeforeFirst();//返回光标是否指向第一个之前的位置行
boolean closed = cursor.isClosed();//游标是否关闭
boolean first = cursor.isFirst();//是否指向第一条
boolean last = cursor.isLast();//是否指向最后一条
boolean aNull = cursor.isNull(1);//指定列是否为空(列基数:0 - columnIndex)
boolean move = cursor.move(1);//offset 以当前位置为参考, 移动指定行
boolean b = cursor.moveToFirst();//定位第一行
boolean b1 = cursor.moveToLast();//移动光标到最后一行
boolean b3 = cursor.moveToNext();//移动光标到下一行
boolean b2 = cursor.moveToPosition(1);//移动光标到一个绝对的位置
boolean b4 = cursor.moveToPrevious();//移动光标到上一行
cursor.registerContentObserver((ContentObserver) null);
cursor.registerDataSetObserver((DataSetObserver) null);
boolean requery = cursor.requery();//过时
Bundle respond = cursor.respond((Bundle) null);
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
	cursor.setExtras((Bundle) null);
}
cursor.setNotificationUri((ContentResolver) null, (Uri) null);
cursor.unregisterContentObserver((ContentObserver) null);
cursor.unregisterDataSetObserver((DataSetObserver) null);

//遍历1
for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//    cursor.get...
}

//遍历2
while (cursor.moveToNext()) {}

//遍历Cursor所有列的名称和类型
if (cursor != null) {
	if (cursor.moveToFirst()) {
		String[] types = {"Cursor.FIELD_TYPE_NULL",//0 字节数组
				"Cursor.FIELD_TYPE_INTEGER",//1
				"Cursor.FIELD_TYPE_FLOAT",//2 float
				"Cursor.FIELD_TYPE_STRING",//3
				"Cursor.FIELD_TYPE_BLOB"};//4
		int columnCount = cursor.getColumnCount();//一共多少列
		String[] columnNames = cursor.getColumnNames();//所有列的名称
		for (int i = 0; i < columnCount; i++) {
			int index = cursor.getColumnIndex(columnNames[i]);//列所在index
			int type = cursor.getType(index);//列的type
			LogUtils.formatError("columnIndex=%d: name=%s, type=%s", true, index, columnNames[i], types[type]);
		}
	}
}
//columnIndex=0: name=_id, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=1: name=local_filename, type=Cursor.FIELD_TYPE_NULL
//columnIndex=2: name=mediaprovider_uri, type=Cursor.FIELD_TYPE_NULL
//columnIndex=3: name=destination, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=4: name=title, type=Cursor.FIELD_TYPE_STRING
//columnIndex=5: name=description, type=Cursor.FIELD_TYPE_STRING
//columnIndex=6: name=uri, type=Cursor.FIELD_TYPE_STRING
//columnIndex=7: name=status, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=8: name=hint, type=Cursor.FIELD_TYPE_STRING
//columnIndex=9: name=media_type, type=Cursor.FIELD_TYPE_NULL
//columnIndex=10: name=total_size, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=11: name=last_modified_timestamp, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=12: name=bytes_so_far, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=13: name=allow_write, type=Cursor.FIELD_TYPE_INTEGER
//columnIndex=14: name=local_uri, type=Cursor.FIELD_TYPE_STRING
//columnIndex=15: name=reason, type=Cursor.FIELD_TYPE_STRING
