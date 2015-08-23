<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	session.removeAttribute("oper");
	session.removeAttribute("operpri");
	
	response.sendRedirect("login.jsp");
 %>