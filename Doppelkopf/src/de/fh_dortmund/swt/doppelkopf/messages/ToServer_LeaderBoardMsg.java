package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToServerMessage;

/**
 * Requests leaderboard info from server
 */
public class ToServer_LeaderBoardMsg implements ToServerMessage {

	private static final long serialVersionUID = -2101571612241831965L;
	
	public static final String type = "ToServer_LeaderBoardMsg";
	private Client sender;
	
	public ToServer_LeaderBoardMsg(Client sender) {
		this.sender = sender;
	}
	
	@Override
	public String getMessage() {
		return sender + " requests leaderboard";
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
