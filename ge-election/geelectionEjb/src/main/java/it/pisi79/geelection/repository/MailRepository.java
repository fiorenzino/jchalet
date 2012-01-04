package it.pisi79.geelection.repository;

import it.pisi79.geelection.domain.Mail;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@LocalBean
@Named
public class MailRepository extends BaseRepository<Mail> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "id";
	}

}
