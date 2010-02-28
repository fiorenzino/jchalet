package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Prenotazione implements Serializable {
	private Long id;
	private Contratto Contratto;
	private Date data;
	private String singleDayName;
	private Servizio servizio;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}
}
