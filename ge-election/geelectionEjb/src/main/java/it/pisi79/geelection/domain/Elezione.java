package it.pisi79.geelection.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Elezione {

	Long id;
	boolean attivo = false;
	String nome;
	String descrizione;
	Date creata;
	Date dal;
	Date al;
	List<Elettore> elettori;
	List<Candidato> candidati;
	String password;
	String digest;
	private int totaleVoti;
	private int totaleElettori;
	boolean modificabile = false;

	public Elezione() {
	}

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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Temporal(TemporalType.DATE)
	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	@Temporal(TemporalType.DATE)
	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elezione")
	public List<Elettore> getElettori() {
		if (elettori == null) {
			elettori = new ArrayList<Elettore>();
		}
		return elettori;
	}

	public void setElettori(List<Elettore> elettori) {
		this.elettori = elettori;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elezione")
	public List<Candidato> getCandidati() {
		if (candidati == null) {
			candidati = new ArrayList<Candidato>();
		}
		return candidati;
	}

	public void setCandidati(List<Candidato> candidati) {
		this.candidati = candidati;
	}

	@Transient
	public String getNomeElezione() {
		return (nome == null || nome.trim().length() == 0) ? "" : (/*"'"*/""
				+ nome.trim() + /*"'"*/"");

	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Date getCreata() {
		return creata;
	}

	public void setCreata(Date creata) {
		this.creata = creata;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	@Transient
	public boolean isConclusa() {
		Calendar c = Calendar.getInstance();
		c.setTime(al);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return new Date().after(c.getTime());
	}

	@Transient
	public boolean isAperta() {
		return new Date().after(dal);
	}

	public void setTotaleVoti(int totaleVoti) {
		this.totaleVoti = totaleVoti;
	}

	@Transient
	public int getTotaleVoti() {
		return totaleVoti;
	}

	
	@Transient
		public int getTotaleElettori() {
		return totaleElettori;
	}

	public void setTotaleElettori(int size) {
		this.totaleElettori = size;
	}

	@Transient
	public boolean isModificabile() {
		return this.modificabile;
	}
	public void setModificabile(boolean modificabile) {
		this.modificabile = modificabile;
	}
	

}
