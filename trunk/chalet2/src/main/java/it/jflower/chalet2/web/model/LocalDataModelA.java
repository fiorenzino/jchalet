package it.jflower.chalet2.web.model;

import it.coopservice.ordinativi.ejb3.utils.JNDIUtils;
import it.coopservice.ordinativi.par.base.Ricerca;

import java.util.List;

import javax.naming.NamingException;

public class LocalDataModelA extends PagedListDataModel {

	private Ricerca ricerca;

	public LocalDataModelA(int pageSize, Ricerca ricerca) {
		super(pageSize);
		this.ricerca = ricerca;
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
		List data = JNDIUtils.getAziendeSession().getList(ricerca, startRow,
				pageSize);
		DataPage dataPage = new DataPage(JNDIUtils.getAziendeSession()
				.getListSize(ricerca), startRow, data);
		return dataPage;
	}
}
