package by.giava.gestionechalet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Preventivo implements Serializable {

	private String servizio;
	private Long numPezzi;
	private Long numGiorni;
	private Date dal;
	private Date al;
	private double total;
	private double costo;
	private Long tariffa;

	public Preventivo() {

	}

	public Preventivo(Date dal, Date al, String servizio, double costo,
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Long getTariffa() {
		return tariffa;
	}

	public void setTariffa(Long tariffa) {
		this.tariffa = tariffa;
	}
}
