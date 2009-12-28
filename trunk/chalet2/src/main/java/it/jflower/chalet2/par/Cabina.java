package it.jflower.chalet2.par;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cabina")
@DiscriminatorValue(value = "CAB")
public class Cabina extends Servizio implements Serializable {

	public static final String TIPO = "CAB";
	private static final long serialVersionUID = 1L;

	public Cabina() {
		super.setTipo(TIPO);
	}
}
