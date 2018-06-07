package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToServer_Logout implements Message {

	public static final String type = "ToServer_Logout";
	private String username;
	
	public ToServer_Logout(String name) {
		this.username = name;
	}
	
	@Override
	public String getMessage() {
		return username + " went offline";
	}
	
	public String getUserName() {
		return username;
	}

}
