package it.jflower.chalet2.web;

import it.jflower.chalet2.ann.LocalDataModel;
import it.jflower.chalet2.ejb3.FileSessionBean;
import it.jflower.chalet2.par.Fila;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Model
public class FileHandler implements Serializable {

	private Fila classe;

	@Inject
	private FileSessionBean fileSessionBean;

	private Fila temp1 = new Fila(true);
	private Fila temp2 = new Fila(true);

	private int rowsPerPage = 10;
	private int scrollerPage = 1;
	private Logger log = Logger.getLogger(getClass().getName());

	@LocalDataModel(pageSize = 0, startRow = 0)
	private ListDataModel model;

	public FileHandler() {

	}

	public ListDataModel getModel() {
		if (this.model == null) {
			List<Fila> classiList = new ArrayList<Fila>();
			classiList = fileSession.getFile();
			model = new ListDataModel(classiList);
		}
		return model;
	}

	public void addNewClasse() {
		updateClassi();
	}

	public void setModel(ListDataModel model) {
		this.model = model;
	}

	public void modClasse() {
		Classe classe = (Classe) getModel().getRowData();
		classe.setModificabile(true);
	}

	public void saveClasse() {
		Classe classe = (Classe) getModel().getRowData();
		JNDIUtils.getClassiSession().update(classe);
		classe.setModificabile(false);
		this.model = null;
		PropertiesHandler loc = (PropertiesHandler) JSFUtils
				.getManagedBean("propertiesHandler");
		loc.setClassiItems(new SelectItem[] {});
	}

	public void annullaEditClasse() {
		Classe classe = (Classe) getModel().getRowData();
		classe.setModificabile(false);
		this.model = null;
	}

	public void deleteClasse() {
		// Classe classe = (Classe) getModel().getRowData();
		// Long res = JNDIUtils.getTipologieSession().getTipologieByClassIdSize(
		// classe.getId());
		// log.info("CI SONO " + res
		// + " TIPOLOGIE COLLEGATE ALLA CLASSE PASSIVATA!");
		// JNDIUtils.getClassiSession().passivate(classe);
		// this.model = null;
		// PropertiesHandler loc = (PropertiesHandler) JSFUtils
		// .getManagedBean("propertiesHandler");
		// loc.setClassiItems(new SelectItem[] {});

	}

	public String updateClassi() {
		boolean mod = false;
		if (temp1.getNome().compareTo("") != 0) {

			JNDIUtils.getClassiSession().persist(temp1);
			temp1 = new Classe(true);
			mod = true;
		}
		if (temp2.getNome().compareTo("") != 0) {

			JNDIUtils.getClassiSession().persist(temp2);
			temp2 = new Classe(true);
			mod = true;
		}
		if (mod) {
			this.model = null;
			PropertiesHandler loc = (PropertiesHandler) JSFUtils
					.getManagedBean("propertiesHandler");
			loc.setClassiItems(new SelectItem[] {});
		}
		return "/private/amministrazione.xhtml";
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	public Classe getTemp1() {
		return temp1;
	}

	public void setTemp1(Classe temp1) {
		this.temp1 = temp1;
	}

	public Classe getTemp2() {
		return temp2;
	}

	public void setTemp2(Classe temp2) {
		this.temp2 = temp2;
	}
}
