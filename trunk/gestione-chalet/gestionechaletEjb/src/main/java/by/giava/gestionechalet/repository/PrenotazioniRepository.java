package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import by.giava.gestionechalet.model.Prenotazione;

@Stateless
@LocalBean
public class PrenotazioniRepository extends BaseRepository<Prenotazione> {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "data asc";
	}

	@Override
	protected Query getRestrictions(Search<Prenotazione> search,
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

		// tipoServizio
		if (search.getObj().getTipoServizio() != null
				&& !search.getObj().getTipoServizio().isEmpty()) {
			sb.append(separator).append(" ").append(alias)
					.append(".servizio.tipo = :tipoServizio ");
			// aggiunta alla mappa
			params.put("tipoServizio", search.getObj().getTipoServizio());
			// separatore
			separator = " and ";
		}

		// id servizio
		if (search.getObj().getServizio() != null
				&& search.getObj().getServizio().getId() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".servizio.id = :idServizio ");
			// aggiunta alla mappa
			params.put("idServizio", search.getObj().getServizio().getId());
			// separatore
			separator = " and ";
		}

		// numero servizio
		if (search.getObj().getNumero() != null
				&& !search.getObj().getNumero().isEmpty()) {
			sb.append(separator).append(" ").append(alias)
					.append(".numero = :numero ");
			// aggiunta alla mappa
			params.put("numero", search.getObj().getNumero());
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

		// per data AL
		if (search.getObj().getDataAl() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".data <= :dataAl ");
			// aggiunta alla mappa
			params.put("dataAl", search.getObj().getDataAl());
			// separatore
			separator = " and ";
		}

		// per data DAL
		if (search.getObj().getDataDal() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".data >= :dataDal ");
			// aggiunta alla mappa
			params.put("dataDal", search.getObj().getDataDal());
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

	public Map<String, Map<String, Prenotazione>> getMappaPrenotazioni(
			Search<Prenotazione> search) {
		Map<String, Map<String, Prenotazione>> mappa = new HashMap<String, Map<String, Prenotazione>>();
		List<Prenotazione> prenotazioni = getList(search, 0, 0);
		for (Prenotazione prenotazione : prenotazioni) {
			String filaNumero = prenotazione.getFila() + "-"
					+ prenotazione.getNumero();
			if (!mappa.containsKey(filaNumero)) {
				mappa.put(filaNumero, new HashMap<String, Prenotazione>());
			}
			mappa.get(filaNumero).put(prenotazione.getSingleDayName(),
					prenotazione);
		}
		return mappa;
	}
}
