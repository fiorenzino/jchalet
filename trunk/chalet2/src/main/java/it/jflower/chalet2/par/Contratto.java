package it.jflower.chalet2.par;

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
	private List<Servizio> servizi;
	private Date dataStipula;
	private Date dataInit;
	private Date dataEnd;
	private String note;
	private Float importoIniziale;
	private Float importoFinale;
	private Float acconto;
	private Float sconto;
	private Float extra;

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

	public Float getExtra() {
		return extra;
	}

	public void setExtra(Float extra) {
		this.extra = extra;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Servizio.class)
	public List<Servizio> getServizi() {
		if (this.servizi == null)
			this.servizi = new ArrayList<Servizio>();
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}

	public void addServizio(Servizio servizio) {
		getServizi().add(servizio);
	}

}
