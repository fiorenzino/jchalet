package it.pisi79.geelection.service;

public class CredenzialiException extends Exception {

	public CredenzialiException(String string) {
		super(string);
	}

	public CredenzialiException(String message, MailService mailService, String contatto, Long idElezione) {
		super(message);
		mailService.errore("Errore " + contatto + " per elezione " + idElezione, message);
	}

	private static final long serialVersionUID = 1L;

}
