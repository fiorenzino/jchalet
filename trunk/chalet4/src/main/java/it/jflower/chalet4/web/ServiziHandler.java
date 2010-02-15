package it.jflower.chalet4.web;

import it.jflower.chalet4.par.Cliente;
import it.jflower.chalet4.par.Servizio;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@ConversationScoped
@Stateful
@Named
public class ServiziHandler implements Serializable {

	private Servizio servizio;
	private Cliente cliente;
	private String start;

	@Inject
	private Conversation conversation;

	@Inject
	AllHandler allHandler;

	private @PersistenceContext(unitName = "chaletPU", type = PersistenceContextType.EXTENDED)
	EntityManager em;

	public ServiziHandler() {
		this.start = new Date().toString();
		System.out.println("NUOVO SERV HANDLER: " + start);
	}

	@PostConstruct
	public void createServizio() {
		conversation.begin();
		allHandler.addConv(conversation.getId(), conversation);
		System.out.println("si parte");
	}

	public void save(ActionEvent actionEvent) {
		em.persist(this.cliente);
		FacesMessage msg = new FacesMessage("Successful", "Welcome :"
				+ this.cliente.getNomeCognome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		allHandler.remConv(conversation.getId());
		conversation.end();
		this.start = null;
	}

	@Remove
	public void destroy() {
		System.out.println("DESTROY");
	}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public Cliente getCliente() {
		if (this.cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

}
