package it.jflower.chalet2.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Fila implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Long anno;
	private Float stagionale;
	private List<Servizio> servizi;
	private List<Tariffa> tariffe;

	private boolean modificabile;
	private boolean attivo;

	public Fila() {
		
	}

	public Fila(boolean attivo) {
		this.attivo = attivo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFila() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Float getStagionale() {
		return stagionale;
	}

	public void setStagionale(Float stagionale) {
		this.stagionale = stagionale;
	}

	@OneToMany(mappedBy = "fila", cascade = { CascadeType.ALL })
	public List<Servizio> getServizi() {
		if (servizi == null)
			this.servizi = new ArrayList<Servizio>();
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}

	public void addServizio(Servizio servizio) {
		getServizi().add(servizio);
	}

	@OneToMany(mappedBy = "fila", cascade = { CascadeType.ALL })
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

	@Transient
	public boolean isModificabile() {
		return modificabile;
	}

	public void setModificabile(boolean modificabile) {
		this.modificabile = modificabile;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
