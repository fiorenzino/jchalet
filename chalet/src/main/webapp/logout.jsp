<%@page import="java.util.Date"%>
<%
	try {
		if (request.getSession() != null) {
			request.getSession().invalidate();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<html>
<head>
<meta http-equiv="Refresh" content="0; URL=home.jsf">
</head>
</html>