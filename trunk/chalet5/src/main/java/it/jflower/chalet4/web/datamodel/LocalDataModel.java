package it.jflower.chalet4.web.datamodel;

import it.jflower.chalet4.ejb.EjbManager;
import it.jflower.chalet4.par.Ricerca;

import java.util.List;

import javax.naming.NamingException;

public class LocalDataModel<T> extends PagedListDataModel<T> {

	private Ricerca ricerca;

	private EjbManager<T> eJBManager;

	public LocalDataModel(int pageSize, Ricerca ricerca,
			EjbManager<T> eJBManager) {
		super(pageSize);
		this.ricerca = ricerca;
		this.eJBManager = eJBManager;
	}

	public DataPage fetchPage(int startRow, int pageSize) {
		try {
			return getDataPage(startRow, pageSize);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DataPage getDataPage(int startRow, int pageSize)
			throws NamingException {
		List<T> data = eJBManager.getList(ricerca, startRow, pageSize);
		DataPage<T> dataPage = new DataPage(eJBManager.getListSize(ricerca),
				startRow, data);
		return dataPage;
	}
}
