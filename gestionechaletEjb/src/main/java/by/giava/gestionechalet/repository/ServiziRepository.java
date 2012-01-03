package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.model.Tariffa;

@Stateless
@LocalBean
public class ServiziRepository extends BaseRepository<Servizio> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "numero";
	}

	@Override
	protected Query getRestrictions(Search<Servizio> search, boolean justCount) {

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

		// tipoServizio
		if (search.getObj().getTipo() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".tipo = :tipoServizio ");
			// aggiunta alla mappa
			params.put("tipoServizio", search.getObj().getTipo());
			// separatore
			separator = " and ";
		}

		// configurazione
		if ((search.getObj().getConfigurazione() != null)
				&& (search.getObj().getConfigurazione().getId() != null)) {
			sb.append(separator).append(" ").append(alias)
					.append(".configurazione.id = :configurazioneId ");
			// aggiunta alla mappa
			params.put("configurazioneId", search.getObj().getConfigurazione()
					.getId());
			// separatore
			separator = " and ";
		}

		// NOT IN IDS
		if (search.getObj().getNotIn() != null
				&& search.getObj().getNotIn().size() > 0) {
			// estrazioen dei campi con cui fare match
			StringBuffer inValues = new StringBuffer();
			for (Long notInOne : search.getObj().getNotIn()) {
				inValues.append(", '" + notInOne + "'");
			}
			sb.append(separator)
					.append(alias)
					.append(".id NOT IN  (" + inValues.toString().substring(1)
							+ ")");
			// aggiunta alla mappa
			// params.put("idCliente", search.getObj().getIdCliente());
			// aggiorno separatore
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

	public void eliminaTuttiServiziPerConfigurazione(
			Configurazione configurazione) {
		try {
			Servizio servizio = new Servizio();
			servizio.setConfigurazione(configurazione);
			Search<Servizio> ricerca = new Search<Servizio>(servizio);
			List<Servizio> servizi = getList(ricerca, 0, 0);
			for (Servizio servizio2 : servizi) {
				logger.info("elimino servizio " + servizio2.getTipo()
						+ " - id: " + servizio2.getId());
				delete(servizio2.getId());

			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}

	public List<Servizio> findNotIn(List<Long> notIn,
			TipoServizioEnum serviceEnum, Long idConf, int num) {
		Servizio servizio = new Servizio();
		servizio.setConfigurazione(new Configurazione());
		servizio.getConfigurazione().setId(idConf);
		if (notIn != null && notIn.size() > 0) {
			servizio.setNotIn(notIn);
		} else {
			servizio.setTipo(serviceEnum);

		}
		List<Servizio> servizi = getList(new Search<Servizio>(servizio), 0, num);
		if (servizi != null && servizi.size() > 0)
			return servizi;
		return null;
	}

	@Override
	public Servizio fetch(Object key) {
		Servizio servizio = find(key);
		for (Tariffa tariffa : servizio.getTariffe()) {
			tariffa.getId();
			tariffa.getNome();
		}
		servizio.getConfigurazione().getId();
		servizio.getConfigurazione().getNome();
		return servizio;
	}
}
