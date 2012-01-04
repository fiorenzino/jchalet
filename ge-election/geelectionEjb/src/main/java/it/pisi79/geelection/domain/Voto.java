package it.pisi79.geelection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Voto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date espressoIl;
	private Candidato candidato;

	public Voto() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEspressoIl() {
		return espressoIl;
	}

	public void setEspressoIl(Date espressoIl) {
		this.espressoIl = espressoIl;
	}

	@ManyToOne
	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

}
