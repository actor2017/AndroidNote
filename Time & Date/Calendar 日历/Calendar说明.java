Calendar calendar = Calendar.getInstance();

Date date = calendar.getTime();

public int get(int field);//field�ֶ�,ȡֵ����:
public final static int ERA = 0;//ʲô��˼?
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
public final static int DST_OFFSET = 16;ʲô��˼?
public static final int ALL_STYLES = 0;ʲô��˼?

//��ӻ��ȥָ���������ֶ���ָ����ʱ��,���磬��ȥ5��:(Calendar.DAY_OF_MONTH, -5)
public void add(int field, int amount);
public String toString();

public void set(int field, int value);//Calendar.YEAR, 1990
public final void set(int year, int month, int date);//����ʱ��
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
public TimeZone getTimeZone();//��ȡʱ������
public int getWeekYear();

abstract public void roll(int field, boolean up);//����
public void roll(int field, int amount);

abstract public int getGreatestMinimum(int field);
abstract public int getLeastMaximum(int field);
abstract public int getMaximum(int field);
abstract public int getMinimum(int field);
public int getMinimalDaysInFirstWeek();


public final void clear();//���������,1970...,Ȼ��isSet()=false
public final void clear(int field);//���ָ���ֶ�(��/����/ʱ��/��...)

public int getActualMaximum(int field);
public int getActualMinimum(int field);
public String getDisplayName(int field, int style, Locale locale);
public Map<String, Integer> getDisplayNames(int field, int style, Locale locale);

public Object clone();