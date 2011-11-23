package by.giava.gestionechalet.controller.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import by.giava.gestionechalet.model.Prenotazione;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.repository.util.TimeUtil;

public class PrenotazioniUtils {
	public static List<Prenotazione[]> getPrenotazioniPerPeriodo(Date dal,
			Date al, List<Ombrellone> ombrelloni,
			Map<String, Map<String, Prenotazione>> mappaPrenotazioni) {
		List<Prenotazione[]> listaCompleta = new ArrayList<Prenotazione[]>();
		Calendar calendar = Calendar.getInstance();
		Long num = TimeUtil.getDiffDays(dal, al);
		for (Ombrellone ombrellone : ombrelloni) {
			String key = ombrellone.getFila() + "-" + ombrellone.getNumero();
			Prenotazione[] prenotaz = new Prenotazione[num.intValue()];
			calendar.setTime(dal);
			Map<String, Prenotazione> preList = null;
			if (mappaPrenotazioni.containsKey(key)) {
				preList = mappaPrenotazioni.get(key);
			}
			for (int i = 0; i < num; i++) {
				prenotaz[i] = new Prenotazione();
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				prenotaz[i].setNumero(ombrellone.getNumero());
				prenotaz[i].setFila(ombrellone.getFila());
				prenotaz[i].setData(calendar.getTime());
				if (preList != null
						&& preList.containsKey(getSingleDayName(calendar
								.getTime()))) {
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
		Long num = TimeUtil.getDiffDays(dal, al);
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
		Long num = TimeUtil.getDiffDays(dal, al);
		calendar.setTime(dal);
		for (int i = 0; i <= num + 1; i++) {
			// gestire fila
			if (i == 0) {
				colonne.add("nome");
			} else {
				colonne.add(getSingleDayName(calendar.getTime()));
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

	public static String getSingleDayName(Date data) {
		if (data != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR);
		}
		return "";
	}
}
