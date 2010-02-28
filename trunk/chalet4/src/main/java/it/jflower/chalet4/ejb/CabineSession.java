package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Cabina;

import java.util.List;

public interface CabineSession {
	public List<Cabina> getAllCabine();

	public Cabina find(Long id);

	public void update(Cabina cabina);

}
