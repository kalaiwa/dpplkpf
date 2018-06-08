package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

public class ToClient_NextPlayerMsg implements ToClientMessage {

	private static final long serialVersionUID = -8720777268181163831L;

	public static final String  type = "ToClient_NextPlayer";

	private String addressee;
	private String name;
	
	public ToClient_NextPlayerMsg(String addressee, String playerName) {
		this.addressee = addressee;
		this.name = playerName;
		}
	
	@Override
	public String getMessage() {
		return "It's " + name + "s turn";
	}
	
	public String getPlayerName() {
		return name;
	}

	@Override
	public String getAddressee() {
		return addressee;
	}

	@Override
	public String getType() {
		return type;
	}
	
	
}
