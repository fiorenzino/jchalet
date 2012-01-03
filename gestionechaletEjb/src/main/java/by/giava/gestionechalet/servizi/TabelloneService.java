package by.giava.gestionechalet.servizi;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;

@Stateless
@LocalBean
public class TabelloneService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	SearchForFreeService searchForFreeService;

	public List<Ombrellone> vistaStagionale(Configurazione configurazione) {
		// cerco tutti gli ombrelloni stagionali occupati
		List<Ombrellone> servizi = searchForFreeService
				.cercaOmbrelloniStagionaliOccupati(configurazione);

		return servizi;
	}

	public List<Ombrellone> vistaGiornaliera(Configurazione configurazione,
			Date data) {
		// cerco tutti gli ombrelloni della data occupati
		List<Ombrellone> servizi = searchForFreeService
				.cercaOmbrelloniGiornalieriOccupati(configurazione, data);
		return servizi;
	}
}
