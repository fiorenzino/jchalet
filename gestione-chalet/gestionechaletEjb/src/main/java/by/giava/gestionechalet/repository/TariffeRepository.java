package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;

import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.pojo.Preventivo;
import by.giava.gestionechalet.repository.util.Tariffeutils;

@Stateless
@LocalBean
public class TariffeRepository extends BaseRepository<Tariffa> {

	private static final long serialVersionUID = 1L;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@Inject
	SdraieRepository sdraieRepository;

	@Inject
	LettiniRepository lettiniRepository;

	@Inject
	CabineRepository cabineRepository;

	@Override
	protected String getDefaultOrderBy() {
		return "numero";
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Preventivo> getTariffeInPeriod(Date start, Date stop,
			Map<String, Long> servizi) {
		List<Preventivo> result = new ArrayList<Preventivo>();
		try {
			List<Tariffa> resultP = (List<Tariffa>) em
					.createQuery(
							"select t from Tariffa t where (t.dal <= :START OR  t.al >= :STOP) order by t.nome")
					.setParameter("START", start).setParameter("STOP", stop)
					.getResultList();
			for (Tariffa tariffa : resultP) {
				if (servizi.containsKey(tariffa.getServiceName())) {
					Preventivo pre = Tariffeutils.getPrenotazione(tariffa,
							start, stop, servizi.get(tariffa.getServiceName()));
					result.add(pre);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tariffa persist(Tariffa tariffa) {
		try {
			List<Costo> costi = tariffa.getCostiValues();
			tariffa.setCosti(null);
			em.persist(tariffa);
			for (Costo costo : costi) {
				costo.setTariffa(tariffa);
				em.persist(costo);
				tariffa.addCosto(costo.getGiorno(), costo);
			}
			switch (tariffa.getServiceType()) {
			case 1:
				// 1, "ombrellone");
				List<Ombrellone> ombrelloni = ombrelloniRepository.getAllList();
				logger.info("num omb: " + ombrelloni.size());
				tariffa.setServiceName("OMB");
				for (Ombrellone ombrellone : ombrelloni) {
					ombrellone.addTariffa(tariffa);
					ombrelloniRepository.update(ombrellone);
				}
				break;
			case 2:
				// 2, "sdraio");
				tariffa.setServiceName("SDR");
				List<Sdraio> sdraie = sdraieRepository.getAllList();
				for (Sdraio sdraio : sdraie) {
					sdraio.addTariffa(tariffa);
					sdraieRepository.update(sdraio);
				}
				break;
			case 3:
				// 3, "lettino");
				tariffa.setServiceName("LET");
				List<Lettino> lettini = lettiniRepository.getAllList();
				for (Lettino lettino : lettini) {
					lettino.addTariffa(tariffa);
					lettiniRepository.update(lettino);
				}
				break;
			case 4:
				// 4, "cabina");
				tariffa.setServiceName("CAB");
				List<Cabina> cabine = cabineRepository.getAllList();
				for (Cabina cabina : cabine) {
					cabina.addTariffa(tariffa);
					cabineRepository.update(cabina);
				}
				break;

			default:
				throw new Exception();
			}
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tariffa;

	}

	@Override
	protected Query getRestrictions(Search<Tariffa> search, boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String separator = " where ";

		// attivo
		sb.append(separator).append(" ").append(alias)
				.append(".attivo = :attivo ");
		// aggiunta alla mappa
		params.put("attivo", true);
		// separatore
		separator = " and ";

		// id
		if (search.getObj().getId() != null) {
			sb.append(separator).append(" ").append(alias).append(".id = :id ");
			// aggiunta alla mappa
			params.put("id", search.getObj().getId());
			// separatore
			separator = " and ";
		}

		if (!justCount) {
			sb.append(getOrderBy(alias, search.getOrder()));
		}

		Query q = getEm().createQuery(sb.toString());
		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

}
