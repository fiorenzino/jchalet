package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.ClientiSession;
import it.jflower.chalet4.par.Cliente;
import it.jflower.chalet4.par.Ricerca;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

@Named
@SessionScoped
public class ClientiHandler implements Serializable {

	private LazyDataModel<Cliente> clientiModel;

	private boolean editMode;

	@Inject
	@Log
	private transient Logger log;

	@Inject
	private ClientiSession clientiSession;

	private Cliente cliente;

	private SelectItem[] clientiItems;

	public ClientiHandler() {
		System.out.println("ClientiHandler ");
	}

	public SelectItem[] getClientiItems() {
		if (clientiItems == null) {
			List<Cliente> clienti = clientiSession.getAll();
			SelectItem[] items = new SelectItem[clienti.size() + 1];
			items[0] = new SelectItem(0, "scegli");
			int i = 1;
			for (Cliente cliente : clienti) {
				items[i++] = new SelectItem(cliente.getId(), cliente
						.getNomeCognome().toUpperCase());
			}
			clientiItems = items;
		}
		return clientiItems;
	}

	public String addCliente1() {
		this.cliente = new Cliente();
		return "/clienti/gestione-cliente.xhtml";
	}

	public String addCliente2() {
		log.info("PERSISTO CLIENTE");
		this.cliente.setDataInserimento(new Date());
		clientiSession.persist(this.cliente);
		log.info("PERSISTO CLIENTE: " + this.cliente.getId());
		aggModel();
		clientiItems = null;
		return "/clienti/scheda-cliente";
	}

	public String scheda(Long id) {
		this.cliente = clientiSession.find(id);
		return "/clienti/scheda-cliente";
	}

	public String modCliente1(Long id) {
		this.cliente = clientiSession.find(id);
		this.editMode = true;
		return "/clienti/gestione-cliente";
	}

	public String modCliente2() {
		clientiSession.update(this.cliente);
		aggModel();
		// Util.valorizzaCliente(cliente);
		clientiItems = null;
		return "/clienti/scheda-cliente";
	}

	public String delCliente() {
		// Long numContratti = JNDIUtils.getContrattiManager()
		// .getNumContrattiCliente(this.cliente.getId());
		// if (numContratti < 1) {
		// JNDIUtils.getClientiManager().delete(this.cliente);
		// }

		aggModel();
		clientiItems = null;
		return "/clienti/clienti";
	}

	public Cliente getCliente() {
		if (cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void aggModel() {
		this.clientiModel = null;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public LazyDataModel<Cliente> getClientiModel() {
		if (clientiModel == null || clientiModel.getRowCount() == 0) {
			int size = clientiSession.getListSize(new Ricerca());
			clientiModel = new LazyDataModel<Cliente>(size) {

				private static final long serialVersionUID = 1L;

				@Override
				public List<Cliente> fetchLazyData(int first, int pageSize) {
					log.info("carico i dati: da" + first + " a: " + first
							+ pageSize);
					List<Cliente> list = (List<Cliente>) clientiSession
							.getList(new Ricerca(), first, pageSize);
					return list;
				}
			};
		}
		return clientiModel;
	}
}
