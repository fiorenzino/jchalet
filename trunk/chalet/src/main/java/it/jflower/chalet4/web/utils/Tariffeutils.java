package it.jflower.chalet4.web.utils;

import java.util.Date;

import it.jflower.chalet4.ejb.utils.TimeUtil;
import it.jflower.chalet4.par.Costo;
import it.jflower.chalet4.par.Prenotazione;
import it.jflower.chalet4.par.Preventivo;
import it.jflower.chalet4.par.Tariffa;

public class Tariffeutils {
	public static Long getNumeroGiorni(Tariffa tariffa, Date dal, Date al) {
		Date start = new Date();
		Date stop = new Date();

		if (tariffa.getDal().compareTo(dal) < 0)
			start = dal;
		else
			start = tariffa.getDal();

		if (tariffa.getAl().compareTo(al) > 0)
			stop = al;
		else
			stop = tariffa.getAl();
		return TimeUtil.getDiffDays(start, stop);
	}

	public static Preventivo getPrenotazione(Tariffa tariffa, Date dal, Date al) {
		Preventivo preventivo = null;
		Date start = new Date();
		Date stop = new Date();

		if (tariffa.getDal().compareTo(dal) < 0)
			start = dal;
		else
			start = tariffa.getDal();

		if (tariffa.getAl().compareTo(al) > 0)
			stop = al;
		else
			stop = tariffa.getAl();
		Long num = TimeUtil.getDiffDays(start, stop);
		Costo costo = tariffa.getCosti().get("" + num);
		if (costo != null)
			preventivo = new Preventivo(dal, al, tariffa.getServiceName(),
					costo.getPrezzo(), num.intValue());
		else
			preventivo = new Preventivo(dal, al, tariffa.getServiceName(), 0,
					num.intValue());
		return preventivo;
	}
}
