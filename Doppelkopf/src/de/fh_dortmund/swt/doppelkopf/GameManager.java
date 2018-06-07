package de.fh_dortmund.swt.doppelkopf;

import java.util.ArrayList;
import java.util.Random;

import de.fh_dortmund.swt.doppelkopf.enumerations.CardColour;
import de.fh_dortmund.swt.doppelkopf.enumerations.CardValue;
import de.fh_dortmund.swt.doppelkopf.enumerations.State;
import de.fh_dortmund.swt.doppelkopf.interfaces.Message;
import de.fh_dortmund.swt.doppelkopf.messages.ToPlayer_NextPlayerMsg;
import de.fh_dortmund.swt.doppelkopf.messages.ToServer_PlayedCardMsg;

public class GameManager {

	private Game game;
	private Client[] players = new Client[4];
	private static boolean waiting = false;
	private static GameManager gm = new GameManager();


	public static void main(String[] args) {
		gm.game = new Game();
		gm.game.nextState();
		gm.handOutCards();
		System.out.print("Players in re-party: ");
		for(int i = 0; i<4 ; i++) {
			if(gm.players[i].isRe()) System.out.print(gm.players[i].getName() + "   ");
		}
		while(!gm.game.nextState().equals(State.EVALUATION)) {
			System.out.println(" ############### Round " + (gm.game.getCurrentState().getRoundNo()+1) + " ############### ");
			gm.round();
		}
		System.out.println("Victory points re-party: " + gm.calcVictoryPoints());
		System.out.print("Players in re-party: ");
		for(int i = 0; i<4 ; i++) {
			if(gm.players[i].isRe()) System.out.print(gm.players[i].getName() + "   ");
		}
		//		HashMap<Player, Integer> points = new HashMap<>(4);
//		for(int i = 0; i < 10; i++) {
//			Trick round = gm.game.getRounds()[i];
//			Player winner = round.evaluate();
//			int pts = round.getPoints() + points.get(winner);
//			points.put(winner, pts);
//		}
//		Player winner = null;
//		int pts = -1;
//		for (Entry<Player, Integer> entry : points.entrySet()) {
//			if(entry.getValue()>pts)
//		}
		
		
		gm.game.nextState();
		System.out.println(" \nGame Over.");


	}



	public int calcVictoryPoints() {
		int victoryPointsRe = 0;
		int pointsRe = 0;
		for(int i = 0; i< 10; i++) {
			Trick trick = game.getRounds()[i];
			boolean winnerIsRe = trick.evaluate().isRe();
			if(winnerIsRe) pointsRe += trick.getPoints();
			if(trick.getPoints()>=40) {
				if(winnerIsRe) victoryPointsRe++;
				else victoryPointsRe--;
			}
			victoryPointsRe+=trick.caughtFoxes();
		}
		if(pointsRe>120) { 
			victoryPointsRe++;
			if(pointsRe>150) {
				victoryPointsRe++;
				if(pointsRe>180) {
					victoryPointsRe++;
					if(pointsRe>210) {
						victoryPointsRe++;
						if(pointsRe==240) {
							victoryPointsRe++;
						}}}}
		} else {
			victoryPointsRe--;
			if(pointsRe<90) { 
				victoryPointsRe--;
				if(pointsRe<60) {
					victoryPointsRe--;
					if(pointsRe<30) {
						victoryPointsRe--;
						if(pointsRe==0) {
							victoryPointsRe--;
						}}}}
		}


		//TODO ANSAGEN PRÜFEN


		return victoryPointsRe;
	}

	public void handOutCards() {
		ArrayList<Card> deck = createDeck();
		Random rand = new Random();
		for(int player = 0; player < 4; player ++) {
			for(int i = 10; i > 0; i--) {
				int idx = rand.nextInt(deck.size());
				Card card = deck.get(idx);
				deck.remove(idx);
				card.setOwner(players[player]);
				players[player].addCard(card);
			}
		}

	}

	public void receiveMessage(Message msg) {
		switch (msg.getMessage()) {
		case "PlayedCard":
			game.playerPlaysCard(((ToServer_PlayedCardMsg) msg).getCard());
			game.nextPlayer();
			waiting = false;
			break;

		default:
			break;
		}
	}

	public ArrayList<Card> createDeck(){
		ArrayList<Card> deck = new ArrayList<>();
		for (CardColour colour : CardColour.values()) {
			for (CardValue value : CardValue.values()) {
				deck.add(new Card(colour, value));
				deck.add(new Card(colour, value));
			}
		}
		return deck;
	}

	public void round() {
		game.setActivePlayer(game.getStartingPlayer());
		for(int i = 0; i < 4; i++) {
			waiting = true;
			players[game.getActivePlayer()].receiveMessage(new ToPlayer_NextPlayerMsg(players[game.getActivePlayer()].getName()));
			while(waiting) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(game.currentRoundToString().toString());
			
		}
		Player winner = game.getCurrentRound().evaluate();
		System.out.println(winner.getName() + " won this round (" + game.getCurrentRound().getPoints() + " pts)");
		for (int i = 0; i < players.length; i++) {
			if(players[i].equals(winner)) game.setStartingPlayer(i);
		}
	}

	public Game getGame() 
	{
		return game;
	}



	public void login(String username, String password) {
		//TODO if provided credentials match those on DB
		players.dd
	}

}
