package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Ricerca;

import java.util.List;

public interface EjbManager<T> {
	public List<T> getList(Ricerca ricerca, int startRow, int pageSize);

	public List<T> getAll();

	public int getListSize(Ricerca ricerca);
}
