package by.giava.gestionechalet.repository.util;

import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.Tariffa;
import by.giava.gestionechalet.pojo.Preventivo;

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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static Preventivo getPrenotazione(Tariffa tariffa, Date dal,
			Date al, Long num) {
		Preventivo preventivo = null;
		Date start = dal;
		Date stop = al;

		if (tariffa.getDal().compareTo(dal) > 0)
			start = tariffa.getDal();
		if (tariffa.getAl().compareTo(al) < 0)
			stop = tariffa.getAl();
		Long gg = TimeUtil.getDiffDays(start, stop);
		Costo costo = tariffa.getCosti().get(num);
		if (costo != null)
			preventivo = new Preventivo(start, stop, tariffa.getServiceName(),
					costo.getPrezzo(), gg, num, tariffa.getId());
		else
			preventivo = new Preventivo(start, stop, tariffa.getServiceName(),
					0, gg, num, tariffa.getId());
		return preventivo;
	}
}
