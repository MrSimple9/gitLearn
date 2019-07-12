package com.jason.server3;

public class RegisterServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.print("<html>");
		response.print("<head>");
		response.print("<title>");
		response.print("this is a title");
		response.print("</title>");
		response.print("<head>");
		response.print("<body>");
		response.print("register");
		response.print("</body>");
		response.print("</body>");
		response.print("<html>");
		
	}


}
