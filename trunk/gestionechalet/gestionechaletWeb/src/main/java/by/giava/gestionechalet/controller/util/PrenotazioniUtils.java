package by.giava.gestionechalet.controller.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.util.TimeUtils;

public class PrenotazioniUtils {
	public static List<Prenotazione[]> getPrenotazioniPerPeriodo(Date dal,
			Date al, List<Ombrellone> ombrelloni,
			Map<String, Map<String, Prenotazione>> mappaPrenotazioni) {
		List<Prenotazione[]> listaCompleta = new ArrayList<Prenotazione[]>();
		Calendar calendar = Calendar.getInstance();
		Long num = TimeUtils.getDiffDays(dal, al, true);

		for (Ombrellone ombrellone : ombrelloni) {
			String key = ombrellone.getFila() + "-" + ombrellone.getNumero();
			// aggiungo una prenotazione vuota in +
			// perche la prima colonna contiene il numero
			Prenotazione[] prenotaz = new Prenotazione[num.intValue() + 1];
			prenotaz[0] = new Prenotazione();
			prenotaz[0].setNumero(ombrellone.getNumero());
			prenotaz[0].setFila(ombrellone.getFila());

			calendar.setTime(dal);
			Map<String, Prenotazione> preList = null;
			if (mappaPrenotazioni.containsKey(key)) {
				preList = mappaPrenotazioni.get(key);
			}
			for (int i = 1; i < num + 1; i++) {
				prenotaz[i] = new Prenotazione();
				if (i > 1)
					calendar.add(Calendar.DAY_OF_YEAR, 1);
				prenotaz[i].setNumero(ombrellone.getNumero());
				prenotaz[i].setFila(ombrellone.getFila());
				prenotaz[i].setData(calendar.getTime());
				if (preList != null
						&& preList.containsKey(TimeUtils.getSingleDayName(
								calendar.getTime(), true))) {
					Prenotazione pre = preList.get(TimeUtils.getSingleDayName(
							calendar.getTime(), true));
					prenotaz[i].setOccupato(true);
				}

			}
			listaCompleta.add(prenotaz);
		}

		return listaCompleta;
	}

	public static List<Prenotazione[]> getPrenotazioniSoloLiberi(Date dal,
			Date al, List<Ombrellone> ombrelloni,
			Map<String, Map<String, Prenotazione>> mappaPrenotazioni) {
		List<Prenotazione[]> listaCompletaSoloLiberi = new ArrayList<Prenotazione[]>();
		Calendar calendar = Calendar.getInstance();
		Long num = TimeUtils.getDiffDays(dal, al, false);
		for (Ombrellone ombrellone : ombrelloni) {
			String key = ombrellone.getFila() + "-" + ombrellone.getNumero();
			if (mappaPrenotazioni.containsKey(key))
				continue;
			Prenotazione[] prenotaz = new Prenotazione[num.intValue()];
			calendar.setTime(dal);
			for (int i = 0; i < num; i++) {
				prenotaz[i] = new Prenotazione();
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				prenotaz[i].setNumero(ombrellone.getNumero());
				prenotaz[i].setFila(ombrellone.getFila());
				prenotaz[i].setData(calendar.getTime());
			}
			listaCompletaSoloLiberi.add(prenotaz);
		}
		return listaCompletaSoloLiberi;
	}

	public static List<String> creaColonne(Date dal, Date al) {
		List<String> colonne = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		Long num = TimeUtils.getDiffDays(dal, al, true);
		calendar.setTime(dal);
		for (int i = 0; i <= num; i++) {
			// gestire fila
			if (i == 0) {
				colonne.add("nome");
			} else {
				colonne.add(TimeUtils.getSingleDayName(calendar.getTime(),
						false));
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}

		}
		return colonne;
	}

	public static List<String> creaRighe(List<Ombrellone> ombrelloni) {
		List<String> righe = new ArrayList<String>();
		for (Ombrellone ombrellone : ombrelloni) {
			righe.add(ombrellone.getFila() + ":" + ombrellone.getNumero());
		}
		return righe;
	}

}
