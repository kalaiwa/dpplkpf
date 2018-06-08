package de.fh_dortmund.swt.doppelkopf;

import de.fh_dortmund.swt.doppelkopf.enumerations.Announcement;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import javafx.util.Pair;

@SuppressWarnings("restriction")
public class Game{

	private Trick[] rounds = new Trick[10];
	private State currentState = State.LOBBY;
	private int startingClient = 0; //first Client to log in
	private int activeClient = 0; 
	private Pair<Announcement, Client> reAnnouncement;
	private Pair<Announcement, Client> contraAnnouncement;
	
	public Game() {
		for(int i = 0; i < 10; i++) {
			rounds[i] = new Trick();
		}
	}
	
	public State nextState() {
		currentState = currentState.next();
		return currentState;
	}
	
	public int nextClient() {
		activeClient++;
		if(activeClient>3) activeClient = 0;
		return activeClient;
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

	public int getStartingClient() {
		return startingClient;
	}

	public void setStartingClient(int startingClient) {
		this.startingClient = startingClient;
	}

	public int getActiveClient() {
		return activeClient;
	}

	public void setActiveClient(int activeClient) {
		this.activeClient = activeClient;
	}

	public Pair<Announcement, Client> getReAnnouncement() {
		return reAnnouncement;
	}

	public void setReAnnouncement(Pair<Announcement, Client> reAnnouncement) {
		this.reAnnouncement = reAnnouncement;
	}

	public Pair<Announcement, Client> getContraAnnouncement() {
		return contraAnnouncement;
	}

	public void setContraAnnouncement(Pair<Announcement, Client> contraAnnouncement) {
		this.contraAnnouncement = contraAnnouncement;
	}

	public void clientPlaysCard(Card card) {
		rounds[currentState.getRoundNo()].addCard(card);
	}

	public String currentRoundToString() {
		return rounds[currentState.getRoundNo()].toString();
	}
	
	public Trick getCurrentRound() {
		return rounds[currentState.getRoundNo()];
	}

	
	
	
}
