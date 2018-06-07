package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Player;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_LoginReactionMsg implements Message {

	public static final String type = "ToPlayer_LoginReaction";
	private boolean success;
	private Player player;
	
	public ToPlayer_LoginReactionMsg(boolean success, Player player) {
		this.success = success;
	}
	
	@Override
	public String getMessage() {
		return success ? "Login was successful" : "Login failed";
	}

	public boolean isSuccess(){
		return success;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
