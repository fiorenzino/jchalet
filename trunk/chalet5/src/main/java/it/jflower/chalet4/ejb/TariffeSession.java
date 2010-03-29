package it.jflower.chalet4.ejb;

import java.util.List;

import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Tariffa;

public interface TariffeSession {

	public void persist(Tariffa tariffa);

	public void persist(Costo costo);

	public void update(Costo costo);

	public List<Tariffa> getAllTariffe();

	public Tariffa find(Long id);
	
	public Tariffa fetch(Long id);

	public void update(Tariffa tariffa);

}
