package it.jflower.chalet2.ejb3;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ServiziSessionBean {

	@PersistenceContext(unitName = "chalet")
	EntityManager em;

	protected EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
