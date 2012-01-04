package it.pisi79.geelection.service;

import it.pisi79.geelection.domain.Candidato;
import it.pisi79.geelection.domain.Elettore;
import it.pisi79.geelection.domain.Voto;
import it.pisi79.geelection.repository.CandidatiRepository;
import it.pisi79.geelection.repository.ElettoriRepository;
import it.pisi79.geelection.repository.VotiRepository;
import it.pisi79.geelection.service.util.MD5Utils;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class VotoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	VotiRepository votiRepository;
	@Inject
	ElettoriRepository elettoriRepository;
	@Inject
	MailService mailService;
	@Inject
	CandidatiRepository candidatiRepository;
	
	public void esprimi(String password, Long idCandidato) 
	throws VotoException {
		// verifica dati
		String digest = MD5Utils.cacolaDigest(password);
		Elettore e = elettoriRepository.findByDigest(digest);
		if ( e == null || e.getElezione() == null || e.getElezione().getId() == null ) {
			throw new VotoException("Errore generico nella valutazione delle credenziali. Si prega di riprovare piu' tardi.", mailService, password, idCandidato);
		}
		// verifica voto unico
		String nomeElezione = (e.getElezione().getNome() == null || e.getElezione().getNome().trim().length() == 0) ? "" : ("'"+e.getElezione().getNome().trim()+"'");
		if ( e.isVotato() ) {
			throw new VotoException("Hai già espresso il tuo voto per l'elezione " + nomeElezione + ".");
		}
		// verifica candidato
		Candidato c = candidatiRepository.find(idCandidato);
		if ( c == null ) {
			throw new VotoException("Errore generico nella valutazione del canddiato. Si prega di riprovare piu' tardi.", mailService, password, idCandidato);
		}
		if ( c.getElezione() == null || e.getElezione() == null || c.getElezione().getId().longValue() != e.getElezione().getId().longValue() ) {
			throw new VotoException("Il nominativo indicato non risulta candidato per l'elezione " +  nomeElezione +".", mailService, password, idCandidato);
		}
		// verifica elezione aperta
		if (c.getElezione().isConclusa()) {
			throw new VotoException("L'elezione " + c.getElezione().getNomeElezione()
					+ " si e' gia' conclusa.");
		}
		// creazione voto anonimo
		Voto v = new Voto();
		v.setCandidato(c);
		v.setEspressoIl(new Date());
		v = votiRepository.persist(v);
		if ( v == null || v.getId() == null ) {
			throw new VotoException("Errori nella registrazione del voto. Si prega di riprovare piu' tardi.", mailService, password, idCandidato);
		}
		// aggiornamento dati
		e.setVotato(true);
		boolean u = elettoriRepository.update(e);
		if ( ! u ) {
			votiRepository.delete(v.getId());
			throw new VotoException("Errori nella validazione del voto. Si prega di riprovare piu' tardi.", mailService, password, idCandidato);
		}
	}
	
}
