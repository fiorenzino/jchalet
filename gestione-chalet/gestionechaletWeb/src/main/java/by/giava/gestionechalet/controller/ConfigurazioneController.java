package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.FilaOmbrelloni;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.pojo.Posto;
import by.giava.gestionechalet.repository.CabineRepository;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.FilaOmbrelloniRepository;
import by.giava.gestionechalet.repository.LettiniRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.SdraieRepository;
import by.giava.gestionechalet.repository.SedieRegistaRepository;
import by.giava.gestionechalet.repository.util.ConfigurazioneUtils;

@Named
@SessionScoped
public class ConfigurazioneController extends
		AbstractLazyController<Configurazione> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/clienti/lista.xhtml";

	@EditPage
	public static final String EDIT = "/clienti/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/configurazione/scheda.xhtml";

	public static final String STEP1 = "/configurazione/step1.xhtml";

	public static final String STEP2 = "/configurazione/step2.xhtml";

	public static final String STEP2GRIGLIA = "/configurazione/step2-griglia.xhtml";

	public static final String STEP3 = "/configurazione/step3.xhtml";

	public static final String DIPOSIZIONE = "/configurazione/disposizione-ombrelloni.xhtml";

	@Inject
	@OwnRepository(ConfigurazioneRepository.class)
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	FilaOmbrelloniRepository filaOmbrelloniRepository;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@Inject
	CabineRepository cabineRepository;

	@Inject
	LettiniRepository lettiniRepository;

	@Inject
	SdraieRepository sdraieRepository;

	@Inject
	SedieRegistaRepository sedieRegistaRepository;

	private Tariffa tariffa;

	private List<Ombrellone> ombrelloni;

	private List<String> colonne;
	private List<Posto[]> posti;
	List<Posto> postiList;
	private int numColonne;
	private boolean newOrUpdate;

	@Override
	public Object getId(Configurazione t) {
		return t.getId();
	}

	public String step1() {
		setElement(configurazioneRepository.findLast());
		if (getElement() == null) {
			setNewOrUpdate(true);
			setElement(new Configurazione());
			getElement().setDataCreazione(new Date());
		} else
			setNewOrUpdate(false);

		return STEP1 + "?faces-redirect=true";
	}

	public String step2() {
		// this.colonne = ConfigurazioneUtils.creaColonne(getElement());
		// this.ombrelloni = new ArrayList<Ombrellone>();

		this.postiList = ConfigurazioneUtils.creaPostiSenzaNumero(getElement());
		return STEP2GRIGLIA + "?faces-redirect=true";
	}

	public String step3() {
		if (newOrUpdate) {
			configurazioneRepository.persist(getElement());
			// devo memorizzare tutti gli ombrelloni che ho in posti
			for (Posto posto : getPostiList()) {

				if (posto.getNumero() != null && !posto.getNumero().isEmpty()) {
					Ombrellone ombrellone = new Ombrellone();
					ombrellone.setNumero(posto.getNumero());
					ombrellone.setColonna("" + posto.getColonna());
					ombrellone.setRiga("" + posto.getRiga());
					ombrellone.setAttivo(true);
					ombrellone.setConfigurazione(getElement());
					ombrellone.setFila(posto.getFila());
					ombrelloniRepository.persist(ombrellone);

				}
			}

			for (int i = 1; i <= getElement().getNumeroCabine(); i++) {
				Cabina cabina = new Cabina();
				cabina.setNumero(i + "");
				cabina.setAttivo(true);
				cabina.setConfigurazione(getElement());
				cabineRepository.persist(cabina);

			}
			for (int i = 1; i <= getElement().getNumeroLettini(); i++) {
				Lettino lettino = new Lettino();
				lettino.setAttivo(true);
				lettino.setNumero(i + "");
				lettino.setConfigurazione(getElement());
				lettiniRepository.persist(lettino);
			}
			for (int i = 1; i <= getElement().getNumeroSdraio(); i++) {
				Sdraio sdraio = new Sdraio();
				sdraio.setAttivo(true);
				sdraio.setNumero(i + "");
				sdraio.setConfigurazione(getElement());
				sdraieRepository.persist(sdraio);
			}
			for (int i = 1; i <= getElement().getNumeroSedieRegista(); i++) {
				SediaRegista sedia = new SediaRegista();
				sedia.setAttivo(true);
				sedia.setNumero(i + "");
				sedia.setConfigurazione(getElement());
				sedieRegistaRepository.persist(sedia);
			}
		}
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
		return VIEW + "?faces-redirect=true";
	}

	public String addNew() {
		setNewOrUpdate(true);
		setElement(new Configurazione());
		getElement().setDataCreazione(new Date());
		return STEP1 + "?faces-redirect=true";
	}

	public String step2Old() {
		if (getElement().getId() == null) {
			configurazioneRepository.persist(getElement());
			for (int i = 1; i <= getElement().getNumeroFile(); i++) {
				logger.info("creo fila: " + i);
				FilaOmbrelloni fila = new FilaOmbrelloni();
				fila.setNumero(i);
				fila.setConfigurazione(getElement());
				filaOmbrelloniRepository.persist(fila);
				getElement().addFilaOmbrelloni(fila);
			}
			configurazioneRepository.update(getElement());
		} else {
			configurazioneRepository.update(getElement());
		}

		return STEP2 + "?faces-redirect=true";
	}

	public String step3Old() {
		// GENERO OMBRELLONI
		if (newOrUpdate) {
			for (FilaOmbrelloni fila : getElement().getFileOmbrelloni()) {
				filaOmbrelloniRepository.update(fila);
				logger.info("step3 fila: " + fila.getNumero());
				for (Integer i = fila.getDal(); i <= fila.getAl(); i++) {
					Ombrellone ombrellone = new Ombrellone();
					ombrellone.setFila("" + fila.getNumero());
					ombrellone.setNumero(i + "");
					ombrellone.setAttivo(true);
					ombrellone.setConfigurazione(getElement());
					ombrelloniRepository.persist(ombrellone);
				}
				logger.info("step3 fila: " + fila.getNumero() + " FINE");
			}

			for (int i = 1; i <= getElement().getNumeroCabine(); i++) {
				Cabina cabina = new Cabina();
				cabina.setNumero(i + "");
				cabina.setAttivo(true);
				cabina.setConfigurazione(getElement());
				cabineRepository.persist(cabina);

			}
			for (int i = 1; i <= getElement().getNumeroLettini(); i++) {
				Lettino lettino = new Lettino();
				lettino.setAttivo(true);
				lettino.setNumero(i + "");
				lettino.setConfigurazione(getElement());
				lettiniRepository.persist(lettino);
			}
			for (int i = 1; i <= getElement().getNumeroSdraio(); i++) {
				Sdraio sdraio = new Sdraio();
				sdraio.setAttivo(true);
				sdraio.setNumero(i + "");
				sdraio.setConfigurazione(getElement());
				sdraieRepository.persist(sdraio);
			}
		}
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
		// this.righe = ConfigurazioneUtils.creaRighe(ombrelloni, getElement());
		// this.colonne = ConfigurazioneUtils.creaColonne(getElement()
		// .getNumeroColonne().intValue());
		return STEP3 + "?faces-redirect=true";
	}

	public String step4() {
		logger.info("salvo la configurazione finale");
		for (Ombrellone ombrellone : ombrelloni) {
			ombrelloniRepository.update(ombrellone);
		}
		return viewPage();
	}

	public String caricaConfigurazioneAttuale() {
		setElement(configurazioneRepository.findLast());
		return viewPage();
	}

	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

	public List<Ombrellone> getOmbrelloni() {
		return ombrelloni;
	}

	public void setOmbrelloni(List<Ombrellone> ombrelloni) {
		this.ombrelloni = ombrelloni;
	}

	public List<String> getColonne() {
		// if (colonne == null) {
		// this.colonne = new ArrayList<String>();
		// for (int i = 0; i < 9; i++) {
		// this.colonne.add("" + i);
		// }
		//
		// }
		return colonne;
	}

	public void setColonne(List<String> colonne) {
		this.colonne = colonne;
	}

	public boolean isNewOrUpdate() {
		return newOrUpdate;
	}

	public void setNewOrUpdate(boolean newOrUpdate) {
		this.newOrUpdate = newOrUpdate;
	}

	public List<Posto[]> getPosti() {
		// if (posti == null) {
		// posti = new ArrayList<Posto[]>();
		// // creo 6 righe
		// int numOmbr = 1;
		// for (int i = 0; i < 5; i++) {
		// Posto[] fila = new Posto[getColonne().size()];
		// for (int j = 0; j < getColonne().size(); j++) {
		// fila[j] = new Posto(i, j, "" + numOmbr);
		// numOmbr++;
		// }
		// posti.add(fila);
		// }
		//
		// }
		return posti;
	}

	public void setPosti(List<Posto[]> posti) {
		this.posti = posti;
	}

	public String vediConfigurazioneOmbrelloniAttuale() {
		caricaConfigurazioneAttuale();
		return vediConfigurazioneOmbrelloni();
	}

	public String vediConfigurazioneOmbrelloni() {
		this.colonne = ConfigurazioneUtils.creaColonne(getElement());
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
		this.posti = ConfigurazioneUtils.creaRighe(ombrelloni, getElement());
		return DIPOSIZIONE + "?faces-redirect=true";
	}

	public List<Posto> getPostiList() {
		return postiList;
	}

	public void setPostiList(List<Posto> postiList) {
		this.postiList = postiList;
	}
}
