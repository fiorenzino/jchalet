package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Lettino;

import java.util.List;

public interface LettiniSession {
	public List<Lettino> getAllLettini();
	public Lettino find(Long id);
	public void update(Lettino lettino);
}
