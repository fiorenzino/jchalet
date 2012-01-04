package it.pisi79.geelection.service.util;

import it.pisi79.geelection.domain.Mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jboss.logging.Logger;

public class GmailUtils {

	static Logger logger = Logger.getLogger(GmailUtils.class);

	@Deprecated
	public static boolean sendGmailTLS(String username, String password,
			Mail mail) {

		try {
			String host = "smtp.gmail.com";
			int port = 587;

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(/* noreply */username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getTo()));
			message.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(mail.getBcc()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);

			Transport.send(message);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public static boolean _sendGmailSSL(final String username,
			final String password, Mail mail) {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// only works the 1st time : Access to default session denied
			// javax.mail
			// Session session = Session.getDefaultInstance(props,
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(/* noreply */username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getTo()));
			message.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(mail.getBcc()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());

			Transport.send(message);

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.setTo("samuele.pasini@gmail.com");
		mail.setBcc("pisi79.spam@gmail.com");
		mail.setBody("Contenuto");
		mail.setSubject("Titolo");
		boolean result = false;
		// result = sendGmailTLS("pisi79.spam@gmail.com", "***", mail);
		// System.out.println("result TLS = " + result);
		result = sendGmailSSL("pisi79.spam@gmail.com", "***", mail);
		System.out.println("result SSL = " + result);
	}

	/**
	 * 
	 * $_POST['username']== "pisi.spam") && ($_POST['pwd']== "flower123UU"
	 * [10:19:32 AM] flower: username: <input type="text" name="username" /><br />
	 * password: <input type="password" name="pwd" /><br />
	 * <br />
	 * to: <input type="text" name="to" /><br />
	 * subject: <input type="text" name="subject" /><br />
	 * message: <textarea name="message" cols=40 rows=6></textarea><br />
	 * from: <input type="text" name="from" /><br />
	 * reply-mail: <input type="text" name="reply-mail" /><br />
	 * 
	 * 
	 * @param username
	 * @param password
	 * @param mail
	 * @return
	 */
	public static boolean sendGmailSSL(final String username,
			final String password, Mail mail) {
		HttpClient httpClient = new HttpClient();
		PostMethod mailPost = new PostMethod(
				"http://pietraiadeipoeti.it/pisi/send.php");
		mailPost.addParameter("username", "pisi.spam");
		mailPost.addParameter("pwd", "flower123UU");
		mailPost.addParameter("to", mail.getTo());
		if (mail.getCc() != null && mail.getCc().trim().length() > 0) {
			mailPost.addParameter("cc", mail.getCc());
		}
		if (mail.getBcc() != null && mail.getBcc().trim().length() > 0) {
			mailPost.addParameter("bcc", mail.getBcc());
		}
		mailPost.addParameter("subject", mail.getSubject());
		mailPost.addParameter("message", mail.getBody());
		mailPost.addParameter("from", "geelection@gmail.com");
		mailPost.addParameter("reply-mail", "noreply@gmail.com");
		try {
			httpClient.executeMethod(mailPost);
			return "OK".equals(mailPost.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
