package by.giava.gestionechalet.servizi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.coopservice.commons2.domain.Search;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import by.giava.gestionechalet.enums.ServiceEnum;
import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.repository.PrenotazioniRepository;
import by.giava.gestionechalet.repository.ServiziRepository;

@Stateless
@LocalBean
public class SearchForFreeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PrenotazioniRepository prenotazioniRepository;

	@Inject
	ServiziRepository serviziRepository;

	public List<Servizio> findLibero(Date dal, Date al,
			ServiceEnum serviceEnum, Long confId, int num) {
		// cerco tutti quelli occupati nel periodo
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setTipoServizio(ServiceEnum.CAB.name());
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
		return serviziRepository.findOneNotIn(notIn, serviceEnum, confId, num);
	}
}
