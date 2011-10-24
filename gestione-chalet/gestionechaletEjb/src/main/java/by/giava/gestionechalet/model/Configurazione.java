package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.ArrayList;
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
	private String nome;
	private Long numeroColonne;
	private Long numeroRighe;
	private Long numeroFile;

	private Long numeroSdraio;
	private Long numeroLettini;
	private Long numeroCabine;

	private Date dataCreazione;
	private List<FilaOmbrelloni> fileOmbrelloni;
	private boolean attivo = true;

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
		if (fileOmbrelloni == null)
			this.fileOmbrelloni = new ArrayList<FilaOmbrelloni>();
		System.out.println("num file: " + this.fileOmbrelloni.size());
		return fileOmbrelloni;
	}

	public void setFileOmbrelloni(List<FilaOmbrelloni> fileOmbrelloni) {
		this.fileOmbrelloni = fileOmbrelloni;
	}

	public void addFilaOmbrelloni(FilaOmbrelloni filaOmbrelloni) {
		getFileOmbrelloni().add(filaOmbrelloni);
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Long getNumeroFile() {
		return numeroFile;
	}

	public void setNumeroFile(Long numeroFile) {
		this.numeroFile = numeroFile;
	}

	public Long getNumeroSdraio() {
		return numeroSdraio;
	}

	public void setNumeroSdraio(Long numeroSdraio) {
		this.numeroSdraio = numeroSdraio;
	}

	public Long getNumeroLettini() {
		return numeroLettini;
	}

	public void setNumeroLettini(Long numeroLettini) {
		this.numeroLettini = numeroLettini;
	}

	public Long getNumeroCabine() {
		return numeroCabine;
	}

	public void setNumeroCabine(Long numeroCabine) {
		this.numeroCabine = numeroCabine;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
