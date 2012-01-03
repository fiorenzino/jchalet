<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	try {

		if (session != null && ! "true".equalsIgnoreCase( application.getInitParameter("development") ) ) {
			session.invalidate();
		}
		String redirectURL = getServletContext().getContextPath() + "/errore.jsf";
		response.sendRedirect(redirectURL);

	} catch (Exception e) {
		e.printStackTrace();
	}
%>