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
import java.util.List;
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
		return "/prenotazioni/calcola-preventivo";
	}

	public String creaPrenotazione() {
		this.ricerca = new Ricerca();
		return "/prenotazioni/gestione-prenotazione1";
	}

	public void calcolaPrezzo() {
		List<String> servizi = new ArrayList<String>();
		// numeroSdraie;
		if (ricerca.getNumeroSdraie() > 0)
			servizi.add(Sdraio.TIPO);
		// numeroLettini;
		if (ricerca.getNumeroLettini() > 0)
			servizi.add(Lettino.TIPO);
		// numeroCabine;
		if (ricerca.getNumeroCabine() > 0)
			servizi.add(Cabina.TIPO);
		// numeroOmbrelloni
		if (ricerca.getNumeroOmbrelloni() > 0)
			servizi.add(Ombrellone.TIPO);
		// CERCO LE TARIFFE PER PERIODO
		List<Tariffa> lista = tariffeSession.getTariffeInPeriod(ricerca
				.getDal(), ricerca.getAl(), servizi);
		// CALCOLO PER OGNI TARIFFA
		this.preventivi = new ArrayList<Preventivo>();
		for (Tariffa tariffa : lista) {
			Preventivo pre = Tariffeutils.getPrenotazione(tariffa, ricerca
					.getDal(), ricerca.getAl());
			if (pre.getTotal() != 0)
				total += pre.getTotal();
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
