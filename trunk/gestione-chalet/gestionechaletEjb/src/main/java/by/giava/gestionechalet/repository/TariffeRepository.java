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

import by.giava.gestionechalet.enums.ServiceEnum;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.ServizioPrenotato;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.model.servizi.Cabina;
import by.giava.gestionechalet.model.servizi.Lettino;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.model.servizi.Sdraio;
import by.giava.gestionechalet.model.servizi.SediaRegista;
import by.giava.gestionechalet.pojo.Preventivo;
import by.giava.gestionechalet.repository.util.Tariffeutils;

@Stateless
@LocalBean
public class TariffeRepository extends BaseRepository<Tariffa> {

	private static final long serialVersionUID = 1L;

	@Inject
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	OmbrelloniRepository ombrelloniRepository;

	@Inject
	SdraieRepository sdraieRepository;

	@Inject
	LettiniRepository lettiniRepository;

	@Inject
	CabineRepository cabineRepository;

	@Inject
	SedieRegistaRepository sedieRegistaRepository;

	@Override
	protected String getDefaultOrderBy() {
		return "nome";
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Preventivo> getTariffeInPeriod(Date start, Date stop,
			Map<ServiceEnum, Long> servizi) {
		List<Preventivo> result = new ArrayList<Preventivo>();
		StringBuffer serviziS = new StringBuffer("");
		for (ServiceEnum service : servizi.keySet()) {
			serviziS.append(",'").append(service.name()).append("'");
		}
		try {
			List<Tariffa> resultP = (List<Tariffa>) em
					.createQuery(
							"select distinct(t) from Tariffa t left join fetch t.costi ti where (t.dal <= :START OR  t.al >= :STOP) AND t.serviceName in ("
									+ serviziS.toString().substring(1)
									+ ")order by t.nome")
					.setParameter("START", start).setParameter("STOP", stop)
					.getResultList();
			for (Tariffa tariffa : resultP) {
				// if (servizi.containsKey(tariffa.getServiceName())) {
				Preventivo pre = Tariffeutils.getPrenotazione(tariffa, start,
						stop, servizi.get(ServiceEnum.valueOf(tariffa
								.getServiceName())));
				result.add(pre);
				// }

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Preventivo> getTariffeInPeriodForServiziPrenotati(
			List<ServizioPrenotato> servizi) {
		List<Preventivo> result = new ArrayList<Preventivo>();
		for (ServizioPrenotato servizioPrenotato : servizi) {
			try {
				List<Tariffa> resultP = (List<Tariffa>) em
						.createQuery(
								"select distinct(t) from Tariffa t left join fetch t.costi ti where (t.dal <= :START OR  t.al >= :STOP) AND t.serviceName = :SERVICENAME order by t.nome")
						.setParameter("START", servizioPrenotato.getDal())
						.setParameter("STOP", servizioPrenotato.getAl())
						.setParameter(
								"SERVICENAME",
								servizioPrenotato.getServizio().getTipo()
										.name()).getResultList();
				if (resultP != null) {
					for (Tariffa tariffa : resultP) {
						// if (servizi.containsKey(tariffa.getServiceName())) {
						Preventivo pre = Tariffeutils.getPrenotazione(tariffa,
								servizioPrenotato.getDal(),
								servizioPrenotato.getAl(), 1L);
						result.add(pre);
						// }

					}
				}
			} catch (Exception e) {
				logger.info("errore: " + e.getMessage());
			}
		}
		return result;
	}

	@Override
	public boolean update(Tariffa tariffa) {
		try {

			List<Costo> costi = tariffa.getCostiValues();
			for (Costo costo : costi) {
				if (costo.getId() == null) {
					costo.setTariffa(tariffa);
					em.persist(costo);
				} else {
					em.merge(costo);
				}
			}
			em.merge(tariffa);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return false;
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
			// qui va presa la configurazione corrispondente
			Configurazione configurazione = configurazioneRepository
					.findAttuale();

			switch (tariffa.getServiceType()) {
			case 1:
				// 1, "ombrellone");
				// List<Ombrellone> ombrelloni =
				// ombrelloniRepository.getAllList();
				Ombrellone ombrelloneSearch = new Ombrellone();
				ombrelloneSearch.setConfigurazione(configurazione);
				Search<Ombrellone> ricercaOmbr = new Search<Ombrellone>(
						ombrelloneSearch);
				List<Ombrellone> ombrelloni = ombrelloniRepository.getList(
						ricercaOmbr, 0, 0);
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
				Sdraio sdraioSearch = new Sdraio();
				sdraioSearch.setConfigurazione(configurazione);
				Search<Sdraio> ricercaSdr = new Search<Sdraio>(sdraioSearch);
				List<Sdraio> sdraie = sdraieRepository
						.getList(ricercaSdr, 0, 0);
				for (Sdraio sdraio : sdraie) {
					sdraio.addTariffa(tariffa);
					sdraieRepository.update(sdraio);
				}
				break;
			case 3:
				// 3, "lettino");
				tariffa.setServiceName("LET");
				Lettino lettinoSearch = new Lettino();
				lettinoSearch.setConfigurazione(configurazione);
				Search<Lettino> ricercaLett = new Search<Lettino>(lettinoSearch);
				List<Lettino> lettini = lettiniRepository.getList(ricercaLett,
						0, 0);
				for (Lettino lettino : lettini) {
					lettino.addTariffa(tariffa);
					lettiniRepository.update(lettino);
				}
				break;
			case 4:
				// 4, "cabina");
				tariffa.setServiceName("CAB");
				Cabina cabinaSearch = new Cabina();
				cabinaSearch.setConfigurazione(configurazione);
				Search<Cabina> ricercaCab = new Search<Cabina>(cabinaSearch);
				List<Cabina> cabine = cabineRepository
						.getList(ricercaCab, 0, 0);
				for (Cabina cabina : cabine) {
					cabina.addTariffa(tariffa);
					cabineRepository.update(cabina);
				}
				break;
			case 5:
				// 5, "sedia");
				tariffa.setServiceName("SED");
				SediaRegista sediaSearch = new SediaRegista();
				sediaSearch.setConfigurazione(configurazione);
				Search<SediaRegista> ricercaSed = new Search<SediaRegista>(
						sediaSearch);
				List<SediaRegista> sedie = sedieRegistaRepository.getList(
						ricercaSed, 0, 0);
				for (SediaRegista sedia : sedie) {
					sedia.addTariffa(tariffa);
					sedieRegistaRepository.update(sedia);
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

		// serviceType
		if (search.getObj().getServiceName() != null
				&& !search.getObj().getServiceName().isEmpty()
				&& !search.getObj().getServiceName().equals("TUTTI")) {
			sb.append(separator).append(" ").append(alias)
					.append(".serviceName = :serviceName ");
			// aggiunta alla mappa
			params.put("serviceName", search.getObj().getServiceName());
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

	public Tariffa fetch(Object key) {
		logger.info("eseguo fetch tariffa");
		List<Tariffa> result = new ArrayList<Tariffa>();
		try {
			result = em
					.createQuery(
							"select t from Tariffa t left join fetch t.costi ti where t.id = :KEY")
					.setParameter("KEY", key).setMaxResults(1).getResultList();
			if (result == null || result.isEmpty())
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result.get(0);
	}

}
