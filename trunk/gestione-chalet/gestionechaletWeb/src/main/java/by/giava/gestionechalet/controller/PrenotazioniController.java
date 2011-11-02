package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.pojo.Preventivo;
import by.giava.gestionechalet.pojo.Ricerca;
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
	TariffeRepository tariffeRepository;

	List<Preventivo> preventivi;

	private Ricerca ricerca;
	private double total;

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
		Map<String, Long> servizi = new HashMap<String, Long>();
		// numeroSdraie;
		int num = 0;
		if (ricerca.getNumeroSdraie() > 0) {
			servizi.put(Sdraio.TIPO, new Long(ricerca.getNumeroSdraie()));
			num++;
		}
		// numeroLettini;
		if (ricerca.getNumeroLettini() > 0) {
			servizi.put(Lettino.TIPO, new Long(ricerca.getNumeroLettini()));
			num++;
		}
		// numeroCabine;
		if (ricerca.getNumeroCabine() > 0) {
			servizi.put(Cabina.TIPO, new Long(ricerca.getNumeroCabine()));
			num++;
		}
		// numero Ombrellone
		if (ricerca.getNumero() > 0) {
			servizi.put(Ombrellone.TIPO,
					new Long(ricerca.getNumeroOmbrelloni()));
			num++;
		}
		// numeroOmbrelloni
		if (ricerca.getNumeroOmbrelloni() > 0) {
			servizi.put(Ombrellone.TIPO,
					new Long(ricerca.getNumeroOmbrelloni()));
			num++;
		}
		// numero sedie
		if (ricerca.getNumeroSedie() > 0) {
			servizi.put(SediaRegista.TIPO, new Long(ricerca.getNumeroSedie()));
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

	public void cercaOmbrelloni() {
		// cerca ombrelloni dal/al
		// cerca tra le prenotazioni
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

}
