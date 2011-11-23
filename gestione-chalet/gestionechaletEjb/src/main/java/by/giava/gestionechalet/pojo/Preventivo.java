package by.giava.gestionechalet.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import by.giava.gestionechalet.model.Contratto;

@Entity
public class Preventivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String servizio;
	private Long numPezzi;
	private Long numGiorni;
	private Date dal;
	private Date al;
	private float total;
	private float costo;
	private Long tariffa;
	private Contratto contratto;

	public Preventivo() {

	}

	public Preventivo(Date dal, Date al, String servizio, float costo,
			Long numGiorni, Long numPezzi, Long tariffa) {
		this.dal = dal;
		this.al = al;
		this.servizio = servizio;
		this.total = numPezzi * costo;
		this.numPezzi = numPezzi;
		this.numGiorni = numGiorni;
		this.costo = costo;
		this.tariffa = tariffa;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public Long getNumPezzi() {
		return numPezzi;
	}

	public void setNumPezzi(Long numPezzi) {
		this.numPezzi = numPezzi;
	}

	public Long getNumGiorni() {
		return numGiorni;
	}

	public void setNumGiorni(Long numGiorni) {
		this.numGiorni = numGiorni;
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

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public Long getTariffa() {
		return tariffa;
	}

	public void setTariffa(Long tariffa) {
		this.tariffa = tariffa;
	}

	@ManyToOne
	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}
}
