package de.fh_dortmund.swt.doppelkopf.messages;

import java.util.Arrays;

import de.fh_dortmund.swt.doppelkopf.Player;
import de.fh_dortmund.swt.doppelkopf.interfaces.ToClientMessage;

/**
 * Sends player info to Client so he can build the leaderboard
 */
public class ToClient_LeaderBoardMsg implements ToClientMessage {

	private static final long serialVersionUID = -1519541605206710285L;

	public static final String type = "ToClient_LeaderBoard";
	
	private String addressee;
	private Player[] players;

	public ToClient_LeaderBoardMsg(String addressee, Player[] players) {
		this.addressee = addressee;		
		this.players = players;
		
		//sort by victory points
		Arrays.sort(players, (a, b) -> a.getVictoryPoints() - b.getVictoryPoints());
	}

	@Override
	public String getMessage() {
		String leaderboard = "";
		for (int i = 0; i < players.length; i++) {
			leaderboard += (i+1) + ". ";
			leaderboard += players[i].getName() + " (" + players[i].getVictoryPoints() + ")\n";
		}
		return leaderboard;
	}

	public Player[] getPlayers() {
		return players;
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
