package it.jflower.chalet2.ejb3;

import it.jflower.chalet2.par.Ombrellone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OmbrelloniSessionBean {

	@PersistenceContext(unitName = "chalet")
	EntityManager em;

	protected EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Ombrellone findOmbrellone(Long id) {
		try {
			return em.find(Ombrellone.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
