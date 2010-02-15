package it.jflower.chalet4.web;

import it.jflower.chalet4.par.Configurazione;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@ConversationScoped
@Named
public class CreatorHandler implements Serializable {

	private Configurazione configurazione;

	public CreatorHandler() {

	}

	public String step1() {
		// INSERISCO NRO FILE E NUM OMBRELLONI

		// INSERISCO NRO COLONNE E NUM RIGHE
		return "";
	}

	public String step2() {

		return "";
	}

	public String step3() {

		return "";
	}

	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}
}
