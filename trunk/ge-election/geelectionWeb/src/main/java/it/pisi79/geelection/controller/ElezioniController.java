package it.pisi79.geelection.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractController;
import it.coopservice.commons2.utils.JSFUtils;
import it.pisi79.geelection.domain.Candidato;
import it.pisi79.geelection.domain.Elettore;
import it.pisi79.geelection.domain.Elezione;
import it.pisi79.geelection.jmx.AdminManagerLocal;
import it.pisi79.geelection.jmx.GeElectionProperties;
import it.pisi79.geelection.repository.CandidatiRepository;
import it.pisi79.geelection.repository.ElettoriRepository;
import it.pisi79.geelection.repository.ElezioniRepository;
import it.pisi79.geelection.service.CredenzialiException;
import it.pisi79.geelection.service.CredenzialiGiaGenerateException;
import it.pisi79.geelection.service.CredenzialiService;
import it.pisi79.geelection.service.MailService;
import it.pisi79.geelection.service.VotoException;
import it.pisi79.geelection.service.VotoService;
import it.pisi79.geelection.service.util.MD5Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ElezioniController extends AbstractController<Elezione> {

	private static final long serialVersionUID = 1L;
	public static final String ID_ELEZIONE_PARAM = "e";
	public static final String PASSWORD_ELEZIONE_PARAM = "x";
	public static final String PASSWORD_ELETTORE_PARAM = "l";

	@ListPage
	public static final String LIST = "/elezioni/lista.xhtml";

	@ViewPage
	public static final String VIEW = "/elezioni/scheda.xhtml";

	@EditPage
	public static final String EDIT = "/elezioni/gestione.xhtml";

	@Inject
	@OwnRepository(ElezioniRepository.class)
	ElezioniRepository elezioniRepository;

	@Inject
	MailService mailService;
	@Inject
	CredenzialiService credenzialiService;
	@Inject
	VotoService votoService;
	@Inject
	CandidatiRepository candidatiRepository;
	@Inject
	ElettoriRepository elettoriRepository;

	@Inject
	AdminManagerLocal adminManager;

	Candidato nuovoCandidato;
	Elettore elettore;
	String contatto;
	boolean perse;

	@Override
	public Object getId(Elezione t) {
		return t.getId();
	}

	@Override
	public String addElement() {
		super.addElement();
		getElement().setNome("Nuova elezione");
		getElement().setDescrizione("Elezione da configurare");
		getElement().setCreata(new Date());
		getElement().setDal(new Date());
		getElement().setAl(new Date());
		getElement().setPassword(MD5Utils.generaPassword());
		getElement().setDigest(
				MD5Utils.cacolaDigest(getElement().getPassword()));
		elezioniRepository.persist(getElement());
		String body = "Indirizzo per la gestione della nuova elezione: "
				+ adminManager.get(GeElectionProperties.geelectionUrl)
				+ "/elezioni/gestione.jsf" + "?" + PASSWORD_ELEZIONE_PARAM
				+ "=" + getElement().getPassword();
		body += "\n\n";
		body += "Indirizzo per la richiesta delle credenziali necessarie al voto: "
				+ adminManager.get(GeElectionProperties.geelectionUrl)
				+ "/elezioni/scheda.jsf"
				+ "?"
				+ ID_ELEZIONE_PARAM
				+ "="
				+ getElement().getId();
		boolean okMail = mailService.invia(
				adminManager.get(GeElectionProperties.emailAdmin),
				"Creazione nuova elezione", body);
		if (okMail) {
			super.addFacesMessage("Eseguito invio mail con codici di accesso");
		} else {
			super.addFacesMessage("Errori nell'invio della mail con i codici di accesso. Riprovare piu' tardi.");
		}
		return null;
	}

	@Override
	public Elezione getElement() { 
		Elezione e = super.getElement();
		String password = (String) JSFUtils
				.getParameter(PASSWORD_ELEZIONE_PARAM);
		if (password != null && password.trim().length() > 0) {

			if (e == null || !e.isModificabile()) {
				try {
					e = elezioniRepository.fetchByDigest(MD5Utils
							.cacolaDigest(password));
					if (e == null) {
						boolean okMail = mailService
								.invia(adminManager
										.get(GeElectionProperties.emailNotifica),
										"GE Election - Attenzione",
										"Tentativo di accesso a gestione elezione fallito.");
						if (okMail) {
							logger.warn("Eseguito invio mail di notifica errore");
						} else {
							logger.error("Fallito invio mail di notifica errore");
						}
					}
					e.setModificabile(true);
					setElement(e);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					ex.printStackTrace();
					boolean okMail = mailService.invia(adminManager
							.get(GeElectionProperties.emailNotifica),
							"GE Election - Attenzione",
							"Errore nell'accesso a gestione elezione.");
					if (okMail) {
						logger.warn("Eseguito invio mail di notifica errore");
					} else {
						logger.error("Fallito invio mail di notifica errore");
					}
				}
			}
		}
		if (e == null) {
			try {
				String id = (String) JSFUtils.getParameter(ID_ELEZIONE_PARAM);
				if (id != null && id.trim().length() > 0) {
					e = elezioniRepository.fetch(Long.parseLong(id));
					if (e == null) {
						boolean okMail = mailService
								.invia(adminManager
										.get(GeElectionProperties.emailNotifica),
										"GE Election - Attenzione",
										"Tentativo di accesso a scheda elezione fallito.");
						if (okMail) {
							logger.warn("Eseguito invio mail di notifica errore");
						} else {
							logger.error("Fallito invio mail di notifica errore");
						}
					}
					setElement(e);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
				boolean okMail = mailService.invia(
						adminManager.get(GeElectionProperties.emailNotifica),
						"GE Election - Attenzione",
						"Errore nell'accesso a scheda elezione.");
				if (okMail) {
					logger.warn("Eseguito invio mail di notifica errore");
				} else {
					logger.error("Fallito invio mail di notifica errore");
				}
			}
		}
		return e;
	}

	@Override
	public String update() {
		getElement().setAttivo(true);
		super.update();
		getElement().setModificabile(true);
		return editPage();
	}

	public Candidato getNuovoCandidato() {
		if (nuovoCandidato == null) {
			nuovoCandidato = new Candidato();
		}
		return nuovoCandidato;
	}

	public void setNuovoCandidato(Candidato nuovoCandidato) {
		this.nuovoCandidato = nuovoCandidato;
	}

	public String aggiungiCandidato() {
		nuovoCandidato.setElezione(getElement());
		candidatiRepository.persist(nuovoCandidato);
		nuovoCandidato = null;
		setElement(elezioniRepository.fetch(getElement().getId()));
		getElement().setModificabile(true);
		return editPage();
	}

	public String rimuoviCandidato(Long idCandidato) {
		candidatiRepository.delete(idCandidato);
		setElement(elezioniRepository.fetch(getElement().getId()));
		getElement().setModificabile(true);
		return editPage();
	}

	public Elettore getElettore() {
		if (elettore == null) {
			try {
				String password = (String) JSFUtils
						.getParameter(PASSWORD_ELETTORE_PARAM);
				if (password != null && password.trim().length() > 0) {
					elettore = elettoriRepository.findByDigest(MD5Utils
							.cacolaDigest(password));
					if (elettore == null) {
						super.addFacesMessage("Credenziali non valide");
					}
					elettore.setPassword(password);
					setElement(elezioniRepository.fetch(elettore.getElezione()
							.getId()));
					if (elettore.isVotato()) {
						super.addFacesMessage("Hai gia' votato per questa elezione.");
					}
					if (getElement().isConclusa()) {
						super.addFacesMessage("L'elezione e' terminata.");
					}
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
				boolean okMail = mailService.invia(
						adminManager.get(GeElectionProperties.emailNotifica),
						"GE Election - Attenzione",
						"Errore nell'accesso di un elettore a una elezione.");
				if (okMail) {
					logger.warn("Eseguito invio mail di notifica errore");
				} else {
					logger.error("Fallito invio mail di notifica errore");
				}
			}
		}
		return elettore;
	}

	public void setElettore(Elettore elettore) {
		this.elettore = elettore;
	}

	@Override
	public String reload() {
		this.elettore = null;
		this.nuovoCandidato = null;
		this.contatto = null;
		this.perse = false;
		setElement(null);
		return super.reload();
	}

	public String richiediCredenziali() {
		try {
			String address = adminManager
					.get(GeElectionProperties.geelectionUrl)
					+ "/elezioni/scheda.jsf";
			String email = credenzialiService.registrati(contatto, getElement()
					.getId(), perse, address, PASSWORD_ELETTORE_PARAM);
			super.addFacesMessage("Eseguito invio mail con codici di accesso all'indirizzo: "
					+ email);
			this.contatto = null;
			this.perse = false;
			return null;
		} catch (CredenzialiGiaGenerateException e) {
			super.addFacesMessage(e.getMessage());
			super.addFacesMessage("Selezione l'opzione per ottenere nuove credenziali. Attenzione: le vecchie credenziali non potranno piu' essere utilizzate.");
			return null;
		} catch (CredenzialiException e) {
			super.addFacesMessage(e.getMessage());
			return null;
		} catch (Exception e) {
			super.addFacesMessage("Errore generico. Riprovare piu' tardi.");
			return null;
		}
	}

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}

	public String vota(Long idCandidato) {
		try {
			votoService.esprimi(elettore.getPassword(), idCandidato);
			boolean okMail = mailService
					.invia(elettore.getEmail(),
							"Grazie per aver partecipato a "
									+ getElement().getNomeElezione(),
							"Le credenziali in tuo possesso non possono essere riutilizzate.\n\nIl tuo voto viene registrato in forma anonima.");
			if (okMail) {
				logger.info("Inviata mail conferma voto.");
				super.addFacesMessage("Grazie per aver votato. Riceverai una mail di conferma a breve.");
				this.elettore.setVotato(true);
				return null;
			} else {
				logger.error("Errore invio mail conferma voto.");
				super.addFacesMessage("Grazie per aver votato.");
				this.elettore.setVotato(true);
				return null;
			}
		} catch (VotoException e) {
			super.addFacesMessage(e.getMessage());
			return null;
		} catch (Exception e) {
			super.addFacesMessage("Errore generico. Riprovare piu' tardi.");
			return null;
		}
	}

	public List<Candidato> getRisultati() {
		List<Candidato> risultati = new ArrayList<Candidato>();
		if (getElement().isConclusa()) {
			for (Candidato c : getElement().getCandidati()) {
				risultati.add(c);
			}
		}
		Collections.sort(risultati, new Comparator<Candidato>() {
			@Override
			public int compare(Candidato o1, Candidato o2) {
				return o1.getNroVoti() - o2.getNroVoti();
			}
		});
		return risultati;
	}

	public boolean isPerse() {
		return perse;
	}

	public void setPerse(boolean perse) {
		this.perse = perse;
	}

	public String getRedirect() {
		try {
			JSFUtils.redirect("/proibito.jsp");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Accesso non autorizzato");
		}
		return null;
	}

}
