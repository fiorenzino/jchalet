package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.ClientiSession;
import it.jflower.chalet4.par.Cliente;
import it.jflower.chalet4.par.Ricerca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

@Named
@SessionScoped
public class ClientiHandler implements Serializable {

	ClientiHandler() {
		System.out.println("vediamo se va ");
	}

	private LazyDataModel<Cliente> clientiModel;

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

	public String modCliente1() {
		this.cliente = (Cliente) getModel().getRowData();
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

	public LazyDataModel<Cliente> getClientiModel() {
		if (clientiModel == null) {
			clientiModel = new LazyDataModel<Cliente>(clientiSession
					.getListSize(new Ricerca())) {

				/**
				 * Dummy implementation of loading a certain segment of data. In
				 * a real applicaiton, this method should access db and do a
				 * limit based query
				 */
				@Override
				public List<Cliente> fetchLazyData(int first, int pageSize) {
					log.info("Loading the lazy car data between {} and {}"
							+ first + " " + first + pageSize);
					List<Cliente> lazyCars = clientiSession.getList(
							new Ricerca(), first, pageSize);
					return lazyCars;
				}
			};
		}
		if (clientiModel.getRowCount() > 0) {
			log.info("MODEL: " + clientiModel.getRowCount());
			// List<Cliente> lista = (List<Cliente>) clientiModel.getRowData();
			// for (Cliente cliente : lista) {
			// System.out.println("CL: " + cliente.getNome());
			// }
		}
		return clientiModel;
	}
}
