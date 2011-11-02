package by.giava.gestionechalet.controller;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.enums.ServiceType;
import by.giava.gestionechalet.model.Cliente;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.ClientiRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;

@Named
@SessionScoped
@SuppressWarnings("rawtypes")
public class PropertiesController implements Serializable {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(PropertiesController.class.getName());

	private Map<Class, SelectItem[]> items = null;
	private SelectItem[] serviziItems = new SelectItem[] {};
	private SelectItem[] fileItems = new SelectItem[] {};
	private SelectItem[] ombrelloniItems = new SelectItem[] {};
	private SelectItem[] numeroAccessori = new SelectItem[] {};
	private SelectItem[] serviziNames = new SelectItem[] {};

	@Inject
	ClientiRepository clienteRepository;

	@Inject
	ConfigurazioneController configurazioneController;

	@Inject
	PrenotazioniController prenotazioniController;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@PostConstruct
	public void reset() {
		items = new HashMap<Class, SelectItem[]>();
	}

	public void resetItemsForClass(Class clazz) {
		if (items.containsKey(clazz)) {
			items.remove(clazz);
		}
	}

	@Produces
	@Named
	public SelectItem[] getClienti() {
		if (items.get(Cliente.class) == null) {
			List<SelectItem> si = new ArrayList<SelectItem>();
			for (Cliente c : clienteRepository.getAllList()) {
				si.add(new SelectItem(c.getId(), c.getCognome()));
			}
			items.put(Cliente.class, si.toArray(new SelectItem[] {}));
		}
		return items.get(Cliente.class);
	}

	@Produces
	@Named
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

	@Produces
	@Named
	public SelectItem[] getServiziItems() {
		if (serviziItems.length == 0) {
			serviziItems = new SelectItem[5];
			serviziItems[0] = new SelectItem(1, "ombrellone");
			serviziItems[1] = new SelectItem(2, "sdraio");
			serviziItems[2] = new SelectItem(3, "lettino");
			serviziItems[3] = new SelectItem(4, "cabina");
			serviziItems[4] = new SelectItem(5, "sedia");
		}
		return serviziItems;
	}

	@Produces
	@Named
	public SelectItem[] getServiziNames() {
		if (serviziNames.length == 0) {
			serviziNames = new SelectItem[ServiceType.values().length + 1];
			serviziNames[0] = new SelectItem("TUTTI", "tutti");
			int i = 1;
			for (ServiceType service : ServiceType.values()) {
				serviziNames[i] = new SelectItem(service.name().substring(0, 3)
						.toUpperCase(), service.name());
				i++;
			}
		}
		return serviziNames;
	}

	@Produces
	@Named
	public SelectItem[] getFileItems() {
		if (fileItems.length == 0) {
			configurazioneController.caricaConfigurazioneAttuale();
			Configurazione config = configurazioneController.getElement();
			fileItems = new SelectItem[config.getNumeroFile().intValue()];
			for (int i = 0; i < config.getNumeroFile().intValue(); i++) {
				int num = i + 1;
				fileItems[i] = new SelectItem(num, num + "");
			}
		}
		return fileItems;
	}

	@Produces
	@Named
	public SelectItem[] getOmbrelloniItems() {
		System.out.println("getOmbrelloniItems: " + ombrelloniItems.length);
		return ombrelloniItems;
	}

	public void cambioFila() {
		logger.info("cambio fila");
		Integer fila = prenotazioniController.getRicerca().getFila();
		Configurazione config = configurazioneController.getElement();
		Ombrellone ombrelloneS = new Ombrellone();
		ombrelloneS.setFila("" + fila);
		ombrelloneS.setConfigurazione(config);
		Search<Ombrellone> search = new Search<Ombrellone>(ombrelloneS);
		List<Ombrellone> ombrelloni = ombrelloniRepository
				.getList(search, 0, 0);
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
