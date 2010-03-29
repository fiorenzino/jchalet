package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.TariffeSession;
import it.jflower.chalet4.ejb.utils.CalendarUtils;
import it.jflower.chalet4.ejb.utils.TimeUtil;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Tariffa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class TariffeHandler implements Serializable {

	private Tariffa tariffa;
	@Inject
	@Log
	private transient Logger log;

	@Inject
	private TariffeSession tariffeSession;

	private List<Tariffa> tariffeModel;

	public String step1() {
		this.tariffa = new Tariffa();
		this.tariffa.setDal(new Date());
		this.tariffa.setAl(new Date());
		return "/configurazione/tariffa1";
	}

	public String step2() {
		log.info("dal: " + tariffa.getDal() + " - " + tariffa.getAl());
		log.info("DIFF OLD: "
				+ TimeUtil.getDiffDays(tariffa.getDal(), tariffa.getAl()));
		CalendarUtils cal = new CalendarUtils(tariffa.getDal().getTime());
		Calendar calen = Calendar.getInstance();
		calen.setTime(tariffa.getAl());
		Long num = cal.diffDay(calen);
		log.info("DIFF NEW: " + num);
		for (int i = 1; i <= num; i++) {
			Costo costo = new Costo();
			costo.setGiorno(i);
			tariffa.addCosto(i + "", costo);
		}
		return "/configurazione/tariffa2";
	}

	public String step3() {
		// peristo la tariffa coi costi
		tariffeSession.persist(tariffa);
		log.info("tariffa creata!");
		aggTariffeModel();
		return "/configurazione/scheda-tariffa";

	}

	public String scheda(Long id) {
		this.tariffa = tariffeSession.fetch(id);
		return "/configurazione/scheda-tariffa";

	}

	public void aggTariffeModel() {
		this.tariffeModel = null;
	}

	public List<Tariffa> getTariffeModel() {
		if (tariffeModel == null)
			tariffeModel = tariffeSession.getAllTariffe();
		return tariffeModel;
	}

	public void setTariffeModel(List<Tariffa> tariffeModel) {
		this.tariffeModel = tariffeModel;
	}

	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

}
