package de.fh_dortmund.swt.doppelkopf;

import de.fh_dortmund.swt.doppelkopf.enumerations.Announcement;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import javafx.util.Pair;

public class Game{

	private Trick[] rounds = new Trick[10];
	private State currentState = State.LOBBY;
	private int startingPlayer = 0; //first player to log in
	private int activePlayer = 0; 
	private Pair<Announcement, Player> reAnnouncement;
	private Pair<Announcement, Player> contraAnnouncement;
	
	public Game() {
		for(int i = 0; i < 10; i++) {
			rounds[i] = new Trick();
		}
	}
	
	public State nextState() {
		currentState = currentState.next();
		return currentState;
	}
	
	public int nextPlayer() {
		activePlayer++;
		if(activePlayer>3) activePlayer = 0;
		return activePlayer;
	}
	
	public Trick[] getRounds() {
		return rounds;
	}

	public void setRounds(Trick[] rounds) {
		this.rounds = rounds;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getStartingPlayer() {
		return startingPlayer;
	}

	public void setStartingPlayer(int startingPlayer) {
		this.startingPlayer = startingPlayer;
	}

	public int getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Pair<Announcement, Player> getReAnnouncement() {
		return reAnnouncement;
	}

	public void setReAnnouncement(Pair<Announcement, Player> reAnnouncement) {
		this.reAnnouncement = reAnnouncement;
	}

	public Pair<Announcement, Player> getContraAnnouncement() {
		return contraAnnouncement;
	}

	public void setContraAnnouncement(Pair<Announcement, Player> contraAnnouncement) {
		this.contraAnnouncement = contraAnnouncement;
	}

	public void playerPlaysCard(Card card) {
		rounds[currentState.getRoundNo()].addCard(card);
	}

	public String currentRoundToString() {
		return rounds[currentState.getRoundNo()].toString();
	}
	
	public Trick getCurrentRound() {
		return rounds[currentState.getRoundNo()];
	}

	
	
	
}
