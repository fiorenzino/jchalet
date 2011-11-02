package by.giava.gestionechalet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Ricerca implements Serializable {

	private Long idClasse;
	private String nome;
	private int numero;
	private int numeroSdraie;
	private int numeroLettini;
	private int numeroCabine;
	private int numeroOmbrelloni;
	private int numeroSedie;
	private int fila;
	private Date dal;
	private Date al;

	public Ricerca() {
		// TODO Auto-generated constructor stub
	}

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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
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

	public int getNumeroSdraie() {
		return numeroSdraie;
	}

	public void setNumeroSdraie(int numeroSdraie) {
		this.numeroSdraie = numeroSdraie;
	}

	public int getNumeroLettini() {
		return numeroLettini;
	}

	public void setNumeroLettini(int numeroLettini) {
		this.numeroLettini = numeroLettini;
	}

	public int getNumeroCabine() {
		return numeroCabine;
	}

	public void setNumeroCabine(int numeroCabine) {
		this.numeroCabine = numeroCabine;
	}

	public int getNumeroOmbrelloni() {
		return numeroOmbrelloni;
	}

	public void setNumeroOmbrelloni(int numeroOmbrelloni) {
		this.numeroOmbrelloni = numeroOmbrelloni;
	}

	public int getNumeroSedie() {
		return numeroSedie;
	}

	public void setNumeroSedie(int numeroSedie) {
		this.numeroSedie = numeroSedie;
	}

}
