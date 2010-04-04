package it.jflower.chalet4.par;

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

	private String fila;
	private String riga;
	private String colonna;

	public Ombrellone() {
		super.setTipo(TIPO);
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public String getRiga() {
		return riga;
	}

	public void setRiga(String riga) {
		this.riga = riga;
	}

	public String getColonna() {
		return colonna;
	}

	public void setColonna(String colonna) {
		this.colonna = colonna;
	}

}
