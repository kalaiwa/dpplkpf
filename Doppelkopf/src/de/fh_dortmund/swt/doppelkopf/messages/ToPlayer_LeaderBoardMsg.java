package de.fh_dortmund.swt.doppelkopf.messages;

import java.util.Arrays;

import de.fh_dortmund.swt.doppelkopf.Player;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;

public class ToPlayer_LeaderBoardMsg implements Message {

	public static final String type = "ToPlayer_LeaderBoard";
	private Player[] players;

	public ToPlayer_LeaderBoardMsg(Player[] players) {
		this.players = players;

		//sort by victory points
		//TODO richtige Reihenfolge?
		Arrays.sort(players, (a, b) -> a.getVictoryPoints() - b.getVictoryPoints());
	}

	@Override
	public String getMessage() {
		String leaderboard = " ############### Leaderboard ############### \n";
		for (int i = 0; i < players.length; i++) {
			leaderboard += (i+1) + ". ";
			leaderboard += players[i].getName() + " (" + players[i].getVictoryPoints() + ")\n";
		}
		return leaderboard;
	}

	public Player[] getPlayers() {
		return players;
	}
}
