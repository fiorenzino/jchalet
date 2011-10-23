package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractController;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Cliente;
import by.giava.gestionechalet.repository.ClientiRepository;

@Named
@SessionScoped
public class ClientiController extends AbstractController<Cliente> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/clienti/lista.xhtml";

	@EditPage
	public static final String EDIT = "/clienti/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/clienti/scheda.xhtml";

	@Inject
	@OwnRepository(ClientiRepository.class)
	ClientiRepository clientiRepository;

	@Override
	public Object getId(Cliente t) {
		return t.getId();
	}

	@Override
	public String save() {
		getElement().setDataInserimento(new Date());
		return super.save();
	}

}
