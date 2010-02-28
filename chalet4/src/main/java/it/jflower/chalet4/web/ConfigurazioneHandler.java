package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.ConfigurazioneSession;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Configurazione;
import it.jflower.chalet4.par.FilaOmbrelloni;
import it.jflower.chalet4.par.Lettino;
import it.jflower.chalet4.par.Ombrellone;
import it.jflower.chalet4.par.Sdraio;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ConversationScoped
@Named
public class ConfigurazioneHandler implements Serializable {

	@Inject
	@Log
	private transient Logger log;

	@Inject
	private ConfigurazioneSession configurazioneSession;

	private Configurazione configurazione;

	public ConfigurazioneHandler() {

	}

	public String step1() {
		this.configurazione = new Configurazione();
		this.configurazione.setDataCreazione(new Date());
		return "configurazione1";
	}

	public String step2() {
		for (int i = 1; i <= this.configurazione.getNumeroFile(); i++) {
			FilaOmbrelloni fila = new FilaOmbrelloni();
			fila.setNumero(i);
			fila.setConfigurazione(configurazione);
			configurazione.addFilaOmbrelloni(fila);
		}
		configurazioneSession.persist(configurazione);
		return "configurazione2";
	}

	public String step3() {
		// GENERO OMBRELLONI
		for (FilaOmbrelloni fila : configurazione.getFileOmbrelloni()) {
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
		return "configurazione3";
	}

	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}
}
