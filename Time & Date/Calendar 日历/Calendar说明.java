Calendar calendar = Calendar.getInstance();

Date date = calendar.getTime();

public int get(int field);//field字段,取值如下:
public final static int ERA = 0;//什么意思?
public final static int YEAR = 1;//Calendar.YEAR
public final static int MONTH = 2;
public final static int WEEK_OF_YEAR = 3;
public final static int WEEK_OF_MONTH = 4;
public final static int DATE = 5;
public final static int DAY_OF_MONTH = 5;
public final static int DAY_OF_YEAR = 6;
public final static int DAY_OF_WEEK = 7;
public final static int DAY_OF_WEEK_IN_MONTH = 8;
public final static int AM_PM = 9;
public final static int HOUR = 10;
public final static int HOUR_OF_DAY = 11;
public final static int MINUTE = 12;
public final static int SECOND = 13;
public final static int MILLISECOND = 14;
public final static int ZONE_OFFSET = 15;
public final static int DST_OFFSET = 16;什么意思?
public static final int ALL_STYLES = 0;什么意思?

//添加或减去指定的日历字段所指定的时间,例如，减去5天:(Calendar.DAY_OF_MONTH, -5)
public void add(int field, int amount);
public String toString();

public void set(int field, int value);//Calendar.YEAR, 1990
public final void set(int year, int month, int date);//设置时间
public final void set(int year, int month, int date, int hourOfDay, int minute);
public final void set(int year, int month, int date, int hourOfDay, int minute, int second);
public final void setTime(Date date);

public boolean before(Object when);//when instanceof Calendar
public boolean after(Object when);
public boolean equals(Object obj);
public int compareTo(Calendar anotherCalendar);//return (thisTime > t) ? 1 : (thisTime == t) ? 0 : -1;
public boolean isLenient();
public final boolean isSet(int field);
public boolean isWeekDateSupported();

public int getFirstDayOfWeek();
public long getTimeInMillis();//System.currentTimeMillis()
public TimeZone getTimeZone();//获取时区对象
public int getWeekYear();

abstract public void roll(int field, boolean up);//滚动
public void roll(int field, int amount);

abstract public int getGreatestMinimum(int field);
abstract public int getLeastMaximum(int field);
abstract public int getMaximum(int field);
abstract public int getMinimum(int field);
public int getMinimalDaysInFirstWeek();


public final void clear();//好像是清除,1970...,然后isSet()=false
public final void clear(int field);//清除指定字段(年/月日/时分/秒...)

public int getActualMaximum(int field);
public int getActualMinimum(int field);
public String getDisplayName(int field, int style, Locale locale);
public Map<String, Integer> getDisplayNames(int field, int style, Locale locale);

public Object clone();