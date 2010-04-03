package it.jflower.chalet4.web.utils;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.MenuItem;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import sun.util.logging.resources.logging;

public class JSFUtils {
	@Inject
	@Log
	private transient Logger log;

	public static Object getParameter(String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(name);
	}

	public static String getCurrentPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		return httpRequest.getRequestURI();
	}

	public static List<MenuItem> getMenuItems() {
		String dove = getCurrentPage().trim();
		String path = getContextPath().replace("/", "").trim();
		String[] dovs = dove.split("/");
		StringBuffer pathComp = new StringBuffer();
		List<MenuItem> menu = new LinkedList<MenuItem>();
		for (int i = 0; i < dovs.length; i++) {
			if (i == 0) {
				menu.add(new MenuItem("home", getAbsolutePath()));
			} else {
				if ((dovs[i] != null) && (dovs[i].trim().compareTo("") != 0)
						&& (dovs[i].trim().compareTo(path) != 0)) {
					if (!dovs[i].endsWith(".jsf")) {
						pathComp.append(dovs[i] + "/");
					}
					menu.add(new MenuItem(dovs[i].replace(".jsf", ""),
							getHostPort() + path + "/" + pathComp + "/"
									+ dovs[i]));
				}
			}
		}
		System.out.println("menu: " + menu.size());
		for (MenuItem menuItem : menu) {
			System.out.println("menu: " + menuItem.getLabel() + " "
					+ menuItem.getLink());
		}
		return menu;
	}

	public static String getRemoteAddr() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		return httpRequest.getRemoteAddr();
	}

	public static String getUserName() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String rem = req.getRemoteUser();
		// System.out.println("******************************");
		// System.out.println("REM USER: " + rem);
		Principal pr = req.getUserPrincipal();
		// System.out.println("PRINC USER: " + pr.getName());
		// System.out.println("******************************");

		if (pr == null)
			return "ucs";
		return pr.getName();
	}

	public static boolean isUserInRole(String role) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context
				.getExternalContext().getRequest();
		return req.isUserInRole(role);
	}

	public static String getContextPath() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String cp = fc.getExternalContext().getRequestContextPath();
		return cp;
	}

	public static String getAbsolutePath() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		String scheme = httpServletRequest.getScheme();
		String hostName = httpServletRequest.getServerName();
		// String hostName = "localhost";
		int port = httpServletRequest.getServerPort();
		// Because this is rendered in a <div> layer, portlets for some reason
		// need the scheme://hostname:port part of the URL prepended.
		return scheme + "://" + hostName + ":" + port + getContextPath();
	}

	public static String getHostPort() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpServletRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		String scheme = httpServletRequest.getScheme();
		String hostName = httpServletRequest.getServerName();
		// String hostName = "localhost";
		int port = httpServletRequest.getServerPort();

		return scheme + "://" + hostName + ":" + port + "/";
	}

}
