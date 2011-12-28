package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import by.giava.gestionechalet.enums.TipoServizioEnum;

@Entity
@Table(name = "chalet_preventivi")
public class Preventivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TipoServizioEnum servizio;
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

	public Preventivo(Date dal, Date al, TipoServizioEnum servizio,
			float costo, Long numGiorni, Long numPezzi, Long tariffa) {
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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "preventivo")
	@SequenceGenerator(name = "preventivo", sequenceName = "preventivo")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public TipoServizioEnum getServizio() {
		return servizio;
	}

	public void setServizio(TipoServizioEnum servizio) {
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
