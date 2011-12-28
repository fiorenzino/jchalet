package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.controller.util.PrenotazioniUtils;
import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.Preventivo;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.model.ServizioPrenotato;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.PrenotazioniRepository;
import by.giava.gestionechalet.repository.ServiziRepository;
import by.giava.gestionechalet.repository.TariffeRepository;
import by.giava.gestionechalet.servizi.SearchForFreeService;

@Named
@SessionScoped
public class PrenotazioniController extends
		AbstractLazyController<Prenotazione> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/prenotazioni/lista.xhtml";

	@EditPage
	public static final String EDIT = "/prenotazioni/gestione.xhtml";

	public static final String GIORNALIERO = "/prenotazioni/giornaliero.xhtml";

	public static final String CALCOLA = "/tariffe/calcola-preventivo.xhtml";

	public static final String EDIT_STAGIONALI = "/prenotazioni/stagionali.xhtml";

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

	@Inject
	SearchForFreeService searchForFreeService;

	private List<Preventivo> preventivi;
	private List<Prenotazione[]> prenotazioni;
	private List<String> colonne;
	private List<String> righe;
	private List<ServizioPrenotato> servizi;
	private int numAccessori = 1;
	private Map<TipoServizioEnum, Long> serviziMap;

	// private Ricerca ricerca;
	private float total;
	private String prop;

	@Override
	public Object getId(Prenotazione t) {
		return t.getId();
	}

	public String calcolaPreventivo() {
		// this.ricerca = new Ricerca();
		this.preventivi = null;
		return CALCOLA + "?faces-redirect=true";
	}

	public String creaPrenotazione() {
		reset();
		resetRicercaOmbrelloni();
		this.preventivi = null;
		return EDIT + "?faces-redirect=true";
	}

	public String creaPrenotazioneStagionale() {
		reset();
		resetRicercaOmbrelloni();
		getSearch().getObj().setSoloStagionali(true);
		getSearch().getObj().setDataDal(
				configurazioneController.getActual().getDataInizioStagione());
		getSearch().getObj().setDataAl(
				configurazioneController.getActual().getDataFineStagione());
		this.preventivi = null;
		return EDIT_STAGIONALI + "?faces-redirect=true";
	}

	public String creaPrenotazioneGiornaliero() {
		resetRicercaOmbrelloni();
		this.preventivi = null;
		return GIORNALIERO + "?faces-redirect=true";
	}

	public void calcolaPrezzoByServices() {
		this.total = 0;
		// CERCO LE TARIFFE PER PERIODO
		List<Preventivo> lista = tariffeRepository
				.getTariffeInPeriodForServiziPrenotati(getServizi(),
						getSearch().getObj().isSoloStagionali());
		// CALCOLO PER OGNI TARIFFA
		this.preventivi = new ArrayList<Preventivo>();
		for (Preventivo pre : lista) {
			this.total = total + pre.getTotal();
			this.preventivi.add(pre);
		}
	}

	public void calcolaPrezzo() {
		this.serviziMap = null;
		// numeroSdraie;
		if (getSearch().getObj().getFila() == null
				|| getSearch().getObj().getFila().equals("TUTTE"))
			getSearch().getObj().setFila(null);
		int num = 0;
		if (getSearch().getObj().getNumeroSdraie() > 0) {
			getServiziMap().put(TipoServizioEnum.SDR,
					new Long(getSearch().getObj().getNumeroSdraie()));
			num++;
		}
		// numeroLettini;
		if (getSearch().getObj().getNumeroLettini() > 0) {
			getServiziMap().put(TipoServizioEnum.LET,
					new Long(getSearch().getObj().getNumeroLettini()));
			num++;
		}
		// numeroCabine;
		if (getSearch().getObj().getNumeroCabine() > 0) {
			getServiziMap().put(TipoServizioEnum.CAB,
					new Long(getSearch().getObj().getNumeroCabine()));
			num++;
		}
		// // numero Ombrellone
		// if (getSearch().getObj().getNumero() > 0) {
		// getServiziMap().put(ServiceEnum.OMB,
		// new Long(ricerca.getNumeroOmbrelloni()));
		// num++;
		// }
		// numeroOmbrelloni
		if (getSearch().getObj().getNumeroOmbrelloni() > 0) {
			getServiziMap().put(TipoServizioEnum.OMB,
					new Long(getSearch().getObj().getNumeroOmbrelloni()));
			num++;
		}
		// numero sedie
		if (Integer.valueOf(getSearch().getObj().getNumeroSedie()) > 0) {
			getServiziMap().put(TipoServizioEnum.SED,
					new Long(getSearch().getObj().getNumeroSedie()));
			num++;
		}
		if (num == 0) {
			// aggiungi errore
			return;

		}
		cercaTariffePerPeriodo();
	}

	private void cercaTariffePerPeriodo() {
		this.total = 0;
		// CERCO LE TARIFFE PER PERIODO
		List<Preventivo> lista = tariffeRepository.getTariffeInPeriod(
				getSearch().getObj().getDataDal(), getSearch().getObj()
						.getDataAl(), getServiziMap(), getSearch().getObj()
						.isSoloStagionali(), getSearch().getObj().getFila());
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
		this.serviziMap = null;
		this.total = 0;
		reset();
	}

	public void ricercaOmbrelloniPerMese() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSearch().getObj().getDataDal());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		getSearch().getObj().setDataDal(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		getSearch().getObj().setDataAl(cal.getTime());
		ricercaOmbrelloni();
	}

	public void ricercaOmbrelloni() {
		// cerca ombrelloni dal/al
		if (getSearch().getObj().getDataDal() == null
				&& getSearch().getObj().getDataDal() == null)
			return;
		Search<Ombrellone> search = new Search<Ombrellone>(new Ombrellone(
				getSearch().getObj().getNumero(), getSearch().getObj()
						.getFila(), configurazioneController.getActual()));
		List<Ombrellone> ombrelloni = ombrelloniRepository
				.getList(search, 0, 0);
		// cerca tra le prenotazioni
		Map<String, Map<String, Prenotazione>> prenotazioniReali = prenotazioniRepository
				.getMappaPrenotazioni(getSearch());
		if (getSearch().getObj().isSoloLiberi()) {
			this.prenotazioni = PrenotazioniUtils.getPrenotazioniSoloLiberi(
					getSearch().getObj().getDataDal(), getSearch().getObj()
							.getDataAl(), ombrelloni, prenotazioniReali);
		} else {
			this.prenotazioni = PrenotazioniUtils.getPrenotazioniPerPeriodo(
					getSearch().getObj().getDataDal(), getSearch().getObj()
							.getDataAl(), ombrelloni, prenotazioniReali);
		}

		this.colonne = PrenotazioniUtils.creaColonne(getSearch().getObj()
				.getDataDal(), getSearch().getObj().getDataAl());
		this.righe = PrenotazioniUtils.creaRighe(ombrelloni);
		if (getSearch().getObj().isSoloStagionali()) {
			// TODO
		}
	}

	public void aggiungi() {
		logger.info("aggiungo");
	}

	public void aggiungOmbrellone() {
		aggiungiServizio("1", "1", "OMB");
	}

	public void aggiungiServizio(String fila, String numero, String tipo) {
		logger.info("aggiungiServizio: " + fila + ":" + numero + " " + tipo);
		List<Servizio> serv = null;
		switch (TipoServizioEnum.valueOf(tipo)) {
		case CAB:
			serv = searchForFreeService.findLibero(getSearch().getObj()
					.getDataDal(), getSearch().getObj().getDataAl(),
					TipoServizioEnum.CAB, configurazioneController.getActual()
							.getId(), getNumAccessori());
			if (serv != null) {
				for (Servizio servizio : serv) {
					Cabina cabina = new Cabina();
					cabina.setNumero(servizio.getNumero());
					cabina.setId(servizio.getId());
					cabina.setConfigurazione(configurazioneController
							.getActual());
					ServizioPrenotato servizioPrenotato = new ServizioPrenotato(
							getSearch().getObj().getDataDal(), getSearch()
									.getObj().getDataAl(), cabina);
					if (!cercaServizio(cabina.getNumero(), cabina.getTipo()
							.name(), false))
						getServizi().add(servizioPrenotato);
				}
			}
			break;
		case LET:
			serv = searchForFreeService.findLibero(getSearch().getObj()
					.getDataDal(), getSearch().getObj().getDataAl(),
					TipoServizioEnum.LET, configurazioneController.getActual()
							.getId(), getNumAccessori());

			if (serv != null) {
				for (Servizio servizio : serv) {
					Lettino lettino = new Lettino();
					lettino.setNumero(servizio.getNumero());
					lettino.setId(servizio.getId());
					lettino.setConfigurazione(configurazioneController
							.getActual());
					ServizioPrenotato servizioPrenotato = new ServizioPrenotato(
							getSearch().getObj().getDataDal(), getSearch()
									.getObj().getDataAl(), lettino);
					if (!cercaServizio(lettino.getNumero(), lettino.getTipo()
							.name(), false))
						getServizi().add(servizioPrenotato);
				}
			}
			break;
		case OMB:
			if (fila != null && !fila.isEmpty() && numero != null
					&& !numero.isEmpty()) {
				Ombrellone ombrellone = ombrelloniRepository.findByFilaNumero(
						fila, numero, configurazioneController.getActual()
								.getId());
				ServizioPrenotato servizioPrenotato = new ServizioPrenotato(
						getSearch().getObj().getDataDal(), getSearch().getObj()
								.getDataAl(), ombrellone);
				if (!cercaServizio(ombrellone.getNumero(), ombrellone.getTipo()
						.name(), false))
					getServizi().add(servizioPrenotato);
			}
			break;
		case SDR:
			serv = searchForFreeService.findLibero(getSearch().getObj()
					.getDataDal(), getSearch().getObj().getDataAl(),
					TipoServizioEnum.SDR, configurazioneController.getActual()
							.getId(), getNumAccessori());
			if (serv != null) {
				for (Servizio servizio : serv) {
					Sdraio sdraio = new Sdraio();
					sdraio.setNumero(servizio.getNumero());
					sdraio.setId(servizio.getId());
					sdraio.setConfigurazione(configurazioneController
							.getActual());
					ServizioPrenotato servizioPrenotato = new ServizioPrenotato(
							getSearch().getObj().getDataDal(), getSearch()
									.getObj().getDataAl(), sdraio);
					if (!cercaServizio(sdraio.getNumero(), sdraio.getTipo()
							.name(), false))
						getServizi().add(servizioPrenotato);
				}
			}
			break;
		case SED:
			serv = searchForFreeService.findLibero(getSearch().getObj()
					.getDataDal(), getSearch().getObj().getDataAl(),
					TipoServizioEnum.SDR, configurazioneController.getActual()
							.getId(), getNumAccessori());

			if (serv != null) {
				for (Servizio servizio : serv) {
					SediaRegista sediaRegista = new SediaRegista();
					sediaRegista.setNumero(servizio.getNumero());
					sediaRegista.setId(servizio.getId());
					sediaRegista.setConfigurazione(configurazioneController
							.getActual());
					ServizioPrenotato servizioPrenotato = new ServizioPrenotato(
							getSearch().getObj().getDataDal(), getSearch()
									.getObj().getDataAl(), sediaRegista);
					if (!cercaServizio(sediaRegista.getNumero(), sediaRegista
							.getTipo().name(), false))
						getServizi().add(servizioPrenotato);
				}
			}
			break;
		}

	}

	public void eliminaServizio(String numero, String tipo) {
		logger.info("elimino: " + numero);
		cercaServizio(numero, tipo, true);
	}

	public boolean cercaServizio(String numero, String tipo, boolean elimina) {
		for (ServizioPrenotato servizioPrenotato : getServizi()) {
			if (servizioPrenotato.getServizio().getNumero().equals(numero)
					&& servizioPrenotato.getServizio().getTipo()
							.equals(TipoServizioEnum.valueOf(tipo))) {
				if (elimina)
					getServizi().remove(servizioPrenotato);
				return true;
			}
		}
		return false;
	}

	public List<Preventivo> getPreventivi() {
		return preventivi;
	}

	public void setPreventivi(List<Preventivo> preventivi) {
		this.preventivi = preventivi;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
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

	public List<ServizioPrenotato> getServizi() {
		if (servizi == null)
			this.servizi = new ArrayList<ServizioPrenotato>();
		return servizi;
	}

	public void setServizi(List<ServizioPrenotato> servizi) {
		this.servizi = servizi;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		logger.info("prop: " + prop);
		this.prop = prop;
	}

	public int getNumAccessori() {
		return numAccessori;
	}

	public void setNumAccessori(int numAccessori) {
		this.numAccessori = numAccessori;
	}

	public Map<TipoServizioEnum, Long> getServiziMap() {
		if (serviziMap == null)
			this.serviziMap = new HashMap<TipoServizioEnum, Long>();
		return serviziMap;
	}

	public void setServiziMap(Map<TipoServizioEnum, Long> serviziMap) {
		this.serviziMap = serviziMap;
	}

	public void impostaRicercaStagionali() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.JUNE);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if (getSearch().getObj().isSoloStagionali()) {
			getSearch().getObj().setDataDal(cal.getTime());
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 30);
			getSearch().getObj().setDataAl(cal.getTime());
		}
	}
}
