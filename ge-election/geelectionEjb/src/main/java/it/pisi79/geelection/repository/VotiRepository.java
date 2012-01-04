package it.pisi79.geelection.repository;

import it.pisi79.geelection.domain.Voto;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@LocalBean
@Named
public class VotiRepository extends BaseRepository<Voto> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDefaultOrderBy() {
		return "id";
	}

}
