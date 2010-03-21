package it.jflower.chalet4.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ConfigurazioneSession.class)
public class ConfigurazioneSessionBean implements ConfigurazioneSession {

	public void persist(Object object) {
		// TODO Auto-generated method stub

	}

}
