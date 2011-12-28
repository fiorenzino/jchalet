package by.giava.gestionechalet.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "chalet_serviziprenotato")
public class ServizioPrenotato {

	private Long id;
	private Servizio servizio;
	private Date dal;
	private Date al;
	private List<Prenotazione> prenotazioni;
	private Contratto contratto;
	private boolean attivo = true;

	public ServizioPrenotato() {
		// TODO Auto-generated constructor stub
	}

	public ServizioPrenotato(Date dal, Date al, Servizio servizio) {
		this.dal = dal;
		this.al = al;
		this.servizio = servizio;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "servizioPrenotato")
	@SequenceGenerator(name = "servizioPrenotato", sequenceName = "servizioPrenotato")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("data")
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	@ManyToOne
	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
