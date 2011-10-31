package by.giava.gestionechalet.pojo;

import java.io.Serializable;

public class Posto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int riga;
	private int colonna;
	private String numero;
	private String fila;
	private String descrizione;

	public Posto() {
		// TODO Auto-generated constructor stub
	}

	public Posto(int riga, int colonna, String numero, String fila) {
		this.colonna = colonna;
		this.riga = riga;
		this.numero = numero;
		this.fila = fila;
		this.descrizione = riga + "x" + colonna;
	}

	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public int getColonna() {
		return colonna;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}
}
