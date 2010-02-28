package it.jflower.chalet4.web;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PropertiesHandler implements Serializable {

	private SelectItem[] serviziItems = new SelectItem[] {};

	public SelectItem[] getServiziItems() {
		if (serviziItems.length == 0) {
			serviziItems = new SelectItem[4];
			serviziItems[0] = new SelectItem(1, "ombrellone");
			serviziItems[1] = new SelectItem(2, "sdraio");
			serviziItems[2] = new SelectItem(3, "lettino");
			serviziItems[3] = new SelectItem(4, "cabina");
		}
		return serviziItems;
	}
}
