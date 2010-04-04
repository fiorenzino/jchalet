package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.ConfigurazioneSession;
import it.jflower.chalet4.ejb.utils.TimeUtil;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Configurazione;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.FilaOmbrelloni;
import it.jflower.chalet4.par.Lettino;
import it.jflower.chalet4.par.Ombrellone;
import it.jflower.chalet4.par.Sdraio;
import it.jflower.chalet4.par.Tariffa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named
public class ConfigurazioneHandler implements Serializable {

	@Inject
	@Log
	private transient Logger log;

	@Inject
	private ConfigurazioneSession configurazioneSession;

	private Configurazione configurazione;
	private Tariffa tariffa;

	public ConfigurazioneHandler() {
		System.out.println("creo configurazione");
	}

	public String step1() {
		this.configurazione = configurazioneSession.findLast();
		if (this.configurazione == null) {
			this.configurazione = new Configurazione();
			this.configurazione.setDataCreazione(new Date());
		}

		return "/configurazione/configurazione1?faces-redirect=true";
	}

	public String step2() {
		if (this.configurazione.getId() == null)
			configurazioneSession.persist(configurazione);
		else
			configurazioneSession.update(configurazione);
		for (int i = 1; i <= this.configurazione.getNumeroFile(); i++) {
			log.info("creo fila: " + i);
			FilaOmbrelloni fila = new FilaOmbrelloni();
			fila.setNumero(i);
			fila.setConfigurazione(configurazione);
			configurazioneSession.persist(fila);
			configurazione.addFilaOmbrelloni(fila);
		}
		configurazioneSession.update(configurazione);

		return "/configurazione/configurazione2?faces-redirect=true";
	}

	public String step3() {
		// GENERO OMBRELLONI
		for (FilaOmbrelloni fila : configurazione.getFileOmbrelloni()) {
			configurazioneSession.update(fila);
			log.info("step3 fila: " + fila.getNumero());
			for (Integer i = fila.getDal(); i <= fila.getAl(); i++) {
				Ombrellone ombrellone = new Ombrellone();
				ombrellone.setFila("" + fila.getNumero());
				ombrellone.setNumero(i + "");
				ombrellone.setAttivo(true);
				configurazioneSession.persist(ombrellone);
			}
			log.info("step3 fila: " + fila.getNumero() + " FINE");
		}

		for (int i = 1; i <= configurazione.getNumeroCabine(); i++) {
			Cabina cabina = new Cabina();
			cabina.setNumero(i + "");
			cabina.setAttivo(true);
			configurazioneSession.persist(cabina);

		}
		for (int i = 1; i <= configurazione.getNumeroLettini(); i++) {
			Lettino lettino = new Lettino();
			lettino.setAttivo(true);
			lettino.setNumero(i + "");
			configurazioneSession.persist(lettino);
		}
		for (int i = 1; i <= configurazione.getNumeroSdraio(); i++) {
			Sdraio sdraio = new Sdraio();
			sdraio.setAttivo(true);
			sdraio.setNumero(i + "");
			configurazioneSession.persist(sdraio);
		}
		return "/configurazione/configurazione3?faces-redirect=true";
	}

	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public Configurazione getActualConfigurazione() {
		return configurazioneSession.findLast();
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}
}
