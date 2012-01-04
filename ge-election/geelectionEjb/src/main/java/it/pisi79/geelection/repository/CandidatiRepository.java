package it.pisi79.geelection.repository;

import it.coopservice.commons2.domain.Search;
import it.pisi79.geelection.domain.Candidato;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;

@Stateless
@LocalBean
@Named
public class CandidatiRepository extends BaseRepository<Candidato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "cognome";
	}


	@Override
	protected Query getRestrictions(Search<Candidato> search, boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String separator = " where ";

		// elezione
		if (search.getObj().getElezione() != null && search.getObj().getElezione().getId() != null ) {
			sb.append(separator).append(alias)
					.append(".elezione.id = :idElezione ");
			// aggiunta alla mappa
			params.put("idElezione", search.getObj().getElezione().getId());
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
