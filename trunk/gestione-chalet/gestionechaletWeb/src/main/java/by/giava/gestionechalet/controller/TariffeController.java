package by.giava.gestionechalet.controller;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.repository.TariffeRepository;
import by.giava.gestionechalet.repository.util.TimeUtil;

@Named
@SessionScoped
public class TariffeController extends AbstractLazyController<Tariffa> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/tariffe/lista.xhtml";

	@EditPage
	public static final String EDIT = "/tariffe/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/tariffe/scheda.xhtml";

	public static final String STEP1 = "/tariffe/step1.xhtml";

	public static final String STEP2 = "/tariffe/step2.xhtml";

	@Inject
	@OwnRepository(TariffeRepository.class)
	TariffeRepository tariffeRepository;

	@Override
	public Object getId(Tariffa t) {
		return t.getId();
	}

	public String step1() {
		setElement(new Tariffa());
		getElement().setDal(new Date());
		getElement().setAl(new Date());
		return STEP1 + REDIRECT_PARAM;
	}

	public String step2() {
		if (getElement().getId() == null) {
			// getElement().setCosti(null);
			// provo ad aggiungere quello che non c'è
			Long num = TimeUtil.getDiffDays(getElement().getAl(), getElement()
					.getDal());
			for (int i = 1; i <= num; i++) {
				Costo costo = new Costo();
				costo.setGiorno(new Long(i));
				getElement().addCosto(new Long(i), costo);
			}
		} else {
			logger.info("dal: " + getElement().getDal() + " - "
					+ getElement().getAl());
			logger.info("DIFF OLD: "
					+ TimeUtil.getDiffDays(getElement().getDal(), getElement()
							.getAl()));
			Long num = TimeUtil.getDiffDays(getElement().getAl(), getElement()
					.getDal());
			logger.info("DIFF NEW: " + num);
			for (int i = 1; i <= num; i++) {
				if (getElement().containsCosto(new Long(i))) {
					logger.info("esiste gia la tariffa");
				} else {
					Costo costo = new Costo();
					costo.setGiorno(new Long(i));
					getElement().addCosto(new Long(i), costo);
				}
			}
		}

		return STEP2 + REDIRECT_PARAM;
	}

	public String step3() {
		// peristo la tariffa coi costi
		if (getElement().getId() == null) {
			save();
			logger.info("tariffa creata!");
		} else {
			update();
			logger.info("tariffa aggiornata!");
		}
		return viewPage();
	}

	@Override
	public String viewElement() {
		// TODO Auto-generated method stub
		return super.viewElement();
	}

	@Override
	public String modElement() {
		// TODO Auto-generated method stub
		super.modElement();
		return STEP1 + REDIRECT_PARAM;
	}

	public String view(Long id) {
		setElement(tariffeRepository.fetch(id));
		return VIEW + REDIRECT_PARAM;
	}

}
