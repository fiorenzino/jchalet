package it.jflower.chalet2.ejb3;

import it.jflower.chalet2.ann.ChaletRepository;
import it.jflower.chalet2.par.Fila;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Stateless
@Named
public class FileSessionBean {

	@Inject
	@ChaletRepository
	EntityManager em;

	@Inject
	Logger log;

	public Fila findFila(Long id) {
		try {
			return em.find(Fila.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Fila> getFile() {
		List<Fila> result = new ArrayList<Fila>();
		try {
			result = em.createQuery(
					"select t from Fila t WHERE t.attivo=true order by t.nome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public void passivate(Fila fila) {
		try {
			log.info("passivate classe");
			fila.setAttivo(false);
			em.merge(fila);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
