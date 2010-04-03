package it.jflower.chalet4.par;

import java.io.Serializable;
import java.util.Date;

public class Preventivo implements Serializable {

	private String servizio;
	private int num;
	private int numGiorni;
	private Date dal;
	private Date al;
	private double total;

	public Preventivo() {

	}

	public Preventivo(Date dal, Date al, String servizio, double total, int num) {
		this.dal = dal;
		this.al = al;
		this.servizio = servizio;
		this.total = total;
		this.num = num;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNumGiorni() {
		return numGiorni;
	}

	public void setNumGiorni(int numGiorni) {
		this.numGiorni = numGiorni;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
