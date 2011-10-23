package by.giava.gestionechalet.controller;

import java.util.Calendar;
import java.util.Date;

import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.xb.binding.introspection.FieldInfo.GetValueAccessFactory;

import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.repository.TariffeRepository;
import by.giava.gestionechalet.repository.util.CalendarUtils;
import by.giava.gestionechalet.repository.util.TimeUtil;

@Named
@SessionScoped
public class TariffeController extends AbstractController<Tariffa> {

	private static final long serialVersionUID = 1L;

	@ListPage
	public static final String LIST = "/tariffe/lista.xhtml";

	@EditPage
	public static final String EDIT = "/tariffe/gestione.xhtml";

	@ViewPage
	public static final String VIEW = "/tariffe/scheda.xhtml";

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
		return "/tariffe/tariffa1?faces-redirect=true";
	}

	public String step2() {
		logger.info("dal: " + getElement().getDal() + " - "
				+ getElement().getAl());
		logger.info("DIFF OLD: "
				+ TimeUtil.getDiffDays(getElement().getDal(), getElement()
						.getAl()));
		CalendarUtils cal = new CalendarUtils(getElement().getDal().getTime());
		Calendar calen = Calendar.getInstance();
		calen.setTime(getElement().getAl());
		Long num = cal.diffDay(calen);
		logger.info("DIFF NEW: " + num);
		for (int i = 1; i <= num; i++) {
			Costo costo = new Costo();
			costo.setGiorno(new Long(i));
			getElement().addCosto(new Long(i), costo);
		}
		return "/tariffe/tariffa2?faces-redirect=true";
	}

	public String step3() {
		// peristo la tariffa coi costi
		tariffeRepository.persist(getElement());
		logger.info("tariffa creata!");
		super.reset();
		return viewPage();

	}

}
