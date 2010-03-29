package it.jflower.chalet4.web;

import it.jflower.chalet4.ejb.OmbrelloniSession;
import it.jflower.chalet4.par.Configurazione;
import it.jflower.chalet4.par.Ombrellone;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PropertiesHandler implements Serializable {

	@Inject
	ConfigurazioneHandler configurazioneHandler;

	@Inject
	PrenotazioniHandler prenotazioniHandler;

	@Inject
	OmbrelloniSession ombrelloniSession;

	private SelectItem[] serviziItems = new SelectItem[] {};
	private SelectItem[] fileItems = new SelectItem[] {};
	private SelectItem[] ombrelloniItems = new SelectItem[] {};
	private SelectItem[] numeroAccessori = new SelectItem[] {};

	public SelectItem[] getNumeroAccessori() {
		if (numeroAccessori.length == 0) {
			numeroAccessori = new SelectItem[5];
			numeroAccessori[0] = new SelectItem(0, "0");
			numeroAccessori[1] = new SelectItem(1, "1");
			numeroAccessori[2] = new SelectItem(2, "2");
			numeroAccessori[3] = new SelectItem(3, "3");
			numeroAccessori[4] = new SelectItem(4, "4");
			
		}
		return numeroAccessori;
	}

	public SelectItem[] getServiziItems() {
		if (serviziItems.length == 0) {
			serviziItems = new SelectItem[4];
			serviziItems[0] = new SelectItem(1, "ombrellone");
			serviziItems[1] = new SelectItem(2, "sdraio");
			serviziItems[2] = new SelectItem(3, "lettino");
			serviziItems[3] = new SelectItem(4, "cabina");
		}
		return serviziItems;
	}

	public SelectItem[] getFileItems() {
		Configurazione config = configurazioneHandler.getActualConfigurazione();
		if (fileItems.length == 0) {
			fileItems = new SelectItem[config.getNumeroFile().intValue()];
			for (int i = 0; i < config.getNumeroFile().intValue(); i++) {
				int num = i + 1;
				fileItems[i] = new SelectItem(num, num + "");
			}
		}
		return fileItems;
	}

	public SelectItem[] getOmbrelloniItems() {
		System.out.println("getOmbrelloniItems: " + ombrelloniItems.length);
		return ombrelloniItems;
	}

	public void cambioFila(ActionEvent event) {
		System.out.println("cambio fila");
		Integer fila = prenotazioniHandler.getRicerca().getFila();
		List<Ombrellone> ombrelloni = ombrelloniSession.getOmbrelloni(fila
				.toString());
		ombrelloniItems = new SelectItem[ombrelloni.size()];
		int i = 0;
		System.out.println("ombrelloni: " + ombrelloni.size());
		for (Ombrellone ombrellone : ombrelloni) {
			ombrelloniItems[i] = new SelectItem(ombrellone.getNumero(),
					ombrellone.getNumero());
			i++;
		}
	}

}
