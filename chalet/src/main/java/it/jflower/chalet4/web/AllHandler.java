package it.jflower.chalet4.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.inject.Named;

@ApplicationScoped
@Named
public class AllHandler implements Serializable {
	Map<String, Conversation> convs;

	public Map<String, Conversation> getConvs() {
		if (this.convs == null)
			this.convs = new HashMap<String, Conversation>();
		return convs;
	}

	public void addConv(String key, Conversation value) {
		System.out.println("ID: " + value.getId());
		System.out.println("TIMEOUT: " + value.getTimeout());
		getConvs().put(key, value);
	}

	public void remConv(String key) {
		if (getConvs().containsKey(key)) {
			getConvs().remove(key);
		}
	}

	public void setConvs(Map<String, Conversation> convs) {
		this.convs = convs;
	}

	public List<String> getAllConvs() {
		List<String> conversations = new ArrayList<String>();
		for (String conversation : getConvs().keySet()) {
			conversations.add(conversation);
		}
		return conversations;
	}
}
