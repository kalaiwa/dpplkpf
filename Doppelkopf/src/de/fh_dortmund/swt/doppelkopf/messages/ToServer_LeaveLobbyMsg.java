package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

public class ToServer_LeaveLobbyMsg implements ToServerMessage{
	private static final long serialVersionUID = 1651793278187136743L;
	
	public static final String type = "LeaveLobby";
	private Client sender;
	private String username;
	
	public ToServer_LeaveLobbyMsg(Client sender) {
		this.sender = sender;
		this.username = sender.getPlayer().getName();
	}
	
	@Override
	public String getMessage() {
		return username + " tries to leave theLobby";
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
