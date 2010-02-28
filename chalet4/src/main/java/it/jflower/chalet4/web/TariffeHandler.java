package it.jflower.chalet4.web;

import it.jflower.chalet4.ann.Log;
import it.jflower.chalet4.ejb.CabineSession;
import it.jflower.chalet4.ejb.ConfigurazioneSession;
import it.jflower.chalet4.ejb.LettiniSession;
import it.jflower.chalet4.ejb.OmbrelloniSession;
import it.jflower.chalet4.ejb.SdraieSession;
import it.jflower.chalet4.ejb.TariffeSession;
import it.jflower.chalet4.ejb.utils.TimeUtil;
import it.jflower.chalet4.par.Cabina;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Lettino;
import it.jflower.chalet4.par.Ombrellone;
import it.jflower.chalet4.par.Sdraio;
import it.jflower.chalet4.par.Tariffa;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TariffeHandler implements Serializable {

	private Tariffa tariffa;
	private int type;
	@Inject
	@Log
	private transient Logger log;

	@Inject
	private ConfigurazioneSession configurazioneSession;

	@Inject
	private OmbrelloniSession ombrelloniSession;

	@Inject
	private SdraieSession sdraieSession;

	@Inject
	private LettiniSession lettiniSession;

	@Inject
	private CabineSession cabineSession;

	@Inject
	private TariffeSession tariffeSession;

	public String step1() {
		Tariffa tariffa = new Tariffa();
		return "tariffa1";
	}

	public String step2() {
		Long num = TimeUtil.getDiffDays(tariffa.getDal(), tariffa.getAl());
		for (int i = 0; i < num; i++) {
			Costo costo = new Costo();
			costo.setGiorno(i);
			tariffa.addCosto(i + "", costo);
		}
		return "tariffa2";
	}

	public String step3() {
		// peristo la tariffa coi costi
		List<Costo> costi = tariffa.getCostiValues();
		tariffa.setCosti(null);
		for (Costo costo : costi) {
			costo.setTariffa(tariffa);
			tariffeSession.persist(costo);
			tariffa.addCosto("" + costo.getGiorno(), costo);
		}
		tariffeSession.persist(tariffa);
		switch (type) {
		case 1:
			// 1, "ombrellone");
			List<Ombrellone> ombrelloni = ombrelloniSession.getAllOmbrelloni();
			for (Ombrellone ombrellone : ombrelloni) {
				ombrellone.addTariffa(tariffa);
				ombrelloniSession.update(ombrellone);
			}
			break;
		case 2:
			// 2, "sdraio");
			List<Sdraio> sdraie = sdraieSession.getAllSdraie();
			for (Sdraio sdraio : sdraie) {
				sdraio.addTariffa(tariffa);
				sdraieSession.update(sdraio);
			}
			break;
		case 3:
			// 3, "lettino");
			List<Lettino> lettini = lettiniSession.getAllLettini();
			for (Lettino lettino : lettini) {
				lettino.addTariffa(tariffa);
				lettiniSession.update(lettino);
			}
			break;
		case 4:
			// 4, "cabina");
			List<Cabina> cabine = cabineSession.getAllCabine();
			for (Cabina cabina : cabine) {
				cabina.addTariffa(tariffa);
				cabineSession.update(cabina);
			}
			break;

		default:
			break;
		}
		return "tariffa3";

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
