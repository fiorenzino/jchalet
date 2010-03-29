package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.Ricerca;

import java.io.Serializable;
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

	private Ricerca ricerca;

	public String creaPrenotazione() {
		this.ricerca = new Ricerca();
		return "/prenotazioni/gestione-prenotazione1";
	}

	public String calcolaPrezzo() {

		return "/prenotazioni/gestione-prenotazione1";
	}

	public Ricerca getRicerca() {
		return ricerca;
	}

	public void setRicerca(Ricerca ricerca) {
		this.ricerca = ricerca;
	}

}
