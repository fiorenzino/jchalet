package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.controller.util.ConfigurazioniUtils;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.pojo.Posto;
import by.giava.gestionechalet.repository.CabineRepository;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.LettiniRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.SdraieRepository;
import by.giava.gestionechalet.repository.SedieRegistaRepository;
import by.giava.gestionechalet.repository.ServiziRepository;

@Named
@SessionScoped
public class ConfigurazioneController extends
		AbstractLazyController<Configurazione> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/configurazione/lista.xhtml";

	@ViewPage
	public static final String VIEW = "/configurazione/scheda.xhtml";

	@EditPage
	public static final String EDIT = "/configurazione/step1.xhtml";

	public static final String STEP2GRIGLIA = "/configurazione/step2.xhtml";

	public static final String STEP3 = "/configurazione/step3.xhtml";

	public static final String DIPOSIZIONE = "/configurazione/disposizione-ombrelloni.xhtml";

	@Inject
	@OwnRepository(ConfigurazioneRepository.class)
	ConfigurazioneRepository configurazioneRepository;

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

	@Inject
	ServiziRepository serviziRepository;

	@Inject
	PropertiesController propertiesController;

	private Configurazione actual;

	private Tariffa tariffa;

	private List<Ombrellone> ombrelloni;

	private List<String> colonne;
	private List<Posto[]> posti;
	List<Posto> postiList;
	private boolean newOrUpdate;

	@Override
	public Object getId(Configurazione t) {
		return t.getId();
	}

	public String step1() {
		caricaConfigurazioneAttuale();
		if (getElement() == null) {
			setNewOrUpdate(true);
			setElement(new Configurazione());
			getElement().setDataCreazione(new Date());
		} else
			setNewOrUpdate(false);

		return EDIT + REDIRECT_PARAM;
	}

	public String step2() {
		// this.colonne = ConfigurazioneUtils.creaColonne(getElement());
		// this.ombrelloni = new ArrayList<Ombrellone>();
		if (newOrUpdate) {
			this.postiList = ConfigurazioniUtils
					.creaPostiSenzaNumero(getElement());
		} else {
			this.colonne = ConfigurazioniUtils.creaColonne(getElement());
			Ombrellone ombrellone = new Ombrellone();
			ombrellone.setConfigurazione(getElement());
			Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
			this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
			this.postiList = ConfigurazioniUtils.creaPostiConNumero(ombrelloni,
					getElement());
		}

		return STEP2GRIGLIA + REDIRECT_PARAM;
	}

	public String step3() {
		if (newOrUpdate) {
			configurazioneRepository.persist(getElement());
		} else {
			// elimina tutti i vecchi servizi della configurazione
			// ricrea tutti i nuovi servizi
			serviziRepository
					.eliminaTuttiServiziPerConfigurazione(getElement());
		}
		if (getElement().isAttuale()) {
			configurazioneRepository.disableAllAuthers(getElement().getId());
		}
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
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
		configurazioneRepository.update(getElement());
		propertiesController.reset();
		return VIEW + REDIRECT_PARAM;
	}

	public String addNew() {
		setNewOrUpdate(true);
		setElement(new Configurazione());
		getElement().setDataCreazione(new Date());
		return EDIT + REDIRECT_PARAM;
	}

	public String caricaConfigurazioneAttuale() {
		setElement(configurazioneRepository.findAttuale());
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
		return colonne;
	}

	@Override
	public String modElement() {
		super.modElement();
		setNewOrUpdate(false);
		return EDIT + REDIRECT_PARAM;

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
		return posti;
	}

	public void setPosti(List<Posto[]> posti) {
		this.posti = posti;
	}

	public String vediConfigurazioneOmbrelloniAttuale() {
		// caricaConfigurazioneAttuale();
		return vediConfigurazioneOmbrelloni();
	}

	public String vediConfigurazioneOmbrelloni() {
		this.colonne = ConfigurazioniUtils.creaColonne(getActual());
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
		this.posti = ConfigurazioniUtils.creaRighe(ombrelloni, getActual());
		return DIPOSIZIONE + REDIRECT_PARAM;
	}

	public List<Posto> getPostiList() {
		return postiList;
	}

	public void setPostiList(List<Posto> postiList) {
		this.postiList = postiList;
	}

	public Configurazione getActual() {
		if (actual == null)
			actual = configurazioneRepository.findAttuale();
		return actual;
	}

	public void setActual(Configurazione actual) {
		this.actual = actual;
	}
}
