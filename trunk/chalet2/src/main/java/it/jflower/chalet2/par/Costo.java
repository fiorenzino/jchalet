package it.jflower.chalet2.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Costo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String giorno;
	private Float normale;
	private Tariffa tariffa;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public Float getNormale() {
		return normale;
	}

	public void setNormale(Float normale) {
		this.normale = normale;
	}

	@ManyToOne
	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}
}
