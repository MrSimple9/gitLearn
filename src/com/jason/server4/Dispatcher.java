package com.jason.server4;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{

	private Request request;
	private Response response;
	
	@Override
	public void run() {
		try {
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if(servlet!=null) {
				servlet.service(request, response);
				response.pushToClient(200);
			}else {
				
				response.pushToClient(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.pushToClient(505);
		}
		
	}

	public Dispatcher(Socket socket) {
		try {
			request = new Request(socket);
			response = new Response(socket);
		} catch (IOException e) {
			e.printStackTrace();
			response.pushToClient(505);
		}
	}
}
