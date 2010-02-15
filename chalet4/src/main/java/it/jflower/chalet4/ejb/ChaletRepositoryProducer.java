package it.jflower.chalet4.ejb;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ChaletRepositoryProducer {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	@Produces
	@ChaletRepository
	public EntityManager retrieveEntityManager() {
		return em;
	}

	public void disposeEntityManager(
			@Disposes @ChaletRepository EntityManager em) {
	}
}
