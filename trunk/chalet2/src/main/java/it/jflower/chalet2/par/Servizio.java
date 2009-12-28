package it.jflower.chalet2.par;

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
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(columnDefinition = "tipo", name = "TIPO", discriminatorType = DiscriminatorType.STRING, length = 3)
@Table(name = "Servizio")
public class Servizio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String fila;
	private String numero;
	private Long anno;
	private String tipo;
	private List<Tariffa> tariffe;
	private List<Contratto> contratti;
	private Float stagionale;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
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

	@ManyToMany(mappedBy = "servizi")
	public List<Contratto> getContratti() {
		return contratti;
	}

	public void setContratti(List<Contratto> contratti) {
		this.contratti = contratti;
	}

	public Float getStagionale() {
		return stagionale;
	}

	public void setStagionale(Float stagionale) {
		this.stagionale = stagionale;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
