package it.jflower.chalet4.ejb.utils;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.ann.Log;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ChaletProducer {

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

	@Produces
	@Log
	Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}
}
