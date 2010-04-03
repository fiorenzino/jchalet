package it.jflower.chalet4.ejb;

import it.jflower.chalet4.par.Cliente;

public interface ClientiSession extends EjbManager {

	public Cliente find(Long id);

	public void update(Cliente cliente);

	public void persist(Cliente cliente);

	public void delete(Cliente cliente);
}
