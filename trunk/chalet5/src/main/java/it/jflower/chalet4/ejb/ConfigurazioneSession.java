package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Configurazione;
import it.jflower.chalet4.par.FilaOmbrelloni;

public interface ConfigurazioneSession {

	public void persist(Configurazione configurazione);

	public void persist(Object object);

	public Configurazione find(Long id);

	public Configurazione findLast();

	public void update(Configurazione configurazione);

	public void update(FilaOmbrelloni fila);
}
