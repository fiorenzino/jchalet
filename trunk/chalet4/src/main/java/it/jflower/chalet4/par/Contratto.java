package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Contratto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Cliente cliente;
	private boolean aperto;
	private List<ServizioPrenotato> serviziPrenotati;
	private Date dataStipula;
	private Date dataInit;
	private Date dataEnd;
	private String note;
	private Float importoIniziale;
	private Float importoFinale;
	private Float acconto;
	private Float sconto;

	private boolean attivo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean getAperto() {
		return aperto;
	}

	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public Date getDataInit() {
		return dataInit;
	}

	public void setDataInit(Date dataInit) {
		this.dataInit = dataInit;
	}

	public Date getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(Date dataEnd) {
		this.dataEnd = dataEnd;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Float getImportoIniziale() {
		return importoIniziale;
	}

	public void setImportoIniziale(Float importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Servizio.class)
	public List<ServizioPrenotato> getServiziPrenotati() {
		if (this.serviziPrenotati == null)
			this.serviziPrenotati = new ArrayList<ServizioPrenotato>();
		return serviziPrenotati;
	}

	public void setServizi(List<ServizioPrenotato> serviziPrenotati) {
		this.serviziPrenotati = serviziPrenotati;
	}

	public void addServizio(ServizioPrenotato servizioPrenotato) {
		getServiziPrenotati().add(servizioPrenotato);
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public Float getImportoFinale() {
		return importoFinale;
	}

	public void setImportoFinale(Float importoFinale) {
		this.importoFinale = importoFinale;
	}

	public Float getAcconto() {
		return acconto;
	}

	public void setAcconto(Float acconto) {
		this.acconto = acconto;
	}

	public Float getSconto() {
		return sconto;
	}

	public void setSconto(Float sconto) {
		this.sconto = sconto;
	}

}
