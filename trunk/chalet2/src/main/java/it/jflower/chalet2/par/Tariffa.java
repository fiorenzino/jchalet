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
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Tariffa implements Serializable {

	private Long id;
	private String fila;
	private Long anno;
	private List<Servizio> servizi;
	private Map<String, Costo> costi;

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

	private static final long serialVersionUID = 1L;

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
}
