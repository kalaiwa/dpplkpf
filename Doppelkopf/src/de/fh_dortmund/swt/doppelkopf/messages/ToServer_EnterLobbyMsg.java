package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

public class ToServer_EnterLobbyMsg implements ToServerMessage {
	private static final long serialVersionUID = -1344876141295510984L;
	
	public static final String type = "EnterLobby";
	private Client sender;
	private String username;
	
	public ToServer_EnterLobbyMsg(Client sender) {
		this.sender = sender;
		this.username = sender.getPlayer().getName();
	}
	
	@Override
	public String getMessage() {
		return username + " tries to enter the Lobby";
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

