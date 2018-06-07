package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToServer_LoginMsg implements Message {

	public static final String type = "ToServer_Login";
	public String password;
	public String username;
	
	public ToServer_LoginMsg(String name, String password) {
		username = name;
		this.password = password;
	}
	
	@Override
	public String getMessage() {
		return username + " tries to login";
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

}
