package it.jflower.chalet2.ejb3;

import it.jflower.chalet2.ann.ChaletRepository;
import it.jflower.chalet2.par.Ombrellone;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class OmbrelloniSessionBean {

	@Inject
	@ChaletRepository
	EntityManager em;

	@Inject
	Logger log;

	public Ombrellone findOmbrellone(Long id) {
		try {
			return em.find(Ombrellone.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
