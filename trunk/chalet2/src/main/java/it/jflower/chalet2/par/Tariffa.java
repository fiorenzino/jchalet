package it.jflower.chalet2.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Tariffa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Fila fila;
	private Long anno;
	private List<Servizio> servizi;
	private Map<String, Costo> costi;
	
	private boolean modificabile;
	private boolean attivo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "tariffa")
	@MapKey(name = "giorno")
	public Map<String, Costo> getCosti() {
		if (this.costi == null)
			this.costi = new HashMap<String, Costo>();
		return costi;
	}

	public void setCosti(Map<String, Costo> costi) {
		this.costi = costi;
	}

	public void addCosti(String giorno, Costo costo) {
		getCosti().put(giorno, costo);
	}

	@ManyToOne
	public Fila getFila() {
		return fila;
	}

	public void setFila(Fila fila) {
		this.fila = fila;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	@ManyToMany(mappedBy = "tariffe")
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
