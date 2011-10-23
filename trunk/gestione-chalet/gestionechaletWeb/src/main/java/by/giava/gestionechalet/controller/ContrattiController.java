package by.giava.gestionechalet.controller;

import java.util.Date;
import java.util.List;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractController;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import by.giava.gestionechalet.model.Cliente;
import by.giava.gestionechalet.model.Contratto;

@Named
@SessionScoped
public class ContrattiController extends AbstractController<Contratto> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/contratti/lista.xhtml";

	@EditPage
	public static final String EDIT = "/contratti/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/contratto/scheda.xhtml";

	@Inject
	@OwnRepository(ContrattiController.class)
	ContrattiController clientiController;

	@Override
	public Object getId(Contratto t) {
		return t.getId();
	}

}
