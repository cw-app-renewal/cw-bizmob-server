<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ page import="org.springframework.web.context.*" %>
<%@ page import="org.springframework.web.context.support.*" %>
<%@ page import="adapter.appstore.CGR221_AppstoreLoginService" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
try {
	WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	CGR221_AppstoreLoginService service = (CGR221_AppstoreLoginService)ctx.getBean("appstoreLoginService");
	
	String userId = request.getParameter("userId");
	String password = request.getParameter("password");
	String mobileNumber = request.getParameter("mobileNo");
	
	String message = service.appstoreLogin(userId, password, mobileNumber);
	
	out.println(message);
	
} catch (Exception e) {
	out.println(e.getMessage());
}
%>

</body>
</html>