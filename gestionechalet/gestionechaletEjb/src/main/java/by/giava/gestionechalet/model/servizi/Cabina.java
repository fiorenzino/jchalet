package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "chalet_cabine")
@DiscriminatorValue(value = "CAB")
public class Cabina extends Servizio implements Serializable {

	// public static final String TIPO = "CAB";
	private static final long serialVersionUID = 1L;

	public Cabina() {
		super.setTipo(TipoServizioEnum.CAB);
	}
}
