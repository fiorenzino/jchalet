package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.model.Cliente;

@Stateless
@LocalBean
public class ClientiRepository extends BaseRepository<Cliente> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "cognome asc";
	}

	@Override
	protected Query getRestrictions(Search<Cliente> search, boolean justCount) {

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

		// cognome
		if (search.getObj().getCognome() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".cognome LIKE :cognome ");
			// aggiunta alla mappa
			params.put("cognome", likeParamR(search.getObj().getCognome()));
			// separatore
			separator = " and ";
		}

		// telefono
		if (search.getObj().getId() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".telefono = :telefono ");
			// aggiunta alla mappa
			params.put("telefono", search.getObj().getTelefono());
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
