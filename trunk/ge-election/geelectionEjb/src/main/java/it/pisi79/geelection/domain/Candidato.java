package it.pisi79.geelection.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Candidato implements Serializable, Comparable<Candidato> {

	private static final long serialVersionUID = 1L;

	Long id;
	String cognome;
	String nome;
	String altriDati;
	Elezione elezione;
	List<Voto> voti;
	int nroVoti;

	public Candidato() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAltriDati() {
		return altriDati;
	}

	public void setAltriDati(String altriDati) {
		this.altriDati = altriDati;
	}

	@ManyToOne
	public Elezione getElezione() {
		return elezione;
	}

	public void setElezione(Elezione elezione) {
		this.elezione = elezione;
	}

	@OneToMany( fetch = FetchType.EAGER, mappedBy = "candidato")
	public List<Voto> getVoti() {
		if ( voti == null ) {
			voti = new ArrayList<Voto>();
		}
		return voti;
	}

	public void setVoti(List<Voto> voti) {
		this.voti = voti;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Transient
	public int getNroVoti() {
		return nroVoti;
	}

	public void setNroVoti(int nroVoti) {
		this.nroVoti = nroVoti;
	}
	
	@Transient
	public String getCognomeNome() {
		return ( ( cognome == null ) ? "" : ( cognome.trim() + " " ) ) + ( ( nome == null ) ? "" : nome.trim() ); 
	}

	@Override
	public int compareTo(Candidato c) {
		return getCognomeNome().toUpperCase().compareTo(c.getCognomeNome().toUpperCase());
	}

	
}
