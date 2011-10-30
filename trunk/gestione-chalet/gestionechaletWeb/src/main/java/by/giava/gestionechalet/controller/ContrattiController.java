package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Contratto;
import by.giava.gestionechalet.repository.ContrattiRepository;

@Named
@SessionScoped
public class ContrattiController extends AbstractLazyController<Contratto> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/contratti/lista.xhtml";

	@EditPage
	public static final String EDIT = "/contratti/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/contratto/scheda.xhtml";

	@Inject
	@OwnRepository(ContrattiRepository.class)
	ContrattiRepository contrattiRepository;

	@Override
	public Object getId(Contratto t) {
		return t.getId();
	}

}
