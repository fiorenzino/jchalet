package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.Date;

public class Ricerca implements Serializable {

	private Long idClasse;
	private String nome;
	private Integer numero;
	private Integer numeroSdraie;
	private Integer numeroLettini;
	private Integer numeroCabine;
	private Integer fila;
	private Date dal;
	private Date al;

	public Long getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getFila() {
		return fila;
	}

	public void setFila(Integer fila) {
		this.fila = fila;
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

	public Integer getNumeroSdraie() {
		return numeroSdraie;
	}

	public void setNumeroSdraie(Integer numeroSdraie) {
		this.numeroSdraie = numeroSdraie;
	}

	public Integer getNumeroLettini() {
		return numeroLettini;
	}

	public void setNumeroLettini(Integer numeroLettini) {
		this.numeroLettini = numeroLettini;
	}

	public Integer getNumeroCabine() {
		return numeroCabine;
	}

	public void setNumeroCabine(Integer numeroCabine) {
		this.numeroCabine = numeroCabine;
	}

}
