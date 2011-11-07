package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.ServiceEnum;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "Ombrellone")
@DiscriminatorValue(value = "OMB")
public class Ombrellone extends Servizio implements Serializable {

	// public static final ServiceEnum TIPO = "OMB";
	private static final long serialVersionUID = 1L;

	private String fila;
	private String riga;
	private String colonna;

	public Ombrellone() {
		super.setTipo(ServiceEnum.OMB);
	}

	public Ombrellone(String numero, String fila) {
		super.setTipo(ServiceEnum.OMB);
		this.fila = fila;
		super.setNumero(numero);
	}

	public Ombrellone(String fila, Configurazione configurazione) {
		super.setTipo(ServiceEnum.OMB);
		this.fila = fila;
		super.setConfigurazione(configurazione);
	}

	public Ombrellone(String numero, String fila, Configurazione configurazione) {
		super.setTipo(ServiceEnum.OMB);
		this.fila = fila;
		super.setNumero(numero);
		super.setConfigurazione(configurazione);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fila == null) ? 0 : fila.hashCode());
		result = prime * result + ((riga == null) ? 0 : riga.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ombrellone other = (Ombrellone) obj;
		if (fila == null) {
			if (other.fila != null)
				return false;
		} else if (!fila.equals(other.fila))
			return false;
		if (riga == null) {
			if (other.riga != null)
				return false;
		} else if (!riga.equals(other.riga))
			return false;
		return true;
	}

}
