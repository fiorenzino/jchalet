package it.pisi79.geelection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private Date sent;

	private boolean success;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column( name = "RECEIVER")
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Column( length = 2048 )
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSent() {
		return sent;
	}
	public void setSent(Date sent) {
		this.sent = sent;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
