package by.giava.gestionechalet.controller;

import it.coopservice.commons2.domain.Search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.enums.StatoContrattoEnum;
import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Cliente;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.ClientiRepository;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.util.AlphanumComparator;

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
	private SelectItem[] statiContratto = new SelectItem[] {};

	@Inject
	ClientiRepository clienteRepository;

	@Inject
	ConfigurazioneController configurazioneController;

	@Inject
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	PrenotazioniController prenotazioniController;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	private final Comparator<SelectItem> SELECT_ITEMS = new Comparator<SelectItem>() {
		public int compare(SelectItem o1, SelectItem o2) {
			Comparator<Object> comparator = new AlphanumComparator();
			if (o1 == null || o1.getValue() == null)
				return Integer.MIN_VALUE;
			else
				return comparator.compare(o1.getLabel().toUpperCase(), o2
						.getLabel().toUpperCase());
		}
	};

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
				si.add(new SelectItem(c.getId(), c.getCognome() + " "
						+ c.getNome()));
			}
			Collections.sort(si, SELECT_ITEMS);
			items.put(Cliente.class, si.toArray(new SelectItem[] {}));
		}
		return items.get(Cliente.class);
	}

	// @Produces
	// @Named
	// public SelectItem[] getStatiContratto() {
	// if (statiContratto.length == 0) {
	// statiContratto = new SelectItem[StatoContrattoEnum.values().length + 1];
	// statiContratto[0] = new SelectItem("TUTTI", "tutti");
	// int i = 1;
	// for (StatoContrattoEnum service : StatoContrattoEnum.values()) {
	// statiContratto[i] = new SelectItem(service.name());
	// i++;
	// }
	// }
	// return statiContratto;
	// }

	@Produces
	@Named
	public StatoContrattoEnum[] getStatoContrattoEnums() {
		return StatoContrattoEnum.values();
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

	// @Produces
	// @Named
	// public SelectItem[] getServiziItems() {
	// if (serviziItems.length == 0) {
	// serviziItems = new SelectItem[5];
	// serviziItems[0] = new SelectItem(4, "cabina");
	// serviziItems[1] = new SelectItem(3, "lettino");
	// serviziItems[2] = new SelectItem(1, "ombrellone");
	// serviziItems[3] = new SelectItem(2, "sdraio");
	// serviziItems[4] = new SelectItem(5, "sedia");
	// }
	// return serviziItems;
	// }

	// @Produces
	// @Named
	// public SelectItem[] getServiziNames() {
	// if (serviziNames.length == 0) {
	// serviziNames = new SelectItem[ServiceEnum.values().length + 1];
	// // serviziNames[0] = new SelectItem("TUTTI", "tutti");
	// int i = 1;
	// for (ServiceEnum service : ServiceEnum.values()) {
	// serviziNames[i] = new SelectItem(service, service.name());
	// i++;
	// }
	// Arrays.sort(serviziNames, SELECT_ITEMS);
	// }
	//
	// return serviziNames;
	// }

	@Produces
	@Named
	public TipoServizioEnum[] getServiceEnums() {
		return TipoServizioEnum.values();
	}

	@Produces
	@Named
	public SelectItem[] getFileItems() {
		if (fileItems.length == 0) {
			configurazioneController.caricaConfigurazioneAttuale();
			Configurazione config = configurazioneController.getElement();
			fileItems = new SelectItem[config.getNumeroFile().intValue() + 1];
			fileItems[0] = new SelectItem("TUTTE");
			for (int i = 1; i <= config.getNumeroFile().intValue(); i++) {
				fileItems[i] = new SelectItem("" + i);
			}
			// Arrays.sort(fileItems, SELECT_ITEMS);
		}
		return fileItems;
	}

	@Produces
	@Named
	public SelectItem[] getOmbrelloniItems() {
		logger.info("getOmbrelloniItems: " + ombrelloniItems.length);
		return ombrelloniItems;
	}

	public void cambioFila() {
		logger.info("cambio fila");
		caricaOmbrelloni(""
				+ prenotazioniController.getSearch().getObj().getFila());
	}

	private void caricaOmbrelloni(String fila) {
		Search<Ombrellone> search = new Search<Ombrellone>(new Ombrellone(fila,
				configurazioneController.getActual()));
		List<Ombrellone> ombrelloni = ombrelloniRepository
				.getList(search, 0, 0);
		ombrelloniItems = new SelectItem[ombrelloni.size() + 1];
		int i = 1;
		logger.info("ombrelloni: " + ombrelloni.size());
		for (Ombrellone ombrellone : ombrelloni) {
			ombrelloniItems[i] = new SelectItem(ombrellone.getNumero(),
					ombrellone.getNumero());
			i++;
		}
		Arrays.sort(ombrelloniItems, SELECT_ITEMS);
		ombrelloniItems[0] = new SelectItem("", "tutti");
	}

	public void cambioFilaPrenotazione() {
		logger.info("cambio fila per prenotazioni");
		caricaOmbrelloni(prenotazioniController.getSearch().getObj().getFila());
	}

}
