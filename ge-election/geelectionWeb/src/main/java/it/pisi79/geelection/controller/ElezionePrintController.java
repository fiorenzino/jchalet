package it.pisi79.geelection.controller;

import it.coopservice.commons2.controllers.AbstractController;
import it.pisi79.geelection.domain.Elezione;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ElezionePrintController extends AbstractController<Elezione> {

	private static final long serialVersionUID = 1L;

	@Inject
	ElezioniController elezioniController;
	
	@Override
	public void defaultCriteria() {
		getSearch().setObj(elezioniController.getSearch().getObj());
		setElement(elezioniController.getElement());
	}

}
