package by.giava.gestionechalet.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
	private SelectItem[] fileItems = new SelectItem[] {};
	private SelectItem[] ombrelloniItems = new SelectItem[] {};
	private SelectItem[] numeroAccessori = new SelectItem[] {};

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
		fileItems = new SelectItem[] {};
		ombrelloniItems = new SelectItem[] {};
		numeroAccessori = new SelectItem[] {};
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
			for (int i = 0; i < 5; i++) {
				numeroAccessori[i] = new SelectItem(i, ""+i);
			}
		}
		return numeroAccessori;
	}

	@Produces
	@Named
	public TipoServizioEnum[] getServiceEnums() {
		return TipoServizioEnum.values();
	}

	@Produces
	@Named
	public SelectItem[] getFileItems() {
		if (fileItems.length == 0) {
			fileItems = new SelectItem[configurazioneController.getActual()
					.getNumeroFile().intValue() + 1];
			fileItems[0] = new SelectItem("TUTTE");
			for (int i = 1; i <= configurazioneController.getActual()
					.getNumeroFile().intValue(); i++) {
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
		List<Ombrellone> ombrelloni = ombrelloniRepository.findByFilaIdConf(
				fila, configurazioneController.getActual().getId());
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
