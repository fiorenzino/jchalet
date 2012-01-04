package it.pisi79.geelection.jmx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

@Startup
@Singleton
@Local(AdminManagerLocal.class)
public class AdminManagerBean implements AdminManagerLocal {

	private static final String CONFIGURATION_FOLDER = "ge-election";

	private static final String CONFIGURATION_FILENAME = "properties.conf";
	public static final String NEWLINE = "<br/>";

	// ---------------------------------------

	Logger logger = Logger.getLogger(getClass());

	// ----------------------------------------------------------------------------------------

	private String getConfigurationFilename() {
		return this.getClass().getClassLoader()
				.getResource(CONFIGURATION_FOLDER).getPath()
				+ "/" + CONFIGURATION_FILENAME;
	}

	public String get(GeElectionProperties property) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(getConfigurationFilename()));
			return p.getProperty(property.name());
		} catch (FileNotFoundException e) {
			logger.warn("Error loading properties: no configuration file! Creating configuration file.");
			return null;
		} catch (IOException e) {
			logger.warn("Error reading configuration file: " + e);
			return null;
		}
	}

}