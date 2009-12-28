package it.jflower.chalet2;

import it.jflower.chalet2.test._01_SetupInizialeTest;

import org.testng.annotations.Test;

public class HelloWorldTest {
	@Test
	public void testGetText() {
		_01_SetupInizialeTest test = new _01_SetupInizialeTest();
		try {
			test.setUpBeforeClass();
			test.testCreazioneOmbrelloni();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
