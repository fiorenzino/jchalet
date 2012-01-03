package by.giava.gestionechalet.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.gestionechalet.controller.util.ConfigurazioniUtils;
import by.giava.gestionechalet.model.servizi.Ombrellone;
import by.giava.gestionechalet.pojo.Posto;
import by.giava.gestionechalet.servizi.TabelloneService;

@Named
@SessionScoped
public class TabelloneController implements Serializable {

	private static final long serialVersionUID = 1L;
	static String FACES_REDIRECT = "?faces-redirect=true";
	private static final String VISTA_STAGIONALI = "/spiaggia/vista-stagionali.xhtml"
			+ FACES_REDIRECT;
	private static final String VISTA_GIORNALIERI = "/spiaggia/vista-giorno.xhtml"
			+ FACES_REDIRECT;
	private static final String VISTA_MESE = "/spiaggia/vista-mese.xhtml"
			+ FACES_REDIRECT;

	@Inject
	ConfigurazioneController configurazioneController;

	@Inject
	TabelloneService tabelloneService;

	private Date dataSearch;

	private List<Posto[]> posti;
	private List<String> colonne;

	public List<Posto[]> getPosti() {
		return posti;
	}

	public void setPosti(List<Posto[]> posti) {
		this.posti = posti;
	}

	public List<String> getColonne() {
		return colonne;
	}

	public String vistaStagionali() {
		this.colonne = ConfigurazioniUtils.creaColonne(configurazioneController
				.getActual());
		List<Ombrellone> ombrelloni = tabelloneService
				.vistaStagionale(configurazioneController.getActual());
		this.posti = ConfigurazioniUtils.creaRighe(ombrelloni,
				configurazioneController.getActual());
		return VISTA_STAGIONALI;
	}

	public String vistaGiornalieri() {
		aggiornaVistaGiornaliera();
		return VISTA_GIORNALIERI;
	}

	public void aggiornaVistaGiornaliera() {
		this.colonne = ConfigurazioniUtils.creaColonne(configurazioneController
				.getActual());
		List<Ombrellone> ombrelloni = tabelloneService.vistaGiornaliera(
				configurazioneController.getActual(), getDataSearch());
		this.posti = ConfigurazioniUtils.creaRighe(ombrelloni,
				configurazioneController.getActual());
	}

	public Date getDataSearch() {
		if (dataSearch == null)
			dataSearch = new Date();
		return dataSearch;
	}

	public void setDataSearch(Date dataSearch) {
		this.dataSearch = dataSearch;
	}

}
