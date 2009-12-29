package it.jflower.chalet2.ejb3.utils;

import it.jflower.chalet2.ann.ChaletRepository;

import java.util.logging.Logger;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class GeneralProducer {

	@Inject
	@ChaletRepository
	EntityManager em;

	// @Produces
	// @Named
	// @RequestScoped
	// @SuppressWarnings("unchecked")
	// List<Widget> getWidgets() {
	// return widgetRepository.createQuery(
	// "select w from Widget w order by w.name").getResultList();
	// }

	@Produces
	@ChaletRepository
	public EntityManager retrieveEntityManager() {
		return em;
	}

	public void disposeEntityManager(
			@Disposes @ChaletRepository EntityManager em) {
	}

	@Produces
	Logger createLogger(InjectionPoint injectionPoint) {

		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());

	}
}
