package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.TariffeSession;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Lettino;
import it.jflower.chalet4.par.Ombrellone;
import it.jflower.chalet4.par.Preventivo;
import it.jflower.chalet4.par.Ricerca;
import it.jflower.chalet4.par.Sdraio;
import it.jflower.chalet4.par.Tariffa;
import it.jflower.chalet4.web.utils.Tariffeutils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PrenotazioniHandler implements Serializable {

	@Inject
	@Log
	private transient Logger log;

	@Inject
	TariffeSession tariffeSession;

	private Ricerca ricerca;

	private List<Preventivo> preventivi;

	private double total;

	public String calcolaPreventivo() {
		this.ricerca = new Ricerca();
		return "/prenotazioni/calcola-preventivo?faces-redirect=true";
	}

	public String creaPrenotazione() {
		this.ricerca = new Ricerca();
		return "/prenotazioni/gestione-prenotazione1?faces-redirect=true";
	}

	public void calcolaPrezzo() {
		this.total = 0;
		Map<String, Long> servizi = new HashMap<String, Long>();
		// numeroSdraie;
		if (ricerca.getNumeroSdraie() > 0)
			servizi.put(Sdraio.TIPO, new Long(ricerca.getNumeroSdraie()));
		// numeroLettini;
		if (ricerca.getNumeroLettini() > 0)
			servizi.put(Lettino.TIPO, new Long(ricerca.getNumeroLettini()));
		// numeroCabine;
		if (ricerca.getNumeroCabine() > 0)
			servizi.put(Cabina.TIPO, new Long(ricerca.getNumeroCabine()));
		// numeroOmbrelloni
		if (ricerca.getNumeroOmbrelloni() > 0)
			servizi.put(Ombrellone.TIPO,
					new Long(ricerca.getNumeroOmbrelloni()));
		// CERCO LE TARIFFE PER PERIODO
		List<Preventivo> lista = tariffeSession.getTariffeInPeriod(ricerca
				.getDal(), ricerca.getAl(), servizi);
		// CALCOLO PER OGNI TARIFFA
		this.preventivi = new ArrayList<Preventivo>();
		for (Preventivo pre : lista) {
			this.total = total + pre.getTotal();
			this.preventivi.add(pre);
		}
	}

	public Ricerca getRicerca() {
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
