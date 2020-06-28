###27.16_JDK5������(ö����ĳ�������)

MyEnum myEnum = Enum.valueOf(MyEnum.class, "get");//Enum ��̬����

//���ྲ̬����
MyEnum myEnum = MyEnum.valueOf("post"); //����ֵ��ȡ Enum
MyEnum[] values = MyEnum.values();      //��ȡ����ö������

//��ͨ����
String name = myEnum.name();            //��ȡ����, "get", "post"
int compare = myEnum.compareTo(myEnum2);//�Ƚ�
int ordinal = myEnum.ordinal();         //˳��, ö������б�ŵ�
String string = myEnum.toString();      //��.name ����һ����ֵ


* A:ö����ĳ�������
	* int ordinal()
	* int compareTo(E o)
	* String name()
	* String toString()
	* <T> T valueOf(Class<T> type,String name)
	* values() 
	* �˷�����Ȼ��JDK�ĵ��в��Ҳ�������ÿ��ö���඼���и÷�����������ö���������ö��ֵ�ǳ�����
* B:������ʾ
	* ö����ĳ�������
package com.heima.ö��2;

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
			System.out.println("����һ");
			break;
		case TUE:
			System.out.println("���ڶ�");
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
package com.heima.ö��2;

public class Demo2_Enum {

	/**
	 * int ordinal()
	* int compareTo(E o)
	* String name()
	* String toString()
	* <T> T valueOf(Class<T> type,String name)
	* values() 
	* �˷�����Ȼ��JDK�ĵ��в��Ҳ�������ÿ��ö���඼���и÷�����������ö���������ö��ֵ�ǳ�����
	 */
	public static void main(String[] args) {
		//demo1();
//		Week2 mon = Week2.valueOf(Week2.class, "MON");		//ͨ���ֽ�������ȡö����
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
		
		/*System.out.println(mon.ordinal());				//ö������б�ŵ�
		System.out.println(tue.ordinal());
		System.out.println(wed.ordinal());
		
		System.out.println(mon.compareTo(tue));			//�Ƚϵ��Ǳ��
		System.out.println(mon.compareTo(wed));*/
		
		System.out.println(mon.name()); 				//��ȡʵ������
		System.out.println(mon.toString()); 			//������д֮���toString����
	}

}
//-------------------------------------------
package com.heima.ö��2;

public enum Week {
	MON,TUE,WED;
}
//----------------------------------------------
package com.heima.ö��2;

public enum Week2 {
	MON("����һ"),TUE("���ڶ�"),WED("������");
	
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
package com.heima.ö��2;

public enum Week3 {
	MON("����һ"){
		public void show() {
			System.out.println("����һ");
		}
	},TUE("���ڶ�"){
		public void show() {
			System.out.println("���ڶ�");
		}
	},WED("������"){
		public void show() {
			System.out.println("������");
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
