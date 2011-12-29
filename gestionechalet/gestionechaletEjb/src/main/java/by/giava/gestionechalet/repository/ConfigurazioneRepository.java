package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.model.Configurazione;

@Stateless
@LocalBean
public class ConfigurazioneRepository extends BaseRepository<Configurazione> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "dataCreazione desc";
	}

	public boolean disableAllAuthers(Long id) {
		List<Configurazione> result = new ArrayList<Configurazione>();
		try {
			result = em
					.createQuery(
							"select t from Configurazione t where t.id != :ID")
					.setParameter("ID", id).getResultList();
			for (Configurazione configurazione : result) {
				configurazione.setAttuale(false);
				em.merge(configurazione);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Configurazione findAttuale() {
		Configurazione result;
		try {
			result = (Configurazione) em
					.createQuery(
							"select t from Configurazione t where t.attuale = :ATTUALE")
					.setParameter("ATTUALE", true).getSingleResult();
			if (result == null)
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	protected Query getRestrictions(Search<Configurazione> search,
			boolean justCount) {

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
