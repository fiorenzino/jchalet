package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import by.giava.gestionechalet.enums.TipoServizioEnum;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(columnDefinition = "tipo", name = "TIPO", discriminatorType = DiscriminatorType.STRING, length = 3)
@Table(name = "chalet_servizi")
public class Servizio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TipoServizioEnum tipo;
	private String numero;
	private String fila;
	private List<Tariffa> tariffe;
	private boolean attivo = true;
	private Configurazione configurazione;
	private List<Long> notIn;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "servizio")
	@SequenceGenerator(name = "servizio", sequenceName = "servizio")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public TipoServizioEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoServizioEnum tipo) {
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

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	@ManyToOne
	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((fila == null) ? 0 : fila.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servizio other = (Servizio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (fila == null) {
			if (other.fila != null)
				return false;
		} else if (!fila.equals(other.fila))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Transient
	public List<Long> getNotIn() {
		if (notIn == null)
			this.notIn = new ArrayList<Long>();
		return notIn;
	}

	public void setNotIn(List<Long> notIn) {
		this.notIn = notIn;
	}

	public void addNotIn(Long notInOne) {
		getNotIn().add(notInOne);
	}

}
