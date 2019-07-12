package com.jason.server4;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	private boolean isRunning;
	private ServerSocket serverSocket;
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		server.receive();
		
	}
	
	
	public void start() {
		try {
			this.serverSocket = new ServerSocket(8888);
			this.isRunning = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务端启动失败");
		}
	}
	
	public void stop() {
		isRunning = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receive() {
		while(isRunning) {
			try {
				 Socket socket = serverSocket.accept();
				 System.out.println("一个客户端建立了链接");
				 new Thread(new Dispatcher(socket)).start();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
		}
		 	
		
	}
}
