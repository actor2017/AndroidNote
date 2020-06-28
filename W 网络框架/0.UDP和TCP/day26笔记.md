day26授课目录：

##26.01_网络编程(网络编程概述)(了解)
* A:计算机网络
	* 是指将地理位置不同的具有独立功能的多台计算机及其外部设备，通过通信线路连接起来，在网络操作系统，网络管理软件及网络通信协议的管理和协调下，实现资源共享和信息传递的计算机系统。
* B:网络编程
	* 就是用来实现网络互连的不同计算机上运行的程序间可以进行数据交换。


##26.02_网络编程(网络编程三要素之IP概述)(掌握)
* 每个设备在网络中的唯一标识
* 每台网络终端在网络中都有一个独立的地址，我们在网络中传输数据就是使用这个地址。 
* ipconfig：查看本机IP`192.168.12.42`
* ping：测试连接`192.168.40.62`
* 本地回路地址：`127.0.0.1`, `255.255.255.255`是广播地址
* IPv4：4个字节组成，4个0-255。大概42亿，30亿都在北美，亚洲4亿。2011年初已经用尽。 
* IPv6：8组，每组4个16进制数。
* 1a2b:0000:aaaa:0000:0000:0000:aabb:1f2f
* 1a2b::aaaa:0000:0000:0000:aabb:1f2f
* 1a2b:0000:aaaa::aabb:1f2f
* 1a2b:0000:aaaa::0000:aabb:1f2f
* 1a2b:0000:aaaa:0000::aabb:1f2f

##26.03_网络编程(网络编程三要素之端口号概述)(掌握)
* 每个程序在设备上的唯一标识
* 每个网络程序都需要绑定一个端口号，传输数据的时候除了确定发到哪台机器上，还要明确发到哪个程序。
* 端口号范围从`0-65535`
* 编写网络应用就需要绑定一个端口号，尽量使用`1024以上`的，1024以下的基本上都被系统程序占用了。
* 常用端口
	* mysql: 3306
	* oracle: 1521
	* web: 80
	* tomcat: 8080
	* QQ: 4000
	* feiQ: 2425

##26.04_网络编程(网络编程三要素协议)(掌握)
* 为计算机网络中进行数据交换而建立的规则、标准或约定的集合。
* UDP
	* 面向无连接，数据不安全，速度快。不区分客户端与服务端。
* TCP
　　* 面向连接（三次握手），数据安全，速度略低。分为客户端和服务端。
	* 三次握手: 客户端先向服务端发起请求, 服务端响应请求, 传输数据
	* Http基于TCP


##26.05_网络编程(Socket通信原理图解)(了解)
* A:Socket套接字概述：
	* 网络上具有唯一标识的IP地址和端口号组合在一起才能构成唯一能识别的标识符套接字。
	* 通信的两端都有Socket。
	* 网络通信其实就是Socket间的通信。
	* 数据在两个Socket间通过IO流传输。
	* Socket在应用程序中创建，通过一种绑定机制与驱动程序建立关系，告诉自己所对应的IP和port。

###26.06_网络编程(UDP传输,`传输数据有大小显示,不能超过64K`)(了解)
* 1.发送Send
	* 创建DatagramSocket, 随机端口号
	* 创建DatagramPacket, 指定数据, 长度, 地址, 端口
	* 使用DatagramSocket发送DatagramPacket
	* 关闭DatagramSocket
* 2.接收Receive
	* 创建DatagramSocket, 指定端口号
	* 创建DatagramPacket, 指定数组, 长度
	* 使用DatagramSocket接收DatagramPacket
	* 关闭DatagramSocket
	* 从DatagramPacket中获取数据
* 3.接收方获取ip和端口号
	* String ip = packet.getAddress().getHostAddress();
	* int port = packet.getPort();

###26.07_网络编程(UDP传输优化)
* 接收端Receive
* 
		DatagramSocket socket = new DatagramSocket(6666);						//创建socket相当于创建码头
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);		//创建packet相当于创建集装箱
		
		while(true) {
			socket.receive(packet);												//接收货物
			byte[] arr = packet.getData();
			int len = packet.getLength();
			String ip = packet.getAddress().getHostAddress();
			System.out.println(ip + ":" + new String(arr,0,len));
		}
