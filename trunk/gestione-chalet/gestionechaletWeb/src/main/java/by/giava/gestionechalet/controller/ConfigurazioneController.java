package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractController;
import it.coopservice.commons2.domain.Search;

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
import by.giava.gestionechalet.repository.CabineRepository;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.FilaOmbrelloniRepository;
import by.giava.gestionechalet.repository.LettiniRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.SdraieRepository;

@Named
@SessionScoped
public class ConfigurazioneController extends
		AbstractController<Configurazione> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/clienti/lista.xhtml";

	@EditPage
	public static final String EDIT = "/clienti/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/configurazione/scheda.xhtml";

	public static final String STEP1 = "/configurazione/step1.xhtml";

	public static final String STEP2 = "/configurazione/step2.xhtml";

	public static final String STEP3 = "/configurazione/step3.xhtml";

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

	private Tariffa tariffa;

	private List<Ombrellone> ombrelloni;

	@Override
	public Object getId(Configurazione t) {
		return t.getId();
	}

	public String step1() {
		setElement(configurazioneRepository.findLast());
		if (getElement() == null) {
			setElement(new Configurazione());
			getElement().setDataCreazione(new Date());
		}

		return STEP1 + "?faces-redirect=true";
	}

	public String addNew() {
		setElement(new Configurazione());
		getElement().setDataCreazione(new Date());
		return STEP1 + "?faces-redirect=true";
	}

	public String step2() {
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

	public String step3() {
		// GENERO OMBRELLONI
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
		Ombrellone ombrellone = new Ombrellone();
		ombrellone.setConfigurazione(getElement());
		Search<Ombrellone> ricerca = new Search<Ombrellone>(ombrellone);
		this.ombrelloni = ombrelloniRepository.getList(ricerca, 0, 0);
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

}
