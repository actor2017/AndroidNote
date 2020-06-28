###27.16_JDK5新特性(枚举类的常见方法)

MyEnum myEnum = Enum.valueOf(MyEnum.class, "get");//Enum 静态方法

//子类静态方法
MyEnum myEnum = MyEnum.valueOf("post"); //根据值获取 Enum
MyEnum[] values = MyEnum.values();      //获取所有枚举类型

//普通方法
String name = myEnum.name();            //获取名称, "get", "post"
int compare = myEnum.compareTo(myEnum2);//比较
int ordinal = myEnum.ordinal();         //顺序, 枚举项都是有编号的
String string = myEnum.toString();      //和.name 返回一样的值


* A:枚举类的常见方法
	* int ordinal()
	* int compareTo(E o)
	* String name()
	* String toString()
	* <T> T valueOf(Class<T> type,String name)
	* values() 
	* 此方法虽然在JDK文档中查找不到，但每个枚举类都具有该方法，它遍历枚举类的所有枚举值非常方便
* B:案例演示
	* 枚举类的常见方法
package com.heima.枚举2;

public class Demo1_Enum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//demo1();
		//demo2();
		//demo3();
		Week3 mon = Week3.TUE;
		switch (mon) {
		case MON:
			System.out.println("星期一");
			break;
		case TUE:
			System.out.println("星期二");
			break;
		}
	}

	public static void demo3() {
		Week3 mon = Week3.MON;
		mon.show();
	}

	public static void demo2() {
		Week2 mon = Week2.MON;
		System.out.println(mon.getName());
	}

	public static void demo1() {
		Week mon = Week.MON;
		System.out.println(mon);
	}

}
//--------------------------------------------------------
package com.heima.枚举2;

public class Demo2_Enum {

	/**
	 * int ordinal()
	* int compareTo(E o)
	* String name()
	* String toString()
	* <T> T valueOf(Class<T> type,String name)
	* values() 
	* 此方法虽然在JDK文档中查找不到，但每个枚举类都具有该方法，它遍历枚举类的所有枚举值非常方便
	 */
	public static void main(String[] args) {
		//demo1();
//		Week2 mon = Week2.valueOf(Week2.class, "MON");		//通过字节码对象获取枚举项
//		System.out.println(mon);
		
		Week2[] arr = Week2.values();
		for (Week2 week2 : arr) {
			System.out.println(week2);
		}
	}

	public static void demo1() {
		Week2 mon = Week2.MON;
		Week2 tue = Week2.TUE;
		Week2 wed = Week2.WED;
		
		/*System.out.println(mon.ordinal());				//枚举项都是有编号的
		System.out.println(tue.ordinal());
		System.out.println(wed.ordinal());
		
		System.out.println(mon.compareTo(tue));			//比较的是编号
		System.out.println(mon.compareTo(wed));*/
		
		System.out.println(mon.name()); 				//获取实例名称
		System.out.println(mon.toString()); 			//调用重写之后的toString方法
	}

}
//-------------------------------------------
package com.heima.枚举2;

public enum Week {
	MON,TUE,WED;
}
//----------------------------------------------
package com.heima.枚举2;

public enum Week2 {
	MON("星期一"),TUE("星期二"),WED("星期三");
	
	private String name;
	private Week2(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
}
//----------------------------------------------------
package com.heima.枚举2;

public enum Week3 {
	MON("星期一"){
		public void show() {
			System.out.println("星期一");
		}
	},TUE("星期二"){
		public void show() {
			System.out.println("星期二");
		}
	},WED("星期三"){
		public void show() {
			System.out.println("星期三");
		}
	};
	
	private String name;
	private Week3(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public abstract void show();
	
}
