package it.jflower.chalet4.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FilaOmbrelloni implements Serializable {

	private Long id;
	private Integer numero;
	private Integer dal;
	private Integer al;
	private Configurazione configurazione;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDal() {
		return dal;
	}

	public void setDal(Integer dal) {
		this.dal = dal;
	}

	public Integer getAl() {
		return al;
	}

	public void setAl(Integer al) {
		this.al = al;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Configurazione getConfigurazione() {
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

	public Integer getNumero() {
		return numero;
	}
}
