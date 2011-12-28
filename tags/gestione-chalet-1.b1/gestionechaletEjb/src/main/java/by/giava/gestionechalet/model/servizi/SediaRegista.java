package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "SedieRegista")
@DiscriminatorValue(value = "SED")
public class SediaRegista extends Servizio implements Serializable {

	public static final String TIPO = "SED";
	private static final long serialVersionUID = 1L;

	public SediaRegista() {
		super.setTipo(TipoServizioEnum.SED);
	}
}
