package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_NextPlayerMsg implements Message {

	public static final String  type = "ToPlayer_NextPlayer";
	private String name;
	
	public ToPlayer_NextPlayerMsg(String playerName) {
		this.name = playerName;
	}
	
	@Override
	public String getMessage() {
		return "It's " + name + "s turn";
	}
	
	public String getPlayerName() {
		return name;
	}
	
	
}
