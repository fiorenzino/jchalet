package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.par.Ombrellone;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import javax.inject.Inject;

public class OmbrelloniSessionBean implements OmbrelloniSession {

	@Inject
	@ChaletRepository
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

}
