package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.enums.StatoContrattoEnum;
import by.giava.gestionechalet.model.Cliente;
import by.giava.gestionechalet.model.Contratto;
import by.giava.gestionechalet.model.Preventivo;
import by.giava.gestionechalet.model.ServizioPrenotato;
import by.giava.gestionechalet.repository.ClientiRepository;
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
	public static final String VIEW = "/contratti/scheda.xhtml";

	@Inject
	@OwnRepository(ContrattiRepository.class)
	ContrattiRepository contrattiRepository;

	@Inject
	PrenotazioniController prenotazioniController;

	@Inject
	ClientiRepository clientiRepository;

	@Override
	public Object getId(Contratto t) {
		return t.getId();
	}

	public String creaContratto() {
		setElement(new Contratto());
		getElement().setStato(StatoContrattoEnum.RISERVATO);
		getElement().setDataStipula(new Date());
		getElement().setImportoIniziale(prenotazioniController.getTotal());
		getElement().setImportoAcconto(0F);
		getElement().setImportoVariazione(0F);
		getElement().setImportoFinale(prenotazioniController.getTotal());
		List<ServizioPrenotato> servizi = prenotazioniController.getServizi();
		for (ServizioPrenotato servizioPrenotato : servizi) {
			servizioPrenotato.setContratto(getElement());
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
				+ getElement().getImportoVariazione();
		getElement().setImportoFinale(importoFinale);
	}

	@Override
	public String save() {
		// TODO Auto-generated method stub
		super.save();
		ricaricaCliente();
		return VIEW;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		super.update();
		ricaricaCliente();
		return VIEW;
	}

	private void ricaricaCliente() {
		if (getElement().getCliente().getId() != null) {
			Cliente cliente = clientiRepository.find(getElement().getCliente()
					.getId());
			getElement().setCliente(cliente);
		}
	}

	public String vediContratti(Long id) {
		reset();
		getSearch().getObj().getCliente().setId(id);
		refreshModel();
		return listPage();
	}

}
