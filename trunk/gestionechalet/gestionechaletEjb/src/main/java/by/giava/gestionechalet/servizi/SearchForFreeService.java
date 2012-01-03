package by.giava.gestionechalet.servizi;

import it.coopservice.commons2.domain.Search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.ConfigurazioneRepository;
import by.giava.gestionechalet.repository.OmbrelloniRepository;
import by.giava.gestionechalet.repository.PrenotazioniRepository;
import by.giava.gestionechalet.repository.ServiziRepository;

@Stateless
@LocalBean
public class SearchForFreeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PrenotazioniRepository prenotazioniRepository;

	@Inject
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@Inject
	ServiziRepository serviziRepository;

	public List<Servizio> findLibero(Date dal, Date al,
			TipoServizioEnum serviceEnum, Long confId, int num) {
		// cerco tutti quelli occupati nel periodo
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setTipoServizio(serviceEnum.name());
		prenotazione.setDataDal(dal);
		prenotazione.setDataAl(al);
		List<Prenotazione> occupati = prenotazioniRepository.getList(
				new Search<Prenotazione>(prenotazione), 0, 0);
		List<Long> notIn = new ArrayList<Long>();
		for (Prenotazione pren : occupati) {
			if (!notIn.contains(pren.getServizio().getId()))
				notIn.add(pren.getServizio().getId());
		}
		// cerco il primo servizio libero del tipo prescelto
		return serviziRepository.findNotIn(notIn, serviceEnum, confId, num);
	}

	public List<Ombrellone> cercaOmbrelloniStagionaliLiberi(
			Configurazione configurazione) {
		List<Ombrellone> occupati = cercaOmbrelloniStagionaliOccupati(configurazione);
		Configurazione conf = configurazioneRepository.findAttuale();
		List<Long> notIn = new ArrayList<Long>();
		for (Servizio servizio : occupati) {
			if (!notIn.contains(servizio.getId()))
				notIn.add(servizio.getId());
		}
		List<Servizio> liberi = serviziRepository.findNotIn(notIn,
				TipoServizioEnum.OMB, conf.getId(), 0);
		List<Ombrellone> libOmbrelloni = new ArrayList<Ombrellone>();
		for (Servizio servizio : liberi) {
			Ombrellone om = ombrelloniRepository.find(servizio.getId());
			libOmbrelloni.add(om);
		}
		return libOmbrelloni;
	}

	public List<Ombrellone> cercaOmbrelloniStagionaliOccupati(
			Configurazione configurazione) {
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setDataDal(configurazione.getDataInizioStagione());
		prenotazione.setDataAl(configurazione.getDataFineStagione());
		prenotazione.setTipoServizio(TipoServizioEnum.OMB.name());
		List<Prenotazione> occupati = prenotazioniRepository.getList(
				new Search<Prenotazione>(prenotazione), 0, 0);
		Map<Long, Ombrellone> notIn = new HashMap<Long, Ombrellone>();
		if (occupati != null && occupati.size() > 0)
			for (Prenotazione pren : occupati) {
				if (!notIn.containsKey(pren.getServizio().getId())) {
					Ombrellone om = ombrelloniRepository.find(pren
							.getServizio().getId());
					notIn.put(pren.getServizio().getId(), om);

				}
			}
		if (notIn.size() > 0)
			return new ArrayList<Ombrellone>(notIn.values());
		return null;
	}

	public List<Ombrellone> cercaOmbrelloniGiornalieriOccupati(
			Configurazione configurazione, Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setDataDal(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		prenotazione.setDataAl(cal.getTime());
		prenotazione.setTipoServizio(TipoServizioEnum.OMB.name());
		List<Prenotazione> occupati = prenotazioniRepository.getList(
				new Search<Prenotazione>(prenotazione), 0, 0);
		Map<Long, Ombrellone> notIn = new HashMap<Long, Ombrellone>();
		if (occupati != null && occupati.size() > 0)
			for (Prenotazione pren : occupati) {
				if (!notIn.containsKey(pren.getServizio().getId())) {
					Ombrellone om = ombrelloniRepository.find(pren
							.getServizio().getId());
					notIn.put(pren.getServizio().getId(), om);

				}
			}
		if (notIn.size() > 0)
			return new ArrayList<Ombrellone>(notIn.values());
		return null;
	}
}
