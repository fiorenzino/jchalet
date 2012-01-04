package it.pisi79.geelection.service.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.jboss.logging.Logger;

public class MD5Utils {

	static Logger logger = Logger.getLogger(MD5Utils.class.getCanonicalName());
	
	public static String cacolaDigest(String password) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			if (algorithm == null) {
				return null;
			}
			algorithm.reset();
			algorithm.update(password.getBytes());
			return new String(algorithm.digest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static String generaPassword() {
		try {
			return "a"
					+ UUID.randomUUID().toString().substring(1, 8)
							.replaceAll("-", "0")
					+ "b"
					+ UUID.randomUUID().toString().substring(1, 8)
							.replaceAll("-", "0")
					+ "c"
					+ UUID.randomUUID().toString().substring(1, 8)
							.replaceAll("-", "0");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}


}
