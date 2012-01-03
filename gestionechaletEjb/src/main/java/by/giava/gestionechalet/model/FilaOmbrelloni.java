package by.giava.gestionechalet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "chalet_fileombrelloni")
public class FilaOmbrelloni implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer numero;
	private Integer dal;
	private Integer al;
	private Configurazione configurazione;
	private boolean attivo = true;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "filaOmbrelloni")
	@SequenceGenerator(name = "filaOmbrelloni", sequenceName = "filaOmbrelloni")
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

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
