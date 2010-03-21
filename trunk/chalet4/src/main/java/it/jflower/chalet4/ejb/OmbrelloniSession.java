package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Ombrellone;

import java.util.List;

public interface OmbrelloniSession {
	
	public List<Ombrellone> getAllOmbrelloni();

	public Ombrellone find(Long id);

	public List<Ombrellone> getOmbrelloni(String fila);

	public void update(Ombrellone ombrellone);
}
