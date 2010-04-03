package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Contratto;
import it.jflower.chalet4.par.Ricerca;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ContrattiSession.class)
public class ContrattiSessionBean implements ContrattiSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public List<Contratto> getAll() {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public List<Contratto> getList(Ricerca ricerca, int startRow, int pageSize) {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public int getListSize(Ricerca ricerca) {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

}
