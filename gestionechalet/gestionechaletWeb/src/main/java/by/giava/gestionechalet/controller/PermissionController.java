package by.giava.gestionechalet.controller;

import it.coopservice.commons2.controllers.AbstractPermissionController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class PermissionController extends AbstractPermissionController {

	@Override
	public String getLoginAlias() {
		return JSFUtils.getUserName();
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO... ciclando su cosa?
		return true;
	}

}
