package it.jflower.chalet4.web;

import java.util.logging.Logger;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.Contratto;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ContrattiHandler {

	@Inject
	@Log
	private transient Logger log;
	
	private Contratto contratto;

	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public String viewContratto() {

		return "";
	}

	public String aggiungiContratto1() {

		return "";
	}

	public String aggiungiContratto2() {

		return "";
	}

	public String modificaContratto1() {

		return "";
	}

	public String modificaContratto2() {

		return "";
	}

}
