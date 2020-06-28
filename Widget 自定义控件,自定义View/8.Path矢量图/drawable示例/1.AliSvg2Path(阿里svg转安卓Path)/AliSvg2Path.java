package com.actor.alisvg2path;

import java.util.Scanner;

/**
 * 阿里http://www.iconfont.cn网站下载的svg矢量图转换成Android的Path
 * @author actor
 * 
//			命令类型	使用描述	代表含义					举例说明
//			移动指令	M x,y		M移动绝对位置				M 100,240
//			移动指令	m x,y		m移动相对于上一个点		m 100,240
//			绘制指令	L 或 l		从当前点绘制直线到指定点	L 100,100
//			绘制指令	H 或 h		水平直线					h 100
//			绘制指令	V 或 v		垂直直线					v 100
//			绘制指令	C 或 c		三次方程式贝塞尔曲线		C 100,200 200,400 300,200
//			绘制指令	Q 或 q		二次方程式贝塞尔曲线		Q 100,200 300,200
//			绘制指令	S 或 s		平滑三次方程式贝塞尔曲线	S 100,200 200,400 300,200
//			绘制指令	T 或 t		平滑二次方程式贝塞尔曲线	T 100,200 300,200
//			绘制指令	A 或 a		椭圆						A 5,5 0 0 1 10,10
//			关闭指令	Z 或 z		将图形的首、尾点用直线连接	Z
//			填充规则	F0			EvenOdd 填充规则	
//			填充规则	F1	
 */
public class AliSvg2Path {

	public static void main(String[] args) {
		Scanner sc;
		System.out.println("本程序作用:阿里http://www.iconfont.cn网站下载的svg矢量图转换成Android的Path.");
		System.out.println("其实不转也可以直接用,但是宽高很有肯能不适配导致看不到效果图,所以本程序只起格式化的作用.\n");
		int type;
		while (true) {
			System.out.println("请选择path来源:");
			System.out.println("①.来自.svg文件,输入:1");
			System.out.println("②.来自已经导入到AndroidStudio的path,输入:2");
			sc = new Scanner(System.in);
			if (sc.hasNextInt()) {
				type = sc.nextInt();
				if (type == 1 || type == 2) break;
			}
		}
		String source;
		while(true) {
			System.out.println(type == 1 ? "请输入.svg文件中<path>标签中d的值:" : "请输入AndroidStudio中<vertor矢量图xml中<path>标签里pathData的值:");
			sc = new Scanner(System.in);
			source = sc.nextLine();
			if (source != null && source.length() > 3) {
				break;
			} else System.out.println("请输入正确的path路径!");
		}
		String result = addEnterIgnoreCase(source, "m", "l", "h", "v", "c", "q", "s", "t", "a");//添加回车
		if (type == 1) {
			result = result.replaceAll(" ", ",").replaceAll("-", ",-");//空格 & ,
			result = deleteComma(result, "m", "l", "h", "v", "c", "q", "s", "t", "a");//去掉逗号,
			String[] rows = result.split("\n");//切割回车[\r\n|\n]
			String returnResult = "";
			for (int i = 0; i < rows.length; i++) {
				String row = rows[i];//每一行
				String[] commas = rows[i].split(",");
				row = "";
				for (int j = 0; j < commas.length; j++) {
					row += commas[j];
					if (j != commas.length - 1) {
						if (j % 2 == 0) {
							row += ",";
						} else row += " ";
					}
				}
				returnResult += row;
				if (i != rows.length - 1) returnResult += "\n";
			}
			result = returnResult;
		}
		
		System.out.println("\n输出结果:");
		System.out.println(result);
	}
	
	//大/小写字母前添加回车
	public static String addEnterIgnoreCase(String source, String... regex) {
		for (int i = 0; i < regex.length; i++) {
			source = source.replaceAll(regex[i].toLowerCase(), "\n" + regex[i].toLowerCase())
					.replaceAll(regex[i].toUpperCase(), "\n" + regex[i].toUpperCase());
		}
		return source;
	}
	
	//去掉字母后面的逗号,
	public static String deleteComma(String source, String... letter) {
		for (int i = 0; i < letter.length; i++) {
			source = source.replaceAll(letter[i].toLowerCase() + ",", letter[i].toLowerCase())
					.replaceAll(letter[i].toUpperCase() + ",", letter[i].toUpperCase());
		}
		return source;
	}
}
