package it.pisi79.geelection.service;

public class VotoException extends Exception {

	public VotoException(String string) {
		super(string);
	}

	public VotoException(String message, MailService mailService, String password, Long idCandidato) {
		super(message);
		mailService.errore("Errore " + password + " per candidato " + idCandidato, message);
	}

	private static final long serialVersionUID = 1L;

}
