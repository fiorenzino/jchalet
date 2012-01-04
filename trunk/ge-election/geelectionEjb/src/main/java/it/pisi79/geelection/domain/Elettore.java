package it.pisi79.geelection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Elettore implements Serializable {

	private static final long serialVersionUID = 1L;

	Long id;
	String email;
	Date registratoIl;
	String password;
	String passwordDigest;
	Elezione elezione;
	boolean votato;

	public Elettore() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne
	public Elezione getElezione() {
		return elezione;
	}

	public void setElezione(Elezione elezione) {
		this.elezione = elezione;
	}

	public boolean isVotato() {
		return votato;
	}

	public void setVotato(boolean votato) {
		this.votato = votato;
	}

	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	public Date getRegistratoIl() {
		return registratoIl;
	}

	public void setRegistratoIl(Date registratoIl) {
		this.registratoIl = registratoIl;
	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
