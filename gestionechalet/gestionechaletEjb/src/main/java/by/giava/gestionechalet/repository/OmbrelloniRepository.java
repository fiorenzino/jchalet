package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.util.AlphanumComparator;
import by.giava.gestionechalet.repository.util.OmbrelloniUtils;

@Stateless
@LocalBean
public class OmbrelloniRepository extends BaseRepository<Ombrellone> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "numero asc";
	}

	public List<Ombrellone> findByFilaIdConf(String fila, Long idConf) {
		List<Ombrellone> result = new ArrayList<Ombrellone>();
		try {
			result = em
					.createQuery(
							"select t from Ombrellone t where t.fila = :FILA and t.configurazione.id = :IDCONF")
					.setParameter("FILA", fila).setParameter("IDCONF", idConf)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Ombrellone findByFilaNumeroIdConf(String fila, String numero,
			Long idConf) {
		Ombrellone ombrellone = null;
		try {
			ombrellone = (Ombrellone) em
					.createQuery(
							"select t from Ombrellone t where t.fila = :FILA and t.numero= :NUMERO and t.configurazione.id = :IDCONF")
					.setParameter("FILA", fila).setParameter("NUMERO", numero)
					.setParameter("IDCONF", idConf).getSingleResult();
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
				&& !search.getObj().getFila().isEmpty()
				&& !search.getObj().getFila().equals("TUTTE")) {
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

	@Override
	public List<Ombrellone> getList(Search<Ombrellone> ricerca, int startRow,
			int pageSize) {
		// TODO Auto-generated method stub
		List<Ombrellone> lista = super.getList(ricerca, startRow, pageSize);
		Collections.sort(lista, OmbrelloniUtils.OMBRELLONI_NUMBERS);
		return lista;
	}

}
