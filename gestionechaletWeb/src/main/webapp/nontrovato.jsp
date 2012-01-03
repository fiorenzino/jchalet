<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	try {

		String redirectURL = getServletContext().getContextPath()
				+ "/nontrovato.jsf";
		response.sendRedirect(redirectURL);

	} catch (Exception e) {
		e.printStackTrace();
	}
%>