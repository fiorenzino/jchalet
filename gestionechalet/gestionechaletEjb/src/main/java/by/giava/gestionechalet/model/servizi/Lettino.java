package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "chalet_lettini")
@DiscriminatorValue(value = "LET")
public class Lettino extends Servizio implements Serializable {

	// public static final String TIPO = "LET";
	private static final long serialVersionUID = 1L;

	public Lettino() {
		super.setTipo(TipoServizioEnum.LET);
	}
}
