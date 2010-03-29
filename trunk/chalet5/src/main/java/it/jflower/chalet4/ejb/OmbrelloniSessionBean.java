package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Ombrellone;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(OmbrelloniSession.class)
public class OmbrelloniSessionBean implements OmbrelloniSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public Ombrellone find(Long id) {
		try {
			Ombrellone ombrellone = em.find(Ombrellone.class, id);
			return ombrellone;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Ombrellone> getAllOmbrelloni() {
		List<Ombrellone> result = new ArrayList<Ombrellone>();
		try {
			result = em.createQuery(
					"select t from Ombrellone t order by t.numero")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Ombrellone> getOmbrelloni(String fila) {
		List<Ombrellone> result = new ArrayList<Ombrellone>();
		try {
			result = em
					.createQuery(
							"select t from Ombrellone t where t.fila = :FILA order by t.numero")
					.setParameter("FILA", fila).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Ombrellone ombrellone) {
		try {
			em.merge(ombrellone);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
