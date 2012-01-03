package by.giava.gestionechalet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "chalet_costi")
public class Costo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long giorno;
	private Float prezzo;
	private Tariffa tariffa;
	private boolean attivo = true;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "costo")
	@SequenceGenerator(name = "costo", sequenceName = "costo")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGiorno() {
		return giorno;
	}

	public void setGiorno(Long giorno) {
		this.giorno = giorno;
	}

	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

	@ManyToOne
	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
