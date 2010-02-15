package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Configurazione implements Serializable {

	private Long id;
	private Long numeroColonne;
	private Long numeroRighe;
	private Date dataCreazione;
	private List<FilaOmbrelloni> fileOmbrelloni;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroColonne() {
		return numeroColonne;
	}

	public void setNumeroColonne(Long numeroColonne) {
		this.numeroColonne = numeroColonne;
	}

	public Long getNumeroRighe() {
		return numeroRighe;
	}

	public void setNumeroRighe(Long numeroRighe) {
		this.numeroRighe = numeroRighe;
	}

	@OneToMany(mappedBy = "configurazione", fetch = FetchType.LAZY)
	@OrderBy("numero")
	public List<FilaOmbrelloni> getFileOmbrelloni() {
		return fileOmbrelloni;
	}

	public void setFileOmbrelloni(List<FilaOmbrelloni> fileOmbrelloni) {
		this.fileOmbrelloni = fileOmbrelloni;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
}
