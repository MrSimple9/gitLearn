package com.jason.server4;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static WebContext  webContext ;
	static {
		try {
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parse =factory.newSAXParser();
			WebHandler handler=new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("web.xml")
			,handler);
			webContext = new WebContext(handler.getEntitys(),handler.getMappings());
		}catch(Exception e) {
			System.out.println("webÅäÖÃÎÄ¼þ½âÎö´íÎó");
		}
	}
	
	public static Servlet getServletFromUrl(String url) {		
		String className = webContext.getClz(url);
		Class clz;
		try {
			clz = Class.forName(className);
			System.out.println(className+"-->"+url);
			Servlet servlet =(Servlet)clz.getConstructor().newInstance();
			return servlet;
		} catch (Exception e) {
			
		}
		
		return null;
		
	}
}
