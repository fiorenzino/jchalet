package it.jflower.chalet2.web.utils;

import it.jflower.chalet2.par.Tariffa;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

public class TariffeUtil {

	// static Logger log = Logger.getLogger(TariffeUtil.class.getName());

	public static Float calcola(Integer numGiorni, Map<Integer, Float> tariffe) {
		// Float result = new Float(0);
		// Integer[] valori = { 1, 2, 3, 4, 5, 6, 7, 30 };
		//
		// for (int i = valori.length - 1; i >= 0; i--) {
		// if (numGiorni >= valori[i]) {
		// Integer resto = numGiorni - valori[i];
		// Float costoMax = tariffe.get(valori[i]);
		// Float costoEx = tariffe.get(0);
		// result = costoMax + (costoEx * resto);
		// log.info("RESULT INIT: " + result);
		// return result;
		// }
		// }
		// return result;
		return null;
	}

	public static Float calcolaTariffa(Integer numGiorni, Tariffa tariffa) {
		// log.info("NUM GG: " + numGiorni);
		// Map<Integer, Float> tariffeA = new HashMap<Integer, Float>();
		// tariffeA.put(1, tariffa.getD1());
		// tariffeA.put(2, tariffa.getD2());
		// tariffeA.put(3, tariffa.getD3());
		// tariffeA.put(4, tariffa.getD4());
		// tariffeA.put(5, tariffa.getD5());
		// tariffeA.put(6, tariffa.getD6());
		// tariffeA.put(7, tariffa.getD7());
		// tariffeA.put(30, tariffa.getD30());
		// tariffeA.put(0, tariffa.getD1ex());
		// return calcola(numGiorni, tariffeA);
		return null;
	}

	public static Float calcolaExtra(Integer numGiorni, Tariffa tariffa) {
		// return numGiorni * tariffa.getD1ex();
		return null;
	}

	public static Float calcolaKmExtra(Float km, Tariffa tariffa) {
		// return km * tariffa.getCostoKm();
		return null;
	}
}
