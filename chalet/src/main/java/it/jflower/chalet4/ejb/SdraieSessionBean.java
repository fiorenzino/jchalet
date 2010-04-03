package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.par.Sdraio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(SdraieSession.class)
public class SdraieSessionBean implements SdraieSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public Sdraio find(Long id) {
		try {
			Sdraio ombrellone = em.find(Sdraio.class, id);
			return ombrellone;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Sdraio> getAllSdraie() {
		List<Sdraio> result = new ArrayList<Sdraio>();
		try {
			result = em.createQuery("select t from Sdraio t order by t.numero")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Sdraio sdraio) {
		try {
			em.merge(sdraio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
