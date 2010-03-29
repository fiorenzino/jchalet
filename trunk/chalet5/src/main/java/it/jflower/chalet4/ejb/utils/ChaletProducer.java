package it.jflower.chalet4.ejb.utils;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.ann.Log;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class ChaletProducer {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	@Produces
	@ChaletRepository
	public EntityManager retrieveEntityManager() {
		System.out.println("PRODUCES");
		return em;
	}

	public void disposeEntityManager(
			@Disposes @ChaletRepository EntityManager em) {
		System.out.println("DISPONGO");
	}

	@Produces
	@Log
	Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}
}
