package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import by.giava.gestionechalet.enums.StatoContrattoEnum;

@Entity
@Table(name = "chalet_contratti")
public class Contratto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Cliente cliente;
	private StatoContrattoEnum stato;
	private List<ServizioPrenotato> serviziPrenotati;
	private List<Preventivo> preventivi;
	private Date dataStipula;
	private Date dataChiusura;
	private String note;
	private float importoIniziale;
	private float importoFinale;
	private float importoAcconto;
	private float importoVariazione;

	private boolean attivo = true;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "contratto")
	@SequenceGenerator(name = "contratto", sequenceName = "contratto")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Cliente getCliente() {
		if (cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getImportoIniziale() {
		return importoIniziale;
	}

	public void setImportoIniziale(float importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	@OneToMany(cascade = { CascadeType.ALL }, targetEntity = ServizioPrenotato.class, fetch = FetchType.EAGER)
	public List<ServizioPrenotato> getServiziPrenotati() {
		if (this.serviziPrenotati == null)
			this.serviziPrenotati = new ArrayList<ServizioPrenotato>();
		return serviziPrenotati;
	}

	public void setServiziPrenotati(List<ServizioPrenotato> serviziPrenotati) {
		this.serviziPrenotati = serviziPrenotati;
	}

	public void addServizioPrenotato(ServizioPrenotato servizioPrenotato) {
		getServiziPrenotati().add(servizioPrenotato);
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public float getImportoFinale() {
		return importoFinale;
	}

	public void setImportoFinale(float importoFinale) {
		this.importoFinale = importoFinale;
	}

	public float getImportoAcconto() {
		return importoAcconto;
	}

	public void setImportoAcconto(float importoAcconto) {
		this.importoAcconto = importoAcconto;
	}

	public float getImportoVariazione() {
		return importoVariazione;
	}

	public void setImportoVariazione(float importoVariazione) {
		this.importoVariazione = importoVariazione;
	}

	@OneToMany(cascade = { CascadeType.ALL }, targetEntity = Preventivo.class, fetch = FetchType.LAZY)
	public List<Preventivo> getPreventivi() {
		if (this.preventivi == null)
			this.preventivi = new ArrayList<Preventivo>();
		return preventivi;
	}

	public void setPreventivi(List<Preventivo> preventivi) {
		this.preventivi = preventivi;
	}

	public void addPreventivo(Preventivo preventivo) {
		getPreventivi().add(preventivo);
	}

	@Enumerated(EnumType.STRING)
	public StatoContrattoEnum getStato() {
		return stato;
	}

	public void setStato(StatoContrattoEnum stato) {
		this.stato = stato;
	}

}
