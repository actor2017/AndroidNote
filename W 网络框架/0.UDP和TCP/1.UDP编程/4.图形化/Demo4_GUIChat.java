package com.actor.udpguichat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * TODO 可以随意换端口了,666啊
 * @author actor
 */
public class Demo4_GUIChat extends JFrame {//Frame窗体

//	private TextField textFieldIp;//输入ip
	private JTextField textFieldIp;
	private JTextField textFieldPort;//端口
	private JButton btnSend;
	private JButton btnRecode;
	private JButton btnClear;
	private JButton btnShake;
//	private Button btnSend;
//	private Button btnRecode;
//	private Button btnClear;
//	private Button btnShake;
//	private TextArea textAreaViewText;
//	private TextArea sendText;
	private JTextArea textAreaViewText;//聊天记录文本框
	private JTextArea textAreaSendText;//发送文本框
	private DatagramSocket sendSocket;//发送
	private DatagramSocket receiveSocket;//接收
	private BufferedWriter bufferedWriter;
	private ReceiveThread receiveThread;//接收消息的线程
	private int defaultPort = 9999;//默认端口

	public static void main(String[] args) {
		new Demo4_GUIChat();//相当于new Frame,创建了一个窗体
	}

	public Demo4_GUIChat() {
		init();
		event();//点击事件
	}

	public void init() {
		this.setLocation(500, 50);//设置位置
		this.setSize(600, 800);//设置大小
		this.setTitle("GUI聊天室(如果显示白板,请最大化再还原!)");
		this.setVisible(true);//设置显示
		try {
			sendSocket = new DatagramSocket();
//			receiveSocket = new DatagramSocket(defaultPort);
			receiveThread = new ReceiveThread();
			receiveThread.start();//打开接收
			bufferedWriter = new BufferedWriter(new FileWriter("config.txt",true));	//需要在尾部追加
		} catch (Exception e) {
			e.printStackTrace();
		}

		//创建南边/最下边的Panel,包含1个TextViele,4个Button
		JPanel south = new JPanel();
		textFieldIp = new JTextField(18);//columns:18列,宽度,不是指输入个数
		textFieldIp.setMinimumSize(new Dimension(18, 0));
		textFieldIp.setText("127.0.0.1");
		textFieldPort = new JTextField(4);
		textFieldPort.setText(String.valueOf(defaultPort));//端口
		btnShake = new JButton("震 动");
		btnRecode = new JButton("记 录");
		btnClear = new JButton("清 屏");
		btnSend = new JButton("发 送");
		btnSend.setToolTipText("Ctrl+Enter");//鼠标移上去就显示提示
//		btnShake = new Button("震 动");
//		btnRecode = new Button("记 录");
//		btnClear = new Button("清 屏");
//		btnSend = new Button("发 送");
		south.add(textFieldIp);
		south.add(textFieldPort);
		south.add(btnShake);
		south.add(btnRecode);
		south.add(btnClear);
		south.add(btnSend);
		this.add(south,BorderLayout.SOUTH);			//将Panel放在Frame的南边

		//创建中间的Panel,包含2个TextArea,分别有聊天内容&输入框
		JPanel center = new JPanel(new BorderLayout(), true);//设置为边界布局管理器,true表示双缓冲,使用更多内存空间实现快速、无闪烁的更新
		textAreaViewText = new JTextArea();
		textAreaViewText.setLineWrap(true);//自动换行
		textAreaViewText.setBackground(new Color(234, 236, 248));//Color.orange设置背景颜色
		textAreaViewText.setEditable(false);				//设置不可以编辑
		//设置字体:String name(随意写), int style(Font.PLAIN:普通样式), int size
		textAreaViewText.setFont(new Font("xxx", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(textAreaViewText);//滚动条
		textAreaSendText = new JTextArea(5,1);
		textAreaSendText.setLineWrap(true);
		textAreaSendText.setBackground(new Color(234, 236, 248));
		textAreaSendText.setBorder(BorderFactory.createEtchedBorder());//边框...
		textAreaSendText.setFont(new Font("xxx", Font.PLAIN, 15));
//		sendText.setMargin(new Insets(50, 0, 0, 0));//没用
		center.add(textAreaSendText,BorderLayout.SOUTH);	//发送的文本区域放在南边
		center.add(scrollPane,BorderLayout.CENTER);	//显示区域放在中间
		this.add(center,BorderLayout.CENTER);
	}

	public void event() {
		//关闭窗体
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					sendSocket.close();
					receiveSocket.close();
					bufferedWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		//发送
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					send();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		//聊天记录
		btnRecode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					logFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		//清屏
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textAreaViewText.setText("");
			}
		});

