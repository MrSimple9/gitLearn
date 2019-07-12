package com.jason.server3;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	private ServerSocket serverSocket;
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		server.receive();
		
		
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
		 System.out.println(request.getParameter("name"));
		 System.out.println(request.getParameter("password"));
		 Response response = new Response(socket);
		 Servlet servlet = WebApp.getServletFromUrl((request.getUrl()));
		 if(null!=servlet) {
			
			 servlet.service(request, response);
			 response.pushToClient(200);
		 }else {
			 response.pushToClient(404);
			 return;
		 }
		 	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
