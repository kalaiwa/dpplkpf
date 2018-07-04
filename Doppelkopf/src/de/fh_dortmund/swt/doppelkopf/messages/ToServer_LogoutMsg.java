package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

/**
 * Informs the server of logout 
 */
public class ToServer_LogoutMsg implements ToServerMessage {

	private static final long serialVersionUID = 1816110379883391660L;

	public static final String type = "ToServer_Logout";
	
	private Client sender;
	
	public ToServer_LogoutMsg(Client sender) {
		this.sender = sender;
	}
	
	@Override
	public String getMessage() {
		return sender.getPlayer().getName() + " went offline";
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
