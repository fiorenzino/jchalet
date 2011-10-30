package by.giava.gestionechalet.repository.util;

import java.util.ArrayList;
import java.util.List;

import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.pojo.Posto;

public class ConfigurazioneUtils {

	public static List<Posto[]> creaRighe(List<Ombrellone> ombrelloni,
			Configurazione configurazione) {
		Ombrellone[][] matrice = getMatrice(ombrelloni, configurazione);
		List<Posto[]> posti = new ArrayList<Posto[]>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			Posto[] fila = new Posto[configurazione.getNumeroColonne()
					.intValue()];
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				if (matrice[i][m] != null)
					fila[m] = new Posto(i, m, matrice[i][m].getNumero());
				else
					fila[m] = new Posto(i, m, "");
			}
			posti.add(fila);
		}

		return posti;
	}

	public static List<Posto[]> creaRigheSenzaNumero(
			Configurazione configurazione) {
		List<Posto[]> posti = new ArrayList<Posto[]>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			Posto[] fila = new Posto[configurazione.getNumeroColonne()
					.intValue()];
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				fila[m] = new Posto(i, m, "");
			}
			posti.add(fila);
		}

		return posti;
	}

	public static List<Posto> creaPostiSenzaNumero(Configurazione configurazione) {
		List<Posto> posti = new ArrayList<Posto>();
		int numPosti = configurazione.getNumeroColonne().intValue()
				* configurazione.getNumeroRighe().intValue();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				Posto posto = new Posto(i, m, "");
				posti.add(posto);
			}

		}

		return posti;
	}

	public static List<String> creaColonne(Configurazione configurazione) {
		List<String> colonne = new ArrayList<String>();
		for (int i = 1; i <= configurazione.getNumeroColonne(); i++) {
			colonne.add("" + i);
		}
		return colonne;
	}

	public static Ombrellone[][] getMatrice(List<Ombrellone> ombrelloni,
			Configurazione configurazione) {
		Ombrellone[][] matrice = new Ombrellone[configurazione
				.getNumeroColonne().intValue()][configurazione.getNumeroRighe()
				.intValue()];
		for (Ombrellone ombrellone : ombrelloni) {
			int colonna = Integer.valueOf(ombrellone.getColonna());
			int riga = Integer.valueOf(ombrellone.getRiga());
			matrice[colonna][riga] = ombrellone;
		}
		return matrice;
	}

	private static Ombrellone find(Ombrellone[][] matrice, int riga, int colonna) {
		return matrice[colonna][riga];
	}

}
