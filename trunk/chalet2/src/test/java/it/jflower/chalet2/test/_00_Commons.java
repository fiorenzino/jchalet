package it.jflower.chalet2.test;

import it.jflower.chalet2.ejb3.OmbrelloniSessionBean;
import it.jflower.chalet2.ejb3.ServiziSessionBean;
import it.jflower.chalet2.par.Ombrellone;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

abstract public class _00_Commons {

	private static final String UNIT_NAME = "chalet";
	protected static ServiziSessionBean serviziEJB;
	protected static OmbrelloniSessionBean ombrelloniEJB;

	protected static EntityManager em;
	protected EntityTransaction etx;

	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// inizializzo la base di dati gestita da hibernate
		Map<String, String> configOverrides = new HashMap<String, String>();
		// configOverrides.put("hibernate.show_sql","true");
		// configOverrides.put("hibernate.hbm2ddl.auto","create-drop");
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory(UNIT_NAME, configOverrides);
		em = entityManagerFactory.createEntityManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	// -------------------------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		initBeans();
		etx = em.getTransaction();
		etx.begin();
	}

	private void initBeans() {
		serviziEJB = new ServiziSessionBean();
		serviziEJB.setEm(em);

	}

	@After
	public void tearDown() throws Exception {
		etx.rollback();
	}

	public static final int QUANTI_OMBR = 230;

	protected Ombrellone[] creaOmbrelloni() {
		Ombrellone[] ombrelloni = new Ombrellone[QUANTI_OMBR];
		for (int i = 0; i < QUANTI_OMBR; i++) {
			int fila = (i / 10) + 1;
			ombrelloni[i] = new Ombrellone();
			ombrelloni[i].setNumero(i + "");
			ombrelloni[i].setAnno(new Long(2009));
			ombrelloni[i].setFila(fila + "");
			ombrelloni[i].setStagionale(new Float(1100));
		}
		for (int i = 0; i < ombrelloni.length; i++) {
			em.persist(ombrelloni[i]);
			em.clear();
		}
		return ombrelloni;
	}

	protected Ombrellone rileggiOmbrellone(Long id) {
		Ombrellone a = ombrelloniEJB.findOmbrellone(id);
		em.clear();
		return a;
	}
}
