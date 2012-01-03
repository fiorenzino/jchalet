package by.giava.gestionechalet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import by.giava.gestionechalet.enums.TipoServizioEnum;

@Entity
@Table(name = "chalet_tariffe")
public class Tariffa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Map<Long, Costo> costi;
	private List<Costo> costiValues;
	private List<Servizio> servizi;
	private Date dal;
	private Date al;
	private TipoServizioEnum serviceName;
	private boolean stagionale = false;
	private boolean attivo = true;
	private String fila;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tariffa")
	@SequenceGenerator(name = "tariffa", sequenceName = "tariffa")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "tariffa")
	@MapKey(name = "giorno")
	@OrderBy("giorno")
	public Map<Long, Costo> getCosti() {
		if (this.costi == null)
			this.costi = new LinkedHashMap<Long, Costo>();
		return costi;
	}

	@Transient
	public List<Costo> getCostiValues() {
		costiValues = new LinkedList<Costo>();
		costiValues.addAll(getCosti().values());
		return costiValues;
	}

	public void setCosti(Map<Long, Costo> costi) {
		this.costi = costi;
	}

	public void addCosto(Long nome, Costo costo) {
		getCosti().put(nome, costo);
	}

	public boolean containsCosto(Long nome) {
		return getCosti().containsKey(nome);
	}

	@ManyToMany(mappedBy = "tariffe")
	public List<Servizio> getServizi() {
		if (servizi == null)
			this.servizi = new ArrayList<Servizio>();
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
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

	@Enumerated(EnumType.STRING)
	public TipoServizioEnum getServiceName() {
		return serviceName;
	}

	public void setServiceName(TipoServizioEnum serviceName) {
		this.serviceName = serviceName;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public boolean isStagionale() {
		return stagionale;
	}

	public void setStagionale(boolean stagionale) {
		this.stagionale = stagionale;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}
}
