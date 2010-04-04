package it.jflower.chalet4.web;

import it.jflower.chalet4.ejb.OmbrelloniSession;
import it.jflower.chalet4.ejb.utils.TimeUtil;
import it.jflower.chalet4.par.Ombrellone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ColumnTest implements Serializable {

	private static final long serialVersionUID = 1L;

	public ColumnTest() {

	}

	@Inject
	OmbrelloniSession ombrelloniSession;

	private List<String> numColonne;
	private List<String[]> righe;
	private String[] ombRighe;
	private int fila;
	private Date dal;
	private Date al;

	public List<String[]> getRighe() {
		if (righe == null) {
			this.righe = new ArrayList<String[]>();
		}
		return righe;
	}

	public List<String> getNumColonne() {
		return numColonne;
	}

	public void setNumColonne(List<String> numColonne) {
		this.numColonne = numColonne;
	}

	public void setRighe(List<String[]> righe) {
		this.righe = righe;
	}

	public void calcolaColonne() {
		List<Ombrellone> ombrelloni = ombrelloniSession
				.getOmbrelloni("" + fila);
		this.ombRighe = new String[ombrelloni.size()];
		int m = 0;
		for (Ombrellone ombrellone : ombrelloni) {
			this.ombRighe[m] = ombrellone.getFila() + ":"
					+ ombrellone.getNumero();
			m++;
		}
		this.righe = new ArrayList<String[]>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDal());
		numColonne = new ArrayList<String>();
		while (cal.getTime().compareTo(getAl()) <= 0) {
			numColonne.add(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		for (Ombrellone ombrellone : ombrelloni) {
			String[] riga = new String[numColonne.size()];
			for (int i = 0; i < numColonne.size(); i++) {
				riga[i] = ombrellone.getFila() + ":" + ombrellone.getNumero()
						+ " ";
			}
			this.righe.add(riga);
		}
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

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public String[] getOmbRighe() {
		return ombRighe;
	}

	public void setOmbRighe(String[] ombRighe) {
		this.ombRighe = ombRighe;
	}

}
