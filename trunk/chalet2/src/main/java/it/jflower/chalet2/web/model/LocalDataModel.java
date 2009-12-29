package it.jflower.chalet2.web.model;

import it.jflower.chalet2.par.Ricerca;

import java.util.List;

import javax.naming.NamingException;

public class LocalDataModel extends PagedListDataModel {
	
	public LocalDataModel(int pageSize, Ricerca ricerca) {
		super(pageSize);
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
		List data = eJBManager.getList(ricerca, startRow, pageSize);
		DataPage dataPage = new DataPage(eJBManager.getListSize(ricerca),
				startRow, data);
		return dataPage;
	}
}
