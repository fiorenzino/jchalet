package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.controller.util.PrenotazioniUtils;
import by.giava.gestionechalet.enums.ServiceEnum;
import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.pojo.Preventivo;
import by.giava.gestionechalet.pojo.Ricerca;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.PrenotazioniRepository;
import by.giava.gestionechalet.repository.ServiziRepository;
import by.giava.gestionechalet.repository.TariffeRepository;

@Named
@SessionScoped
public class PrenotazioniController extends
		AbstractLazyController<Prenotazione> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/prenotazioni/lista.xhtml";

	@EditPage
	public static final String EDIT = "/prenotazioni/gestione.xhtml";

	public static final String CALCOLA = "/prenotazioni/calcola-preventivo.xhtml";

	@Inject
	@OwnRepository(PrenotazioniRepository.class)
	PrenotazioniRepository prenotazioniRepository;

	@Inject
	TariffeRepository tariffeRepository;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@Inject
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	ConfigurazioneController configurazioneController;

	@Inject
	ServiziRepository serviziRepository;

	List<Preventivo> preventivi;

	List<Prenotazione[]> prenotazioni;
	List<String> colonne;
	List<String> righe;
	List<Servizio> servizi;

	private Ricerca ricerca;
	private double total;
	private String prop;

	@Override
	public Object getId(Prenotazione t) {
		return t.getId();
	}

	public String calcolaPreventivo() {
		this.ricerca = new Ricerca();
		this.preventivi = null;
		return CALCOLA + "?faces-redirect=true";
	}

	public String creaPrenotazione() {
		this.ricerca = new Ricerca();
		this.preventivi = null;
		return EDIT + "?faces-redirect=true";
	}

	public void calcolaPrezzo() {
		this.total = 0;
		Map<ServiceEnum, Long> servizi = new HashMap<ServiceEnum, Long>();
		// numeroSdraie;
		int num = 0;
		if (ricerca.getNumeroSdraie() > 0) {
			servizi.put(ServiceEnum.SDR, new Long(ricerca.getNumeroSdraie()));
			num++;
		}
		// numeroLettini;
		if (ricerca.getNumeroLettini() > 0) {
			servizi.put(ServiceEnum.LET, new Long(ricerca.getNumeroLettini()));
			num++;
		}
		// numeroCabine;
		if (ricerca.getNumeroCabine() > 0) {
			servizi.put(ServiceEnum.CAB, new Long(ricerca.getNumeroCabine()));
			num++;
		}
		// numero Ombrellone
		if (ricerca.getNumero() > 0) {
			servizi.put(ServiceEnum.OMB,
					new Long(ricerca.getNumeroOmbrelloni()));
			num++;
		}
		// numeroOmbrelloni
		if (ricerca.getNumeroOmbrelloni() > 0) {
			servizi.put(ServiceEnum.OMB,
					new Long(ricerca.getNumeroOmbrelloni()));
			num++;
		}
		// numero sedie
		if (ricerca.getNumeroSedie() > 0) {
			servizi.put(ServiceEnum.SED, new Long(ricerca.getNumeroSedie()));
			num++;
		}
		if (num == 0) {
			// aggiungi errore
			return;

		}
		// CERCO LE TARIFFE PER PERIODO
		List<Preventivo> lista = tariffeRepository.getTariffeInPeriod(
				ricerca.getDal(), ricerca.getAl(), servizi);
		// CALCOLO PER OGNI TARIFFA
		this.preventivi = new ArrayList<Preventivo>();
		for (Preventivo pre : lista) {
			this.total = total + pre.getTotal();
			this.preventivi.add(pre);
		}
	}

	public void resetRicercaOmbrelloni() {
		this.colonne = null;
		this.prenotazioni = null;
		this.righe = null;
		this.servizi = null;
	}

	public void ricercaOmbrelloni() {
		// cerca ombrelloni dal/al

		Search<Ombrellone> search = new Search<Ombrellone>(new Ombrellone(
				getSearch().getObj().getNumero(), getSearch().getObj()
						.getFila(), configurazioneController.getActual()));
		List<Ombrellone> ombrelloni = ombrelloniRepository
				.getList(search, 0, 0);
		// cerca tra le prenotazioni
		Map<String, Map<String, Prenotazione>> prenotazioniReali = prenotazioniRepository
				.getMappaPrenotazioni(getSearch());
		this.prenotazioni = PrenotazioniUtils.getPrenotazioniPerPeriodo(
				getSearch().getObj().getDataDal(), getSearch().getObj()
						.getDataAl(), ombrelloni, prenotazioniReali);
		this.colonne = PrenotazioniUtils.creaColonne(getSearch().getObj()
				.getDataDal(), getSearch().getObj().getDataAl());
		this.righe = PrenotazioniUtils.creaRighe(ombrelloni);
	}

	public void aggiungi() {
		logger.info("aggiungo");
	}

	public void aggiungOmbrellone() {
		aggiungiServizio("1:1", "OMB");
	}

	public void aggiungiServizio(String filaNumero, String tipo) {
		logger.info("aggiungiServizio: " + filaNumero + " " + tipo);
		switch (ServiceEnum.valueOf(tipo)) {
		case CAB:
			Cabina cabina = new Cabina();
			getServizi().add(cabina);
			break;
		case LET:
			Lettino lettino = new Lettino();
			getServizi().add(lettino);
			break;
		case OMB:
			if (filaNumero != null && filaNumero.contains(":")) {
				String fila = filaNumero.substring(0, filaNumero.indexOf(":"));
				String numero = filaNumero
						.substring(filaNumero.indexOf(":") + 1);
				Ombrellone ombrellone = ombrelloniRepository.findByFilaNumero(
						fila, numero);
				getServizi().add(ombrellone);
			}
			break;
		case SDR:
			Sdraio sdraio = new Sdraio();
			getServizi().add(sdraio);
			break;
		case SED:
			SediaRegista sediaRegista = new SediaRegista();
			getServizi().add(sediaRegista);
			break;
		}

	}

	public void eliminaServizio(int id) {
		logger.info("elimino: " + id);
		getServizi().remove(id);
	}

	public Ricerca getRicerca() {
		if (ricerca == null)
			this.ricerca = new Ricerca();
		return ricerca;
	}

	public void setRicerca(Ricerca ricerca) {
		this.ricerca = ricerca;
	}

	public List<Preventivo> getPreventivi() {
		return preventivi;
	}

	public void setPreventivi(List<Preventivo> preventivi) {
		this.preventivi = preventivi;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Prenotazione[]> getPrenotazioni() {
		if (prenotazioni == null)
			this.prenotazioni = new ArrayList<Prenotazione[]>();
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione[]> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public List<String> getColonne() {
		if (colonne == null)
			this.colonne = new ArrayList<String>();
		return colonne;
	}

	public void setColonne(List<String> colonne) {
		this.colonne = colonne;
	}

	public List<String> getRighe() {
		if (righe == null)
			this.righe = new ArrayList<String>();
		return righe;
	}

	public void setRighe(List<String> righe) {
		this.righe = righe;
	}

	public List<Servizio> getServizi() {
		if (servizi == null)
			this.servizi = new ArrayList<Servizio>();
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		System.out.println("prop: " + prop);
		this.prop = prop;
	}

}