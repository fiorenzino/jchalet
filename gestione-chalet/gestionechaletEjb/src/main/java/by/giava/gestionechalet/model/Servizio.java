package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(columnDefinition = "tipo", name = "TIPO", discriminatorType = DiscriminatorType.STRING, length = 3)
@Table(name = "Servizio")
public class Servizio implements Serializable {

	private Long id;
	private String tipo;
	private String numero;
	private List<Tariffa> tariffe;
	private boolean attivo;
	private Configurazione configurazione;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Tariffa.class)
	public List<Tariffa> getTariffe() {
		if (tariffe == null)
			this.tariffe = new ArrayList<Tariffa>();
		return tariffe;
	}

	public void setTariffe(List<Tariffa> tariffe) {
		this.tariffe = tariffe;
	}

	public void addTariffa(Tariffa tariffa) {
		getTariffe().add(tariffa);
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@ManyToOne
	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

}
