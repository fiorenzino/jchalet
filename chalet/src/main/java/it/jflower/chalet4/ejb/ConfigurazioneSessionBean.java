package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.Configurazione;
import it.jflower.chalet4.par.FilaOmbrelloni;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ConfigurazioneSession.class)
public class ConfigurazioneSessionBean implements ConfigurazioneSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	@Inject
	@Log
	private transient Logger log;

	public void persist(Configurazione configurazione) {
		try {
			// for (FilaOmbrelloni fila : configurazione.getFileOmbrelloni()) {
			// em.persist(fila);
			// }
			em.persist(configurazione);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Configurazione findLast() {
		List<Configurazione> result = new ArrayList<Configurazione>();
		try {
			result = em.createQuery(
					"select t from Configurazione t order by t.id desc")
					.setFirstResult(0).setMaxResults(1).getResultList();
			if (result == null || result.isEmpty())
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result.get(0);
	}

	public Configurazione find(Long id) {
		try {
			Configurazione configurazione = em.find(Configurazione.class, id);
			log
					.info("file da db: "
							+ configurazione.getFileOmbrelloni().size());
			return configurazione;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void update(Configurazione configurazione) {
		try {
			em.merge(configurazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void persist(Object object) {
		try {
			em.persist(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(FilaOmbrelloni fila) {
		try {
			em.merge(fila);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
