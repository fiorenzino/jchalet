package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Tariffa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TariffeSessionBean implements TariffeSession {

	@Inject
	@ChaletRepository
	EntityManager em;

	public void persist(Tariffa tariffa) {
		try {
			em.persist(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Tariffa find(Long id) {
		try {
			Tariffa tariffa = em.find(Tariffa.class, id);
			return tariffa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Tariffa> getAllTariffe() {
		List<Tariffa> result = new ArrayList<Tariffa>();
		try {
			result = em.createQuery("select t from Tariffa t order by t.nome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Tariffa tariffa) {
		try {
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void persist(Costo costo) {
		try {
			em.persist(costo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Costo costo) {
		try {
			em.merge(costo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
