package by.giava.gestionechalet.repository.util;

import java.util.Comparator;

import by.giava.gestionechalet.model.servizi.Ombrellone;

public class OmbrelloniUtils {
	public static final Comparator<Ombrellone> OMBRELLONI_NUMBERS = new Comparator<Ombrellone>() {
		public int compare(Ombrellone o1, Ombrellone o2) {
			Comparator<Object> comparator = new AlphanumComparator();
			if (o1 == null || o1.getFila() == null || o1.getNumero() == null)
				return Integer.MIN_VALUE;
			else {
				if (o1.getFila().toUpperCase()
						.compareTo(o2.getFila().toUpperCase()) != 0)
					return comparator.compare(o1.getFila().toUpperCase(), o2
							.getFila().toUpperCase());
				else {
					return comparator.compare(o1.getNumero().toUpperCase(), o2
							.getNumero().toUpperCase());
				}
			}

		}
	};
}
