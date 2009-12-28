package it.jflower.chalet2.par;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Ombrellone")
@DiscriminatorValue(value = "OMB")
public class Ombrellone extends Servizio implements Serializable {

	public static final String TIPO = "OMB";
	private static final long serialVersionUID = 1L;

	public Ombrellone() {
		super.setTipo(TIPO);
	}
}
