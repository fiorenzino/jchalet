package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.ServiceEnum;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "Sdraio")
@DiscriminatorValue(value = "SDR")
public class Sdraio extends Servizio implements Serializable {

	public static final String TIPO = "SDR";
	private static final long serialVersionUID = 1L;

	public Sdraio() {
		super.setTipo(ServiceEnum.SDR);
	}
}
