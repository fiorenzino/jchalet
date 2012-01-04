package it.pisi79.geelection.service;

import it.pisi79.geelection.domain.Elettore;
import it.pisi79.geelection.domain.Elezione;
import it.pisi79.geelection.jmx.AdminManagerLocal;
import it.pisi79.geelection.jmx.GeElectionProperties;
import it.pisi79.geelection.repository.ElettoriRepository;
import it.pisi79.geelection.repository.ElezioniRepository;
import it.pisi79.geelection.service.util.MD5Utils;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Stateless
@LocalBean
public class CredenzialiService implements Serializable {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());

	@Inject
	ElettoriRepository elettoriRepository;
	@Inject
	ElezioniRepository elezioniRepository;
	@Inject
	AdminManagerLocal adminManager;

	@Inject
	MailService mailService;

	public String registrati(String contatto, Long idElezione, boolean credenzialiPerse, String address, String param)
			throws CredenzialiException {
		// verifica dati
		if (contatto == null || contatto.trim().length() == 0) {
			throw new CredenzialiException(
					"Le credenziali fornite non sono valide.", mailService, contatto, idElezione);
		}
		Elezione e = elezioniRepository.fetch(idElezione);
		if (e == null) {
			throw new CredenzialiException(
					"Errore generico nel caricamento dell'elezione.", mailService, contatto, idElezione);
		}
		// verifica elezione aperta
		if (e.isConclusa()) {
			throw new CredenzialiException("L'elezione " + e.getNomeElezione()
					+ " si e' gia' conclusa.");
		}
		// verifica registrazione precedente
		String email = calcolaEmail(contatto);
		if (email == null || email.length() == 0) {
			throw new CredenzialiException(
					"Errore generico nel calcolo dell'indirizzo email. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
		}
		for ( Elettore l : e.getElettori() ) {
			if ( email.equals(l.getEmail()) ) {
				if ( l.isVotato() ) {
					throw new CredenzialiGiaGenerateException("Hai gia' votato per questa elezione.");
				}
				else if ( credenzialiPerse ) {
					elettoriRepository.delete(l);
					break;
				}
				else {
					throw new CredenzialiGiaGenerateException("Sei gia' registrato per questa elezione.");
				}
			}
		}
		// verifica unicità password
		boolean esistonoCollisioni = false;
		String password, digest;
		Elettore l;
		int max = 100;
		do {
			password = MD5Utils.generaPassword();
			if (password == null || password.length() == 0) {
				throw new CredenzialiException(
						"Errore generico nella generazione della password. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
			}
			digest = MD5Utils.cacolaDigest(password);
			if (digest == null || digest.length() == 0) {
				throw new CredenzialiException(
						"Errore generico nella elaborazione della password. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
			}
			l = elettoriRepository.findByDigest(digest);
			if (l == null) {
				throw new CredenzialiException(
						"Errore generico nella verifica della password. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
			}
			if (l.getId() != null) {
				esistonoCollisioni = true;
			}
			max--;
		} while (esistonoCollisioni && max > 0);
		if (l.getId() != null) {
			throw new CredenzialiException(
					"Errore generico nella verifica della password. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
		}
		l.setElezione(e);
		l.setEmail(email);
		l.setPasswordDigest(digest);
		l.setVotato(false);
		// salvataggio
		l = elettoriRepository.persist(l);
		if (l.getId() == null) {
			throw new CredenzialiException(
					"Errore generico nel salvataggio della registrazione. Si prega di riprovare piu' tardi.", mailService, contatto, idElezione);
		}
		// invio mail
		boolean okMail = mailService.invia(email, "Elezione " + e.getNomeElezione(), generaBody(e,password,address,param));
		if ( okMail ) {
			logger.info("Eseguito invio mail credenziali");
			return email;
		}
		else {
			logger.error("Fallito invio mail credenziali a: " + email );
			elettoriRepository.delete(l.getId());
			return null;
		}
	}

	private String generaBody(Elezione e, String password, String address, String param) {
		return "Puoi esprimere il tuo voto collegandoti a:\n\n" + 
				address + "?"+param+"=" + password;
	}

	private String calcolaEmail(String contatto) {
		return (contatto + "@" + adminManager.get(GeElectionProperties.emailSuffisso)).trim();
	}

}
