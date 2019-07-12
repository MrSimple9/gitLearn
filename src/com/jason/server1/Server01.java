package com.jason.server1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server01 {

	private ServerSocket serverSocket;
	public static void main(String[] args) {
		Server01 server01 = new Server01();
		server01.start();
		server01.receive();
		
		
	}
	
	
	public void start() {
		try {
			this.serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务端启动失败");
		}
	}
	
	public void stop() {
		
	}
	
	public void receive() {
		
		try {
		 Socket socket = serverSocket.accept();
		 System.out.println("一个客户端建立了链接");

		 InputStream is = socket.getInputStream();
		 
		 byte[] datas = new byte[1024*1024];
		 int len = is.read(datas);
		 String requestInfo = new String(datas,0,len);
		 System.out.println(requestInfo);
		 
		 StringBuilder content = new StringBuilder();
		 content.append("<html>");
		 content.append("<head>");
		 content.append("<title>");
		 content.append("服务器响应页面");
		 content.append("</title>");
		 content.append("<head>");
		 content.append("<body>");
		 content.append("hello");
		 content.append("</body>");
		 content.append("<html>");
		 
		 int size = content.toString().getBytes().length;
		 
		 StringBuilder respondHeader = new StringBuilder();
		 String blank=" ";
		 String CRLF ="\r\n";
		 respondHeader.append("HTTP/1.1").append(blank);
		 respondHeader.append("200").append(blank);
		 respondHeader.append("OK").append(CRLF);
		 
		 respondHeader.append("Date:").append(new Date()).append(CRLF);
		 respondHeader.append("Server:").append("Server01").append(CRLF);
		 respondHeader.append("Content-type:text/html").append(CRLF);
		 respondHeader.append("Content-length:").append(size).append(CRLF);
		 respondHeader.append(CRLF);
		 
		 respondHeader.append(content.toString());
		 
		 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		 bw.write(respondHeader.toString());
		 bw.flush();
		 
		 	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
