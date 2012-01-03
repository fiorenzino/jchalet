package by.giava.gestionechalet.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.pojo.Posto;

public class ConfigurazioniUtils {
	static final Logger logger = Logger.getLogger(ConfigurazioniUtils.class
			.getName());

	public static List<Posto[]> creaRighe(List<Ombrellone> ombrelloni,
			Configurazione configurazione) {
		Ombrellone[][] matrice = getMatrice(ombrelloni, configurazione);
		List<Posto[]> posti = new ArrayList<Posto[]>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			Posto[] fila = new Posto[configurazione.getNumeroColonne()
					.intValue()];
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				try {
					if (matrice[i][m] != null)
						fila[m] = new Posto(i, m, matrice[i][m].getNumero(),
								matrice[i][m].getFila());
					else
						fila[m] = new Posto(i, m, "", "");
				} catch (Exception e) {
					logger.info("creaRighe ERRORE i: " + i + " m: " + m);
					// logger.info(e.getMessage());
				}

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
				fila[m] = new Posto(i, m, "", "");
			}
			posti.add(fila);
		}

		return posti;
	}

	public static List<Posto> creaPostiSenzaNumero(Configurazione configurazione) {
		List<Posto> posti = new ArrayList<Posto>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				Posto posto = new Posto(i, m, "", "");
				posti.add(posto);
			}

		}

		return posti;
	}

	public static List<Posto> creaPostiConNumero(List<Ombrellone> ombrelloni,
			Configurazione configurazione) {
		Ombrellone[][] matrice = getMatrice(ombrelloni, configurazione);
		List<Posto> posti = new ArrayList<Posto>();
		for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
			for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
				Posto posto;
				try {
					if (matrice[i][m] != null) {
						posto = new Posto(i, m, matrice[i][m].getNumero(),
								matrice[i][m].getFila());
					} else {
						posto = new Posto(i, m, "", "");
					}
					posti.add(posto);
				} catch (Exception e) {
					logger.info("creaPostiConNumero ERRORE i: " + i + " m: "
							+ m);
					// logger.info(e.getMessage());
				}

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
		Ombrellone[][] matrice = new Ombrellone[configurazione.getNumeroRighe()
				.intValue()][configurazione.getNumeroColonne().intValue()];
		// for (int i = 0; i < configurazione.getNumeroRighe(); i++) {
		// for (int m = 0; m < configurazione.getNumeroColonne(); m++) {
		// matrice[i][m] = new Ombrellone();
		// }
		// }
		if (ombrelloni != null && ombrelloni.size() > 0)
			for (Ombrellone ombrellone : ombrelloni) {
				int colonna = Integer.valueOf(ombrellone.getColonna());
				int riga = Integer.valueOf(ombrellone.getRiga());
				matrice[riga][colonna] = ombrellone;
			}
		return matrice;
	}

	private static Ombrellone find(Ombrellone[][] matrice, int riga, int colonna) {
		return matrice[riga][colonna];
	}

}
