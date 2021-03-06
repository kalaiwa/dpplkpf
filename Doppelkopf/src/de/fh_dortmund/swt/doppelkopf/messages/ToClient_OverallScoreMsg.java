package de.fh_dortmund.swt.doppelkopf.messages;

import de.fh_dortmund.swt.doppelkopf.Client;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

/**
 * Sends a leaderboard String to the Client
 */
public class ToClient_OverallScoreMsg implements ToClientMessage{

	private static final long serialVersionUID = -1519541605206710285L;

	public static final String type = "ToClient_OverallScoreMsg";
	
	private String addressee;
	private String leaderboardString;

	public ToClient_OverallScoreMsg(String addressee, String leaderboardString) {
		this.addressee = addressee;
		this.leaderboardString = leaderboardString;
	}

	@Override
	public String getMessage() {
		return leaderboardString;
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
