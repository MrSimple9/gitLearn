package com.jason.server2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Request {
	
	private String requestInfo;
	private String method;
	private String url;
	private String querryStr;
	private HashMap<String, List<String>> parameterMap;
	
	
	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}
	
	public Request(InputStream is) {
		parameterMap = new HashMap<String, List<String>>();
		byte[] datas = new byte[1024];
		int len = -1;
		try {
			len =is.read(datas);
			requestInfo = (new String(datas,0,len));
			parse();
			getParameterMap();

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}
	
	private void parse() {
		String[] values = this.requestInfo.split("\r\n");
		String[] requestHeader = values[0].split(" ");
		this.method = requestHeader[0].toLowerCase();
		int pIndex = requestHeader[1].indexOf('?');
		if(pIndex>=0&&method.equals("get")) {
			String[] urlStr = requestHeader[1].split("\\?",2);
			this.url = urlStr[0];
			this.querryStr = urlStr[1];
		}
		if(method.equals("post")) {
			this.url = requestHeader[1];
			querryStr = null==querryStr?"":querryStr;
			this.querryStr += requestInfo.substring(this.requestInfo.lastIndexOf("\r\n")).trim();
		}
		querryStr = null==querryStr?"":querryStr;
		
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Request [method=" + method + ", url=" + url + ", querryStr=" + querryStr + "]";
	}
	
	private void getParameterMap() {

		String[] parameters = this.querryStr.split("&");
		for(String parameter:parameters) {		
			if(parameter.length()>0) {
				String[] values = parameter.split("=",2);
				String key = values[0];
				String value = values[1]==null?null:decode(values[1], "utf-8");
				System.out.println(value);
				if(key.length()>0) {
					if(!parameterMap.containsKey(key)) {
						parameterMap.put(key, new ArrayList<String>());
					}
					parameterMap.get(key).add(value);					
				}
					
			}
		}
		/*
		Set<String> keys = parameterMap.keySet();
		
		for(Iterator<String> iterator =keys.iterator();iterator.hasNext();) {
			String key = iterator.next();
			System.out.println(parameterMap.get(key));
		}
		*/
	}
	
	private String decode(String value,String enc) {
		try {
			return java.net.URLDecoder.decode(value,enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] getParameters(String name) {
		List<String> parameters= parameterMap.get(name);
		if(null==parameters||parameters.size()<1){
			return null;
		}
		return parameters.toArray(new String[0]);
	}
	
	public String getParameter(String name) {
		List<String> parameters= parameterMap.get(name);
		return parameters.get(0);
	}

	public String getRequestInfo() {
		return requestInfo;
	}


	public String getMethod() {
		return method;
	}

	
	public String getUrl() {
		return url;
	}

	
	public String getQuerryStr() {
		return querryStr;
	}


	
	
}
