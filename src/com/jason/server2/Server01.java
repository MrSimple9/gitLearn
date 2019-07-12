package com.jason.server2;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


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

		 
		 Request request = new Request(socket);
		 
		 System.out.println(request.getParameter("stuName"));
		 System.out.println(request.getParameter("stuAge"));
		 System.out.println(request.getParameter("stuId"));
		 Response response = new Response(socket);
		 response.print("<html>");
		 response.print("<head>");
		 response.print("<title>");
		 response.print("this is a title");
		 response.print("</title>");
		 response.print("<head>");
		 response.print("<body>");
		 response.print("hello");
		 response.print("</body>");
		 response.print("</body>");
		 response.print("<html>");
		 
		response.pushToClient(200);
		 	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