* 发送端Send

		DatagramSocket socket = new DatagramSocket();		//创建socket相当于创建码头
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			String str = sc.nextLine();
			if("quit".equals(str))
				break;
			DatagramPacket packet = 							//创建packet相当于创建集装箱
					new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getByName("127.0.0.1"), 6666);
			socket.send(packet);			//发货
		}
		socket.close();
###26.08_网络编程(UDP传输多线程)
* A发送和接收在一个窗口完成

		public class Demo3_MoreThread {

			/**
			 * @param args
			 */
			public static void main(String[] args) {
				new Receive().start();
				
				new Send().start();
			}
		
		}

		class Receive extends Thread {
			public void run() {
				try {
					DatagramSocket socket = new DatagramSocket(6666);					//创建socket相当于创建码头
					DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);	//创建packet相当于创建集装箱
					
					while(true) {
						socket.receive(packet);												//接收货物
						byte[] arr = packet.getData();
						int len = packet.getLength();
						String ip = packet.getAddress().getHostAddress();
						System.out.println(ip + ":" + new String(arr,0,len));
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

		class Send extends Thread {
			public void run() {
				try {
					DatagramSocket socket = new DatagramSocket();		//创建socket相当于创建码头
					Scanner sc = new Scanner(System.in);
					
					while(true) {
						String str = sc.nextLine();
						if("quit".equals(str))
							break;
						DatagramPacket packet = 							//创建packet相当于创建集装箱
								new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getByName("127.0.0.1"), 6666);
						socket.send(packet);			//发货
					}
					socket.close();
				}  catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}


###26.09_网络编程(UDP聊天图形化界面)
	

###26.10_网络编程(UDP聊天发送功能)
	
		
###26.11_网络编程(UDP聊天记录功能)
	
	
###26.12_网络编程(UDP聊天清屏功能)


###26.13_网络编程(UDP聊天震动功能)


###26.14_网络编程(UDP聊天快捷键和代码优化)
	

###26.15_网络编程(UDP聊天生成jar文件)

###26.16_网络编程(TCP协议)(掌握)
* 1.客户端
	* 创建Socket连接服务端(指定ip地址,端口号)通过ip地址找对应的服务器
	* 调用Socket的getInputStream()和getOutputStream()方法获取和服务端相连的IO流
	* 输入流可以读取服务端输出流写出的数据
	* 输出流可以写出数据到服务端的输入流
* 2.服务端
	* 创建ServerSocket(需要指定端口号)
	* 调用ServerSocket的accept()方法接收一个客户端请求，得到一个Socket
	* 调用Socket的getInputStream()和getOutputStream()方法获取和客户端相连的IO流
	* 输入流可以读取客户端输出流写出的数据
	* 输出流可以写出数据到客户端的输入流

###26.17_网络编程(TCP协议代码优化)
* 客户端

		Socket socket = new Socket("127.0.0.1", 9999);		//创建Socket指定ip地址和端口号
		InputStream is = socket.getInputStream();			//获取输入流
		OutputStream os = socket.getOutputStream();			//获取输出流
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintStream ps = new PrintStream(os);
		
		System.out.println(br.readLine());
		ps.println("我想报名就业班");
		System.out.println(br.readLine());
		ps.println("爷不学了");
		socket.close();
* 服务端

		ServerSocket server = new ServerSocket(9999);	//创建服务器
		Socket socket = server.accept();				//接受客户端的请求
		InputStream is = socket.getInputStream();		//获取输入流
		OutputStream os = socket.getOutputStream();		//获取输出流
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintStream ps = new PrintStream(os);
		
		ps.println("欢迎咨询传智播客");
		System.out.println(br.readLine());
		ps.println("报满了,请报下一期吧");
		System.out.println(br.readLine());
		server.close();
		socket.close();

###26.18_网络编程(服务端是多线程的)(掌握)
	ServerSocket server = new ServerSocket(9999);	//创建服务器
		while(true) {
			final Socket socket = server.accept();				//接受客户端的请求
			new Thread() {
				public void run() {
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintStream ps = new PrintStream(socket.getOutputStream());
						ps.println("欢迎咨询传智播客");
						System.out.println(br.readLine());
						ps.println("报满了,请报下一期吧");
						System.out.println(br.readLine());
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

###26.19_网络编程(练习)
* 客户端向服务器写字符串(键盘录入),服务器(多线程)将字符串反转后写回,客户端再次读取到是反转后的字符串
###26.20_网络编程(练习)
* 客户端向服务器上传文件
###26.21_day26总结
* 把今天的知识点总结一遍。