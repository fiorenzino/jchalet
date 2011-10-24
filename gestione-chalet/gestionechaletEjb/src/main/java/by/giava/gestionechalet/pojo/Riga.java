package by.giava.gestionechalet.pojo;

import java.io.Serializable;
import java.util.List;

import by.giava.gestionechalet.model.servizi.Ombrellone;

public class Riga implements Serializable {

	private Ombrellone[] ombrelloni;

	public Riga() {
	}

	public Riga(List<Ombrellone> ombrel) {
		if (ombrel != null && ombrel.size() > 0) {
			this.ombrelloni = new Ombrellone[ombrel.size()];
			for (int i = 0; i < ombrel.size(); i++) {
				this.ombrelloni[i] = ombrel.get(i);
			}
		}
	}

	public Ombrellone[] getOmbrelloni() {
		return ombrelloni;
	}

	public void setOmbrelloni(Ombrellone[] ombrelloni) {
		this.ombrelloni = ombrelloni;
	}

}
