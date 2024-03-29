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

@Entity
@Table(name = "chalet_pagamenti")
public class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date data;
	private float prezzo;
	private Contratto contratto;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pagamento")
	@SequenceGenerator(name = "pagamento", sequenceName = "pagamento")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	@ManyToOne
	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
