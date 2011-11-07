package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.model.servizi.Ombrellone;

@Stateless
@LocalBean
public class OmbrelloniRepository extends BaseRepository<Ombrellone> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "numero asc";
	}

	public List<Ombrellone> getOmbrelloni(String fila) {
		List<Ombrellone> result = new ArrayList<Ombrellone>();
		String query = "select t from Ombrellone t where t.fila = :FILA order by t.numero asc";
		if (fila == null || fila.isEmpty()) {
			query = "select t from Ombrellone t order by t.numero asc";
		}
		try {
			Query queryR = em.createQuery(query);
			if (fila != null && !fila.isEmpty()) {
				queryR.setParameter("FILA", fila);
			}
			result = queryR.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Ombrellone findByFilaNumero(String fila, String numero) {
		Ombrellone ombrellone = null;
		try {
			ombrellone = (Ombrellone) em
					.createQuery(
							"select t from Ombrellone t where t.fila = :FILA and t.numero= :NUMERO")
					.setParameter("FILA", fila).setParameter("NUMERO", numero)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ombrellone;
	}

	@Override
	protected Query getRestrictions(Search<Ombrellone> search, boolean justCount) {

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

		// fila
		if (search.getObj().getFila() != null
				&& !search.getObj().getFila().isEmpty()) {
			sb.append(separator).append(" ").append(alias)
					.append(".fila = :fila ");
			// aggiunta alla mappa
			params.put("fila", search.getObj().getFila());
			// separatore
			separator = " and ";
		}

		// numero
		if (search.getObj().getNumero() != null
				&& !search.getObj().getNumero().isEmpty()) {
			sb.append(separator).append(" ").append(alias)
					.append(".numero = :numero ");
			// aggiunta alla mappa
			params.put("numero", search.getObj().getFila());
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
