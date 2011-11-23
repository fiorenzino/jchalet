package by.giava.gestionechalet.controller;

import java.util.Date;
import java.util.List;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Contratto;
import by.giava.gestionechalet.model.ServizioPrenotato;
import by.giava.gestionechalet.pojo.Preventivo;
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

	@Inject
	PrenotazioniController prenotazioniController;

	@Override
	public Object getId(Contratto t) {
		return t.getId();
	}

	public String creaContratto() {
		setElement(new Contratto());
		getElement().setAperto(true);
		getElement().setDataStipula(new Date());
		getElement().setImportoIniziale(prenotazioniController.getTotal());
		getElement().setImportoAcconto(0F);
		getElement().setImportoSconto(0F);
		getElement().setImportoFinale(prenotazioniController.getTotal());
		List<ServizioPrenotato> servizi = prenotazioniController.getServizi();
		for (ServizioPrenotato servizioPrenotato : servizi) {
			getElement().addServizioPrenotato(servizioPrenotato);
		}
		List<Preventivo> preventivi = prenotazioniController.getPreventivi();
		for (Preventivo preventivo : preventivi) {
			preventivo.setContratto(getElement());
		}
		getElement().setPreventivi(preventivi);
		return editPage();
	}

	public void aggiornaImportoFinale() {
		float importoFinale = getElement().getImportoIniziale()
				- getElement().getImportoSconto();
		getElement().setImportoFinale(importoFinale);
	}

	@Override
	public String save() {
		// TODO Auto-generated method stub
		return super.save();
	}

}
