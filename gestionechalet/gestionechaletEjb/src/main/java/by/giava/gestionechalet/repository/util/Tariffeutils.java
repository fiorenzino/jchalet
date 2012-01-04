package by.giava.gestionechalet.repository.util;

import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import by.giava.gestionechalet.model.Costo;
import by.giava.gestionechalet.model.Preventivo;
import by.giava.gestionechalet.model.Tariffa;

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
		return TimeUtils.getDiffDays(start, stop, false);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public static Preventivo getPrenotazione(Tariffa tariffa, Date dal,
			Date al, Long numPezzi) {
		Preventivo preventivo = null;
		Date start = dal;
		Date stop = al;
		Long numGiorni = TimeUtils.getDiffDays(start, stop, false);
		if (tariffa.isStagionale()) {
			Costo costo = tariffa.getCosti().get(0L);
			preventivo = new Preventivo(start, stop, tariffa.getServiceName(),
					costo.getPrezzo(), numGiorni, numPezzi, tariffa.getId());
		} else {
			if (tariffa.getDal().compareTo(dal) > 0)
				start = tariffa.getDal();
			if (tariffa.getAl().compareTo(al) < 0)
				stop = tariffa.getAl();

			Costo costo = tariffa.getCosti().get(numGiorni);
			if (costo != null)
				preventivo = new Preventivo(start, stop,
						tariffa.getServiceName(), costo.getPrezzo(), numGiorni,
						numPezzi, tariffa.getId());
			else
				preventivo = new Preventivo(start, stop,
						tariffa.getServiceName(), 0, numGiorni, numPezzi,
						tariffa.getId());
		}
		return preventivo;
	}
}
