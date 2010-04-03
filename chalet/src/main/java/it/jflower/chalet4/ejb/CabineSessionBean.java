package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Lettino;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(CabineSession.class)
public class CabineSessionBean implements CabineSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public Cabina find(Long id) {
		try {
			Cabina cabina = em.find(Cabina.class, id);
			return cabina;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Cabina> getAllCabine() {
		List<Cabina> result = new ArrayList<Cabina>();
		try {
			result = em.createQuery("select t from Cabina t order by t.numero")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Cabina cabina) {
		try {
			em.merge(cabina);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
