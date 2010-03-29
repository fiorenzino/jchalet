package it.jflower.chalet4.ejb;

import it.jflower.chalet4.ann.ChaletRepository;
import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.par.Cliente;
import it.jflower.chalet4.par.Ricerca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(ClientiSession.class)
@Named
public class ClientiSessionBean implements ClientiSession {

	@Inject
	@Log
	private transient Logger log;

	@PersistenceContext(unitName = "chaletPU")
	EntityManager em;

	public void delete(Cliente cliente) {
		try {
			Cliente cl = em.find(Cliente.class, cliente.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Cliente find(Long id) {
		Cliente cl = null;
		try {
			cl = em.find(Cliente.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cl;
	}

	public List<Cliente> getList(Ricerca ricerca, int startRow, int pageSize) {
		log.info("REWRITE SEARCH LIST");
		log.info("CLASS:" + ricerca.getClass());
		List<Cliente> result = null;
		try {
			try {
				Query res = getRestrictions(ricerca,
						"select a from Cliente as a where ",
						"order by a.cognome");
				result = (List<Cliente>) res.getResultList();
				log.info("getList SIZE: " + result.size());
				return result;
			} catch (Exception e) {
				log.info("Error calling getListSize()");
			}
		} catch (Exception e) {
			log.info("Error calling getList()");
		}
		return null;
	}

	private Query getRestrictions(Ricerca ricerca, String base, String order) {
		Map<String, Object> out = new HashMap<String, Object>();
		StringBuffer queryString = new StringBuffer(base);
		int length = base.length();
		try {
			if (ricerca.getIdClasse() != null) {

				// log.info("tipologia.classe.id");
				// query.setParameter("CLASSE", ricerca.getIdClasse());
				if (queryString.length() > length) {
					queryString.append(" AND ");
				}
				queryString.append("a.tipologia.classe.id = :CLASSE");
				out.put("CLASSE", ricerca.getIdClasse());
			}
			if (ricerca.getNome() != null) {
				// log.info("nome");
				// out.add(Restrictions.like("nome", ricerca.getNome()));
				if (queryString.length() > length) {
					queryString.append(" AND ");
				}
				queryString.append("( upper(a.nome) LIKE :NOME");
				out.put("NOME", "%" + ricerca.getNome().toUpperCase() + "%");
				queryString.append(" OR ");
				queryString.append("upper(a.tipologia.nome) LIKE :NOMETIP )");
				out.put("NOMETIP", "%" + ricerca.getNome().toUpperCase() + "%");
			}
			if (queryString.length() > length) {
				queryString.append(" AND a.attivo=true ");
			} else {
				queryString.append(" a.attivo=true ");
			}
			if (order != null)
				queryString.append(order);
			Query qu = getEm().createQuery(queryString.toString());
			for (String key : out.keySet()) {
				qu.setParameter(key, out.get(key));
			}
			return qu;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public int getListSize(Ricerca ricerca) {
		Long result = new Long(0);
		try {
			Query res = getRestrictions(ricerca,
					"select count(a) from Cliente as a where ", null);
			result = (Long) res.getSingleResult();
			log.info("query SIZE: " + result);
			return result.intValue();
		} catch (Exception e) {
			log.info("Error calling getListSize()");
		}
		return 0;
	}

	public void persist(Cliente cliente) {
		try {
			em.persist(cliente);
			System.out.println("id: " + cliente.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Cliente cliente) {
		try {
			em.merge(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public EntityManager getEm() {
		return em;
	}

	public List<Cliente> getAll() {
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			lista = em
					.createQuery("select c from Cliente c order by c.cognome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
