package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.ClientiSession;
import it.jflower.chalet4.par.Cliente;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ClientiHandler {

	private boolean editMode;

	@Inject
	@Log
	private transient Logger log;

	@Inject
	private ClientiSession clientiSession;

	private Cliente cliente;

	private SelectItem[] clientiItems;

	private DataModel<Cliente> model;

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
		this.cliente.setDataInserimento(new Date());
		clientiSession.persist(this.cliente);
		aggModel();
		clientiItems = null;
		return "/clienti/scheda-cliente.xhtml";
	}

	public String modCliente1() {
		this.cliente = (Cliente) getModel().getRowData();
		this.editMode = true;
		return "/clienti/gestione-cliente.xhtml";
	}

	public String modCliente2() {
		clientiSession.update(this.cliente);
		aggModel();
		// Util.valorizzaCliente(cliente);
		clientiItems = null;
		return "/clienti/scheda-cliente.xhtml";
	}

	public String delCliente() {
		// Long numContratti = JNDIUtils.getContrattiManager()
		// .getNumContrattiCliente(this.cliente.getId());
		// if (numContratti < 1) {
		// JNDIUtils.getClientiManager().delete(this.cliente);
		// }

		aggModel();
		clientiItems = null;
		return "/clienti/clienti.xhtml";
	}

	public String detailCliente() {
		this.cliente = (Cliente) getModel().getRowData();
		// Util.valorizzaCliente(cliente);
		return "/clienti/scheda-cliente.xhtml";
	}

	public Cliente getCliente() {
		if (cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DataModel<Cliente> getModel() {
		return model;
	}

	public void setModel(DataModel<Cliente> model) {
		this.model = model;
	}

	public void aggModel() {

	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
