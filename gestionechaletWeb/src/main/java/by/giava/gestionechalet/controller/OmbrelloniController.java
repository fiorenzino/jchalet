package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.ClientiRepository;

@Named
@SessionScoped
public class OmbrelloniController extends AbstractLazyController<Ombrellone> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/omrelloni/lista.xhtml";

	@EditPage
	public static final String EDIT = "/omrelloni/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/omrelloni/scheda.xhtml";

	@Inject
	@OwnRepository(ClientiRepository.class)
	ClientiRepository clientiRepository;

	@Override
	public Object getId(Ombrellone t) {
		return t.getId();
	}

}
