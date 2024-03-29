package by.giava.gestionechalet.repository;

import it.coopservice.commons2.domain.Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import by.giava.gestionechalet.enums.StatoContrattoEnum;
import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Contratto;
import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.Preventivo;
import by.giava.gestionechalet.model.Servizio;
import by.giava.gestionechalet.model.ServizioPrenotato;

@Stateless
@LocalBean
public class ContrattiRepository extends BaseRepository<Contratto> {

	private static final long serialVersionUID = 1L;

	@Inject
	PrenotazioniRepository prenotazioniRepository;

	@Inject
	ServiziRepository serviziRepository;

	@Override
	protected String getDefaultOrderBy() {
		return "dataStipula desc";
	}

	@Override
	protected Query getRestrictions(Search<Contratto> search, boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String leftOuterJoinAlias = "s";
		if (search.getObj().getNumero() != null
				&& search.getObj().getNumero().trim().length() > 0) {
			sb.append(" left outer join ").append(alias)
					.append(".serviziPrenotati ").append(leftOuterJoinAlias);
			// sb.append(" on ").append(leftOuterJoinAlias).append(".allegati.id = ").append(alias).append(".id");
		}

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

		// numero ombrellone
		if (search.getObj().getNumero() != null
				&& !search.getObj().getNumero().trim().isEmpty()) {
			sb.append(separator).append(leftOuterJoinAlias)
					.append(".servizio.numero = :numero and ")
					.append(leftOuterJoinAlias)
					.append(".servizio.tipo = :tipoServizio ");
			// aggiunta alla mappa
			params.put("numero", search.getObj().getNumero());
			params.put("tipoServizio", TipoServizioEnum.OMB);
			// separatore
			separator = " and ";
		}

		// stato
		if (search.getObj().getStato() != null
				&& !search.getObj().getStato().equals(StatoContrattoEnum.TUTTI)) {
			sb.append(separator).append(" ").append(alias)
					.append(".stato = :stato ");
			// aggiunta alla mappa
			params.put("stato", search.getObj().getStato());
			// separatore
			separator = " and ";
		}

		// cliente cognome
		if (search.getObj().getCliente() != null
				&& search.getObj().getCliente().getCognome() != null
				&& !search.getObj().getCliente().getCognome().isEmpty()) {
			sb.append(separator).append(" ").append(alias)
					.append(".cliente.cognome = :cognome ");
			// aggiunta alla mappa
			params.put("cognome", search.getObj().getCliente().getCognome());
			// separatore
			separator = " and ";
		}

		// cliente id
		if (search.getObj().getCliente() != null
				&& search.getObj().getCliente().getId() != null) {
			sb.append(separator).append(" ").append(alias)
					.append(".cliente.id = :idCliente ");
			// aggiunta alla mappa
			params.put("idCliente", search.getObj().getCliente().getId());
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
	public Contratto persist(Contratto contratto) {
		// TODO Auto-generated method stub
		for (ServizioPrenotato servizioPrenotato : contratto
				.getServiziPrenotati()) {
			Servizio servizio = serviziRepository.fetch(servizioPrenotato
					.getServizio().getId());
			servizioPrenotato.setServizio(servizio);

		}
		for (Preventivo preventivo : contratto.getPreventivi()) {
			preventivo.getTariffa();
		}
		super.persist(contratto);
		for (ServizioPrenotato servizioPrenotato : contratto
				.getServiziPrenotati()) {
			prenotazioniRepository
					.creaPrenotazionePerServizio(servizioPrenotato);
		}
		return contratto;
	}

	@Override
	public boolean delete(Object key) {
		Contratto contratto = find(key);
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setContratto(contratto);
		Search<Prenotazione> search = new Search<Prenotazione>(prenotazione);
		List<Prenotazione> prenotazioni = prenotazioniRepository.getList(
				search, 0, 0);
		for (Prenotazione pre : prenotazioni) {
			em.remove(pre);
		}

		return super.delete(key);
	}

	@Override
	public boolean update(Contratto contratto) {
		for (ServizioPrenotato servizioPrenotato : contratto
				.getServiziPrenotati()) {
			Servizio servizio = serviziRepository.fetch(servizioPrenotato
					.getServizio().getId());
			servizioPrenotato.setServizio(servizio);
		}
		for (Preventivo preventivo : contratto.getPreventivi()) {
			preventivo.getTariffa();
		}
		return super.update(contratto);
	}

	@Override
	public List<Contratto> getList(Search<Contratto> ricerca, int startRow,
			int pageSize) {
		// TODO Auto-generated method stub
		List<Contratto> lista = super.getList(ricerca, startRow, pageSize);
		for (Contratto contratto : lista) {
			contratto.getCliente().getId();
			contratto.getCliente().getCognome();
			contratto.getCliente().getNome();
			for (ServizioPrenotato servizioPrenotato : contratto
					.getServiziPrenotati()) {
				servizioPrenotato.getServizio().getId();
				servizioPrenotato.getDal();
			}
		}
		return lista;
	}

	@Override
	public Contratto fetch(Object key) {
		Contratto contratto = super.fetch(key);
		contratto.getCliente().getId();
		contratto.getCliente().getCognome();
		contratto.getCliente().getNome();
		for (ServizioPrenotato servizioPrenotato : contratto
				.getServiziPrenotati()) {
			servizioPrenotato.getServizio().getId();
			servizioPrenotato.getDal();
		}
		for (Preventivo preventivo : contratto.getPreventivi()) {
			preventivo.getTariffa();
		}
		return contratto;
	}

}
