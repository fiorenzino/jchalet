package it.jflower.chalet2.test;

import it.jflower.chalet2.par.Ombrellone;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class _01_SetupInizialeTest extends _00_Commons {

	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		_00_Commons.setUpBeforeClass();
	}

	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------

	@Test
	public void testCreazioneOmbrelloni() {

		Ombrellone[] ombrelloni = super.creaOmbrelloni();
		for (Ombrellone ombrellone : ombrelloni) {
			Assert.assertNotNull("nuovo Ombrellone", ombrellone);
			Assert.assertNotNull("id", ombrellone.getId());
			Assert.assertNotNull("ombrellone.fila", ombrellone.getFila());
			Assert.assertNotNull("ombrellone.numero", ombrellone.getNumero());
			Assert.assertNotNull("ombrellone.anno", ombrellone.getAnno());
			Assert.assertNotNull("ombrellone.stagionale", ombrellone
					.getStagionale());
		}

		for (Ombrellone ombrellone : ombrelloni) {
			Ombrellone riletto = super.rileggiOmbrellone(ombrellone.getId());
			Assert.assertNotNull("ombrellone riletto", riletto);
			Assert.assertNotNull("id", ombrellone.getId());
			Assert.assertNotNull("ombrellone riletto.fila", ombrellone
					.getFila());
			Assert.assertNotNull("ombrellone riletto.numero", ombrellone
					.getNumero());
			Assert.assertNotNull("ombrellone riletto.anno", ombrellone
					.getAnno());
			Assert.assertNotNull("ombrellone riletto.stagionale", ombrellone
					.getStagionale());
		}

	}

	// -------------------------------------------------------------------------

}
