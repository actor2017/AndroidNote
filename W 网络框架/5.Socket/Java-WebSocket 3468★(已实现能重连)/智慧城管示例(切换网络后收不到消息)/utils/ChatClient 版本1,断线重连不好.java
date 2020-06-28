package com.kuchuanyun.wisdomcitymanagement.utils;

import android.text.TextUtils;

import com.kuchuanyun.wisdomcitymanagement.global.Global;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * java websocket 客户端
 * @author yxl
 *
 */
public class ChatClient extends WebSocketClient {
	
	public static ChatClient client = null;
	private static String userno = null;

	public ChatClient( URI serverUri , Draft draft ) {
		super( serverUri, draft );
	}

	public ChatClient( URI serverURI ) {
		super( serverURI );
	}
	
	
	@Override
	public void onOpen( ServerHandshake handshakedata ) {
		System.out.println( "opened connection" );
		send("[join]"+userno+"-app");
	}
	
	public static void sendLng(String lng,String lat){
		if (client.getConnection().isClosed()) {
			startServer(userno);
			System.out.println("-----------close------------");
		}else{
			client.send("{\"usercode\":\""+userno+"-app\",\"lng\":\""+lng+"\",\"lat\":\""+lat+"\"}");
		}
	}
	

	@Override
	public void onMessage( String message ) {
		System.out.println( "received: " + message );
	}

	public void onFragment( Framedata fragment ) {
		System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );
	}

	@Override
	public void onClose( int code, String reason, boolean remote ) {
		System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
		while (client.getConnection().isClosed()) {
			try {
				//10秒后重新连接
				Thread.sleep(10000);
				startServer(userno);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onError( Exception ex ) {
		System.out.println( "Connection error" );
		ex.printStackTrace();
	}

	private static URI initUri(){
		try {
//			String url = Global.getConfig("lng.socket.adress");
//			String url = "119.23.238.122:8889";
			return new URI("ws://"+ Global.WEB_SOCKET_IP);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//客户端连接服务器
	public static void startServer(String uno){
		if (!TextUtils.isEmpty(uno)) {
			userno = uno;
			client = new ChatClient(initUri());
			client.connect();
		}
	}
	
//	public static void main( String[] args ){
//		ChatClient.startServer(userId);//在登录成功后调用
//		ChatClient.sendLng("106.973056","26.463056");//百度有左边后开始发送坐标.
//	}

}

