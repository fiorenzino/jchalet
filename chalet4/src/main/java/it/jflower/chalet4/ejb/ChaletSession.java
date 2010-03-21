package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.par.Widget;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

public class ChaletSession {
	@Inject
	@ChaletRepository
	EntityManager em;

	@Produces
	@Named
	@RequestScoped
	@SuppressWarnings("unchecked")
	List<Widget> getServizi() {
		return em.createQuery("select w from Servizio w order by w.numero")
				.getResultList();
	}
}
