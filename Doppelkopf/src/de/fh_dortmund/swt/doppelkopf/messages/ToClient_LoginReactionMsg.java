package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Player;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

/**
 * Informs the Client how successful his login attempt was
 */
public class ToClient_LoginReactionMsg implements ToClientMessage {

	private static final long serialVersionUID = -4304791908032282967L;

	public static final String type = "ToClient_LoginReaction";

	private String addressee;
	private Player player;
	private boolean success;
	
	public ToClient_LoginReactionMsg(String addressee, Player player, boolean success) {
		this.addressee = addressee;
		this.player = player;
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

	@Override
	public String getAddressee() {
		return addressee;
	}

	@Override
	public String getType() {
		return type;
	}
	
}
