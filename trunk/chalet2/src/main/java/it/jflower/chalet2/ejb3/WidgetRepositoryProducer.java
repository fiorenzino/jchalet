package it.jflower.chalet2.ejb3;

import it.jflower.chalet2.ann.WidgetRepository;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class WidgetRepositoryProducer {
	// NOTE cannot use producer field because Weld attempts to close
	// EntityManager
	@PersistenceContext(unitName = "chalet")
	EntityManager em;

	@Produces
	@WidgetRepository
	public EntityManager retrieveEntityManager() {
		return em;
	}

	public void disposeEntityManager(
			@Disposes @WidgetRepository EntityManager em) {
	}
}
