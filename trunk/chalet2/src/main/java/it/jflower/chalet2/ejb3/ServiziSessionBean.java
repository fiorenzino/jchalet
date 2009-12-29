package it.jflower.chalet2.ejb3;

import it.jflower.chalet2.ann.ChaletRepository;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class ServiziSessionBean {

	@Inject
	@ChaletRepository
	EntityManager em;

	@Inject
	Logger log;
}
