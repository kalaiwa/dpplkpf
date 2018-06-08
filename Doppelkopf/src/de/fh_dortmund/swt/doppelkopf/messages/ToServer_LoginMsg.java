package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

public class ToServer_LoginMsg implements ToServerMessage {

	private static final long serialVersionUID = -2101571612241831965L;
	
	public static final String type = "ToServer_Login";
	private Client sender;
	private String password;
	private String username;
	
	public ToServer_LoginMsg(Client sender, String name, String password) {
		this.sender = sender;
		this.username = name;
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

	@Override
	public Client getSender() {
		return sender;
	}

	@Override
	public String getType() {
		return type;
	}

}
