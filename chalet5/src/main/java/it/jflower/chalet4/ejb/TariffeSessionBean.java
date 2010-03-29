package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Lettino;
import it.jflower.chalet4.par.Ombrellone;
import it.jflower.chalet4.par.Sdraio;
import it.jflower.chalet4.par.Tariffa;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(TariffeSession.class)
public class TariffeSessionBean implements TariffeSession {

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	@Inject
	@Log
	private transient Logger log;

	@Inject
	private OmbrelloniSession ombrelloniSession;

	@Inject
	private SdraieSession sdraieSession;

	@Inject
	private LettiniSession lettiniSession;

	@Inject
	private CabineSession cabineSession;

	public void persist(Tariffa tariffa) {
		try {
			List<Costo> costi = tariffa.getCostiValues();
			tariffa.setCosti(null);
			em.persist(tariffa);
			for (Costo costo : costi) {
				costo.setTariffa(tariffa);
				em.persist(costo);
				tariffa.addCosto("" + costo.getGiorno(), costo);
			}
			switch (tariffa.getServiceType()) {
			case 1:
				// 1, "ombrellone");
				List<Ombrellone> ombrelloni = ombrelloniSession
						.getAllOmbrelloni();
				log.info("num omb: " + ombrelloni.size());
				tariffa.setServiceName("OMBRELLONE");
				for (Ombrellone ombrellone : ombrelloni) {
					ombrellone.addTariffa(tariffa);
					ombrelloniSession.update(ombrellone);
				}
				break;
			case 2:
				// 2, "sdraio");
				tariffa.setServiceName("SDRAIO");
				List<Sdraio> sdraie = sdraieSession.getAllSdraie();
				for (Sdraio sdraio : sdraie) {
					sdraio.addTariffa(tariffa);
					sdraieSession.update(sdraio);
				}
				break;
			case 3:
				// 3, "lettino");
				tariffa.setServiceName("LETTINO");
				List<Lettino> lettini = lettiniSession.getAllLettini();
				for (Lettino lettino : lettini) {
					lettino.addTariffa(tariffa);
					lettiniSession.update(lettino);
				}
				break;
			case 4:
				// 4, "cabina");
				tariffa.setServiceName("CABINA");
				List<Cabina> cabine = cabineSession.getAllCabine();
				for (Cabina cabina : cabine) {
					cabina.addTariffa(tariffa);
					cabineSession.update(cabina);
				}
				break;

			default:
				throw new Exception();
			}
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Tariffa find(Long id) {
		try {
			Tariffa tariffa = em.find(Tariffa.class, id);
			return tariffa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Tariffa fetch(Long id) {
		try {
			Tariffa tariffa = em.find(Tariffa.class, id);
			tariffa.getCosti().size();
			return tariffa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Tariffa> getAllTariffe() {
		List<Tariffa> result = new ArrayList<Tariffa>();
		try {
			result = em.createQuery("select t from Tariffa t order by t.nome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(Tariffa tariffa) {
		try {
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void persist(Costo costo) {
		try {
			em.persist(costo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Costo costo) {
		try {
			em.merge(costo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
