package it.pisi79.geelection.service;

import it.pisi79.geelection.domain.Mail;
import it.pisi79.geelection.jmx.AdminManagerLocal;
import it.pisi79.geelection.jmx.GeElectionProperties;
import it.pisi79.geelection.repository.MailRepository;
import it.pisi79.geelection.service.util.GmailUtils;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Stateless
@LocalBean
public class MailService implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String MIME_TYPE_TEXT_PLAIN = "text/plain";
	Logger logger = Logger.getLogger(getClass());

	@Inject
	AdminManagerLocal adminManager;
	@Inject
	MailRepository mailRepository;
	
	public boolean invia(String to, String title, String body) {
        Mail mail = new Mail();
        mail.setTo(to);
        mail.setBcc(adminManager.get(GeElectionProperties.emailNotifica));
        mail.setSubject(title);
        mail.setBody(body);
        mail.setSent(new Date());
        boolean success = GmailUtils.sendGmailSSL(adminManager.get(GeElectionProperties.emailAccount), adminManager.get(GeElectionProperties.emailPassword), mail);
        mail.setSuccess(success);
        logger.info("Sent mail to " + to + ": " + success);
        mailRepository.persist(mail);
        return success;
	}

	public boolean errore(String title, String body) {
        Mail mail = new Mail();
        mail.setTo(adminManager.get(GeElectionProperties.emailNotifica));
        mail.setSubject(title);
        mail.setBody(body);
        mail.setSent(new Date());
        boolean success = GmailUtils.sendGmailSSL(adminManager.get(GeElectionProperties.emailAccount), adminManager.get(GeElectionProperties.emailAccount), mail);
        mail.setSuccess(success);
        logger.info("Sent error mail to " + mail.getTo() + ": " + success);
        mailRepository.persist(mail);
        return success;
	}

}
