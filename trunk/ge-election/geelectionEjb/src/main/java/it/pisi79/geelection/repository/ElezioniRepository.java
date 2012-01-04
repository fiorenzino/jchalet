package it.pisi79.geelection.repository;

import it.coopservice.commons2.domain.Search;
import it.pisi79.geelection.domain.Candidato;
import it.pisi79.geelection.domain.Elezione;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;

@Stateless
@LocalBean
@Named
public class ElezioniRepository extends BaseRepository<Elezione> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "dal";
	}

	@Override
	public Elezione fetch(Object key) {
		Elezione e = super.fetch(key);
		int totaleVoti = 0;
		for (Candidato c : e.getCandidati()) {
			c.toString();
			c.setNroVoti(c.getVoti().size());
			totaleVoti += c.getNroVoti();
		}
		e.setTotaleVoti(totaleVoti);
		Collections.sort(e.getCandidati());
//		for ( Elettore l : e.getElettori() ) {
//			l.toString();
//		}
		e.setTotaleElettori(e.getElettori().size());
		return e;
	}

	@Override
	protected Query getRestrictions(Search<Elezione> search, boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String separator = " where ";

		// attivo
		sb.append(separator).append(alias).append(".attivo = :attivo ");
		params.put("attivo", true);
		separator = " and ";

		// nome
		if (search.getObj().getNome() != null
				&& search.getObj().getNome().length() > 0) {
			sb.append(separator).append("upper(").append(alias)
					.append(".nome) like :nome ");
			// aggiunta alla mappa
			params.put("nome", likeParam(search.getObj().getNome()
					.toUpperCase()));
			// separatore
			separator = " and ";
		}

		// desc
		if (search.getObj().getDescrizione() != null
				&& search.getObj().getDescrizione().length() > 0) {
			sb.append(separator).append("upper(").append(alias)
					.append(".descrizione) like :desc ");
			// aggiunta alla mappa
			params.put("desc", likeParam(search.getObj().getDescrizione()
					.toUpperCase()));
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

	public Elezione fetchByDigest(String digest) {
		try {
			Elezione e = (Elezione) getEm()
					.createQuery(
							"select e from " + Elezione.class.getSimpleName()
									+ " e where e.digest = :digest ")
					.setParameter("digest", digest).getSingleResult();
			return this.fetch(e.getId());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
