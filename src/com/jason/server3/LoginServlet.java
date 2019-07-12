package com.jason.server3;

public class LoginServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.print("<html>");
		response.print("<head>");
		response.print("<title>");
		response.print("this is a title");
		response.print("</title>");
		response.print("<head>");
		response.print("<body>");
		response.print("login");
		response.print("</body>");
		response.print("</body>");
		response.print("<html>");
	}

	
}
