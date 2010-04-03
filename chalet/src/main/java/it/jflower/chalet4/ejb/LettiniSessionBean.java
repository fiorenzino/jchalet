package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Lettino;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(LettiniSession.class)
public class LettiniSessionBean implements LettiniSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public Lettino find(Long id) {
		try {
			Lettino lettino = em.find(Lettino.class, id);
			return lettino;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Lettino> getAllLettini() {
		List<Lettino> result = new ArrayList<Lettino>();
		try {
			result = em
					.createQuery("select t from Lettino t order by t.numero")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Lettino lettino) {
		try {
			em.merge(lettino);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
