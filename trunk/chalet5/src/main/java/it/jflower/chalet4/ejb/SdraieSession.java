package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Sdraio;

import java.util.List;

public interface SdraieSession {

	public List<Sdraio> getAllSdraie();

	public Sdraio find(Long id);

	public void update(Sdraio sdraio);

}
