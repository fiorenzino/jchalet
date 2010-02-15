<%@page import="org.jboss.logging.Logger"%>
<%@page import="java.util.Date"%>
<%
	Logger log = Logger.getLogger(getClass().getName());
	log.info("logout volontario");
	try {
		HttpSession sess = request.getSession();
		log.info("logout: facesContext attivo");
		if (session != null) {
			session.invalidate();
		}
	} catch (Exception e) {
		log.info("logout: error");
		e.printStackTrace();
	}
	log.info("logout: end");
%>
<html>
<head>
<meta http-equiv="Refresh" content="0; URL=contratti/contratti.jsf">
</head>
</html>