		//振动
		btnShake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					send(new byte[]{-1},textFieldIp.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		//Ctrl + Enter也可以发送
		textAreaSendText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {	//isControlDown ctrl是否被按下
//				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						send();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void shake() {
		int x = this.getLocation().x;							//获取横坐标位置
		int y = this.getLocation().y;							//获取纵坐标位置
		for(int i = 0; i < 20; i++) {
			try {
				this.setLocation(x + 20, y + 20);
				Thread.sleep(20);
				this.setLocation(x + 20, y - 20);
				Thread.sleep(20);
				this.setLocation(x - 20, y + 20);
				Thread.sleep(20);
				this.setLocation(x - 20, y - 20);
				Thread.sleep(20);
				this.setLocation(x, y);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//聊天记录
	private void logFile() throws IOException {
		bufferedWriter.flush();									//刷新缓冲区
		FileInputStream fis = new FileInputStream("config.txt");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();	//在内存中创建缓冲区
		int len;
		byte[] arr = new byte[8192];
		while((len = fis.read(arr)) != -1) {
			baos.write(arr, 0, len);
		}
		String str = baos.toString();				//将内存中的内容转换成了字符串
		textAreaViewText.setText(str);
		fis.close();
	}

	//发送数据&摇动窗口
	private boolean send(byte[] arr, String ip) throws IOException {
		String text = textFieldPort.getText();//端口
		int port;
		if (text == null || text.length() == 0) {
			int index = JOptionPane.showConfirmDialog(null, "未输入端口号,默认"+defaultPort+"?", "提示",JOptionPane.YES_NO_OPTION);
			if (index == 1) return false;//否
			port = defaultPort;
			textFieldPort.setText(String.valueOf(port));
		} else {
			try {
				port = Integer.parseInt(text);
			} catch (Exception e) {
				e.printStackTrace();
				int index = JOptionPane.showConfirmDialog(null, "端口号输入有误,默认"+defaultPort+"?", "提示",JOptionPane.YES_NO_OPTION);
				if (index == 1) return false;//否
				port = defaultPort;
				textFieldPort.setText(String.valueOf(port));
			}
		}
		if (port != defaultPort) {
			defaultPort = port;//重设端口
			receiveThread.stop();//中断线程
			receiveThread = new ReceiveThread();
			receiveThread.start();//重新开启
		}
		while (true) {
			if (receiveSocket.getLocalPort() == defaultPort) break;//★★★确保端口已经切换过来,否则会收不到端口切换后的第1条消息★★★
		}
		DatagramPacket packet = new DatagramPacket(arr, arr.length, InetAddress.getByName(ip), defaultPort);//创建集装箱
		sendSocket.send(packet);						//发送数据
		return true;
	}

	//发送
	private void send() throws IOException {
		String message = textAreaSendText.getText();		//获取发送区域的内容
		if (message == null || message.trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "不能发送空白消息", null, JOptionPane.ERROR_MESSAGE);//WARNING_MESSAGE...
			return;
		}
		String ip = textFieldIp.getText();					//获取ip地址;
		if (ip == null) ip = "";
		if (ip.trim().length() == 0) {
			int index = JOptionPane.showConfirmDialog(null, "确定发送给所有人吗?IP=255.255.255.255", "提示",JOptionPane.YES_NO_OPTION);
			if (index == 1) return;//否
			ip = "255.255.255.255";
			textFieldIp.setText(ip);
		}
		if (send(message.getBytes(), ip)) {//如果发送成功
			String str = " 我("+getIpMime()+")-->" + ip + (ip.equals("255.255.255.255") ? "所有人" : "") + "    " + getCurrentTime() + getEnter() + message + getEnter();//alt + shift + l 抽取局部变量
			textAreaViewText.append(str);						//将信息添加到显示区域中
			bufferedWriter.write(str);								//将信息写到数据库中
			textAreaSendText.setText("");
		}
	}

	//获取当前时间
	private String getCurrentTime() {
		Date d = new Date();						//创建当前日期对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(d);						//将时间格式化
	}
	
	//获取回车
	private String getEnter() {
		return System.getProperty("line.separator");
	}
	
	//获取本机ip
	private String getIpMime() {
		try {
			InetAddress host=InetAddress.getLocalHost();//获得本机的InetAddress对象 ，回送IP地址
			return host.getHostAddress();			//获得IP（以文本表现形式）
		} catch (IOException e) {
			e.printStackTrace();
			return "127.0.0.1";
		}
	}

	private class ReceiveThread extends Thread {			//接收和发送需要同时执行,所以定义成多线程的
		public void run() {
			try {
				if (receiveSocket != null) receiveSocket.close();//主要作用是取消绑定端口
				receiveSocket = new DatagramSocket(defaultPort);
				DatagramPacket packet = new DatagramPacket(new byte[8192], 8192);
				while(true) {
					receiveSocket.receive(packet);				//接收信息
					byte[] arr = packet.getData();		//获取字节数据
					int len = packet.getLength();		//获取有效的字节数据
					if(arr[0] == -1 && len == 1) {		//如果发过来的数组第一个存储的值是-1,并且数组长度是1
						shake();						//调用震动方法
						continue;						//终止本次循环,继续下次循环,因为震动后不需要执行下面的代码
					}
					String message = new String(arr,0,len);	//转换成字符串

					String ip = packet.getAddress().getHostAddress();	//获取ip地址
					String str = ip + "-->我("+getIpMime()+")    " + getCurrentTime() + getEnter() + message + getEnter();
					textAreaViewText.append(str);
					bufferedWriter.write(str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
