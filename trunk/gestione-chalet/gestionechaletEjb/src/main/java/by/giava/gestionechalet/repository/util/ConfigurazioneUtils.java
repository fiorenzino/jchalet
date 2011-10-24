package by.giava.gestionechalet.repository.util;

import java.util.ArrayList;
import java.util.List;

import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.pojo.Riga;

public class ConfigurazioneUtils {
	public static List<Riga> creaRighe(List<Ombrellone> ombrelloni,
			Configurazione configurazione) {
		List<Riga> righe = new ArrayList<Riga>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			Riga riga;
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				riga = new Riga(find(ombrelloni, "" + i, "" + m));
				righe.add(riga);
			}
		}

		return righe;
	}

	public static List<Integer> creaColonne(int numColonne) {
		List<Integer> colonne = new ArrayList<Integer>();
		for (int i = 0; i < numColonne; i++) {
			colonne.add(i);
		}
		return colonne;
	}

	private static List<Ombrellone> find(List<Ombrellone> ombrelloni,
			String riga, String colonna) {
		List<Ombrellone> result = new ArrayList<Ombrellone>();
		for (Ombrellone ombrellone : ombrelloni) {
			if (ombrellone.getColonna() != null && ombrellone.getRiga() != null
					&& ombrellone.getColonna().equals(colonna)
					&& ombrellone.getRiga().equals(riga)) {
				result.add(ombrellone);
			}
		}
		return result;
	}

}
