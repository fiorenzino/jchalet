package it.coopservice.gestionespecimen.servlet;

import it.coopservice.commons2.servlet.DownloadServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RisultatiServlet", value = { "/risultati/*" })
public class RisultatiServlet extends DownloadServlet<String> {

	private static final long serialVersionUID = 1L;

	@Override
	protected byte[] getBytes(String object, HttpServletRequest req,
			HttpServletResponse resp) {
		String urlString = "http://" + req.getLocalAddr() + ":"
				+ req.getLocalPort() + req.getContextPath()
				+ "/print/risultati.jsf" + ";jsessionid="
				+ req.getSession().getId();
		return getBytesFromUrlAsString(urlString);
	}

	@Override
	protected String fetch(String string, HttpServletRequest req,
			HttpServletResponse resp) {
		return string;
	}


}
