package by.giava.gestionechalet.pojo;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private String label;
	private String link;

	public MenuItem() {
	}

	public MenuItem(String label, String link) {
		this.label = label;
		this.link = link;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
