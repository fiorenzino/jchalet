package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Transient;

@Entity
public class Tariffa implements Serializable {

	private Long id;
	private String nome;
	private Map<String, Costo> costi;
	private List<Costo> costiValues;
	private List<Servizio> servizi;
	private Date dal;
	private Date al;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "tariffa")
	@MapKey(name = "giorno")
	public Map<String, Costo> getCosti() {
		if (this.costi == null)
			this.costi = new HashMap<String, Costo>();
		return costi;
	}

	@Transient
	public List<Costo> getCostiValues() {
		if (this.costi != null)
			costiValues = new ArrayList<Costo>();
		costiValues.addAll(costi.values());
		return new ArrayList<Costo>();
	}

	public void setCosti(Map<String, Costo> costi) {
		this.costi = costi;
	}

	public void addCosto(String nome, Costo costo) {
		getCosti().put(nome, costo);
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

	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

}
