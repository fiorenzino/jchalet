package by.giava.gestionechalet.model.servizi;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.TipoServizioEnum;
import by.giava.gestionechalet.model.Configurazione;
import by.giava.gestionechalet.model.Servizio;

@Entity
@Table(name = "chalet_ombrelloni")
@DiscriminatorValue(value = "OMB")
public class Ombrellone extends Servizio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String riga;
	private String colonna;

	public Ombrellone() {
		super.setTipo(TipoServizioEnum.OMB);
	}

	public Ombrellone(String numero, String fila) {
		super.setTipo(TipoServizioEnum.OMB);
		super.setFila(fila);
		super.setNumero(numero);
	}

	public Ombrellone(String fila, Configurazione configurazione) {
		super.setTipo(TipoServizioEnum.OMB);
		super.setFila(fila);
		super.setConfigurazione(configurazione);
	}

	public Ombrellone(String numero, String fila, Configurazione configurazione) {
		super.setTipo(TipoServizioEnum.OMB);
		super.setFila(fila);
		super.setNumero(numero);
		super.setConfigurazione(configurazione);
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
		result = prime * result + ((colonna == null) ? 0 : colonna.hashCode());
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
		if (riga == null) {
			if (other.riga != null)
				return false;
		} else if (!riga.equals(other.riga))
			return false;
		if (colonna == null) {
			if (other.colonna != null)
				return false;
		} else if (!colonna.equals(other.colonna))
			return false;
		return true;
	}

}
