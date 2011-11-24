package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Prenotazione implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Contratto Contratto;
	private Date data;
	private Servizio servizio;

	private boolean attivo = true;
	private String tipoServizio;
	private Date dataDal;
	private Date dataAl;
	private String numero;
	private int numeroSdraie;
	private int numeroLettini;
	private int numeroCabine;
	private int numeroOmbrelloni;
	private int numeroSedie;
	private String fila;
	private boolean occupato;
	private boolean soloLiberi;
	private boolean soloStagionali;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Contratto getContratto() {
		return Contratto;
	}

	public void setContratto(Contratto contratto) {
		Contratto = contratto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	
	@Transient
	public String getSingleDayName() {
		if (data != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR);
		}
		return "";
	}

	@Transient
	public String getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	@Transient
	public Date getDataDal() {
		return dataDal;
	}

	public void setDataDal(Date dataDal) {
		this.dataDal = dataDal;
	}

	@Transient
	public Date getDataAl() {
		return dataAl;
	}

	public void setDataAl(Date dataAl) {
		this.dataAl = dataAl;
	}

	@Transient
	public int getNumeroSdraie() {
		return numeroSdraie;
	}

	public void setNumeroSdraie(int numeroSdraie) {
		this.numeroSdraie = numeroSdraie;
	}

	@Transient
	public int getNumeroLettini() {
		return numeroLettini;
	}

	public void setNumeroLettini(int numeroLettini) {
		this.numeroLettini = numeroLettini;
	}

	@Transient
	public int getNumeroCabine() {
		return numeroCabine;
	}

	public void setNumeroCabine(int numeroCabine) {
		this.numeroCabine = numeroCabine;
	}

	@Transient
	public int getNumeroOmbrelloni() {
		return numeroOmbrelloni;
	}

	public void setNumeroOmbrelloni(int numeroOmbrelloni) {
		this.numeroOmbrelloni = numeroOmbrelloni;
	}

	@Transient
	public int getNumeroSedie() {
		return numeroSedie;
	}

	public void setNumeroSedie(int numeroSedie) {
		this.numeroSedie = numeroSedie;
	}

	@Transient
	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	@Transient
	public boolean isSoloLiberi() {
		return soloLiberi;
	}

	public void setSoloLiberi(boolean soloLiberi) {
		this.soloLiberi = soloLiberi;
	}

	@Transient
	public boolean isSoloStagionali() {
		return soloStagionali;
	}

	public void setSoloStagionali(boolean soloStagionali) {
		this.soloStagionali = soloStagionali;
	}

}
