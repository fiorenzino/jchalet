package it.jflower.chalet2.par;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Lettino")
@DiscriminatorValue(value = "LET")
public class Lettino extends Servizio implements Serializable {

	public static final String TIPO = "LET";
	private static final long serialVersionUID = 1L;

	public Lettino() {
		super.setTipo(TIPO);
	}
}
