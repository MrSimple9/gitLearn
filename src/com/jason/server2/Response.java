package com.jason.server2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {

	private BufferedWriter bw;
	private StringBuilder content;
	private StringBuilder header;
	private int size;
	private final String BLANK = " ";
	private final String CRLF="\r\n";
	
	public Response() {
		content = new StringBuilder();
		header = new StringBuilder();
		size = 0;
		
	}
	public Response(Socket client) {
		this();
		try {
			this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			header = null;
		}
	}
	
	public Response(OutputStream os) {
		this();
		this.bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	public Response print(String info) {
		content.append(info);
		size += info.getBytes().length;
		return this;
	}
	
	public Response println(String info) {
		content.append(info).append(CRLF);
		size += (info+CRLF).getBytes().length;
		return this;
	}
	
	public void pushToClient(int code) {
		if(header == null) {
			code = 505;
		}
		createHeader(code);
		try {
			bw.append(header);
			bw.append(content);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	             	
	//¹¹½¨Í·
	
	private void createHeader(int code) {
		header.append("HTTP/1.1").append(BLANK);
		header.append(code).append(BLANK);
		switch (code) {
		case 200:
			header.append("OK").append(CRLF);
			break;
		case 404:
			header.append("NOT FOUND").append(CRLF);
			break;
		case 505:
			header.append("SERVER ERROR").append(CRLF);
			break;
			
		default:
			break;
		}
		header.append("Date:").append(new Date()).append(CRLF);
		header.append("Server:").append("Server01/0.0.1;").append("charset=").append("UTF8").append(CRLF);
		header.append("Content-type:text/html").append(CRLF);
		header.append("Content-length:").append(size).append(CRLF);
		header.append(CRLF);
		
	}
	
}
